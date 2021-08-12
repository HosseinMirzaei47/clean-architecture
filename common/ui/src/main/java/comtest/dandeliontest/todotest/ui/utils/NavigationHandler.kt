package comtest.dandeliontest.todotest.ui.utils

import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController

object NavigationHandler {

    private const val DELAY_MILLIS = 500L

    private var previousClickTimeMillis = 0L

    fun navigate(navController: NavController, @IdRes destination: Int, args: Bundle? = null) {
        try {
            val currentTimeMillis = System.currentTimeMillis()
            if (currentTimeMillis >= previousClickTimeMillis + DELAY_MILLIS) {
                previousClickTimeMillis = currentTimeMillis
                navController.navigate(destination, args)
            }
        } catch (exception: IllegalArgumentException) {
        }
    }

    fun navigate(navController: NavController, uri: Uri) {
        try {
            val currentTimeMillis = System.currentTimeMillis()
            if (currentTimeMillis >= previousClickTimeMillis + DELAY_MILLIS) {
                previousClickTimeMillis = currentTimeMillis
                navController.navigate(uri)
            }
        } catch (exception: IllegalArgumentException) {
        }
    }
}