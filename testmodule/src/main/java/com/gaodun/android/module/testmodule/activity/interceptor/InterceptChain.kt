package com.gaodun.android.module.testmodule.activity.interceptor

import android.app.Activity
import androidx.fragment.app.FragmentActivity

class InterceptChain private constructor(
    val activity: Activity? = null,
    private var interceptors: MutableList<Interceptor>?
) {
    companion object {
        @JvmStatic
        fun create(count: Int = 0): Builder {
            return Builder(count)
        }
    }

    private var index: Int = 0
//    fun process(skipIndex:Int=-1) {  可通过改变index 控制数组循环 链路
    fun process() {
        interceptors ?: return
//        if(skipIndex == 0){
//            index = 0
//        }
        when (index) {
            in interceptors!!.indices -> {
                val interceptor = interceptors!![index]
                index++
                interceptor.intercept(this)
            }

            interceptors!!.size -> {
                clearAllInterceptors()
            }
        }
    }

    private fun clearAllInterceptors() {
        interceptors?.clear()
        interceptors = null
    }

    open class Builder(private val count: Int = 0) {

        private val interceptors by lazy(LazyThreadSafetyMode.NONE) {
            ArrayList<Interceptor>(count)
        }

        private var activity: Activity? = null

        fun addInterceptor(interceptor: Interceptor): Builder {
            if (!interceptors.contains(interceptor)) {
                interceptors.add(interceptor)
            }
            return this
        }

        fun attach(activity: FragmentActivity): Builder {
            this.activity = activity
            return this
        }

        fun build(): InterceptChain {
            return InterceptChain(activity, interceptors)
        }
    }
}
