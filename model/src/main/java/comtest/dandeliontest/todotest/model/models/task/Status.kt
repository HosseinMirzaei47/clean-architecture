package comtest.dandeliontest.todotest.model.models.task

data class Status(
    val id: Long,
    val title: String,
    val count: Int,
    val icon: Int,
    val color: Int,
)