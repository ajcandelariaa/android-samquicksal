package com.altwav.samquicksal2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.altwav.samquicksal2.Adapters.RestaurantViewAdapter
import com.altwav.samquicksal2.restaurantViewFragments.AboutFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_restaurant_view.*

class RestaurantViewActivity : AppCompatActivity() {
    private var restaurantId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_view)

        btn_restaurant_view_back.setOnClickListener {
            finish()
        }

        val restId = intent.getIntExtra("restaurantId", 0)
        val restName = intent.getStringExtra("restaurantName")
        Log.d("message", "RESTANME: $restName")
        tvRestaurantNameTitle.text = restName.toString()
        setRestaurantId(restId)

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
    fun setRestaurantId(id: Int){
        restaurantId = id
    }
    fun getRestaurantId(): Int{
        return restaurantId!!
    }
}