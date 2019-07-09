package com.computer.inu.readit_appjam.Network.Get

data class Data(
    val category_list: List<Category>?,
    val contents_list: List<Contents>?,
    val current_date: String?,
    val nickname: String?,
    val profile_img: String?,
    val total_count: Int?,
    val unread_count: Int?
)