package de.hsos.ma.adhocdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_table_add_data_set.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseCoroutineBaseMenuAppCompactActivity (private val layoutRes: Int, private val pageTitle : Int = -1, private val showBackButton : Boolean = false, override val selectedMenuItem: Int) : CoroutineScope, BaseMenu(selectedMenuItem) {
    private lateinit var job : Job
    private var pageTitleString = ""

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
        actionbar!!.title = if(pageTitle >= 0) this.getString(pageTitle) else ""
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(showBackButton)
        actionbar.setDisplayHomeAsUpEnabled(showBackButton)
    }

    public fun titleBarTitle(title: String){
        supportActionBar?.title = title
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
    protected inline fun calcDps(dps: Float): Int {
        //Quelle: https://stackoverflow.com/a/5255256/5026265
        val scale = applicationContext.resources.displayMetrics.density
        return (dps * scale + 0.5f).toInt()
    }
}