package com.altwav.samquicksal2

import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.altwav.samquicksal2.R

class LoadingDialog2(private val myFragment: Fragment) {
    private lateinit var isdialog: AlertDialog

    fun startLoading() {
        val infalter = myFragment.layoutInflater
        val dialogView = infalter.inflate(R.layout.loading_dialog, null)

        val bulider = AlertDialog.Builder(myFragment.requireContext())
        bulider.setView(dialogView)
        bulider.setCancelable(false)

        isdialog = bulider.create()
        isdialog.show()
    }

    fun isDismiss() {
        isdialog.dismiss()
    }
}