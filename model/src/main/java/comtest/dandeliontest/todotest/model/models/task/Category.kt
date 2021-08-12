package comtest.dandeliontest.todotest.model.models.task

import com.support.multicalendar.DandelionDate
import com.support.multicalendar.models.CalendarType
import java.util.*

data class Category(
    val id: Long = 0,
    var title: String = "",
    val color: String = "#ff0000",
    var isSelected: Boolean = false,
    val taskCount: Int = 0,
    var createdAt: DandelionDate = DandelionDate(
        TimeZone.getDefault(),
        CalendarType.GREGORIAN
    ),
    var updatedAt: DandelionDate = DandelionDate(
        TimeZone.getDefault(),
        CalendarType.GREGORIAN
    ),
)