package com.db.inout.model

data class ResponseList(
    val inString:String,
    val outString:List<String>,
    val api_status:Int
)