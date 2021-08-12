package com.speaking.partner.task

import com.speaking.partner.task.ToDoFilterQueryBuilderConstants.QUERY_OPERATOR_TYPE_EQUAL

data class ToDoFilterQueryMethod(
    val columnName: String,
    val parameter: Any?,
    val operator: Int = QUERY_OPERATOR_TYPE_EQUAL,
    val targetTable: String = "",
    val targetTableColumn: String = "",
)