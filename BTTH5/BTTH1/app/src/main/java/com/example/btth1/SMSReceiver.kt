package com.example.btth1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.widget.Toast

class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                // Điện thoại đang đổ chuông (có cuộc gọi đến)
                Toast.makeText(context, "Cuộc gọi đến từ: $phoneNumber", Toast.LENGTH_SHORT).show()
            } else if (state == TelephonyManager.EXTRA_STATE_IDLE) {
                // Điện thoại trở lại trạng thái rảnh (có thể là cuộc gọi nhỡ)
                phoneNumber?.let {
                    sendAutoReplySMS(context, it)
                }
            }
        }
    }

    private fun sendAutoReplySMS(context: Context?, phoneNumber: String) {
        try {
            val smsManager = SmsManager.getDefault()
            val message = "Xin lỗi, tôi đang bận. Tôi sẽ gọi lại sau!"
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(context, "Đã gửi tin nhắn đến $phoneNumber", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Gửi tin nhắn thất bại", Toast.LENGTH_SHORT).show()
        }
    }
}