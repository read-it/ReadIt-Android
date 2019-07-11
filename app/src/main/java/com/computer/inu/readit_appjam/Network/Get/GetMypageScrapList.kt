package com.computer.inu.readit_appjam.Network.Get

data class GetMypageScrapList(
    val data: ArrayList<DataXX>?,
    val message: String,
    val status: Int,
    val success: Boolean
)