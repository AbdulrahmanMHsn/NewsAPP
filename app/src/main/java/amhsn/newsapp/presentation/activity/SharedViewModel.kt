package amhsn.newsapp.presentation.activity

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel(){

    private val _url = mutableStateOf("")
    val url = _url

    fun shareURL(url: String) {
        _url.value = url
    }

}