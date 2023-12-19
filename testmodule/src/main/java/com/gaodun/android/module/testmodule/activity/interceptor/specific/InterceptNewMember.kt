package com.gaodun.android.module.testmodule.activity.interceptor.specific

import com.gaodun.android.module.testmodule.activity.interceptor.BaseInterceptImpl
import com.gaodun.android.module.testmodule.activity.interceptor.InterceptChain
import com.gaodun.android.module.testmodule.activity.interceptor.JobInterceptEntity
import com.gaodun.commonlib.commonutil.mainutil.ToastUtils

class InterceptNewMember(private val entity: JobInterceptEntity) : BaseInterceptImpl() {

    override fun intercept(chain: InterceptChain) {
        super.intercept(chain)

        if (entity.isNewMember) {
            //拦截
            //可以不弹窗，直接就暴力跳转新页面
            ToastUtils.showShort("直接就暴力跳转新页面")
        } else {
            //放行- 转交给下一个拦截器
            chain.process()
        }
    }


    //已经完成了培训-放行
    fun resetNewMember() {
        mChain?.process()
    }

}
