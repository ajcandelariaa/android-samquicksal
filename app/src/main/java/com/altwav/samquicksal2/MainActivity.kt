package com.altwav.samquicksal2

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.geofencing.LocationService
import com.altwav.samquicksal2.mainActivityFragments.HomeFragment
import com.altwav.samquicksal2.mainActivityFragments.NotificationsFragment
import com.altwav.samquicksal2.mainActivityFragments.PromosFragment
import com.altwav.samquicksal2.mainActivityFragments.RestaurantsFragment
import com.altwav.samquicksal2.models.*
import com.altwav.samquicksal2.sidebarActivities.*
import com.altwav.samquicksal2.viewmodel.DeviceTokenViewModel
import com.altwav.samquicksal2.viewmodel.HomepageCustomerViewModel
import com.altwav.samquicksal2.viewmodel.LogoutCustomerViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_nav_drawer.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: HomepageCustomerViewModel
    private lateinit var viewModel2: DeviceTokenViewModel
    private lateinit var viewModel3: LogoutCustomerViewModel
    private var customerId: Int = 0

    private var drawerLayout: DrawerLayout? = null

    private val fragment1: Fragment = HomeFragment()
    private val fragment2: Fragment = RestaurantsFragment()
    private val fragment3: Fragment = PromosFragment()
    private val fragment4: Fragment = NotificationsFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var activeFragment = fragment1

    private val loading = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceIntent = Intent(this, LocationService::class.java)
        startService(serviceIntent)

        clMainActivity.visibility = View.GONE
        loading.startLoading()

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)

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
                        val intent = Intent(this, LiveStatusActivity::class.java)
                        startActivity(intent)
                    }
                } else if (it.status == "eating"){
                    ivOngoingGif.visibility = View.VISIBLE
                    Glide.with(this).load(R.drawable.ongoing_icon).into(ivOngoingGif)
                    ivOngoingGif.setOnClickListener {
                        val intent = Intent(this, OrderingActivity::class.java)
                        startActivity(intent)
                    }
                } else if (it.status == "gcashCheckout"){
                    ivOngoingGif.visibility = View.GONE
                    val intent = Intent(this, GcashCheckoutActivity::class.java)
                    startActivity(intent)
                    finish()
                } else if (it.status == "checkoutValidation"){
                    ivOngoingGif.visibility = View.GONE
                    val intent = Intent(this, CheckoutStatusActivity::class.java)
                    startActivity(intent)
                    finish()
                } else if (it.status == "customerFeedback"){
                    ivOngoingGif.visibility = View.GONE
                    val intent = Intent(this, RateFeedbackActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    ivOngoingGif.visibility = View.GONE
                }
                clMainActivity.visibility = View.VISIBLE
                loading.isDismiss()
            } else {
                clMainActivity.visibility = View.VISIBLE
                loading.isDismiss()
                ivOngoingGif.visibility = View.GONE
                val intent = Intent(this, Login::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                sharedPreferences.edit().remove("CUSTOMER_ID").apply()
                startActivity(intent)
                finish()
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

    fun resetHomeInfo(){
        if (customerId != 0){
            viewModel.getHomepageInfoCustomer(customerId)
        }
    }

    fun sendRegistrationToServer(deviceToken: String){
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)

        viewModel2 = ViewModelProvider(this).get(DeviceTokenViewModel::class.java)
        val customer = DeviceTokenModel(customerId, deviceToken)
        viewModel2.getDeviceTokenInfo(customer)
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
    fun ClickTerms(view: View?){
        val intent = Intent(this, TermsConditions::class.java)
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
                viewModel3 = ViewModelProvider(this).get(LogoutCustomerViewModel::class.java)
                viewModel3.getLogoutCustomerObserver().observe(this, Observer <LogoutCustomerModelResponse>{
                    if(it == null){
                        Toast.makeText(this, "Logout Failed", Toast.LENGTH_SHORT).show()
                    } else {
                        if(it.status == "Logged Out Successfully"){
                            val intent = Intent(this, Login::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                            sharedPreferences.edit().remove("CUSTOMER_ID").apply()
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Logout Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                })

                val customer = LogoutCustomerModel(customerId)
                viewModel3.getLogoutInfoCustomer(customer)

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

    override fun onResume() {
        super.onResume()
        viewModel.getHomepageInfoCustomer(customerId)
    }
}