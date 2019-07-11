package com.computer.inu.readit_appjam.Network.Get

import com.computer.inu.readit_appjam.Data.ContentsOverviewData

data class DataXXX(
    val contents_list: ArrayList<ContentsOverviewData>?,
    val current_date: String,
    val total_count: Int,
    val unread_count: Int
)