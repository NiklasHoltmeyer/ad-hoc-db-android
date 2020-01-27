package de.hsos.ma.adhocdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseCoroutine (private val layoutRes: Int, private val pageTitle : String = "", private val showBackButton : Boolean = false) : CoroutineScope, AppCompatActivity() {
    private lateinit var job : Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        job = Job()

        initActionBar()
    }

    private fun initActionBar() {
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = pageTitle
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(showBackButton)
        actionbar.setDisplayHomeAsUpEnabled(showBackButton)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}