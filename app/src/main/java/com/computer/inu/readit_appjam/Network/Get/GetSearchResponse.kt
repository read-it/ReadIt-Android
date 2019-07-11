package com.computer.inu.readit_appjam.Network.Get

import com.computer.inu.readit_appjam.Data.ContentsSearchData

data class GetSearchResponse(
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<ContentsSearchData>? // null 처리
)