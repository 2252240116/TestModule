package com.gaodun.android.module.testmodule.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.gaodun.android.module.testmodule.R
import com.gaodun.android.module.testmodule.databinding.TestmoduleActivityHomeBinding
import com.gaodun.android.module.testmodule.router.RouterConsts.PATH_HOME_PAGE
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.sankuai.waimai.router.annotation.RouterUri
import com.xbcx.commonsdk.view.BaseActivity
import com.xbcx.commonsdk.vm.BaseViewModel
import kotlinx.coroutines.*

/**
 * 协程
 */
class CoroutinuesActivity : BaseActivity<TestmoduleActivityHomeBinding, BaseViewModel>() {
    lateinit var job: Job
    override fun registerView() {
        super.registerView()
        /**
         * 协程操作符:runBlocking 运行阻塞 会在主线程
         */
        println(" 是否在主线程:" + (Thread.currentThread() == Looper.getMainLooper().thread))
        runBlocking {
            println(" 是否在主线程:" + (Thread.currentThread() == Looper.getMainLooper().thread))
            delay(2000)
            println(" runBlocking的使用")
        }
        println(" 测试结束")

        /**
         * 协程操作符:launch 相当于启动一个Thread
         */
        println(" 是否在主线程1:" + (Thread.currentThread() == Looper.getMainLooper().thread))
        GlobalScope.launch {
            println(" 是否在主线程2:" + (Thread.currentThread() == Looper.getMainLooper().thread))
            delay(2000)
            for (i in 1..100000){
                println("打印$i")
            }
            println("launch的使用")
        }
        println(" 测试结束")

        /**
         * 协程操作符:async 与launch相同 有返回值
         */
        println(" 是否在主线程1:" + (Thread.currentThread() == Looper.getMainLooper().thread))
        var async = GlobalScope.async {
            println(" 是否在主线程2:" + (Thread.currentThread() == Looper.getMainLooper().thread))
            delay(2000)
            println(" launch的使用")
            return@async "async 返回值"
        }
        println(" 测试结束")
        GlobalScope.launch {
            println(" 测试结束:"+async.await())
        }

        /**
         * 协程作用域,协程调度器以及启动模式 以及协程设置超时
         */
        println(" 是否在主线程1:" + (Thread.currentThread() == Looper.getMainLooper().thread))
        job = viewModel.viewModelScope.launch(Dispatchers.Main, CoroutineStart.LAZY) {
            try {
                println(" 是否在主线程2:" + (Thread.currentThread() == Looper.getMainLooper().thread))
                //协程调度到io线程
                //协程调度既可以发生再launch和async也可以发生在协程内部
                withContext(Dispatchers.IO) {
                    println(" 是否在主线程3:" + (Thread.currentThread() == Looper.getMainLooper().thread))
                    delay(2000)
                    println("线程调度2s以后:")
                }
                println(" 是否在主线程4:" + (Thread.currentThread() == Looper.getMainLooper().thread))
                //withTimeoutOrNull 指定运行时间限制 超过返回null
                var withTimeoutOrNull = withTimeoutOrNull(200) {
                    repeat(5) {
                        println("输出" + it)
                        delay(100)
                    }
                    return@withTimeoutOrNull "withTiemout结果"
                }
                println("测试输出结果" + withTimeoutOrNull)
            }catch (e:CancellationException){
                println("协程取消")
            }
        }
        job.start()
        println("测试结束")
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

    override fun initVariableId(): Int {
        return 0
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.testmodule_activity_home
    }

}
