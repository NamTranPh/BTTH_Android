package com.example.btth4

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var txtTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button
    private lateinit var btnReset: Button

    private var seconds = 0
    private var running = false  // Trạng thái chạy
    private val handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {
            if (running) {
                seconds++
                val minutes = seconds / 60
                val sec = seconds % 60
                txtTimer.text = String.format("%02d:%02d", minutes, sec)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtTimer = findViewById(R.id.txtTimer)
        btnStart = findViewById(R.id.btnStart)
        btnPause = findViewById(R.id.btnPause)
        btnReset = findViewById(R.id.btnReset)

        btnStart.setOnClickListener {
            if (!running) {
                running = true
                handler.post(runnable)
            }
        }

        btnPause.setOnClickListener {
            running = false
        }

        btnReset.setOnClickListener {
            running = false
            seconds = 0
            txtTimer.text = "00:00"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}

