package com.altwav.samquicksal2.restaurantViewFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListOfReviewsAdapter
import com.altwav.samquicksal2.Adapters.ListsOfPromosAdapter
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.RestaurantViewActivity
import com.altwav.samquicksal2.viewmodel.RestaurantRewardPromoViewModel
import com.altwav.samquicksal2.viewmodel.RestoReviewViewModel
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.android.synthetic.main.fragment_rewards.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListOfReviewsAdapter

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
        val view = inflater.inflate(R.layout.fragment_reviews, container, false)
        val restaurantId = (activity as RestaurantViewActivity?)?.getRestaurantId()

        recyclerView = view.findViewById(R.id.ratingReviewsRecyclerView)
        adapter = ListOfReviewsAdapter()

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        val viewModel = ViewModelProvider(this).get<RestoReviewViewModel>()
        viewModel.getRestoReviewObserver().observe(viewLifecycleOwner, {
            if (it!= null) {
                if(it.custReviews == null){
                    containerNoRating.visibility = View.VISIBLE
                    tvRRHeader.visibility = View.GONE
                    tvRRAverageRate.visibility = View.GONE
                    tvRRRateCount.visibility = View.GONE
                    tvRRO5.visibility = View.GONE
                    ratingReviewsRecyclerView.visibility = View.GONE
                } else {
                    containerNoRating.visibility = View.GONE
                    tvRRHeader.visibility = View.VISIBLE
                    tvRRAverageRate.visibility = View.VISIBLE
                    tvRRRateCount.visibility = View.VISIBLE
                    tvRRO5.visibility = View.VISIBLE
                    ratingReviewsRecyclerView.visibility = View.VISIBLE

                    adapter.setCustRestReview(it.custReviews)
                    adapter.notifyDataSetChanged()
                    tvRRAverageRate.text = it.averageRating
                    tvRRRateCount.text = "(${it.countReviews})"
                }
            }
        })

        if (restaurantId != null) {
            viewModel.getRestoReviewInfo(restaurantId)
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
         * @return A new instance of fragment ReviewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReviewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}