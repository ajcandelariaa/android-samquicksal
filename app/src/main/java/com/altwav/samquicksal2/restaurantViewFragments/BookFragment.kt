package com.altwav.samquicksal2.restaurantViewFragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListOfOrderSetsAdapter
import com.altwav.samquicksal2.ChooseOrderSetActivity
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.RestaurantViewActivity
import com.altwav.samquicksal2.viewmodel.RestaurantMenuViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.fragment_book.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListOfOrderSetsAdapter

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
        val view = inflater.inflate(R.layout.fragment_book, container, false)
        val restaurantId = (activity as RestaurantViewActivity?)?.getRestaurantId()

        val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        recyclerView = view.findViewById(R.id.orderSetRecyclerView)
        adapter = ListOfOrderSetsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        val viewModel = ViewModelProvider(this).get<RestaurantMenuViewModel>()
        viewModel.getRestaurantMenuObserver().observe(viewLifecycleOwner, {
            if(it != null){
                adapter.setRestaurantMenu(it.menu)
                adapter.notifyDataSetChanged()
                when (it.status) {
                    "onGoing" -> {
                        tvMenuErrorStatus.visibility = View.VISIBLE
                        tvMenuErrorStatus.text = it.description
                        tvMenuDineIn.setTextColor(Color.parseColor("#7A7A7A"))
                        tvMenuReserve.setTextColor(Color.parseColor("#7A7A7A"))
                        btnBookReserve.setBackgroundColor(Color.parseColor("#7A7A7A"))
                        btnBookQueue.setBackgroundColor(Color.parseColor("#7A7A7A"))
                        btnBookQueue.isClickable = false
                        btnBookReserve.isClickable = false
                        Glide.with(this).load(R.drawable.book_queue_icon_disabled).into(ivMenuDineIn)
                        Glide.with(this).load(R.drawable.book_reserve_icon_disabled).into(ivMenuReserve)
                    }
                    "closed" -> {
                        tvMenuErrorStatus.visibility = View.VISIBLE
                        tvMenuErrorStatus.text = it.description
                        tvMenuDineIn.setTextColor(Color.parseColor("#7A7A7A"))
                        btnBookQueue.setBackgroundColor(Color.parseColor("#7A7A7A"))
                        btnBookQueue.isClickable = false
                        Glide.with(this).load(R.drawable.book_queue_icon_disabled).into(ivMenuDineIn)
                    }
                    else -> {
                        tvMenuErrorStatus.visibility = View.GONE
                    }
                }
            }
        })

        view.btnBookQueue.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Queue")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Please note that you may be wait listed from queueing if the restaurant is experiencing a lot of customer")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val intent = Intent(context, ChooseOrderSetActivity::class.java)
                    intent.putExtra("restaurantId", restaurantId)
                    intent.putExtra("bookType", "Queue-in")
                    startActivity(intent)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                }
                .show()
        }

        view.btnBookReserve.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Reserve")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Please note that you may be declined due to the limitation of reservation tables per day.")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val intent = Intent(context, ChooseOrderSetActivity::class.java)
                    intent.putExtra("restaurantId", restaurantId)
                    intent.putExtra("bookType", "Reservation")
                    startActivity(intent)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                }
                .show()
        }

        if (restaurantId != null) {
            viewModel.getMenuInfo(restaurantId, customerId!!)
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
         * @return A new instance of fragment BookFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}