package comtest.dandeliontest.todotest.task

import com.airbnb.epoxy.EpoxyDataBindingPattern

@EpoxyDataBindingPattern(
    rClass = R::class,
    layoutPrefix = "epoxy_item_add_task",
    enableDoNotHash = false
)
interface AddTaskEpoxyDataBindingConfig