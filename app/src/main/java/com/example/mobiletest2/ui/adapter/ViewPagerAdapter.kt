package com.example.mobiletest2.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm:FragmentManager,behavior:Int) : FragmentPagerAdapter(fm,behavior) {

    private val fragmentArrayList:ArrayList<Fragment> = ArrayList()
    private val fragmentTitle:ArrayList<String> = ArrayList()
    override fun getCount(): Int {
       return fragmentArrayList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentArrayList[position]
    }


    fun addFragment(fragment:Fragment,title:String) {
        fragmentArrayList.add(fragment)
        fragmentTitle.add(title)
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitle[position]
    }
}