package com.speaking.partner.model.models.task

data class NavigationItem(val categoryType: Int, val categoryId: Long)

object CategoryTypes {
    const val USER = 0
    const val DEFAULT = 1
}

object DefaultCategories {
    const val TODAY = -3L
    const val UPCOMING = -2L
    const val INBOX = -1L
}


