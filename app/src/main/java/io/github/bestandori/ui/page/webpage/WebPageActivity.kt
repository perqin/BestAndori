package io.github.bestandori.ui.page.webpage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import io.github.bestandori.BuildConfig
import io.github.bestandori.R
import io.github.bestandori.util.URL_BESTDORI
import io.github.bestandori.util.v
import kotlinx.android.synthetic.main.activity_web_page.*

class WebPageActivity : AppCompatActivity() {
    private var isLogin = false

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_page)

        isLogin = intent.getBooleanExtra(EXTRA_LOGIN, false)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(if (isLogin) R.drawable.ic_baseline_check_24 else R.drawable.ic_baseline_arrow_back_24)
        }

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
        webView.webViewClient = CustomWebViewClient()
        webView.webChromeClient = CustomWebChromeClient()
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)

        webView.loadUrl(URL_BESTDORI)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_web_page, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finishLogin()
                true
            }
            R.id.refreshItem -> {
                CookieManager.getInstance().getCookie(URL_BESTDORI).let { v(TAG, it) }
                webView.loadUrl(URL_BESTDORI)
//                webView.reload()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private inner class CustomWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            supportActionBar?.subtitle = getString(R.string.text_loading)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            supportActionBar?.subtitle = null
        }
    }

    private inner class CustomWebChromeClient : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            supportActionBar?.title = title
        }
    }

    private fun finishLogin() {
        if (isLogin) {
            val cookie = CookieManager.getInstance().getCookie(URL_BESTDORI)
            val loggedIn = cookie.split("; ").firstOrNull { it.startsWith("token=") } != null
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(EXTRA_LOGGED_IN, loggedIn)
                if (loggedIn) {
                    putExtra(EXTRA_COOKIE, cookie)
                }
            })
        }
        finish()
    }

    companion object {
        private const val TAG = "WebPageActivity"
        const val EXTRA_LOGIN = "login"
        const val EXTRA_LOGGED_IN = "loggedIn"
        const val EXTRA_COOKIE = "cookie"
    }
}
