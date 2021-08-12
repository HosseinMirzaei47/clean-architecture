package comtest.dandeliontest.todotest.task

import comtest.dandeliontest.todotest.task.ToDoFilterQueryBuilderConstants.ORDER_TYPE_ASC

data class ToDoFilterSortMethod(
    val nameOfColumn: String,
    val orderType: Int = ORDER_TYPE_ASC,
    val putNullsEnd: Boolean = false,
)