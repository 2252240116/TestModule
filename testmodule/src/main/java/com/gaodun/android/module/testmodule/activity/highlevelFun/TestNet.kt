package com.gaodun.android.module.testmodule.activity.highlevelFun

import kotlinx.coroutines.*
import java.util.*

/**
 * Function:
 * Author Name: Riven.zhang
 * Date: 2022/6/28
 * Copyright © 2006-2021 高顿网校, All Rights Reserved.
 */
class TestNet {
    private lateinit var launch:Job;
    fun requestNetwork(
        onCancel: () -> Unit,   //空参和空返回值
        onLoading: ((isShowLoading: Boolean, value: String) -> Boolean)? = null,  //带参数带返回值
        onSuccess: SuccessCallback,//直接回调接口对象
        onFailed: FailedCallback//直接回调接口对象
    ) {
         //MainScope一般用于Activity中需在ondestory中使用
         launch = MainScope().launch {
            var invoke = onLoading?.invoke(true, "80%")

            val result = withContext(Dispatchers.IO) {
                delay(1500)
                return@withContext Random(10).nextLong()
            }

            when {
                result > 8 -> {
                    onCancel()
                }
                result > 5 -> {
                    onSuccess.onSuccess()
                }
                else -> {
                    onFailed.onFailed()
                }
            }
        }


    }

    //kt1.4 可以支持SAM调用 直接加fun ,代码优雅
    interface SuccessCallback {
        fun onSuccess()
    }

    interface FailedCallback {
        fun onFailed()
    }
}
