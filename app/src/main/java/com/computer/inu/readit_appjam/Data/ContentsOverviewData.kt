package com.computer.inu.readit_appjam.Data

data class ContentsOverviewData(
    var thumbnail: String,
    var title: String,
    var url: String,
    var highlight: Int,
    var category: String,
    var read_judge: Boolean
)