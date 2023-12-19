package com.gaodun.android.module.testmodule.activity.livedatabus

/**
 * //接收消息
LiveDataBus.with<"参数类型">("key").observe(this) {
//it为 发布消息的value
}
//发布消息
LiveDataBus.with<"参数类型">("key").value = mState.mPicList//赋值后observe接收
 */
object LiveDataBus {
    //事件总线
    private val mBus = hashMapOf<String, ChannelLiveData<Any>>()

    fun <T> with(channel: String): ChannelLiveData<T> {
        if (!mBus.containsKey(channel)) {
            mBus[channel] = ChannelLiveData()
        }
        return mBus[channel] as ChannelLiveData<T>
    }
}
