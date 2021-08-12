package comtest.dandeliontest.todotest.ui.utils

import android.view.View

fun View.setOnSingleClickListener(l: View.OnClickListener) {
    setOnClickListener(OnSingleClickListener(l))
}