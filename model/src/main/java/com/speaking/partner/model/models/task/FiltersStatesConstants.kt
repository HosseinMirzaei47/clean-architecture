package com.speaking.partner.model.models.task

object FiltersStatesConstants {
    const val NOT_SET = 0

    object DueDateFilter {
        const val TAG = "DDF"
        const val TODAY = 1
        const val TOMORROW = 2
        const val SEVEN_DAYS = 3
        const val CUSTOM = 4
    }

    object StatusFilter {
        const val TAG = "STF"
        const val DONE = 1
        const val UNDONE = 2
    }

    object ReminderFilter {
        const val TAG = "RMF"
        const val WITH_REMINDER = 1
        const val WITHOUT_REMINDER = 2
    }

    object PriorityFilter {
        const val TAG = "PRF"
        const val LOW = 1
        const val MEDIUM = 2
        const val HIGH = 3
    }

    object CategoryFilter {
        const val TAG = "CGF"
    }
}
