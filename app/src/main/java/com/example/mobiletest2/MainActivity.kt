package com.example.mobiletest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

import com.example.mobiletest2.ui.NearestFragment
import com.example.mobiletest2.ui.PreviousRideFragment
import com.example.mobiletest2.ui.UpcomingFragment
import com.example.mobiletest2.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var filterCard:CardView
    private lateinit var filterTxt : TextView
    private lateinit var stateTextView: AutoCompleteTextView
    private lateinit var cityTextView: AutoCompleteTextView


    private lateinit var nearestFragment : NearestFragment
    private lateinit var upComingFragment : UpcomingFragment
    private lateinit var previousFragment : PreviousRideFragment


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

        nearestFragment = NearestFragment()
        upComingFragment = UpcomingFragment()
        previousFragment = PreviousRideFragment()


        //initially the visibility of filtercard is GONE
        filterCard.visibility = View.GONE

        //when user click on filter textview than we check if filter card is open then close it else open it
        filterTxt.setOnClickListener {
            isFilterCardOpen = !isFilterCardOpen

            if(isFilterCardOpen) {
                filterCard.visibility = View.VISIBLE

                //adding the states of rides in state exposed dropdown filter
                val rides = getStateRides()
                val arrayAdapter = ArrayAdapter(this,R.layout.dropdownlistlayout,rides)
                stateTextView.setAdapter(arrayAdapter)

                //adding the cities of rides in state exposed dropdown filter
                val cityRides = getCityRides()
                val cityRidesArrayAdapter = ArrayAdapter(this,R.layout.dropdownlistlayout,cityRides)
                cityTextView.setAdapter(cityRidesArrayAdapter)
            }

            else filterCard.visibility = View.GONE
        }

        //passing the selected state to filter the list in fragments
        stateTextView.setOnItemClickListener { adapterView, view, i, l ->
            filterCard.visibility = View.GONE
            setFilterState(getStateRides()[i])
        }

        //passing the selected city to filter the list in fragments
        cityTextView.setOnItemClickListener { adapterView, view, i, l ->
            filterCard.visibility = View.GONE
            setFilterCity(getCityRides()[i])
        }


        //setting up the viewpager adapter and adding the fragments in the viewpager adapter
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPagerAdapter.addFragment(nearestFragment,"Nearest")
        viewPagerAdapter.addFragment(upComingFragment,"Upcoming")
        viewPagerAdapter.addFragment(previousFragment,"Past")
        viewPager.adapter = viewPagerAdapter




    }


    //method which passes the state filter in selected fragment
    fun setFilterState(stateName : String) {
        if (tabLayout.selectedTabPosition == 0) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 0) as? NearestFragment
            f?.setDataFromActivity(stateName)
        }
        else if(tabLayout.selectedTabPosition == 1) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 1) as? UpcomingFragment
            f?.setDataFromActivity(stateName)
        }
        else {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 2) as? PreviousRideFragment
            f?.setDataFromActivity(stateName)
        }
    }

//    method which pass the city filter in selected fragment
    fun setFilterCity(cityName : String) {
        if (tabLayout.selectedTabPosition == 0) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 0) as? NearestFragment
            f?.setCityDataFromActivity(cityName)
        }
        else if(tabLayout.selectedTabPosition == 1) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 1) as? UpcomingFragment
            f?.setCityDataFromActivity(cityName)
        }
        else {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 2) as? PreviousRideFragment
            f?.setCityDataFromActivity(cityName)
        }
    }

    //getting the list of state from the selected fragment
    fun getStateRides():List<String> {
       if(tabLayout.selectedTabPosition == 0) {
           val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 0) as? NearestFragment
           val stateRides = f?.getStateList()!! as ArrayList
           stateRides.add(0,"state")
           return stateRides
       }
       else if(tabLayout.selectedTabPosition == 1) {
           val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 1) as? UpcomingFragment
           val stateRides = f?.getStateList()!! as ArrayList
           stateRides.add(0,"state")
           return stateRides
       }
        else {
           val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 2) as? PreviousRideFragment
           val stateRides = f?.getStateList()!! as ArrayList
           stateRides.add(0,"state")
           return stateRides
       }
    }

    //getting the list of city from the selected fragment
    fun getCityRides():List<String> {
        if(tabLayout.selectedTabPosition == 0) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 0) as? NearestFragment
            val cityRides = f?.getCityList()!! as ArrayList
            cityRides.add(0,"city")
            return cityRides
        }
        else if(tabLayout.selectedTabPosition == 1) {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 1) as? UpcomingFragment
            val cityRides = f?.getCityList()!! as ArrayList
            cityRides.add(0,"city")
            return cityRides
        }
        else {
            val f = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpage.toString() + ":" + 2) as? PreviousRideFragment
            val cityRides = f?.getCityList()!! as ArrayList
            cityRides.add(0,"city")
            return cityRides
        }
    }
}