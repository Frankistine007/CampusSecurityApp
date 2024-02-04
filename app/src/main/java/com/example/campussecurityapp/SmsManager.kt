package com.example.campussecurityapp

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


open class SmsManager : Activity() {
    var sendBtn: Button? = null
    var txtphoneNo: EditText? = null
    var txtMessage: EditText? = null
    var phoneNo: String? = null
    var message: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_manager)
        sendBtn = findViewById<View>(R.id.btnSendSMS) as Button
        txtphoneNo = findViewById<View>(R.id.editText) as EditText
        txtMessage = findViewById<View>(R.id.editText2) as EditText
        sendBtn!!.setOnClickListener { sendSMSMessage() }
    }

    private fun sendSMSMessage() {
        phoneNo = txtphoneNo!!.text.toString()
        message = txtMessage!!.text.toString()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.SEND_SMS
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf<String>(Manifest.permission.SEND_SMS),
                    MY_PERMISSIONS_REQUEST_SEND_SMS
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_SEND_SMS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(phoneNo, null, message, null, null)
                    Toast.makeText(
                        applicationContext, "SMS sent.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "SMS faild, please try again.", Toast.LENGTH_LONG
                    ).show()
                    return
                }
            }
        }
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_SEND_SMS = 0
    }
}
