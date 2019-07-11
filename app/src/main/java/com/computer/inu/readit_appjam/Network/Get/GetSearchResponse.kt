package com.computer.inu.readit_appjam.Network.Get

import com.computer.inu.readit_appjam.Data.ContentsOverviewData

data class GetSearchResponse(
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<ContentsOverviewData> // null 처리
)