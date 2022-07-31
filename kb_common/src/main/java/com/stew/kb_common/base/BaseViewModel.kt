package com.stew.kb_common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by stew on 7/29/22.
 * mail: stewforani@gmail.com
 */

typealias BLOCK = suspend () -> Unit
typealias ERROR = suspend (Exception) -> Unit

open class BaseViewModel : ViewModel() {
    //fun 默认public，这里使用protected
    protected fun launch(
        block: BLOCK,
        error: ERROR
    ) {
        //使用viewModelScope需要添加依赖androidx.navigation:navigation-fragment-ktx
        //这里不需要指定viewModelScope.launch(Dispatchers.IO)，因为retrofit自身会在子线程进行网络请求
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                error(e)
            }
        }
    }
}
