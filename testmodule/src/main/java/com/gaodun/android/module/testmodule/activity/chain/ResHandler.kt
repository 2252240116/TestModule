package com.gaodun.android.module.testmodule.activity.chain


/**
 *
 * 责任链核心类设计.
 * 无论是哪个处理函数，都会以 ResHandlerChain 作为其中一个参数。
 * 而 ResHandlerChain 就是所谓的 处理链条，用于容纳所有的处理流程对象，并且 提供一个函数，执行当前流程的核心handler函数。
 *
 */

abstract class ResHandler {

    //=========基础业务======================//
    abstract fun handle(chain: ResHandlerChain)//处理过程

    abstract fun endProcess(chain: ResHandlerChain, resBool: Boolean)//结束流程,如果需要把进度反馈给外界

    abstract fun onProcess(chain: ResHandlerChain, progress: Int)//处理流程中,如果需要把进度反馈给外界

    /**
     * 构建新的链条，并且开始执行连锁任务
     *
     */
    fun callNextHandler(chain: ResHandlerChain) {
        val newChain = ResHandlerChain(
            chain.handlerList,
            chain.pkg,
            chain.index + 1
        )
        newChain.proceed()
    }


    /**
     * 链条类
     * 处理器链条 使用的是内部类
     *
     */
    class ResHandlerChain(
        val handlerList: List<ResHandler>,
        val pkg: OfflinePackages,
        val index: Int = 0
    ) {
        fun proceed() {
            // 取得当前index的handler对象并且执行
            val currentHandler = handlerList[index]
            currentHandler.handle(this)
        }
    }
}
