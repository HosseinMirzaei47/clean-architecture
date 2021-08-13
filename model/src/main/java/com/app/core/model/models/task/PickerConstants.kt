package com.app.core.model.models.task

object PickerConstants {
    object ItemType {
        const val PICKER_PRIORITY = "picker_item_type_priority"
        const val PICKER_DUE_DATE = "picker_item_type_due_date"
        const val PICKER_DUE_TIME = "picker_item_type_due_time"
        const val PICKER_CATEGORY = "picker_category"
        const val PICKER_REMINDER = "picker_reminder"
    }

    object Priority {
        const val NOT_SET = 0
        const val LOW = 1
        const val MEDIUM = 2
        const val HIGH = 3
    }

    object DueDate {
        const val NO_DATE = 0
        const val TODAY = 1
        const val TOMORROW = 2
        const val PICK_DATE = 3
    }

    object DueTime {
        const val NO_TIME = 0
        const val AM_9 = 1
        const val PM_6 = 2
        const val PICK_TIME = 3
    }

    object Reminder {
        const val NO_REMINDER = 0L
        const val PM_12 = 1L
        const val CUSTOM = 2L
    }

}