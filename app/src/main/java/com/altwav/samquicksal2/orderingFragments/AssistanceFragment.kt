package com.altwav.samquicksal2.orderingFragments

import android.app.AlertDialog
import android.content.Context
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
import com.altwav.samquicksal2.Adapters.OrderingAssistanceAdapter
import com.altwav.samquicksal2.Adapters.OrderingFoodSetAdapter
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.OrderingAssistanceModel
import com.altwav.samquicksal2.viewmodel.CurrentOrdersViewModel
import com.altwav.samquicksal2.viewmodel.OrderAsstHistViewModel
import com.altwav.samquicksal2.viewmodel.OrderingAssistanceViewModel
import com.altwav.samquicksal2.viewmodel.UpdateFoodItemViewModel
import kotlinx.android.synthetic.main.fragment_assistance.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AssistanceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AssistanceFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderingAssistanceAdapter

    private lateinit var viewModel: OrderAsstHistViewModel
    private lateinit var viewModel2: OrderingAssistanceViewModel

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
        val view =  inflater.inflate(R.layout.fragment_assistance, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        recyclerView = view.findViewById(R.id.orderingAssistanceRecyclerView)
        adapter = OrderingAssistanceAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get<OrderAsstHistViewModel>()
        viewModel.getOrderAsstHistObserver().observe(viewLifecycleOwner, {
            if (it != null){
                adapter.setOrderAssistHist(it)
                adapter.notifyDataSetChanged()
                view.textView71.visibility = View.VISIBLE
                view.textView73.visibility = View.VISIBLE
                view.orderingAssistanceRecyclerView.visibility = View.VISIBLE
                view.clCONoRequests.visibility = View.GONE
            } else {
                view.textView71.visibility = View.GONE
                view.textView73.visibility = View.GONE
                view.orderingAssistanceRecyclerView.visibility = View.GONE
                view.clCONoRequests.visibility = View.VISIBLE
            }
        })

        viewModel.getOrderAsstHistInfo(customerId!!)

        view.clCAChangeGrill.setOnClickListener{
            AlertDialog.Builder(view.context)
                .setTitle("Change Grill")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Are you sure you want to change grill?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val assistance = OrderingAssistanceModel(customerId, "Change Grill")
                    viewModel2.getOrderingAssistanceInfo(assistance)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                }
                .show()
        }

        view.clCACallWaiter.setOnClickListener {
            AlertDialog.Builder(view.context)
                .setTitle("Call A Waiter")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Are you sure you want to call a waiter?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val assistance = OrderingAssistanceModel(customerId, "Call A Waiter")
                    viewModel2.getOrderingAssistanceInfo(assistance)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                }
                .show()
        }

        viewModel2 = ViewModelProvider(this).get<OrderingAssistanceViewModel>()
        viewModel2.getOrderingAssistanceObserver().observe(viewLifecycleOwner, {
            if (it != null){
                Toast.makeText(view.context, "${it.status}", Toast.LENGTH_LONG).show()
                viewModel.getOrderAsstHistInfo(customerId)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(view.context, "ERROR", Toast.LENGTH_LONG).show()
            }
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AssistanceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AssistanceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}