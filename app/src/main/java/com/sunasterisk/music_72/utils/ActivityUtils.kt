package com.sunasterisk.music_72.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun AppCompatActivity.addFragmentToActivity(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    idRes: Int
) {
    fragmentManager.beginTransaction()
        .setCustomAnimations(
            android.R.animator.fade_in, android.R.animator.fade_out,
            android.R.animator.fade_in, android.R.animator.fade_out
        )
        .add(idRes, fragment, fragment::class.java.simpleName)
        .addToBackStack(fragment::class.java.simpleName)
        .commit()
}

fun AppCompatActivity.replaceFragmentToActivity(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    idRes: Int
) {
   fragmentManager.beginTransaction()
        .setCustomAnimations(
            android.R.animator.fade_in, android.R.animator.fade_out,
            android.R.animator.fade_in, android.R.animator.fade_out
        )
        .replace(idRes, fragment)
        .addToBackStack(fragment::class.java.simpleName)
        .commit()
}

fun AppCompatActivity.removeFragmentToActivity(
    fragmentManager: FragmentManager,
    fragment: Fragment
) {
    fragmentManager.beginTransaction()
        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
        .remove(fragment)
        .commit()
}

fun AppCompatActivity.setupToolbar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)
}
