package io.github.bestandori.ui.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import io.github.bestandori.R
import io.github.bestandori.ui.page.cards.CardsFragment
import io.github.bestandori.util.lazyListOf
import kotlinx.android.synthetic.main.fragment_information.view.*

/**
 * A simple [Fragment] subclass.
 */
class InformationFragment : Fragment() {
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information, container, false)
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
        { CardsFragment() }
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun getTitle(position: Int) = fragments[position].title
}
