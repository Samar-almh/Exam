package com.samar.exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

lateinit var tabLayout:TabLayout
lateinit var tabViewPager:ViewPager2

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(),ToDo.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
           val fragment =  ToDo.newInstance()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
        tabLayout=findViewById(R.id.tabs)
        tabViewPager=findViewById(R.id.pager)
        tabViewPager.adapter=object :FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {


                return when (position) {
                    0 -> ToDo.newInstance("", "")
                    1 -> InProgress.newInstance("", "")
                    2 -> Done.newInstance("", "")
                    else -> {
                        Done.newInstance("", "")
                    }
                }
            }
            override fun getItemCount(): Int {
                return 3
            }
        }
        TabLayoutMediator(tabLayout, tabViewPager){tab,position->
            tab.text=when(position){
                0 ->"To-Do"
                1 ->"In-Progress"
                2 ->"Done"
                else -> null
            }

        }.attach()

    }
    override fun onTaskSelected(taskId: UUID) {
        val fragment = TaskFragment.newInstance(taskId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

    }
}