package com.android.shawnclisby.thetodolist.data.models

sealed class SortOrder {
    data class DateOrder(val orderBy: OrderBy = OrderBy.ASC) : SortOrder()
    data class TitleOrder(val orderBy: OrderBy = OrderBy.ASC) : SortOrder()
}

enum class OrderBy {
    DESC, ASC
}