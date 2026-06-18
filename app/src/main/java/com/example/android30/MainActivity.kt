package com.example.android30 // Change to your package name

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Start the streaming service immediately
        val intent: Intent = Intent(this, StreamingService::class.java)
        startForegroundService(intent)


        // Finish activity so it runs in background
        finish()

    }

}
