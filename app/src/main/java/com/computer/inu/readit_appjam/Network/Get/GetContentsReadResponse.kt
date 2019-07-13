package com.computer.inu.readit_appjam.Network.Get

import java.io.Serializable

data class GetContentsReadResponse(
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<HighlightData>
)

data class HighlightData(
    var highlight_idx: Int,
    var contents_idx: Int,
    var highlight_date: String,
    var highlight_rect: String
) : Serializable