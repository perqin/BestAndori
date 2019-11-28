package io.github.bestandori.ui.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.bestandori.R
import io.github.bestandori.ui.widget.DestFragment

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : DestFragment() {
    override val title: String
        get() = "Account"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

}
