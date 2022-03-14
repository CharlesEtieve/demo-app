package com.demo.app.app.utils

import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseFragment(layoutId: Int) : Fragment(layoutId)  {

    protected val disposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}