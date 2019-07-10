package com.computer.inu.readit_appjam.Network.Get

data class GetCategoryResponse(
    val data: DataX?,
    val message: String,
    val status: Int,
    val success: Boolean
)