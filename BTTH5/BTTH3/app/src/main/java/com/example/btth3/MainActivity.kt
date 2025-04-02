package com.example.btth3

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edtPhoneNumber: EditText
    private lateinit var btnAdd: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BlockedNumbersAdapter
    private val blockedNumbers = mutableListOf<String>()

    private val permissions = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ANSWER_PHONE_CALLS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        edtPhoneNumber = findViewById(R.id.edtPhoneNumber)
        btnAdd = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        if (!hasPermissions()) {
            ActivityCompat.requestPermissions(this, permissions, 100)
        }

        loadBlockedNumbers()

        btnAdd.setOnClickListener {
            val number = edtPhoneNumber.text.toString()
            if (number.isNotEmpty()) {
                blockedNumbers.add(number)
                saveBlockedNumbers()
                edtPhoneNumber.text.clear()
                updateRecyclerView()
            }
        }
    }

    private fun saveBlockedNumbers() {
        val prefs = getSharedPreferences("BlockedList", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putStringSet("blockedNumbers", blockedNumbers.toSet())
        editor.apply()
    }

    private fun loadBlockedNumbers() {
        val prefs = getSharedPreferences("BlockedList", MODE_PRIVATE)
        val savedNumbers = prefs.getStringSet("blockedNumbers", emptySet())
        blockedNumbers.clear()
        blockedNumbers.addAll(savedNumbers!!)
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        adapter = BlockedNumbersAdapter(blockedNumbers)
        recyclerView.adapter = adapter
    }

    private fun hasPermissions(): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}
