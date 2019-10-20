package com.db.inout

import android.content.Context
import android.net.ConnectivityManager
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


fun createJsonParam(toString: String?): JSONObject {
    /*{
         "data": ["diwali"]
    }*/
    val jsonObject = JSONObject()
    val jsonArray = JSONArray()
    jsonArray.put(toString)
    try {
        jsonObject.put("data", jsonArray)
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return jsonObject
}

fun checkInternet(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}