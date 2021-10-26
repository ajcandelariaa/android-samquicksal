package com.altwav.samquicksal2.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.altwav.samquicksal2.orderingFragments.AssistanceFragment
import com.altwav.samquicksal2.orderingFragments.CheckoutFragment
import com.altwav.samquicksal2.orderingFragments.MenuFragment
import com.altwav.samquicksal2.orderingFragments.OrdersFragment

class OrderingAdapter (fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when (position){
            0 -> {
                return MenuFragment()
            }
            1 -> {
                return OrdersFragment()
            }
            2 -> {
                return AssistanceFragment()
            }
            else -> {
                return CheckoutFragment()
            }
        }
    }
}