package com.altwav.samquicksal2.mainActivityFragments

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.NearbyRestaurantsAdapter
import com.altwav.samquicksal2.Adapters.RatedRestaurantsAdapter
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.NearbyRestoModel
import com.altwav.samquicksal2.viewmodel.NearbyRestoViewModel
import com.altwav.samquicksal2.viewmodel.RatedRestaurantsViewModel
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import com.google.android.gms.location.LocationServices


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
    private lateinit var viewModel2: NearbyRestoViewModel

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient



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

        if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            view.tvTurnOnLocation.visibility = View.VISIBLE
            view.btn_turn_on_location.visibility = View.VISIBLE
            view.nearbyRestaurantsRecycler.visibility = View.GONE

            view.btn_turn_on_location.setOnClickListener {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            }
        } else {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
            fusedLocationProviderClient.locationAvailability.addOnSuccessListener {
                if(it.isLocationAvailable){
                    fusedLocationProviderClient.lastLocation.addOnCompleteListener { location2 ->
                        recyclerView2 = view.findViewById(R.id.nearbyRestaurantsRecycler)
                        adapter2 = NearbyRestaurantsAdapter()
                        recyclerView2.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        recyclerView2.adapter = adapter2

                        viewModel2 = ViewModelProvider(this).get<NearbyRestoViewModel>()
                        viewModel2.getNearbyRestoObserver().observe(viewLifecycleOwner, { it2 ->
                            if (it2 != null) {
                                view.nearbyRestaurantsRecycler.visibility = View.VISIBLE
                                view.tvTurnOnLocation.visibility = View.GONE
                                view.btn_turn_on_location.visibility = View.GONE
                                adapter2.setNearbyResto(it2)
                                adapter2.notifyDataSetChanged()
                            } else {
                                view.nearbyRestaurantsRecycler.visibility = View.GONE
                                view.btn_turn_on_location.visibility = View.GONE
                                view.tvTurnOnLocation.visibility = View.VISIBLE
                                view.tvTurnOnLocation.text = "It seems like there are no nearby restaurants at your location right now"
                            }
                        })

                        val location: Location = location2.result
                        val custLoc = NearbyRestoModel("${location.latitude}", "${location.longitude}")
                        viewModel2.getNearbyRestoInfo(custLoc)
                    }
                } else {
                    nearbyRestaurantsRecycler.visibility = View.GONE
                    btn_turn_on_location.visibility = View.GONE
                    tvTurnOnLocation.visibility = View.VISIBLE
                    tvTurnOnLocation.text = "Could not get location, please turn on your GPS"
                }
            }
        }


        view.layout_home_fragment.setOnRefreshListener {
            if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                view.tvTurnOnLocation.visibility = View.VISIBLE
                view.btn_turn_on_location.visibility = View.VISIBLE
                view.nearbyRestaurantsRecycler.visibility = View.GONE

                view.btn_turn_on_location.setOnClickListener {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
                }
            } else {
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
                fusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                fusedLocationProviderClient.locationAvailability.addOnSuccessListener {
                    if(it.isLocationAvailable){
                        fusedLocationProviderClient.lastLocation.addOnCompleteListener { location2 ->
                            recyclerView2 = view.findViewById(R.id.nearbyRestaurantsRecycler)
                            adapter2 = NearbyRestaurantsAdapter()
                            recyclerView2.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                            recyclerView2.adapter = adapter2

                            viewModel2 = ViewModelProvider(this).get<NearbyRestoViewModel>()
                            viewModel2.getNearbyRestoObserver().observe(viewLifecycleOwner, { it2 ->
                                if (it2 != null) {
                                    view.nearbyRestaurantsRecycler.visibility = View.VISIBLE
                                    view.tvTurnOnLocation.visibility = View.GONE
                                    view.btn_turn_on_location.visibility = View.GONE
                                    adapter2.setNearbyResto(it2)
                                    adapter2.notifyDataSetChanged()
                                } else {
                                    view.nearbyRestaurantsRecycler.visibility = View.GONE
                                    view.btn_turn_on_location.visibility = View.GONE
                                    view.tvTurnOnLocation.visibility = View.VISIBLE
                                    view.tvTurnOnLocation.text = "It seems like there are no nearby restaurants at your location right now"
                                }
                            })

                            val location: Location = location2.result
                            val custLoc = NearbyRestoModel("${location.latitude}", "${location.longitude}")
                            viewModel2.getNearbyRestoInfo(custLoc)
                        }
                    } else {
                        nearbyRestaurantsRecycler.visibility = View.GONE
                        btn_turn_on_location.visibility = View.GONE
                        tvTurnOnLocation.visibility = View.VISIBLE
                        tvTurnOnLocation.text = "Could not get location, please turn on your GPS"
                    }
                }
            }
            view.layout_home_fragment.isRefreshing = false
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