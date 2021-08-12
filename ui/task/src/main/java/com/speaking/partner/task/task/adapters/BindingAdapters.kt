package com.speaking.partner.task.task.adapters

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.speaking.partner.model.models.task.*
import com.speaking.partner.model.models.task.PickerConstants.Priority.HIGH
import com.speaking.partner.model.models.task.PickerConstants.Priority.LOW
import com.speaking.partner.model.models.task.PickerConstants.Priority.MEDIUM
import com.speaking.partner.model.models.task.PickerConstants.Priority.NOT_SET
import com.speaking.partner.task.R
import com.speaking.partner.ui.bindingadapters.getColorStateList
import com.speaking.partner.ui.bindingadapters.loadImageRes
import com.speaking.partner.ui.utils.getCompleteDueDate
import com.speaking.partner.ui.utils.getDueTime
import com.speaking.partner.ui.utils.getTimeInSecond
import com.support.multicalendar.DandelionDate
import java.text.SimpleDateFormat

@BindingAdapter("toggleColor")
fun ImageView.toggleColor(toggle: Boolean) {
    imageTintList = getColorStateList(toggle, context)
}

@BindingAdapter(value = ["onDueDateSet", "onDueTimeSet"], requireAll = false)
fun TextView.onDueDateSet(dueDate: DandelionDate?, dueTime: DandelionDate?) {
    val formattedDueDate = dueDate?.toString(SimpleDateFormat("MMM d"))
    val formattedDueTime = dueTime?.toString(SimpleDateFormat(", hh:mm")) ?: ""
    text = context.getString(R.string.tasks_item_due_date_time, formattedDueDate, formattedDueTime)
}

@BindingAdapter("setFilterDate")
fun TextView.setFilterDate(dueDate: DandelionDate?) {
    text = dueDate?.let {
        context.getString(
            R.string.filter_date_label,
            dueDate.day,
            dueDate.month,
            dueDate.year
        )
    } ?: run { "" }
}

@BindingAdapter("onChipSelected")
fun Chip.onChipSelected(action: () -> Unit) {
    setOnCloseIconClickListener { action() }
}

@BindingAdapter("setColorByPriority")
fun View.setColorByPriority(priority: Int) {
    when (priority) {
        PriorityType.NOT_SET -> setBackgroundResource(R.drawable.gradient_priority_unset)
        PriorityType.LOW -> setBackgroundResource(R.drawable.gradient_priority_low)
        PriorityType.MEDIUM -> setBackgroundResource(R.drawable.gradient_priority_medium)
        PriorityType.HIGH -> setBackgroundResource(R.drawable.gradient_priority_high)
    }
}

@BindingAdapter("setSrcByPriority")
fun ImageView.setSrcByPriority(priority: Int) {
    when (priority) {
        PriorityType.LOW -> setImageResource(R.drawable.ic_priority_low_task_item)
        PriorityType.MEDIUM -> setImageResource(R.drawable.ic_priority_medium_task_item)
        PriorityType.HIGH -> setImageResource(R.drawable.ic_priority_high_task_item)
    }
}

@BindingAdapter("setSrcByDesc")
fun ImageView.setSrcByDesc(isEmpty: Boolean) {
    if (!isEmpty) setImageResource(R.drawable.ic_desc_task_item)
}

@BindingAdapter("centerIfEmpty")
fun TextView.centerIfEmpty(noDesc: Boolean) {
    val params = layoutParams as ConstraintLayout.LayoutParams
    params.bottomToBottom = if (noDesc) 0 else 1
    layoutParams = params
}

@BindingAdapter("onEndIconClick")
fun TextInputLayout.onEndIconClick(action: () -> Unit) {
    setEndIconOnClickListener { action() }
}

@BindingAdapter("handleImeAction")
fun TextInputEditText.handleImeAction(callback: () -> Unit) {
    val actionListener = TextView.OnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEND) callback(); true
    }
    setOnEditorActionListener(actionListener)
}

@BindingAdapter("disableIfNull", "uncheckIfNull")
fun RadioGroup.disableIfNull(dueDate: DandelionDate?, dueTime: DandelionDate?) {
    for (i in 0 until childCount) {
        val radioButton = (getChildAt(i) as RadioButton)
        dueDate?.let { radioButton.isEnabled = true } ?: run {
            radioButton.isEnabled = false
        }
        if (dueTime == null) radioButton.isChecked = i == 0
    }
}

@BindingAdapter("setPriority")
fun RadioGroup.setPriority(priority: Int) {
    when (priority) {
        PriorityType.NOT_SET -> check(R.id.rb_priority_unset)
        PriorityType.LOW -> check(R.id.rb_priority_low)
        PriorityType.MEDIUM -> check(R.id.rb_priority_medium)
        PriorityType.HIGH -> check(R.id.rb_priority_high)
    }
}

@BindingAdapter("setDateState")
fun RadioGroup.setDateState(dueDate: DandelionDate?) {

    val today = DandelionDate().day
    val tomorrow = today + 1

    dueDate?.let {
        when (dueDate.day) {
            today -> check(R.id.rb_today)
            tomorrow -> check(R.id.rb_tomorrow)
            else -> {
                val dateButton = findViewById<RadioButton>(R.id.rb_date_custom)
                check(dateButton.id)
                dateButton.text =
                    if (dateButton.isChecked) getCompleteDueDate(context, dueDate)
                    else this.context.getString(R.string.pick_date)
            }
        }
    } ?: run { check(R.id.rb_no_date) }
}

@BindingAdapter("setDateState")
fun MaterialButton.setDateState(dueDate: DandelionDate?) {
    val today = DandelionDate().day
    val tomorrow = today + 1
    dueDate?.let {
        text = when (dueDate.day) {
            today -> context.getString(R.string.today)
            tomorrow -> context.getString(R.string.tomorrow)
            else -> getCompleteDueDate(context, dueDate)
        }
    } ?: run { text = context.getString(R.string.pick_date) }
}

@BindingAdapter("setCategoryState")
fun MaterialButton.setCategoryState(categories: List<Category>) {
    text = when {
        categories.isNotEmpty() && categories.size < 2 -> categories.first().title
        categories.isNotEmpty() -> context.getString(
            R.string.multiple_categories,
            categories.size.toString()
        )
        else -> context.getString(R.string.no_category)
    }
}

@BindingAdapter("setTimeState")
fun RadioGroup.setTimeState(dueTime: DandelionDate?) {

    val am9 = CommonDueTime.AM_9.getTimeInSecond()
    val pm6 = CommonDueTime.PM_6.getTimeInSecond()

    dueTime?.let {
        when (dueTime.timeInMillis / 1000) {
            am9 -> check(R.id.rb_am_nine)
            pm6 -> check(R.id.rb_pm_six)
            else -> {
                val timeButton = findViewById<RadioButton>(R.id.rb_due_time_custom)
                check(timeButton.id)
                timeButton.text =
                    if (timeButton.isChecked) getDueTime(context, dueTime)
                    else context.getString(R.string.pick_time)
            }
        }
    } ?: run { check(R.id.rb_no_time) }
}

@BindingAdapter("setReminderState")
fun RadioGroup.setReminderState(reminders: List<Reminder>) {
    val pm12 = CommonReminderTime.PM_12.getTimeInSecond()
    if (reminders.isNotEmpty()) {
        if (pm12 == reminders[0].time.timeInMillis / 1000) check(R.id.rb_pm_twelve)
        else {
            val reminderButton = findViewById<RadioButton>(R.id.rb_reminder_custom)
            check(reminderButton.id)
            reminderButton.text = getCompleteDueDate(context, reminders[0].time)
        }
    } else {
        check(R.id.rb_no_reminder)
        context.getString(R.string.pick_time)
    }
}

@BindingAdapter("loadPriorityRes")
fun ImageView.loadPriorityRes(priority: Int?) {
    when (priority) {
        NOT_SET -> loadImageRes(R.drawable.ic_priority)
        LOW -> loadImageRes(R.drawable.ic_item_priority_low)
        MEDIUM -> loadImageRes(R.drawable.ic_item_priority_medium)
        HIGH -> loadImageRes(R.drawable.ic_item_priority_high)
    }
}