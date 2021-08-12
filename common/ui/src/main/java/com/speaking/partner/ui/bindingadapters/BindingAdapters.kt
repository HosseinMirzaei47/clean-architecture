package com.speaking.partner.ui.bindingadapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.textview.MaterialTextView
import com.speaking.partner.ui.R
import com.speaking.partner.ui.utils.EpoxyEventListener
import com.speaking.partner.ui.utils.OnSingleClickListener
import kotlin.math.abs

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String) {
    load(url) {
        crossfade(true)
    }
}

@BindingAdapter("goneUnless")
fun View.goneUnless(value: Boolean) {
    isGone = value
}

@BindingAdapter("visibleUnless")
fun View.visibleUnless(hide: Boolean) {
    isVisible = !hide
}

@BindingAdapter("invisibleUnless")
fun View.invisibleUnless(visible: Boolean) {
    isInvisible = !visible
}

@BindingAdapter("goneIfNull")
fun View.goneIfNull(value: Any?) {
    isGone = value == null
}

@BindingAdapter("onSafeClick")
fun View.setOnSafeClick(callback: () -> Unit) {
    setOnClickListener(OnSingleClickListener { callback() })
}

@BindingAdapter("setTextByCategory")
fun TextView.setTextByCategory(titleOrId: String?) {
    titleOrId?.toIntOrNull()?.let {
        val string = context.getString(it)
        text = string
    } ?: run {
        text = titleOrId
    }
}

@BindingAdapter("onLongClick")
fun View.onLongClick(action: () -> Unit) {
    setOnLongClickListener { action(); true }
}

@BindingAdapter("enabledColor")
fun View.enabledColor(value: Boolean) {
    isEnabled = value
    backgroundTintList = getColorStateList(value, context)
}

fun getColorStateList(enabled: Boolean, context: Context) = if (enabled) {
    ColorStateList.valueOf(
        ContextCompat.getColor(
            context, R.color.todo_secondary
        )
    )
} else {
    ColorStateList.valueOf(
        ContextCompat.getColor(
            context, R.color.darker_gray
        )
    )
}

@BindingAdapter("onCheckedChange")
fun CheckBox.onCheckedChange(action: EpoxyEventListener) {
    setOnClickListener { action.onEvent(isChecked) }
}

@BindingAdapter("onCheckedChange")
fun Chip.onCheckedChange(action: EpoxyEventListener) {
    setOnClickListener { action.onEvent(isChecked) }
}

@BindingAdapter("onCheckedChange")
fun MaterialButton.onCheckedChange(action: EpoxyEventListener) {
    isChecked = false
    setOnClickListener { action.onEvent(isChecked) }
}

@BindingAdapter("setStrokeColorRes")
fun MaterialCardView.setStrokeColorRes(@ColorRes color: Int) {
    strokeColor = ContextCompat.getColor(context, color)
}

@BindingAdapter("loadImageRes")
fun ImageView.loadImageRes(@DrawableRes imageRes: Int?) {
    isVisible = imageRes != null
    Glide.with(context).load(imageRes).into(this)
}

@BindingAdapter("setCompleteCounts")
fun TextView.setCompleteCounts(count: Int) {
    text = context.getString(R.string.complete_count, count)
}

@BindingAdapter("setStrike")
fun TextView.setStrike(flag: Boolean) {
    paintFlags = if (flag)
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    else
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

@BindingAdapter("goneUnlessWithAnimation")
fun View.goneUnlessWithAnimation(value: Boolean) {
    if (value) {
        animate().alpha(0.0f).withEndAction { isGone = true }
    } else {
        animate().alpha(1.0f).withStartAction { isGone = false }
    }
}

@BindingAdapter("setTextColor")
fun Chip.setTextColor(isSelected: Boolean) {
    setTextColor(getItemBackground(isSelected))
}

@BindingAdapter("setCardBackground")
fun MaterialCardView.setCardBackground(isSelected: Boolean) {
    setCardBackgroundColor(getItemBackground(isSelected))
}

@BindingAdapter("setWeekText")
fun TextView.setWeekText(selectedWeek: Int) {
    text = when {
        selectedWeek == 0 -> context.getString(R.string.this_week)
        selectedWeek == 1 -> context.getString(R.string.next_week)
        selectedWeek > 1 -> context.getString(R.string.some_week_later, selectedWeek)
        selectedWeek == -1 -> context.getString(R.string.last_week)
        else -> context.getString(R.string.some_week_ago, abs(selectedWeek))
    }
}

@BindingAdapter("setResText")
fun View.setResText(labelOrId: String?) {
    val label = labelOrId?.toIntOrNull()?.let {
        if (it < 31) it.toString() /* Is the number of a day in month*/
        else context.getString(it) /* Is string resource */
    } ?: labelOrId
    isVisible = label.isNullOrBlank().not()
    when (this) {
        is Chip -> text = label
        is MaterialTextView -> text = label
        is MaterialButton -> text = label
    }
}

private fun View.getItemBackground(
    isSelected: Boolean,
    enableColor: Int = R.color.picker_color_enable,
    disableColor: Int = R.color.light_gray,
): Int {
    return if (isSelected) ContextCompat.getColor(context, enableColor)
    else ContextCompat.getColor(context, disableColor)
}

@BindingAdapter("setTextList")
fun TextView.setTextList(labels: List<String>?) {
    labels?.let { label ->
        var finalText = ""
        label.forEach { finalText += it + "\n" }
        text = finalText
    }
}