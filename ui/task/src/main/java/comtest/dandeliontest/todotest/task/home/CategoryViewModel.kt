package comtest.dandeliontest.todotest.task.home

import androidx.lifecycle.*
import com.part.livetaskcore.usecases.asLiveTask
import com.partsoftware.formmanager.form.form
import com.partsoftware.formmanager.rules.NotEmpty
import com.partsoftware.formmanager.utils.requireValue
import comtest.dandeliontest.todotest.domain.usecases.category.EditCategoryUseCase
import comtest.dandeliontest.todotest.domain.usecases.category.GetCategoryUseCase
import comtest.dandeliontest.todotest.model.models.task.Category
import comtest.dandeliontest.todotest.task.R
import comtest.dandeliontest.todotest.ui.snackbar.SnackbarMessage
import comtest.dandeliontest.todotest.ui.snackbar.SnackbarMessageManager
import comtest.dandeliontest.todotest.ui.utils.Event
import comtest.dandeliontest.todotest.ui.utils.LengthLessThan
import comtest.dandeliontest.todotest.ui.utils.getSnackBarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    getCategoryUseCase: GetCategoryUseCase,
    editCategoryUseCase: EditCategoryUseCase,
    savedState: SavedStateHandle,
    val snackbarManager: SnackbarMessageManager,
) : ViewModel() {

    private val _category = MutableLiveData(Category())
    val category: LiveData<Category> get() = _category

    private val _navigateUp = MutableLiveData<Event<Unit>>()
    val navigateUp: LiveData<Event<Unit>> get() = _navigateUp

    val categoryForm = form(silentAutoValidate = true) {
        formField(
            FIELD_TITLE,
            autoValidate = true,
            initialValue = _category.value?.title,
        ) {
            validationRules(NotEmpty(), LengthLessThan(20))
            validationCondition { !it.isNullOrBlank() }
        }
    }

    private val editCategoryLiveTask = getCategoryUseCase.asLiveTask {
        onSuccess<Category> {
            _category.postValue(it)
            viewModelScope.launch(kotlinx.coroutines.Dispatchers.Main) {
                categoryForm.getField(FIELD_TITLE).updateValue(it.title)
            }
        }
    }

    init {
        savedState.get<Long>("categoryId")?.let { categoryId ->
            if (categoryId > 0) {
                viewModelScope.launch {
                    editCategoryLiveTask.setParameter(categoryId).run()
                }
            }
        }
    }

    private val saveCategory = editCategoryUseCase.asLiveTask() {
        onSuccess<Unit> {
            _navigateUp.value = Event(Unit)
        }
        onError {
            snackbarManager.queueMessage(it.getSnackBarMessage())
        }
    }

    fun onSaveCategory() {
        if (categoryForm.checkIsValid()) {
            val title = categoryForm.getField(FIELD_TITLE).mappedValue
            _category.value?.title = title!!
            _category.value = _category.value
            viewModelScope.launch {
                saveCategory.setParameter(_category.requireValue())
                saveCategory.run()
            }
        } else {
            snackbarManager.queueMessage(SnackbarMessage(R.string.error_blank_title))
        }
    }

    fun onCancelClicked() {
        _navigateUp.value = Event(Unit)
    }

    companion object {
        const val FIELD_TITLE = 1
    }
}