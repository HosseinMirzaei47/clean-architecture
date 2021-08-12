package comtest.dandeliontest.todotest.task.tasks

import android.app.PendingIntent
import androidx.lifecycle.*
import com.part.livetaskcore.usecases.asLiveTask
import com.partsoftware.formmanager.utils.requireValue
import comtest.dandeliontest.todotest.domain.usecases.category.GetCategoryUseCase
import comtest.dandeliontest.todotest.domain.usecases.savestate.SaveCurrentStateUseCase
import comtest.dandeliontest.todotest.domain.usecases.task.ChangeIsDoneUseCase
import comtest.dandeliontest.todotest.domain.usecases.task.DeleteTaskUseCase
import comtest.dandeliontest.todotest.domain.usecases.task.GetTasksUseCase
import comtest.dandeliontest.todotest.model.models.SavedStateInformation
import comtest.dandeliontest.todotest.model.models.task.*
import comtest.dandeliontest.todotest.task.*
import comtest.dandeliontest.todotest.task.R
import comtest.dandeliontest.todotest.ui.snackbar.SnackbarMessage
import comtest.dandeliontest.todotest.ui.snackbar.SnackbarMessageManager
import comtest.dandeliontest.todotest.ui.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    val snackbarManager: SnackbarMessageManager,
    savedState: SavedStateHandle,
    deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase,
    changeIsDoneUseCase: ChangeIsDoneUseCase,
    getCategoryUseCase: GetCategoryUseCase,
    val saveCurrentStateUseCase: SaveCurrentStateUseCase,
) : ViewModel() {

    private var pendingIntent = MutableLiveData<PendingIntent?>()

    private var queryBuilder = ToDoFilterQueryBuilder(
        ToDoFilterSortMethod(
            "isDone",
            ToDoFilterQueryBuilderConstants.ORDER_TYPE_ASC
        )
    )

    private val categoryId = savedState.get<Long>("categoryId")
        ?: 0

    private lateinit var taskToCheck: Task

    private val calendarInstance = Calendar.getInstance()

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    private var _noTaskBannerGone = MutableLiveData<Boolean>(true)
    val noTaskBannerGone: LiveData<Boolean> get() = _noTaskBannerGone

    private val _checkTasks = MutableLiveData<Event<Unit>>()
    val checkTask: LiveData<Event<Unit>> get() = _checkTasks

    private var _isDoneVisible = MutableLiveData(false)
    val isDoneVisible: LiveData<Boolean> get() = _isDoneVisible

    private val _onAddTaskClicked = MutableLiveData<Event<Long?>>()
    val onAddTaskClicked: LiveData<Event<Long?>> get() = _onAddTaskClicked

    private var _titleOrId = MutableLiveData<String>()
    val titleOrId: LiveData<String> get() = _titleOrId

    private val _navigateUp = MutableLiveData<Event<Unit>>()
    val navigateUp: LiveData<Event<Unit>> get() = _navigateUp

    private var _onOpenMenu = MutableLiveData<Event<Unit>>()
    val onOpenMenu: LiveData<Event<Unit>> get() = _onOpenMenu

    private var _sortTypeState = MutableLiveData<Int>(0)
    val sortTypeState: LiveData<Int> get() = _sortTypeState

    private var _sortOrderState = MutableLiveData<Int>(1)
    val sortOrderState: LiveData<Int> get() = _sortOrderState

    private val doneTask = changeIsDoneUseCase.asLiveTask() {
        onSuccess<Unit> {
            _checkTasks.postValue(Event(Unit))
        }
    }

    private val deleteTaskLiveTask = deleteTaskUseCase.asLiveTask {
        onSuccess<Unit> { snackbarManager.queueMessage(SnackbarMessage(R.string.success_delete_task_message)) }
    }

    private val tasksLiveTask =
        getTasksUseCase.asLiveTask {
            withParameter(buildFinalQuery())
            onSuccess<List<Task>> {
                _noTaskBannerGone.value = it.isNotEmpty()
                _tasks.postValue(it)
            }
        }

    private val categoryLiveTask = getCategoryUseCase.asLiveTask {
        categoryId.let { id ->
            withParameter(id)
            onSuccess<Category> {
                _titleOrId.postValue(it.title)
            }
        }
    }

    init {
        viewModelScope.launch {
            when (categoryId) {
                DefaultCategories.TODAY -> {
                    buildTodayTasksQuery()
                    _titleOrId.postValue(R.string.today.toString())
                }
                DefaultCategories.UPCOMING -> {
                    buildUpcomingTasksQuery()
                    _titleOrId.postValue(R.string.upcoming.toString())
                }
                DefaultCategories.INBOX -> {
                    _titleOrId.postValue(R.string.inbox.toString())
                }
                else -> {
                    buildCategoryQuery()
                    categoryLiveTask.run()
                }
            }
            tasksLiveTask.run()
        }
    }

    private fun buildCategoryQuery() {
        queryBuilder.cleanQuery()
        queryBuilder.checkManyHaveManyQuery(
            ToDoFilterQueryMethod(
                columnName = "taskId",
                parameter = categoryId,
                operator = ToDoFilterQueryBuilderConstants.QUERY_OPERATOR_TYPE_EQUAL,
                targetTable = "task_category_cross_ref",
                targetTableColumn = "categoryId",
            )
        )
    }

    fun onTaskStatusChanged(task: Task) {
        taskToCheck = task
        taskToCheck.isDone = taskToCheck.isDone.not()

        task.reminders.forEach {
            if (!it.time.toDueDate().hasValidDueTime()) it.hasValidDueTime = false
        }

        viewModelScope.launch {
            doneTask.setParameter(
                TaskWithReminderPendingIntent(
                    taskToCheck,
                    pendingIntent.value?.let { ParcelablePendingIntent(it) }
                )
            )
            doneTask.run()
        }
    }

    fun onOpenMenuClicked() {
        _onOpenMenu.value = Event(Unit)
    }

    fun openTaskFragment() {
        _onAddTaskClicked.value = Event(categoryId)
    }

    fun navigateUp() {
        _navigateUp.value = Event(Unit)
    }

    fun setPendingIntent(pendingIntent: PendingIntent) {
        this.pendingIntent.value = pendingIntent
    }

    fun deleteTask(position: Long) {
        viewModelScope.launch {
            deleteTaskLiveTask.setParameter(position)
            deleteTaskLiveTask.run()
        }
    }

    fun triggerAllTaskUpdate() {
        _isDoneVisible.value = _isDoneVisible.value?.not()
        _tasks.notifyChange()
    }

    fun onTaskStatusChanged(taskId: Long) {
        _tasks.value?.firstOrNull { it.id == taskId }?.let { task ->
            onTaskStatusChanged(task)
        }
    }

    fun onStateChanged(id: Int) {
        viewModelScope.launch {
            val stateInfo = SavedStateInformation(id.toLong(), categoryId)
            saveCurrentStateUseCase(stateInfo)
        }
    }

    private fun buildTodayTasksQuery() {
        queryBuilder.cleanQuery()

        val endDate = calendarInstance.getEndMillis()
        val startDate = calendarInstance.getStartMillis()

        queryBuilder.addWhereQuery(
            ToDoFilterQueryMethod(
                columnName = "dueDate",
                parameter = startDate,
                operator = ToDoFilterQueryBuilderConstants.QUERY_OPERATOR_TYPE_BIGGER_THAN,
            ),
            ToDoFilterQueryMethod(
                columnName = "dueDate",
                parameter = endDate,
                operator = ToDoFilterQueryBuilderConstants.QUERY_OPERATOR_TYPE_SMALLER_THAN,
            )
        )
    }

    private fun buildUpcomingTasksQuery() {
        queryBuilder.cleanQuery()
        val startDate = calendarInstance.getEndMillis()
        queryBuilder.addWhereQuery(
            ToDoFilterQueryMethod(
                columnName = "dueDate",
                parameter = startDate,
                operator = ToDoFilterQueryBuilderConstants.QUERY_OPERATOR_TYPE_BIGGER_THAN
            )
        )
    }

    private fun buildFinalQuery() = queryBuilder.buildQuery()

    fun onSortStatusChanged(newStatus: Int) {
        _sortTypeState.value = newStatus
        _sortOrderState.value = SORT_STATUS_ASC

        buildSortQuery()
    }

    private fun buildSortQuery() {
        queryBuilder.cleanSortQuery()
        when (_sortTypeState.requireValue()) {
            SORT_STATUS_NOT_SET -> {

            }
            SORT_STATUS_BY_PRIORITY -> {
                queryBuilder.addSortQuery(
                    ToDoFilterSortMethod(
                        "priority",
                        orderType = if (_sortOrderState.requireValue() != SORT_STATUS_ASC) ToDoFilterQueryBuilderConstants.ORDER_TYPE_ASC else ToDoFilterQueryBuilderConstants.ORDER_TYPE_DESC
                    )
                )
            }
            SORT_STATUS_BY_DUE_DATE -> {
                queryBuilder.addSortQuery(
                    ToDoFilterSortMethod(
                        "dueDate",
                        orderType = if (_sortOrderState.requireValue() == SORT_STATUS_ASC) ToDoFilterQueryBuilderConstants.ORDER_TYPE_ASC else ToDoFilterQueryBuilderConstants.ORDER_TYPE_DESC,
                        putNullsEnd = true
                    ),
                    ToDoFilterSortMethod(
                        "dueTime",
                        orderType = if (_sortOrderState.requireValue() == SORT_STATUS_ASC) ToDoFilterQueryBuilderConstants.ORDER_TYPE_ASC else ToDoFilterQueryBuilderConstants.ORDER_TYPE_DESC,
                        putNullsEnd = true
                    )
                )
            }
            SORT_STATUS_BY_DATE_ADDED -> {
                queryBuilder.addSortQuery(
                    ToDoFilterSortMethod(
                        "createdAt",
                        orderType = if (_sortOrderState.requireValue() == SORT_STATUS_ASC) ToDoFilterQueryBuilderConstants.ORDER_TYPE_ASC else ToDoFilterQueryBuilderConstants.ORDER_TYPE_DESC
                    )
                )
            }
            SORT_STATUS_BY_ALPHABETICALLY -> {
                queryBuilder.addSortQuery(
                    ToDoFilterSortMethod(
                        "title",
                        orderType = if (_sortOrderState.requireValue() == SORT_STATUS_ASC) ToDoFilterQueryBuilderConstants.ORDER_TYPE_ASC else ToDoFilterQueryBuilderConstants.ORDER_TYPE_DESC
                    )
                )
            }
        }
        viewModelScope.launch {
            tasksLiveTask.cancel()
            tasksLiveTask.run()
        }
    }

    fun onSortOrderStateChanged() {
        _sortOrderState.value =
            if (_sortOrderState.value == SORT_STATUS_ASC) SORT_STATUS_DESC else SORT_STATUS_ASC

        buildSortQuery()
    }

    companion object {
        const val SORT_STATUS_NOT_SET = 0
        const val SORT_STATUS_BY_PRIORITY = 1
        const val SORT_STATUS_BY_DUE_DATE = 2
        const val SORT_STATUS_BY_DATE_ADDED = 3
        const val SORT_STATUS_BY_ALPHABETICALLY = 4

        const val SORT_STATUS_ASC = 0
        const val SORT_STATUS_DESC = 1
    }
}