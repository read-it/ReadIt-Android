package com.computer.inu.readit_appjam.Network.Get

data class GetUndefinedResponse(
    val data: UndefinedData?,
    val message: String,
    val status: Int,
    val success: Boolean
)