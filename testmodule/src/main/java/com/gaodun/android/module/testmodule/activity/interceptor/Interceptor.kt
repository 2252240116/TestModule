package com.gaodun.android.module.testmodule.activity.interceptor

/**
 * https://juejin.cn/post/7108149354342383624
 */
interface Interceptor {
    fun intercept(chain: InterceptChain)
}
