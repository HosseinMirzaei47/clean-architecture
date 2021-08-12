package com.speaking.partner.ui.utils

import android.view.View

fun View.setOnSingleClickListener(l: View.OnClickListener) {
    setOnClickListener(OnSingleClickListener(l))
}