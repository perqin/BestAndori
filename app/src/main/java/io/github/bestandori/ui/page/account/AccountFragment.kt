package io.github.bestandori.ui.page.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import io.github.bestandori.R
import io.github.bestandori.ui.page.webpage.WebPageActivity
import io.github.bestandori.ui.widget.DestFragment
import kotlinx.android.synthetic.main.layout_account_logged_in.*
import kotlinx.android.synthetic.main.layout_account_not_logged_in.*

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : DestFragment() {
    private val viewModel by viewModels<AccountViewModel>()
    override val title: String
        get() = "Account"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loginButton.setOnClickListener {
            startActivityForResult(Intent(requireContext(), WebPageActivity::class.java).apply {
                putExtra(WebPageActivity.EXTRA_LOGIN, true)
            }, REQUEST_LOGIN)
        }
        logoutButton.setOnClickListener {
            viewModel.logout()
        }
        openWebPageButton.setOnClickListener {
            startActivity(Intent(requireContext(), WebPageActivity::class.java))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.isLogin.observe(this, Observer {
            notLoggedInRootLayout.visibility = if (it) View.GONE else View.VISIBLE
            loggedInRootLayout.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.loggedInUsername.observe(this, Observer {
            loggedInAsTextView.text = getString(R.string.text_loggedInAs, it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_LOGIN -> if (resultCode == Activity.RESULT_OK) {
                val loggedIn = data?.getBooleanExtra(WebPageActivity.EXTRA_LOGGED_IN, false) ?: false
                if (loggedIn) {
                    viewModel.login(data?.getStringExtra(WebPageActivity.EXTRA_COOKIE) ?: "")
                } else {
                    viewModel.logout()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_LOGIN = 1
    }
}
