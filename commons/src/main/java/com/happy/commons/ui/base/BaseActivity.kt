package com.etoos.commons.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: T

    abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        binding.lifecycleOwner = this@BaseActivity
        initData()
        initUi()
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }


    /* bundle, intent 처리 및 기본값 초기화 */
    open fun initData() = Unit

    /* ui 초기화 */
    open fun initUi() = Unit

    /* livedata, flow, RX observer */
    open fun setupObservers() = Unit
}