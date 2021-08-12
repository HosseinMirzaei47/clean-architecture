package comtest.dandeliontest.todotest.task.tasks

import android.widget.TextView
import androidx.databinding.BindingAdapter
import comtest.dandeliontest.todotest.task.R

@BindingAdapter("setSortTitle")
fun TextView.setSortTitle(sortType: Int) {
    text = when (sortType) {
        TasksViewModel.SORT_STATUS_BY_PRIORITY -> resources.getString(R.string.sort_type_priority)
        TasksViewModel.SORT_STATUS_BY_DUE_DATE -> resources.getString(R.string.sort_type_due_date)
        TasksViewModel.SORT_STATUS_BY_DATE_ADDED -> resources.getString(R.string.sort_type_date_added)
        TasksViewModel.SORT_STATUS_BY_ALPHABETICALLY -> resources.getString(R.string.sort_type_alphabetically)
        else -> ""
    }
}