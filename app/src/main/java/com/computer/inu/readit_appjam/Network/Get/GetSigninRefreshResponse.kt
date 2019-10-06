package com.computer.inu.readit_appjam.Network.Get

data class GetSigninRefreshResponse (
    val data: TokenData?,
    val message: String,
    val status: Int,
    val success: Boolean
)