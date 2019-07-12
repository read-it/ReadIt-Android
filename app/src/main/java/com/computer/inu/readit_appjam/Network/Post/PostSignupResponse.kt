package com.computer.inu.readit_appjam.Network.Post

import com.computer.inu.readit_appjam.Data.Token

data class PostSignupResponse(var status: Int, var success: Boolean, var message: String, var data: Token)