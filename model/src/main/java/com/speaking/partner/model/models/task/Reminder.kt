package com.speaking.partner.model.models.task

import android.os.Parcelable
import com.support.multicalendar.DandelionDate
import com.support.multicalendar.models.CalendarType
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.*

@Parcelize
data class Reminder(
    var id: Long = 0,
    var taskId: Long = 0,
    val time: @RawValue DandelionDate,
    val createdAt: @RawValue DandelionDate = DandelionDate(
        TimeZone.getDefault(),
        CalendarType.GREGORIAN
    ),
    val updatedAt: @RawValue DandelionDate = DandelionDate(
        TimeZone.getDefault(),
        CalendarType.GREGORIAN
    ),
    var hasValidDueTime: Boolean = true
) : Parcelable