package comtest.dandeliontest.todotest.ui.snackbar

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import comtest.dandeliontest.todotest.ui.utils.Event
import javax.inject.Inject

class SnackbarMessageManager @Inject constructor() {

    private var canLoadMessage = true

    private val messagesQueue = mutableListOf<Event<SnackbarMessage>>()
    private val result = MutableLiveData<Event<SnackbarMessage?>>()

    fun queueMessage(message: SnackbarMessage) {
        // If the new message is about the same change as a pending one, keep the new one. (rare)
        val sameMessage = messagesQueue.filter {
            it.peekContent().messageId == message.messageId
                    && it.peekContent().actionId == message.actionId
                    && !it.hasBeenHandled
        }
        if (sameMessage.isNotEmpty()) {
            messagesQueue.removeAll(sameMessage)
        }

        messagesQueue.add(Event(message))
        if (canLoadMessage) loadNextMessage()

        // Remove old messages
        if (messagesQueue.size > MAX_ITEMS) {
            messagesQueue.retainAll(messagesQueue.drop(messagesQueue.size - MAX_ITEMS))
        }
    }

    internal fun clearQueue() {
        messagesQueue.clear()
        result.postValue(Event(null))
        canLoadMessage = true
    }

    internal fun loadNextMessage() {
        canLoadMessage = true
        result.postValue(messagesQueue.firstOrNull { !it.hasBeenHandled })
    }

    internal fun observeNextMessage(): LiveData<Event<SnackbarMessage?>> {
        return result
    }

    internal fun lockLoader() {
        canLoadMessage = false
    }

    @VisibleForTesting
    fun hasQueuedMessage(stringId: Int): Boolean {
        return result.value?.peekContent()?.messageId == stringId
    }

    companion object {
        private const val MAX_ITEMS = 2
    }
}