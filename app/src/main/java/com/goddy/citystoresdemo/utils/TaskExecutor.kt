package com.goddy.citystoresdemo.utils

interface TaskExecutor {
    fun executeOnDiskIO(runnable: Runnable)
    fun executeOnNetworkIO(runnable: Runnable)
    fun executeOnMainThread(runnable: Runnable)
}