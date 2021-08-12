package com.speaking.partner.task

import com.airbnb.epoxy.EpoxyDataBindingPattern

@EpoxyDataBindingPattern(
    rClass = R::class,
    layoutPrefix = "epoxy_item_add_task",
    enableDoNotHash = false
)
interface AddTaskEpoxyDataBindingConfig