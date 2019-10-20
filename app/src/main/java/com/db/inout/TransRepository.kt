package com.db.inout

import com.db.inout.model.Transliteration
import com.db.inout.network.getRetrofitClient
import okhttp3.MediaType
import okhttp3.RequestBody

class TransRepository {
    suspend fun getResults(
        targetLang: String,
        sourceLang: String,
        domain: Int,
        postBody: String
    ): Transliteration {

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"), createJsonParam(postBody).toString()
        )

        return getRetrofitClient(BASE_URL).getResults(
            BuildConfig.API_KEY, BuildConfig.APP_ID, targetLang, sourceLang,
            domain, body
        )

    }
}