package comtest.dandeliontest.todotest.task.tasks

import com.airbnb.epoxy.TypedEpoxyController
import comtest.dandeliontest.todotest.model.models.task.Task
import comtest.dandeliontest.todotest.task.allTasks
import comtest.dandeliontest.todotest.task.doneHeader
import comtest.dandeliontest.todotest.ui.utils.EpoxyActionListener
import comtest.dandeliontest.todotest.ui.utils.EpoxyEventListener

class TasksController(
    private var isDoneVisible: Boolean,
    private val onClick: EpoxyEventListener,
    private val onChange: EpoxyEventListener,
    private val triggerShowDone: EpoxyActionListener,
) : TypedEpoxyController<List<Task>>() {

    override fun buildModels(data: List<Task>?) {
        var needHeader = true
        data?.forEach { task ->
            if (needHeader && task.isDone) {
                doneHeader {
                    id("header")
                    val count = data.count { it.isDone }
                    count(count)
                    clickListener {
                        this@TasksController.isDoneVisible =
                            this@TasksController.isDoneVisible.not()
                        this@TasksController.triggerShowDone.onAction()
                    }
                }
                needHeader = false
            }
            if (!task.isDone || !isDoneVisible) {
                allTasks {
                    id(task.id)
                    title(task.title)
                    description(task.description)
                    isDone(task.isDone)
                    dueDate(task.dueDate)
                    dueTime(task.dueTime)
                    priority(task.priority)
                    clickListener { this@TasksController.onClick.onEvent(task.id) }
                    checkedChangeListener { this@TasksController.onChange.onEvent(task) }
                }
            }
        }
    }
}