package com.demo.app

import com.demo.app.presenter.util.MySchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class TestMySchedulers : MySchedulers {
    override val io: Scheduler
        get() = Schedulers.trampoline()
    override val main: Scheduler
        get() = Schedulers.trampoline()
}