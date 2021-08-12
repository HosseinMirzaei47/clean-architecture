package com.speaking.partner.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.part.livetaskcore.livatask.CombinedException
import com.speaking.partner.model.models.task.*
import com.speaking.partner.model.models.task.PickerConstants.ItemType.PICKER_CATEGORY
import com.speaking.partner.shared.resource.Resource
import com.speaking.partner.ui.R
import com.speaking.partner.ui.snackbar.SnackbarMessage
import com.support.multicalendar.DandelionDate
import com.support.multicalendar.models.CalendarType
import java.util.*
import com.part.livetaskcore.Resource as LiveTaskResult

val containsItem: PickerModel.(PickerItem) -> Boolean = { pickerItem ->
    selectedItem == pickerItem.id
}

fun Int.getMonthName(style: Int = Calendar.LONG): String? {
    val calendar = Calendar.getInstance()
    calendar[Calendar.MONTH] = this
    val monthDisplayName = calendar.getDisplayName(Calendar.MONTH, style, Locale.getDefault())
    calendar.clear(Calendar.MONTH)
    return monthDisplayName
}

fun Int.getTimeInMills(): Long {
    val calendar = Calendar.getInstance()
    calendar[Calendar.HOUR_OF_DAY] = this
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    val timeInMillis = calendar.timeInMillis
    calendar.clear(Calendar.HOUR_OF_DAY)
    return timeInMillis
}

fun Int.getTimeInSecond() = getTimeInMills() / 1000

fun Task.areContentsTheSame(newItem: Task) =
    this.title.length == newItem.title.length &&
            this.description?.length == newItem.description?.length &&
            this.dueDate?.timeInMillis == newItem.dueDate?.timeInMillis &&
            this.isDone == newItem.isDone &&
            this.reminders.size == newItem.reminders.size &&
            this.categories.size == newItem.categories.size

fun DandelionDate.toDueDate() = DueDate(
    year = year,
    month = month,
    day = day,
    hour = hour,
    minute = minute
)

fun DueDate.toDandelionDate() = DandelionDate(
    calendarType = CalendarType.GREGORIAN,
    year = this.year,
    month = this.month,
    day = this.day,
    hour = this.hour,
    minute = this.minute,
    second = 0,
    millisSecond = 0,
    timeZone = TimeZone.getDefault()
)

fun Exception.getSnackBarMessage(): SnackbarMessage {
    return SnackbarMessage(
        messageId = message.hashCode(),
        message = message
    )
}

@SuppressLint("ClickableViewAccessibility")
fun View.disallowParentTouchEvents() {
    setOnTouchListener { v, event ->
        v.parent.requestDisallowInterceptTouchEvent(true)
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP ->
                v.parent.requestDisallowInterceptTouchEvent(false)
        }
        false
    }
}

fun <T> MutableLiveData<T>.notifyChange() {
    value = value
}

fun DueDate.getUtc() = DandelionDate(
    CalendarType.GREGORIAN,
    year = this.year,
    month = this.month,
    day = this.day,
    hour = this.hour,
    minute = this.minute,
).epoch

fun DueDate.hasValidDueTime() = getUtc() > System.currentTimeMillis().div(1000)

fun DandelionDate.hasValidDueTime() =
    this.toDueDate().getUtc() > System.currentTimeMillis().div(1000)

fun Calendar.getEndMillis(offsetInDays: Int = 0): Long {
    // Why using clone() ?? because doing operations on the actual object (the extended one) will change it and those changes will remain as is.
    return clone().let {
        (it as Calendar).set(Calendar.DAY_OF_YEAR, get(Calendar.DAY_OF_YEAR) + offsetInDays)
        it.set(Calendar.HOUR_OF_DAY, getActualMaximum(Calendar.HOUR_OF_DAY))
        it.set(Calendar.MINUTE, getActualMaximum(Calendar.MINUTE))
        it.set(Calendar.SECOND, getActualMaximum(Calendar.SECOND))
        it.set(Calendar.MILLISECOND, getActualMaximum(Calendar.MILLISECOND))
        it.timeInMillis
    }
}

fun Calendar.getStartMillis(offset: Int = 0): Long {
    return clone().let {
        (it as Calendar).set(Calendar.DAY_OF_YEAR, get(Calendar.DAY_OF_YEAR) + offset)
        it.set(Calendar.HOUR_OF_DAY, getActualMinimum(Calendar.HOUR_OF_DAY))
        it.set(Calendar.MINUTE, getActualMinimum(Calendar.MINUTE))
        it.set(Calendar.SECOND, getActualMinimum(Calendar.SECOND))
        it.set(Calendar.MILLISECOND, getActualMinimum(Calendar.MILLISECOND))
        it.timeInMillis
    }
}

fun getCompleteDueDate(context: Context, dandelionDate: DandelionDate): String {
    val dueDate = dandelionDate.toDueDate()
    val monthDisplayName = dueDate.month.getMonthName(Calendar.SHORT)
    return context.getString(
        R.string.label_complete_due_date,
        "${dueDate.year}, $monthDisplayName",
        dueDate.day,
        dueDate.hour,
        dueDate.minute
    )
}

fun getDueDate(context: Context, dandelionDate: DandelionDate): String {
    val dueDate = dandelionDate.toDueDate()
    val monthDisplayName = dueDate.month.getMonthName(Calendar.SHORT)
    return context.getString(
        R.string.label_due_date,
        monthDisplayName,
        dueDate.day,
    )
}

fun getDueTime(context: Context, dandelionDate: DandelionDate): String {
    val dueDate = dandelionDate.toDueDate()
    return context.getString(
        R.string.create_task_due_time,
        dueDate.hour,
        dueDate.minute
    )
}

fun Calendar.getWeekDayName(): Int {
    return when (get(Calendar.DAY_OF_WEEK)) {
        1 -> R.string.sunday
        2 -> R.string.monday
        3 -> R.string.tuesday
        4 -> R.string.wednesday
        5 -> R.string.thursday
        6 -> R.string.friday
        7 -> R.string.saturday
        else -> R.string.friday
    }
}

fun Throwable.getMessage(context: Context): String {
    return if (this is CombinedException) {
        exceptions.map { it.getMessage(context) }.distinct().joinToString("\n")
    } else {
        context.getString(R.string.error_something_went_wrong)
    }
}

fun Resource<*>.toLiveTaskResource(): LiveTaskResult<*> = when (this) {
    is Resource.Error -> {
        LiveTaskResult.Error(error)
    }
    Resource.Loading -> {
        LiveTaskResult.Loading()
    }
    is Resource.Success -> {
        LiveTaskResult.Success(data)
    }
    else -> {
        LiveTaskResult.Error(Exception())
    }
}

fun List<Category>.toVerticalPickerModel(): List<VerticalPickerModel> {
    val list = mutableListOf<VerticalPickerModel>()
    this.forEach {
        list.add(
            VerticalPickerModel(
                itemType = PICKER_CATEGORY,
                id = it.id,
                title = it.title,
                isSelected = it.isSelected,
            )
        )
    }
    return list.toList()
}