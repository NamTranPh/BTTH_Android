package com.example.btth1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var Account: EditText
    private lateinit var Password: EditText
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var btnShow: Button
    private lateinit var txtInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        preferenceHelper = PreferenceHelper(this)

        Account = findViewById(R.id.txt_account)
        Password = findViewById(R.id.txt_password)
        btnSave = findViewById(R.id.btn_save)
        btnDelete = findViewById(R.id.btn_delete)
        btnShow = findViewById(R.id.btn_show)
        txtInfo = findViewById(R.id.txt_kq)

        btnSave.setOnClickListener {
            val username = Account.text.toString()
            val password = Password.text.toString()
            preferenceHelper.saveData(username, password)
        }

        btnDelete.setOnClickListener {
            preferenceHelper.deleteData()
        }

        btnShow.setOnClickListener {
            val (username, password) = preferenceHelper.getData()
            if (username != null && password != null) {
                txtInfo.text = "Tài khoản: $username - Mật khẩu: $password"
            } else {
                txtInfo.text = "Không có dữ liệu!"
            }
        }
    }
}