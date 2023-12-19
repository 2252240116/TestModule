package com.gaodun.android.module.testmodule.activity

import android.os.Bundle
import android.widget.Toast
import com.gaodun.android.module.testmodule.R
import com.gaodun.android.module.testmodule.activity.highlevelFun.TestNet
import com.gaodun.android.module.testmodule.activity.highlevelFun.TestNetComplex1
import com.gaodun.android.module.testmodule.databinding.TestmoduleActivityHomeBinding
import com.gaodun.android.module.testmodule.router.RouterConsts.PATH_HOME_PAGE
import com.gaodun.android.module.testmodule.router.RouterConsts.PATH_KOTLIN_HIGH_PAGE
import com.gaodun.commonlib.commonutil.mainutil.ToastUtils
import com.gaodun.commonlib.log.GdLog
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.sankuai.waimai.router.annotation.RouterUri
import com.xbcx.commonsdk.view.BaseActivity
import com.xbcx.commonsdk.vm.BaseViewModel
import kotlinx.android.synthetic.main.testmodule_activity_kotlin.*

/**
 * Function:
 * Author Name: Riven.zhang
 * Date: 2022/1/19
 * Copyright © 2006-2021 高顿网校, All Rights Reserved.
 */
@RouterUri(path = [PATH_KOTLIN_HIGH_PAGE])
class KotlinHighActivity : BaseActivity<TestmoduleActivityHomeBinding, BaseViewModel>() {

    override fun initVariableId(): Int {
        return 0
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.testmodule_activity_kotlin
    }

    override fun initTitleBar(topBarLayout: QMUITopBarLayout?) {
        super.initTitleBar(topBarLayout)
        topBarLayout?.setTitle(R.string.testmodule_title_home_page)
    }

    override fun registerView() {
        super.registerView()
        tv.setOnClickListener {
            CallBack(callBack).call()
        }
        tv1.setOnClickListener {
            TestNet().requestNetwork(onCancel = {
                ToastUtils.showShort("取消网络")
            }, onLoading = { isShowLoading, value ->
                if (isShowLoading) {
                    ToastUtils.showShort("显示进度$value")
                }
                true
            }, onFailed = object : TestNet.FailedCallback {
                override fun onFailed() {
                    ToastUtils.showShort("请求失败")
                }
            }, onSuccess = object : TestNet.SuccessCallback {
                override fun onSuccess() {
                    ToastUtils.showShort("请求成功")
                }
            })

            //上述方式不优雅,kt1.4支持sam 可以再接口前加fun
//            ,onFailed = {
//                 toast("test network onFailed")
//            }, onSuccess = {
//            toast("test network onSuccess")
//        })
        }

        tv2.setOnClickListener {
            TestNetComplex1().requestNetwork(onCancel = {

                ToastUtils.showShort("test network onCancel")

            }, onFailed = {
                //先 调用内部 的函数处理逻辑
                it.onFailed("哎呦")  //在这里调用内部定义过的函数，如果不调用，TestNet中 YYLogUtils.w("可以随便写点什么逻辑") 不会执行

                it.onError()

                //在打印日志
                GdLog.w("test network onFailed")

            }, onSuccess = {
                //先打印日志
                GdLog.w("test network onSuccess")

                //再调用内部的函数处理逻辑
                this.onSuccess("我的成功数据字符串")    //上面是高阶函数的调用 - 这里是高阶扩展函数的调用，同样的效果，上面需要用it调用，这里直接this 调用

                this.doSth()

                ""

            }, onFinished = {
                GdLog.w("当前值是10,满足条件：$it")  //这里的it是那边的回调  内部回调过来的值
                true  //那边是带参数返回的，这里需要返回Booble给那边  返回给内部的值
            })

        }
    }

    val callBack: (String) -> Unit = {
        ToastUtils.showShort(it)
    }
}


class CallBack(private val callBack: (String) -> Unit) {
    fun call() {
        callBack.invoke("高阶函数代替接口回调")
    }
}
