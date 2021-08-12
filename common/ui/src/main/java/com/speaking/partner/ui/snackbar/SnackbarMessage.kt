package com.speaking.partner.ui.snackbar

import com.google.android.material.snackbar.Snackbar

data class SnackbarMessage(
    val messageId: Int? = null,
    val message: String? = null,
    val actionId: Int? = null,
    val duration: Int = Snackbar.LENGTH_SHORT,
    val actionCallback: (() -> Unit)? = null
) {
    operator fun invoke() {
        if (messageId == null && message == null) {
            throw IllegalArgumentException("One of messageId or message must be filled")
        }
    }
}