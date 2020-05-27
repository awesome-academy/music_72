package com.sunasterisk.music_72.screen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.screen.fragment.home.HomeFragment
import com.sunasterisk.music_72.utils.addFragmentToActivity
import com.sunasterisk.music_72.screen.service.PlayTrackService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var connection: ServiceConnection? = null
    private var playTrackService: PlayTrackService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initConnection()
    }

    private fun initView() {
        navigationBottomHome.setOnNavigationItemSelectedListener(this)
        addFragmentToActivity(supportFragmentManager, HomeFragment.newInstance(), R.id.container)
    }

    private fun initConnection() {
        connection = object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
                val binder = iBinder as PlayTrackService.PlayBinder
                playTrackService = binder.getService()
                initView()
            }

            override fun onServiceDisconnected(componentName: ComponentName) {
            }
        }

        bindService(
            PlayTrackService.getIntent(this),
            connection as ServiceConnection,
            Context.BIND_AUTO_CREATE)
    }

    fun getService() : PlayTrackService? {
        return playTrackService!!
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.navigationBottomHome -> return true
            R.id.navigationBottomLibrary -> return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        connection?.let { unbindService(it) }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
