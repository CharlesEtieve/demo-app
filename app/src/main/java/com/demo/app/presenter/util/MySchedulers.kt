package com.demo.app.presenter.util

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface MainSchedulerProvider {
    val main: Scheduler
        get() = AndroidSchedulers.mainThread()
}

interface IOSchedulerProvider {
    val io: Scheduler
        get() = Schedulers.io()
}

interface MySchedulers : MainSchedulerProvider, IOSchedulerProvider
class MySchedulersImpl @Inject constructor() : MySchedulers
