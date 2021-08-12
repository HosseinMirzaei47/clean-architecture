package comtest.dandeliontest.todotest.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.part.livetaskcore.livatask.combine
import com.part.livetaskcore.usecases.asLiveTask
import com.partsoftware.formmanager.utils.requireValue
import comtest.dandeliontest.todotest.domain.usecases.category.GetAllCategoriesWithTaskCountUseCase
import comtest.dandeliontest.todotest.domain.usecases.profile.GetAllDoneTasksCountUseCase
import comtest.dandeliontest.todotest.domain.usecases.profile.GetAllTasksCountUseCase
import comtest.dandeliontest.todotest.domain.usecases.profile.GetWeekTasksUseCase
import comtest.dandeliontest.todotest.model.models.task.Category
import comtest.dandeliontest.todotest.model.models.task.ChartItem
import comtest.dandeliontest.todotest.model.models.task.Task
import comtest.dandeliontest.todotest.ui.snackbar.SnackbarMessageManager
import comtest.dandeliontest.todotest.ui.utils.Event
import comtest.dandeliontest.todotest.ui.utils.getEndMillis
import comtest.dandeliontest.todotest.ui.utils.getStartMillis
import comtest.dandeliontest.todotest.ui.utils.getWeekDayName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getAllCategoriesWithTaskCountUseCase: GetAllCategoriesWithTaskCountUseCase,
    getWeekTasksUseCase: GetWeekTasksUseCase,
    getAllTasksCountUseCase: GetAllTasksCountUseCase,
    getAllDoneTasksCountUseCase: GetAllDoneTasksCountUseCase,
    val snackbarManager: SnackbarMessageManager,
) : ViewModel() {

    private val _allCategories = MutableLiveData<List<Category>>()
    val allCategories: LiveData<List<Category>> get() = _allCategories

    private val _chartItems = MutableLiveData<List<ChartItem>>()
    val chartItems: LiveData<List<ChartItem>> get() = _chartItems

    private val _allTasksCount = MutableLiveData<Int>()
    val allTasksCount: LiveData<Int> get() = _allTasksCount

    private val _doneTasksCount = MutableLiveData<Int>()
    val doneTasksCount: LiveData<Int> get() = _doneTasksCount

    private val _selectedWeek = MutableLiveData<Int>(0)
    val selectedWeek: LiveData<Int> get() = _selectedWeek

    private val allCategoriesLiveTask =
        getAllCategoriesWithTaskCountUseCase.asLiveTask {
            withParameter(Unit)
            onSuccess<List<Category>> {
                _allCategories.postValue(it)
            }
        }

    private val weekTasksLiveTask =
        getWeekTasksUseCase.asLiveTask() {
            onSuccess<List<Task>> {
                prepareChartData(it)
            }
        }


    private val _navigateAllCategoriesManagement = MutableLiveData<Event<Unit>>()
    val navigateAllCategoriesManagement: LiveData<Event<Unit>> get() = _navigateAllCategoriesManagement

    private val allTasksCountLiveTask = getAllTasksCountUseCase.asLiveTask {
        withParameter(Unit)
        onSuccess<Int> {
            _allTasksCount.postValue(it)
        }
    }

    private val doneTasksCountLiveTask = getAllDoneTasksCountUseCase.asLiveTask {
        withParameter(Unit)
        onSuccess<Int> {
            _doneTasksCount.postValue(it)
        }
    }

    private val initialTasks = combine(
        allCategoriesLiveTask,
        allTasksCountLiveTask,
        doneTasksCountLiveTask
    )

    init {
        viewModelScope.launch {
            initialTasks.run()
        }
        updateWeekTasks()
    }

    private fun prepareChartData(tasks: List<Task>) {
        val calendar = Calendar.getInstance()
        val listOfChartItems = mutableListOf<ChartItem>()

        calendar.add(Calendar.DAY_OF_YEAR, 7 * _selectedWeek.requireValue())
        calendar.set(Calendar.DAY_OF_WEEK, 6)
        calendar.add(Calendar.DAY_OF_WEEK, 1)

        for (i in 0..6) {
            val chartItems = ChartItem(
                calendar.getWeekDayName(),
                tasks.count {
                    it.doneDate?.day == calendar.get(Calendar.DAY_OF_MONTH)
                },
                tasks.count {
                    it.dueDate?.day == calendar.get(Calendar.DAY_OF_MONTH)
                }
            )
            listOfChartItems.add(chartItems)
            calendar.add(Calendar.DAY_OF_WEEK, -1)
        }

        _chartItems.postValue(listOfChartItems)
    }


    fun onCategoryManagementClick() {
        _navigateAllCategoriesManagement.value = Event(Unit)
    }

    private fun updateWeekTasks() {
        val calendarInstance = Calendar.getInstance()
        calendarInstance.add(Calendar.DAY_OF_YEAR, 7 * _selectedWeek.requireValue())

        calendarInstance.set(
            Calendar.DAY_OF_WEEK,
            calendarInstance.getActualMinimum(Calendar.DAY_OF_WEEK)
        )
        val startWeekTimeMillis = calendarInstance.getStartMillis()

        val endWeekTimeMillis =
            calendarInstance.getEndMillis(calendarInstance.getActualMaximum(Calendar.DAY_OF_WEEK) - 1)

        val weekDates = Pair(startWeekTimeMillis, endWeekTimeMillis)

        viewModelScope.launch {
            weekTasksLiveTask.setParameter(weekDates)
            weekTasksLiveTask.run()
        }
    }

    fun onSetSelectedWeek(increaseMode: Boolean) {
        if (increaseMode) {
            _selectedWeek.value = _selectedWeek.requireValue() + 1
        } else {
            _selectedWeek.value = _selectedWeek.requireValue() - 1
        }
        updateWeekTasks()
    }
}