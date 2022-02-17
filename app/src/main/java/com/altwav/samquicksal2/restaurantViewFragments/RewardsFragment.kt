package com.altwav.samquicksal2.restaurantViewFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListOfTasksAdapter
import com.altwav.samquicksal2.Adapters.ListsOfParticularPromosAdapter
import com.altwav.samquicksal2.RestaurantViewActivity
import com.altwav.samquicksal2.viewmodel.RestaurantRewardPromoViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_rewards.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.LoadingDialog2


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RewardsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RewardsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListOfTasksAdapter

    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter2: ListsOfParticularPromosAdapter

    private val loading = LoadingDialog2(this)

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
        val view =
            inflater.inflate(com.altwav.samquicksal2.R.layout.fragment_rewards, container, false)

        view.clRestaurantPromos.visibility = View.GONE
        loading.startLoading()

        val restaurantId = (activity as RestaurantViewActivity?)?.getRestaurantId()

        val imageView = view.findViewById<ImageView>(com.altwav.samquicksal2.R.id.gifImage)
        Glide.with(view).load(com.altwav.samquicksal2.R.drawable.rewards_gif).into(imageView)

        recyclerView = view.findViewById(com.altwav.samquicksal2.R.id.tasksRecyclerView)
        adapter = ListOfTasksAdapter()
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        recyclerView2 = view.findViewById(com.altwav.samquicksal2.R.id.particularPromosRecyclerview)
        adapter2 = ListsOfParticularPromosAdapter()
        recyclerView2.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView2.adapter = adapter2

        val viewModel = ViewModelProvider(this).get<RestaurantRewardPromoViewModel>()
        viewModel.getRestaurantRewardPromoObserver().observe(viewLifecycleOwner, {
            view.clRestaurantPromos.visibility = View.VISIBLE
            loading.isDismiss()
            if (it.stampTasks == null && it.promos == null) {
                view.containerRewardsAndPromo.visibility = ViewGroup.GONE
            } else {
                view.containerNoRewardsNoPromo.visibility = ViewGroup.GONE

                if (it.stampTasks != null) {
                    view.containerNoRewards.visibility = ViewGroup.GONE
                    adapter.setRestaurantTask(it.stampTasks)
                    adapter.notifyDataSetChanged()

                    view.tvStampReward.text = it.stampReward
                    view.tvStampCapacity.text = it.stampCapacity.toString()
                    view.tvStampValidity.text = it.stampValidity
                } else {
                    view.stampCardContainer.visibility = ViewGroup.GONE
                    val constraintLayout: ConstraintLayout = view.containerRewardsAndPromo
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(constraintLayout)
                    constraintSet.connect(
                        R.id.containerPromoTitle,
                        ConstraintSet.TOP,
                        R.id.containerNoRewards,
                        ConstraintSet.BOTTOM,
                        80,
                    )
                    constraintSet.applyTo(constraintLayout)
                }

                // PROMO SECTION
                if (it.promos != null) {
                    view.containerNoPromos.visibility = ViewGroup.GONE
                    adapter2.setRestaurantParticularPromo(it.promos, restaurantId!!)
                    adapter2.notifyDataSetChanged()
                } else {
                    view.particularPromosRecyclerview.visibility = View.GONE
                }
            }
        })


        if (restaurantId != null) {
            viewModel.getRewardPromoInfo(restaurantId)
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
         * @return A new instance of fragment RewardsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RewardsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}