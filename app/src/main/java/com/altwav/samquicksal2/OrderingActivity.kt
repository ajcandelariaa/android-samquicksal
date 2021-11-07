package com.altwav.samquicksal2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.altwav.samquicksal2.Adapters.OrderingAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_ordering.*

class OrderingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordering)

        btn_ordering_view_back.setOnClickListener {
            finish()
        }



        val viewPager2 = findViewById<ViewPager2>(R.id.orderingViewPager)
        val tabLayout = findViewById<TabLayout>(R.id.orderingViewTabLayout)
        viewPager2.adapter =  OrderingAdapter(this)
        val tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Menu"
                }
                1 -> {
                    tab.text = "Orders"
                }
                2 -> {
                    tab.text = "Assistance"
                }
                3 -> {
                    tab.text = "Checkout"
                }
            }
        })
        tabLayoutMediator.attach()
    }
}