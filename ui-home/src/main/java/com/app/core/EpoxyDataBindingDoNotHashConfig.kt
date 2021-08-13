package com.app.core

import com.airbnb.epoxy.EpoxyDataBindingPattern
import com.app.core.task.R

@EpoxyDataBindingPattern(
    rClass = R::class,
    layoutPrefix = "epoxy_item_add_task",
    enableDoNotHash = false
)
interface EpoxyDataBindingDoNotHashConfig