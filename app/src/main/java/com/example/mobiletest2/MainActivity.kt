package com.example.mobiletest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mobiletest2.data.model.Ride

import com.example.mobiletest2.ui.Nearest
import com.example.mobiletest2.ui.PreviousRide
import com.example.mobiletest2.ui.Upcoming
import com.example.mobiletest2.ui.adapter.ViewPagerAdapter
import com.example.mobiletest2.ui.viewModel.NearestRideViewModel
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var filterCard:CardView
    private lateinit var filterTxt : TextView
    private lateinit var stateTextView: AutoCompleteTextView
    private lateinit var cityTextView: AutoCompleteTextView


    private lateinit var nearestFragment : Nearest
    private lateinit var upComingFragment : Upcoming
    private lateinit var previousFragment : PreviousRide


    private var isFilterCardOpen = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewpage)
        filterCard = findViewById(R.id.filter_card)
        filterTxt = findViewById(R.id.filter)
        stateTextView = findViewById(R.id.statetxt)
        cityTextView = findViewById(R.id.citytxt)

        tabLayout.setupWithViewPager(viewPager)

        nearestFragment = Nearest()
        upComingFragment = Upcoming()
        previousFragment = PreviousRide()


        filterCard.visibility = View.GONE
        filterTxt.setOnClickListener {
            isFilterCardOpen = !isFilterCardOpen

            if(isFilterCardOpen) {
                filterCard.visibility = View.VISIBLE
                val rides = getStateRides()
                val arrayAdapter = ArrayAdapter(this,R.layout.dropdownlistlayout,rides)
                stateTextView.setAdapter(arrayAdapter)

                val cityRides = getCityRides()
                val cityRidesArrayAdapter = ArrayAdapter(this,R.layout.dropdownlistlayout,cityRides)
                cityTextView.setAdapter(cityRidesArrayAdapter)
            }

            else filterCard.visibility = View.GONE
        }

        stateTextView.setOnItemClickListener { adapterView, view, i, l ->
            filterCard.visibility = View.GONE
            setFilterState(getStateRides()[i])
        }

        cityTextView.setOnItemClickListener { adapterView, view, i, l ->
            filterCard.visibility = View.GONE
            setFilterCity(getCityRides()[i])
        }
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPagerAdapter.addFragment(nearestFragment,"Nearest")
        viewPagerAdapter.addFragment(upComingFragment,"Upcoming")
        viewPagerAdapter.addFragment(previousFragment,"Past")
        viewPager.adapter = viewPagerAdapter




    }

    fun setFilterState(stateName : String) {
        if (tabLayout.selectedTabPosition == 0) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 0) as? Nearest
            f?.setDataFromActivity(stateName)
        }
        else if(tabLayout.selectedTabPosition == 1) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 1) as? Upcoming
            f?.setDataFromActivity(stateName)
        }
        else {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 2) as? PreviousRide
            f?.setDataFromActivity(stateName)
        }
    }
    fun setFilterCity(cityName : String) {
        if (tabLayout.selectedTabPosition == 0) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 0) as? Nearest
            f?.setCityDataFromActivity(cityName)
        }
        else if(tabLayout.selectedTabPosition == 1) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 1) as? Upcoming
            f?.setCityDataFromActivity(cityName)
        }
        else {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 2) as? PreviousRide
            f?.setCityDataFromActivity(cityName)
        }
    }
    fun getStateRides():List<String> {
       if(tabLayout.selectedTabPosition == 0) {
           val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 0) as? Nearest
           val stateRides = f?.getStateList()!! as ArrayList
           stateRides.add(0,"state")
           return stateRides
       }
       else if(tabLayout.selectedTabPosition == 1) {
           val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 1) as? Upcoming
           val stateRides = f?.getStateList()!! as ArrayList
           stateRides.add(0,"state")
           return stateRides
       }
        else {
           val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 2) as? PreviousRide
           val stateRides = f?.getStateList()!! as ArrayList
           stateRides.add(0,"state")
           return stateRides
       }
    }

    fun getCityRides():List<String> {
        if(tabLayout.selectedTabPosition == 0) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 0) as? Nearest
            val cityRides = f?.getCityList()!! as ArrayList
            cityRides.add(0,"city")
            return cityRides
        }
        else if(tabLayout.selectedTabPosition == 1) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 1) as? Upcoming
            val cityRides = f?.getCityList()!! as ArrayList
            cityRides.add(0,"city")
            return cityRides
        }
        else {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 2) as? PreviousRide
            val cityRides = f?.getCityList()!! as ArrayList
            cityRides.add(0,"city")
            return cityRides
        }
    }
}