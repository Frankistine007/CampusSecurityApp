package com.example.campussecurityapp

// VolumeButtonReceiver.kt
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.Toast
import androidx.annotation.RequiresApi

class VolumeButtonReceiver : BroadcastReceiver() {

    private var isVolumeDownPressed = false
    private val handler = Handler(Looper.getMainLooper())
    private val longPressDuration = 3000L // 3 seconds

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_MEDIA_BUTTON) {
            val keyEvent = intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
            if (keyEvent?.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                when (keyEvent.action) {
                    KeyEvent.ACTION_DOWN -> {
                        isVolumeDownPressed = true
                        handler.postDelayed({
                            if (isVolumeDownPressed) {
                                startRecordingService(context)
                            }
                        }, longPressDuration)
                    }

                    KeyEvent.ACTION_UP -> {
                        isVolumeDownPressed = false
                        handler.removeCallbacksAndMessages(null)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startRecordingService(context: Context?) {
        Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
        val serviceIntent = Intent(context, MainActivity::class.java)
        context?.startForegroundService(serviceIntent)
    }
}

