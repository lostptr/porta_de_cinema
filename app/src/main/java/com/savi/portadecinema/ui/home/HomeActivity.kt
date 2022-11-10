package com.savi.portadecinema.ui.home

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.savi.portadecinema.databinding.ActivityHomeBinding

class HomeActivity : FragmentActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPagerViewer()
    }

    private fun setupPagerViewer() {
        val pageAdapter = HomePagerAdapter(this)
        with(binding) {
            homePageViewer.adapter = pageAdapter
            TabLayoutMediator(homeTab, homePageViewer) { tab, position ->
                tab.text = pageAdapter.getTitle(position)
            }.attach()
        }
    }

}