package comtest.dandeliontest.todotest.task.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.part.livetaskcore.usecases.asLiveTask
import comtest.dandeliontest.todotest.domain.usecases.category.GetAllCategoriesWithTaskCountUseCase
import comtest.dandeliontest.todotest.domain.usecases.profile.GetAllTasksCountUseCase
import comtest.dandeliontest.todotest.domain.usecases.task.GetTodayTasksCountUseCase
import comtest.dandeliontest.todotest.domain.usecases.task.GetUpcomingTasksCountUseCase
import comtest.dandeliontest.todotest.model.models.task.Category
import comtest.dandeliontest.todotest.model.models.task.TodayTasksFilterModel
import comtest.dandeliontest.todotest.ui.snackbar.SnackbarMessageManager
import comtest.dandeliontest.todotest.ui.utils.Event
import comtest.dandeliontest.todotest.ui.utils.getEndMillis
import comtest.dandeliontest.todotest.ui.utils.getStartMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllCategoriesWithTaskCountUseCase: GetAllCategoriesWithTaskCountUseCase,
    getTodayTasksCountUseCase: GetTodayTasksCountUseCase,
    getUpcomingTasksUseCase: GetUpcomingTasksCountUseCase,
    getAllTasksCountUseCase: GetAllTasksCountUseCase,
    val snackbarManager: SnackbarMessageManager,
) : ViewModel() {

    private val calendarInstance = Calendar.getInstance()

    private val _allCategories = MutableLiveData<List<Category>>()
    val allCategories: LiveData<List<Category>> get() = _allCategories

    private var _todayTasksCount = MutableLiveData<String>()
    val todayTasksCount: LiveData<String> get() = _todayTasksCount

    private var _upcomingTasksCount = MutableLiveData<String>()
    val upcomingTasksCount: LiveData<String> get() = _upcomingTasksCount

    private var _allTasksCount = MutableLiveData<String>()
    val allTasksCount: LiveData<String> get() = _allTasksCount

    private val _navigateUp = MutableLiveData<Event<Unit>>()
    val navigateUp: LiveData<Event<Unit>> get() = _navigateUp

    private var _onOpenSettingsClicked = MutableLiveData<Event<Unit>>()
    val onOpenSettingsClicked: LiveData<Event<Unit>> get() = _onOpenSettingsClicked

    private var _onCategoriesClick = MutableLiveData<Event<Long>>()
    val onCategoriesClick: LiveData<Event<Long>> get() = _onCategoriesClick


    private val allTagsLiveTask = getAllCategoriesWithTaskCountUseCase.asLiveTask {
        withParameter(Unit)
        onSuccess<List<Category>> {
            _allCategories.postValue(it)
        }
    }

    private val todayTasksCountLiveTask = getTodayTasksCountUseCase.asLiveTask {
        withParameter(
            TodayTasksFilterModel(
                startMillis = calendarInstance.getStartMillis(),
                endMillis = calendarInstance.getEndMillis()
            )
        )
        onSuccess<Int> {
            _todayTasksCount.postValue(it.toString())
        }
    }

    private val upcomingTasksCountLiveTask = getUpcomingTasksUseCase.asLiveTask {
        withParameter(calendarInstance.getEndMillis())
        onSuccess<Int> {
            _upcomingTasksCount.postValue(it.toString())
        }
    }

    private val allTasksCountLiveTask = getAllTasksCountUseCase.asLiveTask {
        withParameter(Unit)
        onSuccess<Int> {
            _allTasksCount.postValue(it.toString())
        }
    }

    init {
        viewModelScope.launch {
            allTagsLiveTask.run()
            todayTasksCountLiveTask.run()
            upcomingTasksCountLiveTask.run()
            allTasksCountLiveTask.run()
        }
    }

    fun onBackClick() {
        _navigateUp.value = Event(Unit)
    }

    fun onOpenSettingsClicked() {
        _onOpenSettingsClicked.value = Event(Unit)
    }

    fun onCategoriesClicked(item: Long) {
        _onCategoriesClick.value = Event(item)
    }


}