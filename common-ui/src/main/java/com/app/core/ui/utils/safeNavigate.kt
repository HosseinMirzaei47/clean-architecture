package com.app.core.ui.utils

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDirections

/** safe way to navigate between fragments (no crash on multi touch and fast touch)*/
fun NavController.safeNavigate(directions: NavDirections) {
    val action = (currentDestination ?: graph).getAction(directions.actionId) ?: return
    val destId = action.destinationId
    if (currentDestination?.id != destId) {
        NavigationHandler.navigate(this, destId, directions.arguments)
    }
}

fun NavController.safeNavigate(uri: Uri) {
    NavigationHandler.navigate(this, uri)
}