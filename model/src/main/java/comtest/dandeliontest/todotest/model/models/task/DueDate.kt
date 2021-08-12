package comtest.dandeliontest.todotest.model.models.task

data class DueDate(
    val year: Int,
    val month: Int,
    val day: Int,
    var hour: Int = 0,
    var minute: Int = 0,
) {
    override fun toString(): String {
        return "Due date = $year/$month/$day"
    }
}