package com.example.mobiletest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mobiletest2.ui.Nearest
import com.example.mobiletest2.ui.PreviousRide
import com.example.mobiletest2.ui.Upcoming
import com.example.mobiletest2.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewpage)

        tabLayout.setupWithViewPager(viewPager)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPagerAdapter.addFragment(Nearest(),"Nearest")
        viewPagerAdapter.addFragment(Upcoming(),"Upcoming")
        viewPagerAdapter.addFragment(PreviousRide(),"Past")
        viewPager.adapter = viewPagerAdapter
    }
}