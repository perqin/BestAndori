package io.github.bestandori.ui.page.account

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import io.github.bestandori.R
import io.github.bestandori.data.repository.AuthRepository
import io.github.bestandori.data.sp.SpRepository
import io.github.bestandori.util.PK_COOKIE
import io.github.bestandori.util.PK_USERNAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel(app: Application) : AndroidViewModel(app) {
    val isLogin: LiveData<Boolean> = Transformations.map(SpRepository.liveSp.getString(PK_USERNAME, "")) { it != "" }
    val loggedInUsername: LiveData<String> = SpRepository.liveSp.getString(PK_USERNAME, "")

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    AuthRepository.login(username, password)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(getApplication(), R.string.text_loginFailed, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(cookie: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    AuthRepository.login(cookie)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(getApplication(), R.string.text_loginFailed, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logout() {
        SpRepository.update(PK_USERNAME, "")
        SpRepository.update(PK_COOKIE, "")
    }
}
