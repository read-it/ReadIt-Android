package com.computer.inu.readit_appjam.Network.Get

data class GetMyPageResponse(
    val data: GetUserData,
    val message: String,
    val status: Int,
    val success: Boolean
)