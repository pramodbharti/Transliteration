package com.db.inout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.db.inout.model.Transliteration
import kotlinx.coroutines.Dispatchers

class TransViewModel : ViewModel() {

    private val repository = TransRepository()

    fun getResults(
        targetLang: String,
        sourceLang: String,
        domain: Int,
        postBody: String
    ): LiveData<Transliteration> {
        return liveData(Dispatchers.IO) {
            val getResults = repository.getResults(targetLang, sourceLang, domain, postBody)
            emit(getResults)
        }
    }
}