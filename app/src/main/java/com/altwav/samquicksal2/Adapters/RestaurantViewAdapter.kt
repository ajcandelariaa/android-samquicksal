package com.altwav.samquicksal2.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.altwav.samquicksal2.restaurantViewFragments.AboutFragment
import com.altwav.samquicksal2.restaurantViewFragments.BookFragment
import com.altwav.samquicksal2.restaurantViewFragments.ReviewsFragment
import com.altwav.samquicksal2.restaurantViewFragments.RewardsFragment


class RestaurantViewAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when (position){
            0 -> {
                return AboutFragment()
            }
            1 -> {
                return BookFragment()
            }
            2 -> {
                return RewardsFragment()
            }
            else -> {
                return ReviewsFragment()
            }
        }
    }


}