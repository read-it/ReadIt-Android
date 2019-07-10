package com.computer.inu.readit_appjam.Network.Get

data class GetMainStorageResponse(
    val data: Data?,
    val message: String,
    val status: Int,
    val success: Boolean
)