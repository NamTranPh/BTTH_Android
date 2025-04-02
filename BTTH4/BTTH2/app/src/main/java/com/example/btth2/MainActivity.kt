package com.example.btth2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnEdit: Button
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

        dbHelper = DatabaseHelper(this)

        name = findViewById<EditText>(R.id.txt_name)
        phone = findViewById<EditText>(R.id.txt_phone)
        btnAdd = findViewById<Button>(R.id.btn_add)
        btnEdit = findViewById<Button>(R.id.btn_edit)
        btnDelete = findViewById<Button>(R.id.btn_delete)
        btnShow = findViewById<Button>(R.id.btn_show)
        txtInfo = findViewById<TextView>(R.id.txt_kq)

        btnAdd.setOnClickListener {
            dbHelper.insertData(name.text.toString(), phone.text.toString())
        }

        btnShow.setOnClickListener {
            val data = dbHelper.getContacts()
            txtInfo.text = data.joinToString("\n") { "Họ và tên: ${it.second} - SĐT: ${it.third}" }
        }
    }
}