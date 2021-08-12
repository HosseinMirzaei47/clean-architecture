package comtest.dandeliontest.todotest.task.home

import com.airbnb.epoxy.TypedEpoxyController
import comtest.dandeliontest.todotest.model.models.task.Category
import comtest.dandeliontest.todotest.task.categoriesFooter
import comtest.dandeliontest.todotest.task.homeAllCategories
import comtest.dandeliontest.todotest.ui.utils.EpoxyEventListener

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