package com.computer.inu.readit_appjam.Data

data class ContentsOverviewData(
    var contents_idx: Int,
    var title: String?,
    var thumbnail: String?,
    var created_date: String,
    var estimate_time: String,
    var read_flag: Int,
    var contents_url: String,
    var site_url: String,
    var fixed_date: String?,
    var delete_flag: Int,
    var category_idx: Int,
    var user_idx: Int,
    var highlight_cnt: Int,
    var scrap_flag: Int,
    var category_name: String,
    var after_create_date: String
)