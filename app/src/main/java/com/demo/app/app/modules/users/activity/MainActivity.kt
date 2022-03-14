package com.demo.app.app.modules.users.activity

import com.demo.app.app.utils.BaseActivity
import com.demo.app.app.utils.viewBinding.activityViewBinding
import com.eurosportdemo.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity() {

    private val binding by activityViewBinding(ActivityMainBinding::inflate)
}