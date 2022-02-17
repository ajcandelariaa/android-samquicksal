package com.altwav.samquicksal2

import android.app.Activity
import android.app.AlertDialog

class LoadingDialog(private val myActivity: Activity) {
    private lateinit var isdialog: AlertDialog

    fun startLoading() {
        val infalter = myActivity.layoutInflater
        val dialogView = infalter.inflate(R.layout.loading_dialog, null)

        val bulider = AlertDialog.Builder(myActivity)
        bulider.setView(dialogView)
        bulider.setCancelable(false)

        isdialog = bulider.create()
        isdialog.show()
    }

    fun isDismiss() {
        isdialog.dismiss()
    }
}