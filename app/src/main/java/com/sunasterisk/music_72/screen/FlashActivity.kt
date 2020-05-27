package com.sunasterisk.music_72.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.screen.service.PlayTrackService

class FlashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash)
        startService(PlayTrackService.getIntent(this))
        startActivity(MainActivity.getIntent(this))
    }
}
