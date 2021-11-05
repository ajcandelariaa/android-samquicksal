package com.altwav.samquicksal2.mainActivityFragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.NearbyRestaurantsAdapter
import com.altwav.samquicksal2.Adapters.RatedRestaurantsAdapter
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.viewmodel.CurrentOrdersViewModel
import com.altwav.samquicksal2.viewmodel.RatedRestaurantsViewModel
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.android.synthetic.main.fragment_orders.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RatedRestaurantsAdapter
    private lateinit var viewModel: RatedRestaurantsViewModel

    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter2: NearbyRestaurantsAdapter

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
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val imageList = ArrayList<SlideModel>()
        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        imageList.add(SlideModel(R.drawable.imgslider_3))
        imageList.add(SlideModel(R.drawable.imgslider_1))
        imageList.add(SlideModel(R.drawable.imgslider_2))
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)

        recyclerView = view.findViewById(R.id.ratedRestaurantsRecycler)
        adapter = RatedRestaurantsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get<RatedRestaurantsViewModel>()
        viewModel.getRatedRestaurantsObserver().observe(viewLifecycleOwner, {
            if (it != null){
                adapter.setRatedRestaurants(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.getRatedRestaurantsInfo()


        recyclerView2 = view.findViewById(R.id.nearbyRestaurantsRecycler)
        adapter2 = NearbyRestaurantsAdapter()
        recyclerView2.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView2.adapter = adapter2



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}