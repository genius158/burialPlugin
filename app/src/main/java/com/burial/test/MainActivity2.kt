package com.burial.test

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.burial.test.R.layout

class MainActivity2 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        test3()
    }

    override fun onCreate(
            savedInstanceState: Bundle?,
            persistentState: PersistableBundle?
    ) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }

    override fun onPostCreate(
            savedInstanceState: Bundle?,
            persistentState: PersistableBundle?
    ) {
        super.onPostCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()
        Presenter().forClass()
    }

    override fun onPostResume() {
        super.onPostResume()
    }

    override fun onResume() {
        super.onResume()
    }

    fun test() {
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i("test", "testtesttesttesttesttesttesttesttesttest")
    }

    fun test2() {
        val persenter = Presenter()
    }

    fun test3() {
        test()
        test2()
    }
}