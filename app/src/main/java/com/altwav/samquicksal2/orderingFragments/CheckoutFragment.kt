package com.altwav.samquicksal2.orderingFragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import com.altwav.samquicksal2.*
import com.altwav.samquicksal2.Adapters.OrderingBillAdapter
import com.altwav.samquicksal2.Adapters.OrderingOrdersAdapter
import com.altwav.samquicksal2.models.OrderingAssistanceModel
import com.altwav.samquicksal2.viewmodel.*
import kotlinx.android.synthetic.main.fragment_checkout.*
import kotlinx.android.synthetic.main.fragment_checkout.view.*
import kotlinx.android.synthetic.main.fragment_orders.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderingBillAdapter

    private lateinit var viewModel: OrderingBillViewModel
    private lateinit var viewModel2: OrderingCheckoutViewModel

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
        val view = inflater.inflate(R.layout.fragment_checkout, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        recyclerView = view.findViewById(R.id.orderingBillRecyclerView)
        adapter = OrderingBillAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter


        viewModel = ViewModelProvider(this).get<OrderingBillViewModel>()
        viewModel.getOrderingBillObserver().observe(viewLifecycleOwner, {
            if (it != null){
                adapter.setOrderingbill(it.orders)
                adapter.notifyDataSetChanged()
                tvCBillOrderSet.text = it.orderSetName
                tvCBillOrderSetQuantity.text = "${it.numberOfPersons.toString()}x"
                tvCBillOrderSetTPrice.text = it.orderSetPriceTotal
                tvCBillSubtotal.text = it.subtotal
                tvCBillNumberPwd.text = "${it.numberOfPwd.toString()}x"
                tvCBillPwdTDiscount.text = it.pwdDiscount
                tvCBillChildrenPercent.text = "Children (${it.childrenPercentage.toString()}%)"
                tvCBillNumberChildren.text = "${it.numberOfChildren.toString()}x"
                tvCBillChildrenTDiscount.text = it.childrenDiscount
                tvCBillPromoDiscount.text = it.promoDiscount
                tvCBillAdditionalDiscount.text = it.additionalDiscount
                tvCBillReward.text = "Reward (${it.rewardName})"
                tvCBillRewardDiscount.text = it.rewardDiscount
                tvCBillOffenseCharge.text = it.offenseCharge
                tvCBillTotalPrice.text = it.totalPrice
            }
        })

        viewModel2 = ViewModelProvider(this).get<OrderingCheckoutViewModel>()
        viewModel2.getOrderingCheckoutObserver().observe(viewLifecycleOwner, {
            if (it != null){
                if(it.status == "Cash Payment"){
                    val intent = Intent(view.context, CheckoutStatusActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else if (it.status == "GCash Payment"){
                    val intent = Intent(view.context, GcashCheckoutActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {}
                Toast.makeText(view.context, "${it.status}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(view.context, "ERROR", Toast.LENGTH_LONG).show()
            }
        })

        view.btnPayUsingCash.setOnClickListener {
            AlertDialog.Builder(view.context)
                .setTitle("Pay Using Cash")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Are you sure you want to checkout already? After you proceed you can not go back. If you really want to proceed please wait for the staff to come to your table")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val assistance = OrderingAssistanceModel(customerId, "Cash Payment")
                    viewModel2.getOrderingCheckoutInfo(assistance)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                }
                .show()
        }

        view.btnPayUsingGCash.setOnClickListener {
            AlertDialog.Builder(view.context)
                .setTitle("Pay Using GCash")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Are you sure you want to checkout already? After you proceed you can not go back. If you really want to proceed please double check first your GCash account")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val assistance = OrderingAssistanceModel(customerId, "GCash Payment")
                    viewModel2.getOrderingCheckoutInfo(assistance)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                }
                .show()
        }


        viewModel.getOrderingBillInfo(customerId!!)


        view.refreshCheckoutBill.setOnRefreshListener{
            viewModel.getOrderingBillInfo(customerId)
            refreshCheckoutBill.isRefreshing = false
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
         * @return A new instance of fragment CheckoutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckoutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}