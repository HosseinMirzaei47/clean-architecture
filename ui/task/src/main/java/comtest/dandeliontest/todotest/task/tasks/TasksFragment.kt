package comtest.dandeliontest.todotest.task.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.airbnb.epoxy.EpoxyTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.partsoftware.formmanager.utils.requireValue
import comtest.dandeliontest.todotest.model.models.task.StateConstants
import comtest.dandeliontest.todotest.model.models.task.Task
import comtest.dandeliontest.todotest.task.AllTasksBindingModel_
import comtest.dandeliontest.todotest.task.R
import comtest.dandeliontest.todotest.task.databinding.FragmentTasksBinding
import comtest.dandeliontest.todotest.ui.snackbar.bindToSnackBarManager
import comtest.dandeliontest.todotest.ui.utils.ArgumentKeysConstants.PENDING_INTENT_TASK_ARGUMENT
import comtest.dandeliontest.todotest.ui.utils.EventObserver
import comtest.dandeliontest.todotest.ui.utils.autoCleared
import comtest.dandeliontest.todotest.ui.utils.getNotificationPendingIntent
import comtest.dandeliontest.todotest.ui.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks), PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: FragmentTasksBinding
    private val viewModel by viewModels<TasksViewModel>()
    private var allTasksController by autoCleared<TasksController>()

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.onStateChanged(StateConstants.NOT_SET)
                    findNavController().navigateUp()
                }
            })
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentTasksBinding.inflate(
            inflater, container, false
        ).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        allTasksController = TasksController(
            viewModel.isDoneVisible.requireValue(),
            onClick = {
                findNavController().safeNavigate(
                    TasksFragmentDirections.actionTasksFragmentToTaskFragment(it as Long, null)
                )
            },
            onChange = { task ->
                task as Task
                val pendingIntent = getNotificationPendingIntent(
                    taskId = task.id,
                    parameterKey = PENDING_INTENT_TASK_ARGUMENT,
                    notificationDestination = R.id.addTaskFragment
                )

                viewModel.setPendingIntent(pendingIntent)
                viewModel.onTaskStatusChanged(task.id)

            },
            triggerShowDone = { viewModel.triggerAllTaskUpdate() }
        )
        binding.tasksRecyclerView.setController(allTasksController)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindToSnackBarManager(viewModel.snackbarManager)
        setUpSwipeToDelete()

        val destId = findNavController().currentDestination?.id
        destId?.let {
            viewModel.onStateChanged(it)
        }

        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            if (tasks.isEmpty())
                binding.motionLayout.getTransition(R.id.title_transition).setEnable(false)
            allTasksController.setData(tasks)
        }

        viewModel.onAddTaskClicked.observe(viewLifecycleOwner, EventObserver { categoryId ->
            findNavController().safeNavigate(
                TasksFragmentDirections.actionTasksFragmentToTaskFragment(0L, categoryId.toString())
            )
        })

        viewModel.navigateUp.observe(viewLifecycleOwner, EventObserver {
            viewModel.onStateChanged(StateConstants.NOT_SET)
            findNavController().navigateUp()
        })

        viewModel.onOpenMenu.observe(viewLifecycleOwner, EventObserver {
            showMenu(R.menu.tasks_menu)
        })
    }

    private fun showMenu(menuId: Int) {
        PopupMenu(requireContext(), binding.btnMenu).apply {
            setOnMenuItemClickListener(this@TasksFragment)
            inflate(menuId)
            show()
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_sort -> {
                showMenu(R.menu.sort_menu)
                true
            }
            R.id.menu_item_sort_priority -> {
                viewModel.onSortStatusChanged(TasksViewModel.SORT_STATUS_BY_PRIORITY)
                true
            }
            R.id.menu_item_sort_due_date -> {
                viewModel.onSortStatusChanged(TasksViewModel.SORT_STATUS_BY_DUE_DATE)
                true
            }
            R.id.menu_item_sort_date_added -> {
                viewModel.onSortStatusChanged(TasksViewModel.SORT_STATUS_BY_DATE_ADDED)

                true
            }
            R.id.menu_item_sort_alphabetically -> {
                viewModel.onSortStatusChanged(TasksViewModel.SORT_STATUS_BY_ALPHABETICALLY)

                true
            }
            else -> false
        }
    }

    private fun setUpSwipeToDelete() {
        EpoxyTouchHelper.initSwiping(binding.tasksRecyclerView)
            .leftAndRight()
            .withTarget(AllTasksBindingModel_::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<AllTasksBindingModel_>() {

                override fun onSwipeCompleted(
                    model: AllTasksBindingModel_,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    if (direction == ItemTouchHelper.LEFT) {
                        showDeleteTaskDialog(model.id())
                    } else {
                        viewModel.onTaskStatusChanged(model.id())
                    }
                    allTasksController.notifyModelChanged(position)
                }
            })
    }

    private fun showDeleteTaskDialog(taskId: Long) {
        MaterialAlertDialogBuilder(requireContext(), R.style.DeleteTaskDialog)
            .setTitle(resources.getString(R.string.delete_task_dialog_title))
            .setMessage(resources.getString(R.string.delete_task_dialog_message))
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.delete)) { _, _ ->
                viewModel.deleteTask(taskId)
            }.show()
    }
}
