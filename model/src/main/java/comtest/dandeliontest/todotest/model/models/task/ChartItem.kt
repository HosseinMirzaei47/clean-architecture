package comtest.dandeliontest.todotest.model.models.task

import androidx.annotation.StringRes

data class ChartItem(
    @StringRes val titleRes: Int,
    val isDoneTasks: Int,
    val allTasks: Int
)