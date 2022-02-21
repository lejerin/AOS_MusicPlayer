package com.happy.commons.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    lateinit var binding: T

    abstract val layoutResourceId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        setupObservers()
        initData()
    }

    /**액티비티에 붙어있는지 확인하거나, 람다식으로 실행할 코드가 있을 경우*/
    fun withAttached(action: (() -> Unit)? = null): Boolean? {
        try {
            if (isVisible && activity != null && context != null) {
                action?.invoke()
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
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