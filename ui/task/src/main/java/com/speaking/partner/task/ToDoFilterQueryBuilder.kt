package com.speaking.partner.task

import androidx.sqlite.db.SimpleSQLiteQuery
import com.speaking.partner.task.ToDoFilterQueryBuilderConstants.ORDER_TYPE_ASC
import com.speaking.partner.task.ToDoFilterQueryBuilderConstants.QUERY_OPERATOR_TYPE_BIGGER_THAN
import com.speaking.partner.task.ToDoFilterQueryBuilderConstants.QUERY_OPERATOR_TYPE_EQUAL
import com.speaking.partner.task.ToDoFilterQueryBuilderConstants.QUERY_OPERATOR_TYPE_NOT_EQUAL
import com.speaking.partner.task.ToDoFilterQueryBuilderConstants.QUERY_OPERATOR_TYPE_SMALLER_THAN
import com.speaking.partner.task.ToDoFilterQueryBuilderConstants.SELECT_OPERATOR_AND

// TODO: 4/16/21 This Class (And All Data Classes) Should Move To Best Destination. Please Do It After Apply New Architecture.
class ToDoFilterQueryBuilder(private val defaultToDoFilterSort: ToDoFilterSortMethod) {

    private val selectItems = "taskId,title,description,dueDate,dueTime,isDone,priority"
    private val tableName = "tasks"

    private var selectQuery: String = "SELECT $selectItems FROM $tableName "
    private var whereQuery: String = ""
    private var orderByQuery: String = ""

    private var isWhereWordAdded = false
    private var isOrderByWordAdded = false

    private val listOfParams = arrayListOf<Any?>()

    init {
        addSortQuery(defaultToDoFilterSort)
    }

    /*
    * This Method Used For Queries In One Table. For Example One Of Columns Of A Table
    * */
    fun addWhereQuery(
        vararg toDoFilterQueryMethods: ToDoFilterQueryMethod,
        selectionMethod: Int = SELECT_OPERATOR_AND,
    ) {
        toDoFilterQueryMethods.forEachIndexed { index, queryMethod ->
            initialQuery(index, selectionMethod)

            whereQuery += " ${queryMethod.columnName} "
            whereQuery += when (queryMethod.operator) {
                QUERY_OPERATOR_TYPE_EQUAL -> {
                    "=?"
                }
                QUERY_OPERATOR_TYPE_NOT_EQUAL -> {
                    "!=?"
                }
                QUERY_OPERATOR_TYPE_BIGGER_THAN -> {
                    ">=?"
                }
                QUERY_OPERATOR_TYPE_SMALLER_THAN -> {
                    "<=?"
                }
                else -> ""
            }
            listOfParams.add(queryMethod.parameter)
        }
        whereQuery += " )"
    }

    /*
    * This Method Used For Two Tables Queries.Like A Task In First Table And Reminders In Second Table. And Just Detect It Has Any Item In Second Table Or Not
    * */
    fun checkOneHasManyQuery(
        vararg toDoFilterQueryMethods: ToDoFilterQueryMethod,
        selectionMethod: Int = SELECT_OPERATOR_AND,
    ) {
        toDoFilterQueryMethods.forEachIndexed { index, queryMethod ->
            initialQuery(index, selectionMethod)

            whereQuery += " ${queryMethod.columnName}"
            whereQuery += if (queryMethod.operator == QUERY_OPERATOR_TYPE_EQUAL) " IN " else " NOT IN "
            whereQuery += "(select ${queryMethod.parameter} from ${queryMethod.targetTable}) "
        }
        whereQuery += " )"
    }

    /*
    * This Method Used For Three Table Queries.By This You Can Check Is There Any Row in Table One That Has Relations To Specific Row In Table Three.
    * */
    fun checkManyHaveManyQuery(
        vararg toDoFilterQueryMethods: ToDoFilterQueryMethod,
        selectionMethod: Int = SELECT_OPERATOR_AND,
    ) {
        toDoFilterQueryMethods.forEachIndexed { index, queryMethod ->
            initialQuery(index, selectionMethod)
            /*
            "select * from tasks where taskId in " +
            "(SELECT tasks.taskId FROM tasks,task_tag_cross_ref  WHERE tasks.taskId = task_tag_cross_ref.taskId AND task_tag_cross_ref.tagId=2)"
            */
            whereQuery += " ${queryMethod.columnName} IN "
            whereQuery += " (SELECT $tableName.${queryMethod.columnName} FROM $tableName,${queryMethod.targetTable} WHERE $tableName.${queryMethod.columnName} = ${queryMethod.targetTable}.${queryMethod.columnName} AND ${queryMethod.targetTable}.${queryMethod.targetTableColumn} =? )"
            listOfParams.add(queryMethod.parameter)
        }
        whereQuery += " )"
    }

    /*
    * This Method Used For Sort Query Results
    * */
    fun addSortQuery(vararg toDoFilterSortMethods: ToDoFilterSortMethod) {
        toDoFilterSortMethods.forEachIndexed { index, sortMethod ->
            if (index == 0 && !isOrderByWordAdded) {
                orderByQuery += " ORDER BY "
                isOrderByWordAdded = true
            } else {
                orderByQuery += " , "
            }

            if (sortMethod.putNullsEnd) {
                orderByQuery += " CASE WHEN ${sortMethod.nameOfColumn} IS NULL THEN 1 ELSE 0 END, "
            }
            orderByQuery += " ${
                sortMethod.nameOfColumn
            } "

            orderByQuery += if (sortMethod.orderType == ORDER_TYPE_ASC) " ASC " else " DESC "

        }
    }

    /*
    * Return Final Query With All Closures And Sort Orders
    * */
    fun buildQuery(): SimpleSQLiteQuery {
        val finalQuery = selectQuery + whereQuery + orderByQuery
        return SimpleSQLiteQuery(finalQuery, listOfParams.toArray())
    }

    private fun initialQuery(index: Int, selectionMethod: Int) {
        if (index == 0 && !isWhereWordAdded) {
            whereQuery += " WHERE ("
            isWhereWordAdded = true
        } else if (index == 0 && isWhereWordAdded) {
            whereQuery += " AND ( "
        } else {
            whereQuery += if (selectionMethod == SELECT_OPERATOR_AND) " AND " else " OR "
        }
    }

    fun cleanQuery() {
        whereQuery = ""
        isWhereWordAdded = false
        listOfParams.clear()
    }

    fun cleanSortQuery() {
        orderByQuery = ""
        isOrderByWordAdded = false
        addSortQuery(defaultToDoFilterSort)
    }
}