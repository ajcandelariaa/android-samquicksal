    package com.altwav.samquicksal2.mainActivityFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.ListsOfPromosAdapter
import com.altwav.samquicksal2.Adapters.ListsOfRestaurantAdapter
import com.altwav.samquicksal2.LoadingDialog2
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.viewmodel.ListOfPromosViewModel
import com.altwav.samquicksal2.viewmodel.ListsOfRestaurantsViewModel
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import kotlinx.android.synthetic.main.fragment_promos.view.*
import kotlinx.android.synthetic.main.fragment_restaurants.view.*

    // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PromosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PromosFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListsOfPromosAdapter


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
        val view = inflater.inflate(R.layout.fragment_promos, container, false)

        recyclerView = view.findViewById(R.id.promosRecyclerView)
        adapter = ListsOfPromosAdapter()

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        val viewModel = ViewModelProvider(this).get<ListOfPromosViewModel>()
        viewModel.getListOfPromosObserver().observe(viewLifecycleOwner, {
            if (it != null){
                view.containerNoPromosFragment.visibility = View.GONE
                view.promosRecyclerView.visibility = View.VISIBLE
                adapter.setPromosList(it)
                adapter.notifyDataSetChanged()
            } else {
                view.containerNoPromosFragment.visibility = View.VISIBLE
                view.promosRecyclerView.visibility = View.GONE
            }
        })
        viewModel.getPromosInfo()

        view.refreshPromos.setOnRefreshListener {
            viewModel.getPromosInfo()
            view.refreshPromos.isRefreshing = false
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
         * @return A new instance of fragment PromosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PromosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}