package com.speaking.partner

import com.airbnb.epoxy.EpoxyDataBindingPattern
import com.speaking.partner.task.R

@EpoxyDataBindingPattern(
    rClass = R::class,
    layoutPrefix = "epoxy_item_add_task",
    enableDoNotHash = false
)
interface EpoxyDataBindingDoNotHashConfig