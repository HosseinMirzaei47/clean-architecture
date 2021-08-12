package comtest.dandeliontest.todotest.ui.snackbar

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.Callback
import comtest.dandeliontest.todotest.ui.R
import comtest.dandeliontest.todotest.ui.utils.EventObserver

fun Fragment.bindToSnackBarManager(manager: SnackbarMessageManager) {
    lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            viewLifecycleOwnerLiveData.observe(
                this@bindToSnackBarManager,
                Observer { viewLifecycleOwner ->
                    var snackbar: Snackbar? = null

                    viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                        fun onResume() {
                            if (snackbar?.duration == Snackbar.LENGTH_INDEFINITE) snackbar?.show()
                            manager.observeNextMessage()
                                .observe(viewLifecycleOwner, EventObserver {
                                    it?.let { data ->
                                        snackbar = if (data.message != null) {
                                            Snackbar.make(
                                                requireView(),
                                                data.messageId!!,
                                                data.duration
                                            )
                                        } else {
                                            Snackbar.make(
                                                requireView(),
                                                data.messageId!!,
                                                data.duration
                                            )
                                        }.apply {
                                            if (data.actionId != null) {
                                                setAction(data.actionId) { data.actionCallback?.invoke() }
                                            }
                                            addCallback(object : Callback() {
                                                override fun onShown(sb: Snackbar?) {
                                                    manager.lockLoader()
                                                }

                                                override fun onDismissed(
                                                    transientBottomBar: Snackbar?,
                                                    event: Int
                                                ) {
                                                    manager.loadNextMessage()
                                                }
                                            })
                                        }
                                        snackbar?.view?.z = Float.MAX_VALUE
                                        try {
                                            snackbar?.setAnchorView(R.id.fabAddTask)
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                        snackbar?.show()
                                    } ?: run {
                                        snackbar?.dismiss()
                                        snackbar = null
                                    }
                                })
                        }

                        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                        fun onPause() {
                            snackbar?.dismiss()
                        }

                        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                        fun onDestroy() {
                            snackbar = null
                        }
                    })
                })
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            manager.clearQueue()
        }
    })
}