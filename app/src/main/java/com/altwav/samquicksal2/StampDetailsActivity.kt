package com.altwav.samquicksal2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altwav.samquicksal2.Adapters.StampTasksAdapter
import com.altwav.samquicksal2.viewmodel.StampDetailsViewModel
import kotlinx.android.synthetic.main.activity_stamp_details.*

class StampDetailsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StampTasksAdapter

    private lateinit var viewModel: StampDetailsViewModel
    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamp_details)
        clStampCardDetails.visibility = View.GONE
        loading.startLoading()

        btn_stamp_details_back.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.stampTasksRecyclerView)
        adapter = StampTasksAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(StampDetailsViewModel::class.java)
        viewModel.getStampDetailsObserver().observe(this,{
            clStampCardDetails.visibility = View.VISIBLE
            loading.isDismiss()
            if (it != null){
                adapter.setStampTasksDone(it.stampDoneTasks)
                adapter.notifyDataSetChanged()

                tvSCDReward.text = it.stampReward
                tvSCDValidity.text = it.stampValidity

                var count = 1
                var finalTask = ""
                for (task in it.stampTasks!!){
                    if(count == 1){
                        finalTask += "$count. $task"
                    } else {
                        finalTask += "\n$count. $task"
                    }
                    count+=1
                }
                tvSCDTasks.text = finalTask
            }
        })

        val stamp_id = intent.getIntExtra("stamp_id", 0)

        viewModel.getStampDetailsInfo(stamp_id)

    }
}