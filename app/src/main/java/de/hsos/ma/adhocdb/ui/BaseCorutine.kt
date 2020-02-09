package de.hsos.ma.adhocdb.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseCoroutine : CoroutineScope {
    private var job : Job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun onDestroy() {
        job.cancel()
    }
}
