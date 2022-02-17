package com.altwav.samquicksal2.sidebarActivities

import android.content.ContentResolver
import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.altwav.samquicksal2.LoadingDialog
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.UploadRequestBody
import com.altwav.samquicksal2.models.AccountCustomerModelResponse
import com.altwav.samquicksal2.models.UpdateNameModel
import com.altwav.samquicksal2.models.UpdatePasswordModel
import com.altwav.samquicksal2.viewmodel.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_gcash_checkout.*
import kotlinx.android.synthetic.main.activity_live_status.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.update_image.view.*
import kotlinx.android.synthetic.main.update_name.view.*
import kotlinx.android.synthetic.main.update_name.view.btnUpdateUpdate
import kotlinx.android.synthetic.main.update_password.view.*
import kotlinx.android.synthetic.main.update_password.view.btnUpdateUpdatePassword
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.regex.Pattern

class Account : AppCompatActivity() {

    private lateinit var viewModel: AccountCustomerViewModel
    private lateinit var viewModel2: UpdateNameViewModel
    private lateinit var viewModel3: UpdatePasswordViewModel
    private lateinit var viewModel4: UpdateImageViewModel
    private lateinit var viewModel5: VerifyEmailViewModel
    private var customerId: Int = 0
    private lateinit var emailAddress: String
    private lateinit var nameDialog: AlertDialog
    private lateinit var passwordDialog: AlertDialog
    private lateinit var imageDialog: AlertDialog

    private lateinit var getContent: ActivityResultLauncher<String>
    private lateinit var viewUpdateImage: View
    private lateinit var requestImage: MultipartBody.Part
    private lateinit var cust_id: RequestBody

    private val loading = LoadingDialog(this)

    private val validEmail = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    private val validFullName = Pattern.compile("^[a-zA-Z\\s]+")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        clAccountDetails.visibility = View.GONE
        loading.startLoading()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        customerId = sharedPreferences.getInt("CUSTOMER_ID", 0)

        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null){
                viewUpdateImage.btnUploadImageLabel.visibility = View.GONE
                viewUpdateImage.btnUploadImage.setImageURI(uri)

                val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r", null)
                val inputStream = FileInputStream(parcelFileDescriptor!!.fileDescriptor)
                val file = File(cacheDir, contentResolver.getFileName(uri))
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)

                val body = UploadRequestBody(file, "image")
                requestImage = MultipartBody.Part.createFormData("userProfile", file.name, body)
                cust_id = customerId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

            } else {
                viewUpdateImage.btnUploadImageLabel.visibility = View.VISIBLE
                Glide.with(this).load(R.drawable.upload_icon).into(viewUpdateImage.btnUploadImage)
            }
        }

        btn_account_back.setOnClickListener {
            finish()
        }

        btnUpdateImage.setOnClickListener {
            updateImageDialog()
        }

        btnUpdateName.setOnClickListener {
            updateNameDialog("Change Name", "Name")
        }

        btnUpdateEmailAddress.setOnClickListener {
            updateNameDialog("Change Email Address", "Email Address")
        }

        btnUpdateContactNumber.setOnClickListener {
            updateNameDialog("Change Contact Number", "Contact Number")
        }

        btnUpdatePassword.setOnClickListener {
            updatePasswordDialog()
        }

        viewModel5 = ViewModelProvider(this).get(VerifyEmailViewModel::class.java)
        viewModel5.getVerifyEmailObserver().observe(this, {
            if(it != null){
                viewModel.getAccountInfoCustomer(customerId)
                Toast.makeText(this, "Link has been sent to your email", Toast.LENGTH_LONG).show()
            }
        })

        viewModel = ViewModelProvider(this).get(AccountCustomerViewModel::class.java)
        viewModel.getAccountCustomerObserver().observe(this, Observer <AccountCustomerModelResponse>{
            if(it != null){
                clAccountDetails.visibility = View.VISIBLE
                loading.isDismiss()

                tvAccountName.text = it.name
                tvHeaderAccountName.text = it.name
                tvAccountEmailAddress.text = it.emailAddress
                tvAccountContactNumber.text = it.contactNumber
                emailAddress = it.emailAddress.toString()

                if(it.emailAddressVerified == "No"){
                    tvEmailAddressStatus.text = "Not Verified"
                    tvEmailAddressStatus.setTextColor(Color.parseColor("#91001B"))
                    Glide.with(this).load(R.drawable.ic_not_verified).into(ivEmailAddressStatus)

                } else {
                    tvEmailAddressStatus.text = "Verified"
                    tvEmailAddressStatus.setTextColor(Color.parseColor("#0AA034"))
                    Glide.with(this).load(R.drawable.ic_verified).into(ivEmailAddressStatus)
                }

                Glide.with(this).load(it.profileImage).into(ivAccountImageInner)
                Glide.with(this).load(it.profileImage).into(ivAccountImageOuter)


                tvEmailAddressStatus.setOnClickListener {
                    if(tvEmailAddressStatus.text == "Not Verified"){
                        AlertDialog.Builder(this)
                            .setTitle("Verify your email")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("Are you sure you want verify your email?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                clAccountDetails.visibility = View.GONE
                                loading.startLoading()
                                viewModel5.getVerifyEmailInfo(customerId)
                            }
                            .setNegativeButton("No") { dialog, id ->
                                dialog.cancel()
                            }
                            .show()
                    }
                }
            }
        })

        if (customerId != 0){
            viewModel.getAccountInfoCustomer(customerId)
        }

        viewModel2 = ViewModelProvider(this).get(UpdateNameViewModel::class.java)
        viewModel2.getUpdateNameObserver().observe(this, {
            if(it != null){
                when (it.status) {
                    "nameUpdated" -> {
                        viewModel.getAccountInfoCustomer(customerId)
                        nameDialog.dismiss()
                        Toast.makeText(this, "Name Updated Successfully", Toast.LENGTH_LONG).show()
                    }
                    "emailUpdated" -> {
                        viewModel.getAccountInfoCustomer(customerId)
                        nameDialog.dismiss()
                        Toast.makeText(this, "Email Address Updated Successfully", Toast.LENGTH_LONG).show()
                    }
                    "numberUpdated" -> {
                        viewModel.getAccountInfoCustomer(customerId)
                        nameDialog.dismiss()
                        Toast.makeText(this, "Contact Number Updated Successfully", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this, "${it.status}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        viewModel3 = ViewModelProvider(this).get(UpdatePasswordViewModel::class.java)
        viewModel3.getUpdatePasswordObserver().observe(this, {
            if(it != null){
                if(it.status == "passwordUpdated"){
                    viewModel.getAccountInfoCustomer(customerId)
                    passwordDialog.dismiss()
                    Toast.makeText(this, "Password Updated Successfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "${it.status}", Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel4 = ViewModelProvider(this).get(UpdateImageViewModel::class.java)
        viewModel4.getUploadImageObserver().observe(this, {
            if(it != null){
                viewModel.getAccountInfoCustomer(customerId)
                imageDialog.dismiss()
                Toast.makeText(this, "Image Updated Successfully", Toast.LENGTH_LONG).show()
            }
        })


        refreshAccount.setOnRefreshListener {
            viewModel.getAccountInfoCustomer(customerId)
            refreshAccount.isRefreshing = false
        }
    }

    private fun updateImageDialog(){
        val dialogBuilder = AlertDialog.Builder(this)
        viewUpdateImage = layoutInflater.inflate(R.layout.update_image, null)

        viewUpdateImage.btnUploadImage.setOnClickListener {
            getContent.launch("image/*")
        }

        viewUpdateImage.btnUpdateUpdateImage.setOnClickListener {
            if(viewUpdateImage.btnUploadImageLabel.visibility == View.VISIBLE){
                Toast.makeText(applicationContext, "Please choose an image first", Toast.LENGTH_LONG).show()
            } else {
                clAccountDetails.visibility = View.GONE
                loading.startLoading()
                viewModel4.getUploadImageInfo(requestImage, cust_id)
            }
        }

        dialogBuilder.setView(viewUpdateImage)
        imageDialog = dialogBuilder.create()
        imageDialog.show()

    }

    private fun updatePasswordDialog(){
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.update_password, null)

        view.btnUpdateUpdatePassword.setOnClickListener {
            if(view.etUpdateOldPassword.text.isEmpty()){
                view.etUpdateOldPassword.error = "Old Password is Required"
                return@setOnClickListener
            } else if(view.etUpdateNewPassword.text.isEmpty()){
                view.etUpdateNewPassword.error = "New Password is Required"
                return@setOnClickListener
            } else if(view.etUpdateNewPassword.text.length < 6){
                view.etUpdateNewPassword.error = "Password must contain at least 6 characters"
                return@setOnClickListener
            } else if(view.etUpdateNewPassword.text.contains(" ")){
                view.etUpdateNewPassword.error = "Password must not contain any spaces"
                return@setOnClickListener
            } else if(view.etUpdateConfirmPassword.text.isEmpty()){
                view.etUpdateConfirmPassword.error = "Confirm Password is Required"
                return@setOnClickListener
            } else if(view.etUpdateConfirmPassword.text.toString() != view.etUpdateNewPassword.text.toString()){
                view.etUpdateConfirmPassword.error = "Confirm Password does not match New Password"
                return@setOnClickListener
            } else {
                val customerInfo = UpdatePasswordModel(customerId, view.etUpdateOldPassword.text.toString(), view.etUpdateNewPassword.text.toString())
                viewModel3.getUpdatePasswordInfo(customerInfo)
            }
        }

        dialogBuilder.setView(view)
        passwordDialog = dialogBuilder.create()
        passwordDialog.show()
    }

    private fun updateNameDialog(title: String, label: String){
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.update_name, null)

        view.tvUpdateTitle.text = title
        view.tvUpdateLabel.text = label

        when (label) {
            "Name" -> {
                view.etUpdateInfo.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME

                view.btnUpdateUpdate.setOnClickListener {
                    if(view.etUpdateInfo.text.isEmpty()){
                        view.etUpdateInfo.error = "Name is Required"
                        return@setOnClickListener
                    } else if (!validFullName.matcher(view.etUpdateInfo.text).matches()){
                        view.etUpdateInfo.error = "Name must contain letters only"
                        return@setOnClickListener
                    } else {
                        val customerInfo = UpdateNameModel(customerId, "Name", view.etUpdateInfo.text.toString())
                        viewModel2.getUpdateNameInfo(customerInfo)
                    }
                }
            }
            "Email Address" -> {
                view.etUpdateInfo.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

                view.btnUpdateUpdate.setOnClickListener {
                    if(view.etUpdateInfo.text.isEmpty()){
                        view.etUpdateInfo.error = "Email Address is Required"
                        return@setOnClickListener
                    } else if (!validEmail.matcher(view.etUpdateInfo.text).matches()){
                        view.etUpdateInfo.error = "Must be a valid Email Address"
                        return@setOnClickListener
                    } else if (view.etUpdateInfo.text.toString() == emailAddress) {
                        view.etUpdateInfo.error = "Must not the same as your previous email address"
                        return@setOnClickListener
                    } else {
                        val customerInfo = UpdateNameModel(customerId, "Email Address", view.etUpdateInfo.text.toString())
                        viewModel2.getUpdateNameInfo(customerInfo)
                    }
                }
            }
            "Contact Number" -> {
                view.etUpdateInfo.inputType = InputType.TYPE_CLASS_PHONE

                view.btnUpdateUpdate.setOnClickListener {
                    if(view.etUpdateInfo.text.isEmpty()){
                        view.etUpdateInfo.error = "Contact Number is Required"
                        return@setOnClickListener
                    } else {
                        val customerInfo = UpdateNameModel(customerId, "Contact Number", view.etUpdateInfo.text.toString())
                        viewModel2.getUpdateNameInfo(customerInfo)
                    }
                }
            }
            else -> {}
        }

        dialogBuilder.setView(view)
        nameDialog = dialogBuilder.create()
        nameDialog.show()
    }

    fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }
}