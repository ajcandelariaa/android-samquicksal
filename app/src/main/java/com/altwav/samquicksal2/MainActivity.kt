package com.altwav.samquicksal2

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.recreate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.mainActivityFragments.HomeFragment
import com.altwav.samquicksal2.mainActivityFragments.NotificationsFragment
import com.altwav.samquicksal2.mainActivityFragments.PromosFragment
import com.altwav.samquicksal2.mainActivityFragments.RestaurantsFragment
import com.altwav.samquicksal2.models.HomepageCustomerModel
import com.altwav.samquicksal2.models.HomepageCustomerModelResponse
import com.altwav.samquicksal2.models.LoginCustomerModel
import com.altwav.samquicksal2.models.LoginCustomerModelResponse
import com.altwav.samquicksal2.sidebarActivities.Account
import com.altwav.samquicksal2.sidebarActivities.Rewards
import com.altwav.samquicksal2.sidebarActivities.ScanQrCode
import com.altwav.samquicksal2.sidebarActivities.TransactionHistory
import com.altwav.samquicksal2.viewmodel.HomepageCustomerViewModel
import com.altwav.samquicksal2.viewmodel.LoginCustomerViewModel
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_restaurants.*
import kotlinx.android.synthetic.main.main_nav_drawer.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: HomepageCustomerViewModel

    private var drawerLayout: DrawerLayout? = null

    private val fragment1: Fragment = HomeFragment()
    private val fragment2: Fragment = RestaurantsFragment()
    private val fragment3: Fragment = PromosFragment()
    private val fragment4: Fragment = NotificationsFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var activeFragment = fragment1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)

        getCustomerId(customerId)

        viewModel = ViewModelProvider(this).get(HomepageCustomerViewModel::class.java)
        viewModel.getHomepageCustomerObserver().observe(this, Observer <HomepageCustomerModelResponse>{
            if(it != null){
                customerEmailAddress.text = it.emailAddress
                customerName.text = it.name
                Glide.with(this).load(it.profileImage).into(customerImage)
                if(it.status == "onGoing"){
                    ivOngoingGif.visibility = View.VISIBLE
                    Glide.with(this).load(R.drawable.ongoing_icon).into(ivOngoingGif)
                    ivOngoingGif.setOnClickListener {
                        finish()
                        overridePendingTransition( 0, 0);
                        startActivity(intent);
                        overridePendingTransition( 0, 0);
                    }
                } else {
                    ivOngoingGif.visibility = View.GONE
                }
            }
        })

        if (customerId != 0){
            viewModel.getHomepageInfoCustomer(customerId)
        }

        drawerLayout = findViewById(R.id.drawer_layout)

        tvTitle.visibility = View.GONE

        fm.beginTransaction().add(R.id.fragment, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.fragment, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment,fragment1, "1").commit();


        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment1 -> {
                    tvTitle.visibility = View.GONE
                    ivSamquicksalTextLogo.visibility = View.VISIBLE
                    fm.beginTransaction().hide(activeFragment).show(fragment1).addToBackStack(null).commit();
                    activeFragment = fragment1
                }
                R.id.restaurantsFragment1 -> {
                    tvTitle.visibility = View.VISIBLE
                    ivSamquicksalTextLogo.visibility = View.GONE
                    tvTitle.text = "Restaurants"
                    fm.beginTransaction().hide(activeFragment).show(fragment2).addToBackStack(null).commit();
                    activeFragment = fragment2
                }
                R.id.promosFragment1 -> {
                    tvTitle.visibility = View.VISIBLE
                    ivSamquicksalTextLogo.visibility = View.GONE
                    tvTitle.text = "Promos"
                    fm.beginTransaction().hide(activeFragment).show(fragment3).addToBackStack(null).commit();
                    activeFragment = fragment3
                }
                R.id.notificationsFragment1 -> {
                    tvTitle.visibility = View.VISIBLE
                    ivSamquicksalTextLogo.visibility = View.GONE
                    tvTitle.text = "Notifications"
                    fm.beginTransaction().hide(activeFragment).show(fragment4).addToBackStack(null).commit();
                    activeFragment = fragment4
                }
            }
            true
        }

    }

    override fun onBackPressed() {
        if (bottomNavigationView.selectedItemId == R.id.homeFragment1){
            AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    finish()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                }
                .show()
        } else {
            bottomNavigationView.selectedItemId = R.id.homeFragment1
        }
    }


    private var custId = 0
    fun getCustomerId(id: Int){
        custId = id
    }

    //PARA SA MGA NEW ACTIVITES
    fun ClickAccount(view: View?){
        val intent = Intent(this, Account::class.java)
        intent.putExtra("id", custId)
        startActivity(intent)
    }
    fun ClickRewards(view: View?){
        val intent = Intent(this, Rewards::class.java)
        startActivity(intent)
    }
    fun ClickTransaction(view: View?){
        val intent = Intent(this, TransactionHistory::class.java)
        startActivity(intent)
    }
    fun ClickScanQrCode(view: View?){
        val intent = Intent(this, ScanQrCode::class.java)
        startActivity(intent)
    }
    fun ClickLogout(view: View?){
        logout(this);
    }
    private fun logout(activity: Activity) {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setIcon(R.mipmap.ic_launcher)
            .setMessage("Are you sure you want to Logout?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                val intent = Intent(this, Login::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                sharedPreferences.edit().remove("CUSTOMER_ID").apply()
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
            }
            .show()
    }




    //PARA SA PAG OPEN AT CLOSE NG SIDEBAR
    fun ClickMenu(view: View?) {
        openDrawer(drawerLayout)
    }
    private fun openDrawer(drawerLayout: DrawerLayout?) {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
    private fun closeDrawer(drawerLayout: DrawerLayout?) {
        if (drawerLayout != null) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
    }
    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout)
    }
}