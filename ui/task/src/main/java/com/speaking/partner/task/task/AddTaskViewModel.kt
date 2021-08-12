package com.speaking.partner.task.task

import NotEmpty
import android.app.PendingIntent
import androidx.lifecycle.*
import com.part.livetaskcore.livatask.combine
import com.part.livetaskcore.usecases.asLiveTask
import com.partsoftware.formmanager.form.form
import com.partsoftware.formmanager.utils.requireValue
import com.speaking.partner.domain.usecases.category.GetAllCategoriesUseCase
import com.speaking.partner.domain.usecases.task.EditTaskUseCase
import com.speaking.partner.domain.usecases.task.GetTaskUseCase
import com.speaking.partner.domain.usecases.task.UpdateAlarmUseCase
import com.speaking.partner.model.models.task.*
import com.speaking.partner.model.models.task.PickerConstants.DueDate.NO_DATE
import com.speaking.partner.model.models.task.PickerConstants.DueDate.PICK_DATE
import com.speaking.partner.model.models.task.PickerConstants.DueDate.TODAY
import com.speaking.partner.model.models.task.PickerConstants.DueDate.TOMORROW
import com.speaking.partner.model.models.task.PickerConstants.DueTime.AM_9
import com.speaking.partner.model.models.task.PickerConstants.DueTime.NO_TIME
import com.speaking.partner.model.models.task.PickerConstants.DueTime.PICK_TIME
import com.speaking.partner.model.models.task.PickerConstants.DueTime.PM_6
import com.speaking.partner.model.models.task.PickerConstants.ItemType.PICKER_CATEGORY
import com.speaking.partner.model.models.task.PickerConstants.ItemType.PICKER_DUE_DATE
import com.speaking.partner.model.models.task.PickerConstants.ItemType.PICKER_DUE_TIME
import com.speaking.partner.model.models.task.PickerConstants.ItemType.PICKER_PRIORITY
import com.speaking.partner.model.models.task.PickerConstants.ItemType.PICKER_REMINDER
import com.speaking.partner.model.models.task.PickerConstants.Priority.HIGH
import com.speaking.partner.model.models.task.PickerConstants.Priority.LOW
import com.speaking.partner.model.models.task.PickerConstants.Priority.MEDIUM
import com.speaking.partner.model.models.task.PickerConstants.Priority.NOT_SET
import com.speaking.partner.model.models.task.PickerConstants.Reminder.CUSTOM
import com.speaking.partner.model.models.task.PickerConstants.Reminder.NO_REMINDER
import com.speaking.partner.model.models.task.PickerConstants.Reminder.PM_12
import com.speaking.partner.task.R
import com.speaking.partner.task.task.VisibleItemStates.NOTHING
import com.speaking.partner.task.task.VisibleItemStates.SHOW_CATEGORY
import com.speaking.partner.task.task.VisibleItemStates.SHOW_DUE_DATE
import com.speaking.partner.task.task.VisibleItemStates.SHOW_DUE_TIME
import com.speaking.partner.task.task.VisibleItemStates.SHOW_PRIORITY
import com.speaking.partner.task.task.VisibleItemStates.SHOW_REMINDER
import com.speaking.partner.ui.snackbar.SnackbarMessage
import com.speaking.partner.ui.snackbar.SnackbarMessageManager
import com.speaking.partner.ui.utils.*
import com.support.multicalendar.DandelionDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale.getDefault
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    savedState: SavedStateHandle,
    editTaskUseCase: EditTaskUseCase,
    getTaskUseCase: GetTaskUseCase,
    getAllCategoriesUseCase: GetAllCategoriesUseCase,
    updateAlarmUseCase: UpdateAlarmUseCase,
    val snackbarManager: SnackbarMessageManager,
) : ViewModel() {

    private val _task = MutableLiveData(Task())
    val task: LiveData<Task> get() = _task

    private val _openReminderDialog = MutableLiveData<Event<DueDate?>>()
    val openReminderDialog: LiveData<Event<DueDate?>> get() = _openReminderDialog

    private val _visibleItem = MutableLiveData(NOTHING)
    val visibleItem: LiveData<String> get() = _visibleItem

    private val _openDueDateDialog = MutableLiveData<Event<DandelionDate>>()
    val openDueDateDialog: LiveData<Event<DandelionDate>> get() = _openDueDateDialog

    private val _openReminderTimeDialog = MutableLiveData<Event<DueDate>>()
    val openReminderTimeDialog: LiveData<Event<DueDate>> get() = _openReminderTimeDialog

    private var _openDueTimeDialog = MutableLiveData<Event<DueDate>>()
    val openDueTimeDialog: LiveData<Event<DueDate>> get() = _openDueTimeDialog

    private val _navigateUp = MutableLiveData<Event<Unit>>()
    val navigateUp: LiveData<Event<Unit>> get() = _navigateUp

    private var _onCreateNotificationPendingIntent = MutableLiveData<Event<Long>>()
    val onCreateNotificationPendingIntent: LiveData<Event<Long>> get() = _onCreateNotificationPendingIntent

    private val taskToEdit = getTaskUseCase.asLiveTask {
        onSuccess<Task> {
            val task = it
            _task.postValue(task)
            viewModelScope.launch(Dispatchers.Main) {
                taskForm.getField(FIELD_TITLE).updateValue(task.title)
                taskForm.getField(FIELD_DESCRIPTION).updateValue(task.description)
            }
        }
    }

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private var _priorityList = MutableLiveData<PickerModel>()
    val priorityList: LiveData<PickerModel> get() = _priorityList

    private var _dueTimePickerList = MutableLiveData<PickerModel>()
    val dueTimePickerList: LiveData<PickerModel> get() = _dueTimePickerList

    private var _dueDatePickerList = MutableLiveData<PickerModel>()
    val dueDatePickerList: LiveData<PickerModel> get() = _dueDatePickerList

    private var _reminderList = MutableLiveData<List<VerticalPickerModel>>()
    val reminderList: LiveData<List<VerticalPickerModel>> get() = _reminderList

    private val categoriesLiveTask = getAllCategoriesUseCase.asLiveTask {
        withParameter(Unit)
        onSuccess<List<Category>> {
            _categories.postValue(it)
        }
    }

    val taskForm = form(silentAutoValidate = true) {
        formField(
            FIELD_TITLE,
            autoValidate = true,
            initialValue = _task.value?.title
        ) {
            validationRules(NotEmpty(), LengthLessThan(40))
            validationCondition { !it.isNullOrBlank() }
        }
        formField(
            FIELD_DESCRIPTION,
            autoValidate = false,
            initialValue = _task.value?.description
        ) {}
    }

    val fetchTaskCategories = combine(
        taskToEdit,
        categoriesLiveTask
    )

    init {
        savedState.get<Long>("taskId")?.let { taskId ->
            if (taskId > 0) {
                viewModelScope.launch {
                    taskToEdit.setParameter(taskId).run()
                }
            }
        }
    }

    private val saveTask = editTaskUseCase.asLiveTask {
        onSuccess<Long> {
            if (_task.value?.reminders?.isNotEmpty() == true)
                _onCreateNotificationPendingIntent.value = Event(it)
            else
                _navigateUp.value = Event(Unit)
        }
        onError { snackbarManager.queueMessage(it.getSnackBarMessage()) }
    }

    private val setAlarm = updateAlarmUseCase.asLiveTask {
        onSuccess<Unit> { _navigateUp.value = Event(Unit) }
        onError { snackbarManager.queueMessage(it.getSnackBarMessage()) }
    }

    fun saveTask() {
        if (taskForm.checkIsValid()) {
            val title = taskForm.getField(FIELD_TITLE).mappedValue
            val description = taskForm.getField(FIELD_DESCRIPTION).mappedValue
            _task.value?.title = title!!
            _task.value?.description = description
            _task.notifyChange()
            viewModelScope.launch {
                saveTask.setParameter(task.requireValue())
                saveTask.run()
            }
        } else {
            snackbarManager.queueMessage(SnackbarMessage(R.string.error_blank_title_task))
        }
    }

    fun initPriorityPicker() {
        _priorityList.postValue(
            PickerModel(
                itemType = PICKER_PRIORITY,
                selectedItem = getSelectedPriorityItem(),
                items = getPriorityPickerContent()
            )
        )
        onVisibleItemChanged(SHOW_PRIORITY)
    }

    fun initDueTimePicker() {
        _task.value?.dueDate?.let {
            _dueTimePickerList.postValue(
                PickerModel(
                    itemType = PICKER_DUE_TIME,
                    selectedItem = getSelectedDueTimeItem(),
                    items = getDueTimePickerContent()
                )
            )
            onVisibleItemChanged(SHOW_DUE_TIME)
        } ?: run { snackbarManager.queueMessage(SnackbarMessage(R.string.error_no_due_date_set)) }
    }

    fun initDueDatePicker() {
        _dueDatePickerList.postValue(
            PickerModel(
                itemType = PICKER_DUE_DATE,
                selectedItem = getSelectedDueDateItem(),
                items = getDueDatePickerContent()
            )
        )
        onVisibleItemChanged(SHOW_DUE_DATE)
    }

    fun initCategoryList() {
        viewModelScope.launch { categoriesLiveTask.run() }
        onVisibleItemChanged(SHOW_CATEGORY)
    }

    fun initReminderList() {
        _task.value?.dueDate?.let {
            val labels = mutableListOf(
                R.string.no_reminder,
                R.string.common_due_date_12_pm,
                getReminderText()
            )
            if (!shouldSuggestReminderTime()) labels.removeAt(1)
            val reminders = mutableListOf<VerticalPickerModel>()
            labels.forEachIndexed { index, labelId ->
                reminders.add(
                    VerticalPickerModel(
                        id = index.toLong(),
                        title = labelId.toString(),
                        isSelected = index.isReminderItemSelected(),
                        itemType = PICKER_REMINDER
                    )
                )
            }
            onVisibleItemChanged(SHOW_REMINDER)
            _reminderList.value = reminders
            _reminderList.notifyChange()
        } ?: run { snackbarManager.queueMessage(SnackbarMessage(R.string.error_no_due_date_set)) }
    }

    fun onPickerItemClicked(idWithType: Pair<Int, String>) {
        val itemId = idWithType.first
        when (idWithType.second) {
            PICKER_PRIORITY -> onPriorityItemClicked(itemId)
            PICKER_DUE_DATE -> onDueDateItemClicked(itemId)
            PICKER_DUE_TIME -> onDueTimeItemClicked(itemId)
        }
    }

    fun onAlarmPendingIntentChanged(pendingIntent: PendingIntent) {
        setAlarm.setParameter(
            IdWithPendingIntent(
                task.requireValue().id,
                ParcelablePendingIntent(pendingIntent)
            )
        )
        viewModelScope.launch {
            setAlarm.run()
        }
    }

    fun onReminderDueDateChanged(dueDate: DandelionDate) {
        _openReminderTimeDialog.value = Event(dueDate.toDueDate())
    }

    fun onReminderChange(dueDate: DueDate?) {
        dueDate?.let { it ->
            invokeIfValid(it.toDandelionDate()) {
                val reminderDate = it.toDandelionDate()
                val taskReminders = mutableListOf<Reminder>()
                reminderDate.let { dandelionDate ->
                    taskReminders.add(
                        Reminder(
                            taskId = _task.requireValue().id,
                            time = dandelionDate
                        )
                    )
                }
                _task.value?.reminders = taskReminders
            }
        } ?: run { _task.value?.reminders = mutableListOf() }
        _reminderList.value?.let {
            it.last().title = getReminderText()
            it.onEachIndexed { index, model ->
                model.isSelected = index.isReminderItemSelected()
            }
        }
        _reminderList.notifyChange()
        _task.notifyChange()
    }

    fun onTaskDueDateChanged(date: DandelionDate?) {
        date?.let {
            _task.value?.dueDate = date
        } ?: run { _task.value?.dueDate = null }
        _dueDatePickerList.value?.let {
            it.copy().items.last().info = getDateText()
            it.selectedItem = getSelectedDueDateItem()
        }
        _task.notifyChange()
        _dueDatePickerList.notifyChange()
    }

    fun onTaskDueTimeChanged(date: DueDate?) {
        date?.let {
            val dandelionDate = it.toDandelionDate()
            _task.value?.dueTime = dandelionDate
        } ?: run { _task.value?.dueTime = null }
        _dueTimePickerList.value?.let {
            it.copy().items.last().info = getTimeText()
            it.selectedItem = getSelectedDueTimeItem()
        }
        _task.notifyChange()
        _dueTimePickerList.notifyChange()
    }

    fun onCommonDueDateSet(offset: Int) {
        _task.requireValue().dueDate = null
        val date = DandelionDate()
        date.changeDateByDayOffset(offset)
        onTaskDueDateChanged(date)
    }

    fun onCommonDueTimeSet(dueTime: Int) {
        _task.value?.dueTime = DandelionDate(dueTime.getTimeInMills())
        _task.notifyChange()
    }

    fun onCommonReminderTimeSet(time: Int) {
        _task.value?.reminders?.clear()
        val reminderDate = DandelionDate(time.getTimeInMills())
        invokeIfValid(reminderDate) {
            _task.value?.reminders = mutableListOf(
                Reminder(
                    taskId = _task.requireValue().id,
                    time = reminderDate
                )
            )
            _task.notifyChange()
        }
    }

    private fun clearReminders() {
        _task.value?.reminders = mutableListOf()
        _task.notifyChange()
    }

    fun clearDueDate() {
        onTaskDueDateChanged(null)
        onTaskDueTimeChanged(null)
    }

    fun clearDueTime() {
        onTaskDueTimeChanged(null)
        _task.notifyChange()
    }

    fun onVerticalPickerItemClicked(idWithType: Pair<Int, String>) {
        val itemId = idWithType.first.toLong()
        when (idWithType.second) {
            PICKER_REMINDER -> {
                _reminderList.requireValue().find { it.id == itemId }?.let { reminder ->
                    onReminderItemClicked(reminder, reminder.isSelected)
                }
            }
            PICKER_CATEGORY -> {
                _categories.requireValue().find { it.id == itemId }?.let { category ->
                    onCategoryCheckedChange(category, category.isSelected.not())
                }
            }
        }
    }

    fun onCheckSelectedCategories() {
        _task.value?.categories?.forEach { selectedCategory ->
            _categories.value?.find { category ->
                category.id == selectedCategory.id
            }?.let {
                it.isSelected = true
            }
        }
        _categories.value?.let {
            _categories.postValue(it)
        }
    }

    fun onVisibleItemChanged(visibleItem: String) {
        _visibleItem.value =
            (if (visibleItem == _visibleItem.requireValue()) NOTHING else visibleItem)
        _visibleItem.notifyChange()
    }

    private fun onReminderItemClicked(reminder: VerticalPickerModel, isSelected: Boolean) {
        if (shouldSuggestReminderTime()) {
            when (reminder.id) {
                NO_REMINDER -> onReminderChange(null)
                PM_12 -> onCommonReminderTimeSet(CommonReminderTime.PM_12)
                CUSTOM -> openReminderDialog()
            }
        } else {
            when (reminder.id) {
                0L -> onReminderChange(null)
                1L -> openReminderDialog()
            }
        }

        /* Reminders recyclerview doesn't allow multi selection so we need to set
        ** the "isSelected" property to false for the deselected items */
        _reminderList.requireValue().forEach {
            if (it.id == reminder.id) it.isSelected = isSelected
            else it.isSelected = false
        }
        _reminderList.notifyChange()
        _task.notifyChange()
    }

    private fun onCategoryCheckedChange(category: Category, checked: Boolean) {
        if (checked) {
            _task.requireValue().categories.add(category)
        } else {
            _task.requireValue().categories.find {
                it.id == category.id
            }?.let { _task.requireValue().categories.remove(it) }
        }
        _categories.requireValue().find {
            it.id == category.id
        }?.let {
            it.isSelected = checked
        }
        _task.notifyChange()
        _categories.notifyChange()
    }

    private fun openReminderDialog() {
        _openReminderDialog.value = Event(
            when {
                _task.requireValue().reminders.isNotEmpty() -> {
                    _task.requireValue().reminders[0].time.toDueDate()
                }
                _task.value?.dueDate != null -> {
                    val dueDate = _task.value?.dueDate!!.toDueDate()
                    if (!dueDate.hasValidDueTime())
                        DandelionDate().toDueDate()
                    else
                        dueDate
                }
                else -> {
                    DandelionDate().toDueDate()
                }
            }
        )
    }

    private fun onDueTimeItemClicked(itemId: Int) {
        val item = _dueTimePickerList.requireValue().items.find { it.id == itemId }
        when (item?.id) {
            NO_TIME -> onTaskDueTimeChanged(null)
            AM_9 -> onCommonDueTimeSet(CommonDueTime.AM_9)
            PM_6 -> onCommonDueTimeSet(CommonDueTime.PM_6)
            PICK_TIME -> openDueTimeDialog()
        }
        _dueTimePickerList.requireValue().apply {
            selectedItem = itemId
            itemType = PICKER_DUE_TIME
        }
        _dueTimePickerList.notifyChange()
    }

    private fun onDueDateItemClicked(itemId: Int) {
        val item = _dueDatePickerList.requireValue().items.find { it.id == itemId }
        when (item?.id) {
            NO_DATE -> {
                onTaskDueDateChanged(null)
                _task.value?.dueTime = null
                _task.value?.reminders = mutableListOf()
                _task.notifyChange()
            }
            TODAY -> onCommonDueDateSet(CommonDueDates.TODAY)
            TOMORROW -> onCommonDueDateSet(CommonDueDates.TOMORROW)
            PICK_DATE -> openDueDateDialog()
        }
        _dueDatePickerList.requireValue().apply {
            selectedItem = itemId
            itemType = PICKER_DUE_DATE
        }
        _dueDatePickerList.notifyChange()
    }

    private fun onPriorityItemClicked(itemId: Int) {
        val item = _priorityList.requireValue().items.find { it.id == itemId }
        when (item?.id) {
            NOT_SET -> onPriorityChanged(PriorityType.NOT_SET)
            LOW -> onPriorityChanged(PriorityType.LOW)
            MEDIUM -> onPriorityChanged(PriorityType.MEDIUM)
            HIGH -> onPriorityChanged(PriorityType.HIGH)
        }
        _priorityList.requireValue().apply {
            selectedItem = itemId
            itemType = PICKER_PRIORITY
        }
        _priorityList.notifyChange()
    }

    private fun openDueTimeDialog() {
        _task.requireValue().dueDate?.let {
            _openDueTimeDialog.value = Event(it.toDueDate())
        } ?: run {
            snackbarManager.queueMessage(SnackbarMessage(R.string.error_no_due_date_set))
        }
    }

    private fun openDueDateDialog() {
        _task.value?.dueDate?.let {
            _openDueDateDialog.value = Event(it)
        } ?: run {
            _openDueDateDialog.value = Event(DandelionDate())
        }
    }

    private fun invokeIfValid(time: DandelionDate, function: () -> Unit) {
        if (time.hasValidDueTime()) function() else {
            snackbarManager.queueMessage(
                SnackbarMessage(R.string.error_reminder_passed_due_time)
            )
            _task.value?.reminders?.forEach { it.hasValidDueTime = false }
            clearReminders()
        }
    }

    private fun onPriorityChanged(priorityType: Int) {
        _task.value?.priority = priorityType
        _task.notifyChange()
    }

    private fun getDueTimePickerContent(): MutableList<PickerItem> {
        val items = mutableListOf<PickerItem>()
        val labelIds = listOf(
            R.string.empty_string,
            R.string.common_due_date_9_am,
            R.string.common_due_date_6_pm,
            R.string.empty_string
        )
        val info = listOf(
            R.string.no_time,
            R.string.empty_string,
            R.string.empty_string,
            getTimeText()
        )
        labelIds.forEachIndexed { index, labelId ->
            items.add(
                PickerItem(
                    id = index,
                    label = labelId.toString(),
                    info = info[index].toString(),
                    activatedBackground = R.color.picker_color_enable,
                    inactiveBackground = R.color.light_gray
                )
            )
        }
        return items
    }

    private fun getDueDatePickerContent(): MutableList<PickerItem> {
        val items = mutableListOf<PickerItem>()
        val today = DandelionDate().day
        val labels = listOf(
            R.string.empty_string,
            today.toString(),
            (today + 1).toString(),
            R.string.empty_string
        )
        val info = listOf(
            R.string.no_date,
            R.string.today,
            R.string.tomorrow,
            getDateText()
        )
        labels.forEachIndexed { index, text ->
            items.add(
                PickerItem(
                    id = index,
                    label = text.toString(),
                    info = info[index].toString(),
                    activatedBackground = R.color.picker_color_enable,
                    inactiveBackground = R.color.light_gray
                )
            )
        }
        return items
    }

    private fun getPriorityPickerContent(): MutableList<PickerItem> {
        val items = mutableListOf<PickerItem>()
        val activeColorIds = listOf(
            R.color.gray,
            R.color.pearl_aqua,
            R.color.flavescent,
            R.color.vividTangerine
        )
        val info = listOf(
            R.string.no_priority,
            R.string.priority_low,
            R.string.priority_medium,
            R.string.priority_high
        )
        val icons = listOf(
            null,
            R.drawable.ic_item_priority_low,
            R.drawable.ic_item_priority_medium,
            R.drawable.ic_item_priority_high
        )
        info.forEachIndexed { index, text ->
            items.add(
                PickerItem(
                    id = index,
                    info = text.toString(),
                    icon = icons[index],
                    activatedBackground = activeColorIds[index],
                    inactiveBackground = R.color.light_gray
                )
            )
        }
        return items
    }

    private fun getSelectedDueDateItem(): Int {
        return _task.value?.dueDate?.let {
            val today = DandelionDate().day
            return when (it.day) {
                today -> TODAY
                today + 1 -> TOMORROW
                else -> PICK_DATE
            }
        } ?: run { return NO_DATE }
    }

    private fun getSelectedDueTimeItem(): Int {
        return _task.value?.dueTime?.let {
            val am9 = CommonDueTime.AM_9.getTimeInSecond()
            val pm6 = CommonDueTime.PM_6.getTimeInSecond()
            return when (it.timeInMillis / 1000) {
                am9 -> AM_9
                pm6 -> PM_6
                else -> PICK_TIME
            }
        } ?: run { return NO_TIME }
    }

    private fun getSelectedPriorityItem(): Int {
        return _task.value?.priority?.let {
            return when (it) {
                PriorityType.LOW -> LOW
                PriorityType.MEDIUM -> MEDIUM
                PriorityType.HIGH -> HIGH
                else -> NOT_SET
            }
        } ?: run { return NOT_SET }
    }

    private fun getReminderText(): String {
        _task.requireValue().reminders.let {
            return if (it.equal12Pm() || it.isEmpty()) return R.string.pick_time.toString()
            else it.first().time.toString(SimpleDateFormat("MMM dd, hh:mm a", getDefault()))
        }
    }

    private fun getTimeText(): String {
        val pickTime = R.string.pick_time.toString()
        val time =
            _task.value?.dueTime?.toString(SimpleDateFormat("hh mm a", getDefault())) ?: pickTime
        _task.value?.dueTime?.timeInMillis?.div(1000)?.let {
            return if (it.equals9AmOr6Pm()) pickTime else time
        } ?: run { return pickTime }
    }

    private fun getDateText(): String {
        val pickDate = R.string.pick_date.toString()
        val date =
            _task.value?.dueDate?.toString(SimpleDateFormat("MMM d", getDefault())) ?: pickDate
        _task.value?.dueDate?.day?.let {
            return if (it.equalsTodayOrTomorrow()) pickDate else date
        } ?: run { return pickDate }
    }

    private fun Int.isReminderItemSelected(): Boolean {
        _task.requireValue().reminders.let { reminders ->
            return when {
                this == PM_12.toInt() && reminders.size != 0 -> if (shouldSuggestReminderTime()) reminders.equal12Pm() else false
                this == CUSTOM.toInt() -> reminders.isNotEmpty() && reminders.equal12Pm().not()
                this == NO_REMINDER.toInt() -> reminders.isEmpty()
                else -> false
            }
        }
    }

    private fun List<Reminder>.equal12Pm(): Boolean {
        return isNotEmpty() &&
                CommonReminderTime.PM_12.getTimeInSecond() == first().time.timeInMillis.div(1000)
    }

    private fun Int.equalsTodayOrTomorrow(): Boolean {
        val today = DandelionDate()
        val tomorrow = DandelionDate()
        today.changeDateByDayOffset(CommonDueDates.TODAY)
        tomorrow.changeDateByDayOffset(CommonDueDates.TOMORROW)
        return this == today.day || this == tomorrow.day
    }

    private fun Long.equals9AmOr6Pm(): Boolean {
        val am9 = CommonDueTime.AM_9.getTimeInSecond()
        val pm6 = CommonDueTime.PM_6.getTimeInSecond()
        return this == am9 || this == pm6
    }

    private fun shouldSuggestReminderTime() =
        DandelionDate(CommonReminderTime.PM_12.getTimeInMills()).hasValidDueTime()

    companion object {
        const val FIELD_TITLE = 1
        const val FIELD_DESCRIPTION = 2
    }

}