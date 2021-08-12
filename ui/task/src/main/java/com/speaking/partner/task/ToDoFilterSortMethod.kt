package com.speaking.partner.task

import com.speaking.partner.task.ToDoFilterQueryBuilderConstants.ORDER_TYPE_ASC

data class ToDoFilterSortMethod(
    val nameOfColumn: String,
    val orderType: Int = ORDER_TYPE_ASC,
    val putNullsEnd: Boolean = false,
)