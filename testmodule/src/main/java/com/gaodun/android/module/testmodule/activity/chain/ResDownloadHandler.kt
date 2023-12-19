package com.gaodun.android.module.testmodule.activity.chain

import com.gaodun.commonlib.log.GdLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Function:资源下载处理器
 * Author Name: Riven.zhang
 * Date: 2022/6/16
 * Copyright © 2006-2021 高顿网校, All Rights Reserved.
 */
class ResDownloadHandler : ResHandler() {

    override fun handle(chain: ResHandlerChain) {
        var pkg = chain.pkg // 离线包对象
        //模拟下载
        GlobalScope.launch {
            try {
                onProcess(chain, 1)
                delay(2000)
//                pkg.localPath = "下载地址完成地址url:xxxx"
                callNextHandler(chain)
            } catch (e: Exception) {
                endProcess(chain, false)
            }
        }
    }

    override fun endProcess(chain: ResHandlerChain, resBool: Boolean) {
        GdLog.e("{${chain.pkg.localPath}}下载结束，结果为:$resBool ")
    }

    override fun onProcess(chain: ResHandlerChain, progress: Int) {
        GdLog.e("{${chain.pkg.localPath}}当前的下载进度是:$progress%")
    }
}
