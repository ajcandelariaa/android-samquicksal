package com.altwav.samquicksal2.orderingFragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListOfReviewsAdapter
import com.altwav.samquicksal2.Adapters.OrderingFoodSetAdapter
import com.altwav.samquicksal2.Adapters.OrderingOrdersAdapter
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.UpdateFoodItemInterface
import com.altwav.samquicksal2.models.AddFooditemModel
import com.altwav.samquicksal2.models.UpdateFoodItemModel
import com.altwav.samquicksal2.viewmodel.CurrentOrdersViewModel
import com.altwav.samquicksal2.viewmodel.OrderingFoodSetViewModel
import com.altwav.samquicksal2.viewmodel.SubmitOrdersViewModel
import com.altwav.samquicksal2.viewmodel.UpdateFoodItemViewModel
import kotlinx.android.synthetic.main.fragment_assistance.*
import kotlinx.android.synthetic.main.fragment_assistance.view.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import kotlinx.android.synthetic.main.fragment_notifications.view.refreshNotifications
import kotlinx.android.synthetic.main.fragment_orders.*
import kotlinx.android.synthetic.main.fragment_orders.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdersFragment : Fragment(), UpdateFoodItemInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderingOrdersAdapter

    private lateinit var viewModel: CurrentOrdersViewModel
    private lateinit var viewModel2: UpdateFoodItemViewModel
    private lateinit var viewModel3: SubmitOrdersViewModel

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

        val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        recyclerView = view.findViewById(R.id.orderingOrdersRecyclerView)
        adapter = OrderingOrdersAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get<CurrentOrdersViewModel>()
        viewModel.getCurrentOrdersObserver().observe(viewLifecycleOwner, {
            if (it != null){
                var totalPrice = 0.0
                adapter.setCurrentOrders(it)
                adapter.notifyDataSetChanged()
                for (item in it){
                    totalPrice += item.price?.toDouble()!!
                }

                tvCOTotalPrice.text = "${String.format("%.2f", totalPrice)}"
                llCOTotalContainer.visibility = View.VISIBLE
                orderingOrdersRecyclerView.visibility = View.VISIBLE
                llCOHeader.visibility = View.VISIBLE
                clCONoOrders.visibility = View.GONE

                btnCOSubmitOrder.setOnClickListener {
                    AlertDialog.Builder(view.context)
                        .setTitle("Confirm Order")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("Are you sure you want to submit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialog, id ->
                            viewModel3.getSubmitOrdersInfo(customerId!!)
                        }
                        .setNegativeButton("No") { dialog, id ->
                            dialog.cancel()
                        }
                        .show()
                }
            } else {
                llCOTotalContainer.visibility = View.GONE
                orderingOrdersRecyclerView.visibility = View.GONE
                llCOHeader.visibility = View.GONE
                clCONoOrders.visibility = View.VISIBLE
            }
        })


        viewModel.getCurrentOrdersInfo(customerId!!)

        viewModel2 = ViewModelProvider(this).get<UpdateFoodItemViewModel>()
        viewModel2.getUpdateOrderingFIObserver().observe(viewLifecycleOwner, {
            if (it != null){
                Toast.makeText(view.context, "${it.status}", Toast.LENGTH_LONG).show()
                viewModel.getCurrentOrdersInfo(customerId)
                adapter.notifyDataSetChanged()
            }
        })


        viewModel3 = ViewModelProvider(this).get<SubmitOrdersViewModel>()
        viewModel3.getSubmitOrdersObserver().observe(viewLifecycleOwner, {
            if(it != null) {
                Toast.makeText(view.context, "${it.status}", Toast.LENGTH_LONG).show()
                viewModel.getCurrentOrdersInfo(customerId)
                adapter.notifyDataSetChanged()
            }
        })

        view.refreshCurrentOrders.setOnRefreshListener {
            viewModel.getCurrentOrdersInfo(customerId)
            refreshCurrentOrders.isRefreshing = false
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

    override fun updateItem(foodItem: UpdateFoodItemModel) {
        viewModel2.getUpdateOrderingFIInfo(foodItem)
    }
}