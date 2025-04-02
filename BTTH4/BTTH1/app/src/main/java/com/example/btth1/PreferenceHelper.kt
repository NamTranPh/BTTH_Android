package com.example.btth1

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences("Nam", Context.MODE_PRIVATE)

    fun saveData(username: String, password: String){
        sharedPref.edit().apply() {
            putString("USERNAME", username)
            putString("PASSWORD", password)
            apply()
        }
    }

    fun deleteData(){
        sharedPref.edit().clear().apply()
    }

    fun getData(): Pair<String?, String?>{
        val username = sharedPref.getString("USERNAME", null)
        val password = sharedPref.getString("PASSWORD", null)
        return Pair(username, password)
    }
}