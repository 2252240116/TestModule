package com.gaodun.android.module.testmodule.activity.interceptor

import androidx.annotation.CallSuper

abstract class BaseInterceptImpl : Interceptor {

    protected var mChain: InterceptChain? = null

    @CallSuper
    override fun intercept(chain: InterceptChain) {
        mChain = chain
    }
}
