package com.example.btth3

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.ActivityCompat

class CallBlockerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                if (incomingNumber != null && isBlockedNumber(context, incomingNumber)) {
                    endCall(context)
                }
            }
        }
    }

    private fun isBlockedNumber(context: Context?, number: String): Boolean {
        val prefs = context?.getSharedPreferences("BlockedList", Context.MODE_PRIVATE)
        val blockedNumbers = prefs?.getStringSet("blockedNumbers", emptySet())
        return blockedNumbers?.contains(number) ?: false
    }

    private fun endCall(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val telecomManager = context?.getSystemService(Context.TELECOM_SERVICE) as? TelecomManager
            if (telecomManager == null) {
                Log.e("CallBlocker", "Không lấy được TelecomManager")
                return
            }

            if (context.let {
                    ActivityCompat.checkSelfPermission(it, Manifest.permission.ANSWER_PHONE_CALLS)
                } != PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("CallBlocker", "Thiếu quyền ANSWER_PHONE_CALLS")
                return
            }

            telecomManager.endCall()
            Log.d("CallBlocker", "Cuộc gọi đã bị chặn")
        } else {
            Log.e("CallBlocker", "Chặn cuộc gọi không hỗ trợ trên Android < 9 (Pie)")
        }
    }
}
