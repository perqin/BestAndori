package io.github.bestandori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import io.github.bestandori.ui.page.account.AccountFragment
import io.github.bestandori.ui.page.profiles.ProfilesFragment
import io.github.bestandori.util.lazyListOf
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Setup ViewPager
        pagerAdapter = PagerAdapter(this)
        view.viewPager.adapter = pagerAdapter
        // Setup TabLayout
        TabLayoutMediator(view.tabLayout, view.viewPager) { tab, position ->
            tab.text = pagerAdapter.getTitle(position)
        }.attach()
    }
}

private class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = lazyListOf(
        { AccountFragment() },
        { ProfilesFragment() }
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun getTitle(position: Int) = fragments[position].title
}
