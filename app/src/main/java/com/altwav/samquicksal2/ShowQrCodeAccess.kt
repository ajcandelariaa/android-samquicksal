package com.altwav.samquicksal2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.activity_show_qr_code_access.*

class ShowQrCodeAccess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_qr_code_access)

        btn_myqr_code_back.setOnClickListener {
            finish()
        }

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode("s@mquicks@l,$customerId", BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for(x in 0 until width){
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }
            ivQrCode.setImageBitmap(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}