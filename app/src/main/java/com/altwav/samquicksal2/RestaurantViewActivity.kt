package com.altwav.samquicksal2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.altwav.samquicksal2.Adapters.RestaurantViewAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_restaurant_view.*

class RestaurantViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_view)

        btn_restaurant_view_back.setOnClickListener {
            finish()
        }

        val viewPager2 = findViewById<ViewPager2>(R.id.restaurantViewViewPager)
        val tabLayout = findViewById<TabLayout>(R.id.restaurantViewTablayout)
        viewPager2.adapter =  RestaurantViewAdapter(this)
        val tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                0 -> {

                    tab.text = "About"
                }
                1 -> {
                    tab.text = "Book"
                }
                2 -> {
                    tab.text = "Promos"
                }
                3 -> {
                    tab.text = "Reviews"
                }
            }
        })
        tabLayoutMediator.attach()


    }
}