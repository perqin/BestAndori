package io.github.bestandori.data.repository

import android.webkit.CookieManager
import io.github.bestandori.data.service.BestdoriServiceFactory
import io.github.bestandori.data.service.pojo.LoginRequestBody
import io.github.bestandori.data.sp.SpRepository
import io.github.bestandori.util.PK_COOKIE
import io.github.bestandori.util.PK_USERNAME
import io.github.bestandori.util.URL_BESTDORI
import io.github.bestandori.util.v
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

object AuthRepository {
    private const val TAG = "AuthRepository"

    suspend fun login(username: String, password: String) {
        // Get cookie first!
        val homePageResponse = BestdoriServiceFactory.get().homePage(URL_BESTDORI)
        if (!homePageResponse.isSuccessful) {
            throw HttpException(homePageResponse)
        }
        val homePageCookie = homePageResponse.headers()["set-cookie"]
        v(TAG, "login: homePageCookie = $homePageCookie")
        val response = BestdoriServiceFactory.get().login(homePageCookie?:"", LoginRequestBody(username, password))
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        if (response.body()?.result != true) {
            throw RuntimeException("Login failed: result == false")
        }
        v(TAG, "login: Logged in as $username with cookie ${response.headers()["set-cookie"]}")
        SpRepository.liveSp.getPreferences().edit()
            .putString(PK_USERNAME, username)
            .putString(PK_COOKIE, response.headers()["set-cookie"])
            .apply()
        CookieManager.getInstance().setCookie(URL_BESTDORI, homePageCookie)
        CookieManager.getInstance().setCookie(URL_BESTDORI, response.headers()["set-cookie"])
    }

    suspend fun login(cookie: String) {
        val response = BestdoriServiceFactory.get().getMe(cookie)
        if (!response.result) {
            throw RuntimeException("Login failed: result == false")
        }
        withContext(Dispatchers.Main) {
            v(TAG, "login: Login successfully")
            // TODO: Not updating UI automatically
            SpRepository.liveSp.getPreferences().edit().putString(PK_USERNAME, response.username).apply()
            SpRepository.liveSp.getPreferences().edit().putString(PK_COOKIE, cookie).apply()
        }
    }
}
