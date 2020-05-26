package com.sunasterisk.music_72.screen

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sunasterisk.music_72.R
import com.sunasterisk.music_72.screen.fragment.home.HomeFragment
import com.sunasterisk.music_72.utils.addFragmentToActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        addFragmentToActivity(supportFragmentManager, HomeFragment.newInstance(), R.id.container)
        navigationBottomHome.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.navigationBottomHome -> return true
            R.id.navigationBottomLibrary -> return true
        }
        return false
    }
}
