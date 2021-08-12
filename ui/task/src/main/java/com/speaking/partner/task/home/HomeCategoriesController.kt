package com.speaking.partner.task.home

import com.airbnb.epoxy.TypedEpoxyController
import com.speaking.partner.model.models.task.Category
import com.speaking.partner.task.categoriesFooter
import com.speaking.partner.task.homeAllCategories
import com.speaking.partner.ui.utils.EpoxyEventListener

class HomeCategoriesController(
    private val onClick: EpoxyEventListener? = null,
    private val onAddCategoryClick: EpoxyEventListener? = null,
) : TypedEpoxyController<List<Category>>() {
    companion object {
        const val FOOTER_ID = 0L
    }

    override fun buildModels(data: List<Category>?) {
        data?.forEach {
            homeAllCategories {
                id(it.id)
                title(it.title)
                count(it.taskCount)
                openCategoryClickListener { this@HomeCategoriesController.onClick?.onEvent(it.id) }
            }
        }

        categoriesFooter {
            id(FOOTER_ID)
            addCategoryClickListener {
                this@HomeCategoriesController.onClick?.onEvent(FOOTER_ID)
            }
        }
    }
}