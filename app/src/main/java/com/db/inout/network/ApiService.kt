package com.db.inout.network

import com.db.inout.model.Transliteration
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("transliteration/1.0")
    suspend fun getResults(
        @Header("rev-api-key") apiKey: String,
        @Header("rev-app-id") apiId: String,
        @Query("target_lang") targetLang: String,
        @Query("source_lang") sourceLang: String,
        @Query("domain") domain: Int,
        @Body postBody: RequestBody
    ): Transliteration
}