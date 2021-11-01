package com.altwav.samquicksal2.orderingFragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListOfReviewsAdapter
import com.altwav.samquicksal2.Adapters.OrderingFoodSetAdapter
import com.altwav.samquicksal2.Adapters.OrderingOrdersAdapter
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.viewmodel.CurrentOrdersViewModel
import com.altwav.samquicksal2.viewmodel.OrderingFoodSetViewModel
import kotlinx.android.synthetic.main.fragment_orders.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderingOrdersAdapter

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
        val view =  inflater.inflate(R.layout.fragment_orders, container, false)

        recyclerView = view.findViewById(R.id.orderingOrdersRecyclerView)
        adapter = OrderingOrdersAdapter()

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        val viewModel = ViewModelProvider(this).get<CurrentOrdersViewModel>()
        viewModel.getCurrentOrdersObserver().observe(viewLifecycleOwner, {
            if (it != null){
                var totalPrice = 0.0
                adapter.setCurrentOrders(it)
                adapter.notifyDataSetChanged()
                for (item in it){
                    totalPrice += item.price?.toDouble()!!
                }
                tvCOTotalPrice.text = "${String.format("%.2f", totalPrice)}"
                clCONoOrders.visibility = View.GONE

            } else {
                llCOTotalContainer.visibility = View.GONE
                orderingOrdersRecyclerView.visibility = View.GONE
                llCOHeader.visibility = View.GONE
                clCONoOrders.visibility = View.VISIBLE
            }
        })

        val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        viewModel.getCurrentOrdersInfo(customerId!!)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrdersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrdersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}