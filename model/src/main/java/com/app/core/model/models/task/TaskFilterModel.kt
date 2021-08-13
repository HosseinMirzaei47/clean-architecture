package com.app.core.model.models.task

import android.os.Parcelable
import com.app.core.model.models.task.FiltersStatesConstants.NOT_SET
import com.support.multicalendar.DandelionDate
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class TaskFilterModel(
    var dueDateState: Int = NOT_SET,
    var statusState: Int = NOT_SET,
    var reminderState: Int = NOT_SET,
    var priorityStates: @RawValue MutableList<Int> = mutableListOf(),
    var categoriesList: @RawValue MutableList<PickerItem> = mutableListOf<PickerItem>(),
    var startDate: @RawValue DandelionDate? = null,
    var endDate: @RawValue DandelionDate? = null,
) : Parcelable {

    fun resetDueDateFilter() {
        dueDateState = NOT_SET
        startDate = null
        endDate = null
    }

    fun resetStatusFilter() {
        statusState = NOT_SET
    }

    fun resetReminderFilter() {
        reminderState = NOT_SET
    }

    fun resetCategoryFilter(categoryId: Int?) {
        categoryId?.let {
            categoriesList.find {
                it.id == categoryId
            }?.let { category ->
                categoriesList.remove(category)
            }
        } ?: run {
            categoriesList.clear()
        }
    }

    fun resetAllFilters() {
        resetDueDateFilter()
        resetStatusFilter()
        resetReminderFilter()
        resetCategoryFilter(null)
        resetPriorityFilter(null)
    }

    fun resetPriorityFilter(filterId: Int?) {
        filterId?.let {
            priorityStates.find {
                it == filterId
            }?.let { item ->
                priorityStates.remove(item)
            }
        } ?: run {
            priorityStates.clear()
        }
    }
}