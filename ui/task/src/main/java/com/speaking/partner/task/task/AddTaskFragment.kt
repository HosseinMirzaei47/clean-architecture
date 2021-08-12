package com.speaking.partner.task.task

import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.OnRebindCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.speaking.partner.model.models.task.DueDate
import com.speaking.partner.model.models.task.MaterialPickersTags.DUE_DATE_DIALOG
import com.speaking.partner.model.models.task.MaterialPickersTags.DUE_TIME_DIALOG
import com.speaking.partner.model.models.task.MaterialPickersTags.REMINDER_TIME_DIALOG
import com.speaking.partner.model.models.task.PickerItem
import com.speaking.partner.model.models.task.PickerModel
import com.speaking.partner.task.R
import com.speaking.partner.task.category.VerticalPickerController
import com.speaking.partner.task.databinding.FragmentAddTaskBinding
import com.speaking.partner.ui.snackbar.bindToSnackBarManager
import com.speaking.partner.ui.utils.*
import com.support.multicalendar.DandelionDate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddTaskFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private val viewModel by viewModels<AddTaskViewModel>()
    private var verticalPickerController by autoCleared<VerticalPickerController>()
    private var horizontalPickerController by autoCleared<HorizontalPickerController>()

    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddTaskBinding.inflate(
            inflater, container, false
        ).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        verticalPickerController = VerticalPickerController(
            onItemClicked = { idWithType ->
                @Suppress("UNCHECKED_CAST")
                viewModel.onVerticalPickerItemClicked(idWithType as Pair<Int, String>)
            }
        )

        horizontalPickerController = HorizontalPickerController(
            onItemClicked = { idWithType ->
                @Suppress("UNCHECKED_CAST")
                viewModel.onPickerItemClicked(idWithType as Pair<Int, String>)
            }
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindToSnackBarManager(viewModel.snackbarManager)
        binding.etTaskTitle.requestFocus()
        setUpRebindCallback()

        viewModel.openDueDateDialog.observe(viewLifecycleOwner, EventObserver {
            dismissKeyboard()
            showDueDateDialog()
        })

        viewModel.openReminderDialog.observe(viewLifecycleOwner, EventObserver {
            dismissKeyboard()
            showReminderDialog(it)
        })

        viewModel.openReminderTimeDialog.observe(viewLifecycleOwner, EventObserver { date ->
            val timePicker = getTimePickerDialog()
            timePicker.addOnPositiveButtonClickListener {
                date.hour = timePicker.hour
                date.minute = timePicker.minute
                viewModel.onReminderChange(date)
            }
            timePicker.show(childFragmentManager, REMINDER_TIME_DIALOG)
        })

        viewModel.openDueTimeDialog.observe(viewLifecycleOwner, EventObserver { date ->
            val timePicker = getTimePickerDialog()
            timePicker.addOnPositiveButtonClickListener {
                date.hour = timePicker.hour
                date.minute = timePicker.minute
                viewModel.onTaskDueTimeChanged(date)
            }
            timePicker.show(childFragmentManager, DUE_TIME_DIALOG)
        })

        viewModel.onCreateNotificationPendingIntent.observe(viewLifecycleOwner, EventObserver {
            viewModel.onAlarmPendingIntentChanged(
                getNotificationPendingIntent(
                    taskId = it,
                    parameterKey = ArgumentKeysConstants.PENDING_INTENT_TASK_ARGUMENT,
                    notificationDestination = R.id.addTaskFragment
                )
            )
        })

        viewModel.navigateUp.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigateUp()
        })

        viewModel.categories.observe(viewLifecycleOwner) {
            verticalPickerController.setData(it.toVerticalPickerModel())
        }

        viewModel.reminderList.observe(viewLifecycleOwner) {
            verticalPickerController.setData(it)
        }

        viewModel.fetchTaskCategories.asLiveData().observe(viewLifecycleOwner) {
            viewModel.onCheckSelectedCategories()
        }

        viewModel.priorityList.observe(viewLifecycleOwner) {
            horizontalPickerController.apply {
                isSelected = { pickerItem -> it.containsItem(pickerItem) }
                setData(
                    it.copy(items = getItemsWithBackgrounds(it))
                )
            }
        }

        viewModel.dueTimePickerList.observe(viewLifecycleOwner) {
            horizontalPickerController.apply {
                isSelected = { pickerItem -> it.containsItem(pickerItem) }
                setData(it.copy(items = getItemsWithBackgrounds(it)))
            }
        }

        viewModel.dueDatePickerList.observe(viewLifecycleOwner) {
            horizontalPickerController.apply {
                isSelected = { pickerItem -> it.containsItem(pickerItem) }
                setData(it.copy(items = getItemsWithBackgrounds(it)))
            }
        }

        binding.rvOptionsCommon.setController(horizontalPickerController)

        with(binding.rvAddTaskVerticalPicker) {
            setController(verticalPickerController)
            isNestedScrollingEnabled = true
        }
    }

    private fun showDueDateDialog() {
        val datePicker = getMaterialDatePickerDialog(
            listener = { viewModel.onTaskDueDateChanged(DandelionDate(it)) },
        )
        datePicker.show(childFragmentManager, DUE_DATE_DIALOG)
    }

    private fun showReminderDialog(dueDate: DueDate?) {
        val datePicker = getMaterialDatePickerDialog(
            listener = { viewModel.onReminderDueDateChanged(DandelionDate(it)) },
            startDate = dueDate?.toDandelionDate()
        )
        datePicker.show(childFragmentManager, REMINDER_TIME_DIALOG)
    }

    private fun getTimePickerDialog(): MaterialTimePicker {
        return MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .build()
    }

    private fun setUpRebindCallback() {
        val fadeTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.transition_fade)

        binding.addOnRebindCallback(object : OnRebindCallback<FragmentAddTaskBinding>() {
            override fun onPreBind(binding: FragmentAddTaskBinding): Boolean {
                TransitionManager.beginDelayedTransition(
                    binding.rvOptionsCommon as ViewGroup,
                    fadeTransition
                )
                TransitionManager.beginDelayedTransition(
                    binding.rvAddTaskVerticalPicker as ViewGroup,
                    fadeTransition
                )
                TransitionManager.beginDelayedTransition(binding.llDateCategory as ViewGroup)
                return super.onPreBind(binding)
            }
        })
    }

    private fun getItemsWithBackgrounds(selectableFilter: PickerModel): List<PickerItem> {
        return selectableFilter.items.mapIndexed { _, item ->
            item.copy(
                activatedBackground = item.activatedBackground?.let {
                    ContextCompat.getColor(requireContext(), it)
                },
                inactiveBackground = item.inactiveBackground?.let {
                    ContextCompat.getColor(requireContext(), it)
                }
            )
        }
    }

}