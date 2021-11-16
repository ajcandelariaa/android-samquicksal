package com.altwav.samquicksal2.orderingFragments

import android.content.Context
import android.content.Intent
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
import com.altwav.samquicksal2.Adapters.OrderingFoodSetAdapter
import com.altwav.samquicksal2.OrderingFoodItemActivity
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.ShowQrCodeAccess
import com.altwav.samquicksal2.sidebarActivities.ScanQrCode
import com.altwav.samquicksal2.viewmodel.ListsOfRestaurantsViewModel
import com.altwav.samquicksal2.viewmodel.OrderingFoodSetViewModel
import com.altwav.samquicksal2.viewmodel.OrdrChkCusAccsViewModel
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.android.synthetic.main.fragment_restaurants.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderingFoodSetAdapter

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
        val view =  inflater.inflate(R.layout.fragment_menu, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        recyclerView = view.findViewById(R.id.orderingFoodSetRecyclerView)
        adapter = OrderingFoodSetAdapter()

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        val viewModel2 = ViewModelProvider(this).get<OrdrChkCusAccsViewModel>()
        viewModel2.getOrdrChkCusAccsObserver().observe(viewLifecycleOwner, {
            if (it != null){
                when (it.status) {
                    "mainCustomer" -> {
                        view.btnORGetQrCode.visibility = View.VISIBLE
                        view.btnORGetQrCode.setOnClickListener {
                            val intent = Intent(activity, ShowQrCodeAccess::class.java)
                            startActivity(intent)
                        }
                        val viewModel = ViewModelProvider(this).get<OrderingFoodSetViewModel>()
                        viewModel.getOrderFSObserver().observe(viewLifecycleOwner, { it2 ->
                            if (it2.restAccId != null){
                                adapter.setOrderingFoodSet(it2.foodSets, it2.restAccId, it2.orderSetId!!)
                                adapter.notifyDataSetChanged()
                            }
                        })

                        viewModel.getOrderFSInfo(customerId!!)
                    }
                    "subCustomer" -> {
                        view.btnORGetQrCode.visibility = View.GONE

                        val viewModel = ViewModelProvider(this).get<OrderingFoodSetViewModel>()
                        viewModel.getOrderFSObserver().observe(viewLifecycleOwner, { it2 ->
                            if (it2.restAccId != null){
                                adapter.setOrderingFoodSet(it2.foodSets, it2.restAccId, it2.orderSetId!!)
                                adapter.notifyDataSetChanged()
                            }
                        })

                        viewModel.getOrderFSInfo(it.mainCust_id!!)
                    }
                    else -> {

                    }
                }
            }
        })

        viewModel2.getOrdrChkCusAccsInfo(customerId!!)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}