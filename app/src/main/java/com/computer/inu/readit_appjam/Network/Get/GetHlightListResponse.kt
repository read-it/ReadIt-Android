package com.computer.inu.readit_appjam.Network.Get

data class GetHlightListResponse(
    val data: ArrayList<hilightListData>?,
    val message: String,
    val status: Int,
    val success: Boolean
)