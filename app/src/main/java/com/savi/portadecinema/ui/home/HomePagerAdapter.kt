package com.savi.portadecinema.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.savi.portadecinema.R
import com.savi.portadecinema.ui.favorites.FavoritesFragment
import com.savi.portadecinema.ui.popular.PopularFragment

private val TAB_TITLES = arrayOf(
    R.string.fragment_popular_title,
    R.string.fragment_favorite_title
)

class HomePagerAdapter(private val fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PopularFragment()
            1 -> FavoritesFragment()
            else -> throw IndexOutOfBoundsException()
        }
    }

    fun getTitle(position: Int) = fa.getString(TAB_TITLES[position])
}
