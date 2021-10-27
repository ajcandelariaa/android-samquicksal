package com.altwav.samquicksal2.restaurantViewFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListOfStoreHoursAdapter
import com.altwav.samquicksal2.Adapters.ListsOfPostsAdapter
import com.altwav.samquicksal2.Adapters.RatedRestaurantsAdapter
import com.altwav.samquicksal2.Adapters.RestaurantPolicyAdapter
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.RestaurantViewActivity
import com.altwav.samquicksal2.viewmodel.ListsOfRestaurantsViewModel
import com.altwav.samquicksal2.viewmodel.RestaurantAboutViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.fragment_about.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListsOfPostsAdapter

    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter2: ListOfStoreHoursAdapter

    private lateinit var recyclerView3: RecyclerView
    private lateinit var adapter3: RestaurantPolicyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val restaurantId = (activity as RestaurantViewActivity?)?.getRestaurantId()

        recyclerView = view.findViewById(R.id.postsRecyclerView)
        adapter = ListsOfPostsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        recyclerView2 = view.findViewById(R.id.storeHoursRecyclerView)
        adapter2 = ListOfStoreHoursAdapter()
        recyclerView2.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView2.adapter = adapter2

        recyclerView3 = view.findViewById(R.id.restaurantPolicyRecyclerview)
        adapter3 = RestaurantPolicyAdapter()
        recyclerView3.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView3.adapter = adapter3


        val viewModel = ViewModelProvider(this).get<RestaurantAboutViewModel>()
        viewModel.getRestaurantAboutObserver().observe(viewLifecycleOwner, {
            if (it != null){
                adapter.setRestaurantPost(it.rPosts)
                adapter.notifyDataSetChanged()

                adapter2.setRestaurantSchedule(it.rSchedule)
                adapter2.notifyDataSetChanged()

                adapter3.setRestaurantPolicy(it.rPolicy)
                adapter3.notifyDataSetChanged()
                view.tvAboutRestaurantName.text = it.rName
                view.tvAboutRestaurantAddress.text = it.rAddress
                view.tvAboutTablesCapacity.text = "${it.rTableStatus} / ${it.rTableCapacity}"
                view.tvAboutReservedTables.text = it.rReservedTables.toString()
                view.tvAboutNumberOfPeople.text = it.rNumberOfPeople.toString()
                view.tvAboutNumberOfQueues.text = it.rNumberOfQueues.toString()
                Glide.with(this).load(it.rImage).into(view.ivAboutRestaurantImage)
            } else {
                Toast.makeText(activity, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })

        if (restaurantId != null) {
            viewModel.getRestaurantAboutInfo(restaurantId)
        }

        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AboutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}