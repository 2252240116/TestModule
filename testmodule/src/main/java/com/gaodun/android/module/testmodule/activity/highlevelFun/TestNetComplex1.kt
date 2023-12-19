package com.gaodun.android.module.testmodule.activity.highlevelFun

import com.gaodun.commonlib.log.GdLog
import kotlinx.coroutines.*
import java.util.*

/**
 * Function:
 * Author Name: Riven.zhang
 * Date: 2022/6/28
 * Copyright © 2006-2021 高顿网校, All Rights Reserved.
 */
class TestNetComplex1 {
    fun requestNetwork(
        onCancel: () -> Unit,   //空参和空返回值
        onFinished: ((Boolean) -> Boolean)? = null,  //带参数带返回值
        onSuccess: SuccessCallback.() -> String,   //使用高阶扩展函数的方式
        onFailed: (FailedCallback) -> Unit     //使用带参数的高阶函数的方式
    ) {
        MainScope().launch {
            val result = withContext(Dispatchers.IO) {
                delay(1500)

                return@withContext Random().nextInt(10)
            }

            GdLog.w("result:$result")

            when {
                result == 10 -> {
                    val res = onFinished?.invoke(true)

                    GdLog.w("接收到对面return的值 :$res")
                }
                result > 8 -> {
                    onCancel()
                }
                result > 5 -> {
                    val res = onSuccess(object : SuccessCallback {
                        override fun onSuccess(str: String): String {
                            //这里的str是外包传进来的，我们对这个字符串做处理
                            val str2 = "$str 再加一点数据"
                            GdLog.w("result:$str2")
                            return str2
                        }

                        override fun doSth() {
                            GdLog.w("可以随便写点什么成功之后的逻辑")
                        }
                    })

                    GdLog.w("res:$res")

                }
                else -> {
                    onFailed(object : FailedCallback {    //这种接口的方式只能使用object的实现了
                        override fun onFailed(str: String) {
                            GdLog.w("可以随便写点什么Failed逻辑 :$str")
                        }

                        override fun onError() {
                            GdLog.w("可以随便写点什么Error逻辑")
                        }
                    })
                }
            }
        }
    }

    interface SuccessCallback {  //多个函数不能使用fun修饰了  所以不能用kt1.4SAM了 但是高阶函数可以直接{}
        fun onSuccess(str: String): String

        fun doSth()
    }


    interface FailedCallback {
        fun onFailed(str: String)

        fun onError()
    }
}