package com.gaodun.android.module.testmodule.activity.chain;

import com.gaodun.commonlib.log.GdLog
import java.io.File
import java.io.IOException;

import kotlin.jvm.Throws;

/**
 * 解压控制器
 */
class ResUnzipHandler : ResHandler() {

    override fun handle(chain: ResHandlerChain) {
        unzip(chain)
    }

    private fun unzip(chain: ResHandlerChain) {
        val pkg = chain.pkg
        val path = pkg.localPath
        val f = File(path)
        GdLog.e("开始解压:${f.name}")

        val folder = "${pkg.projectName}"
        val success = doUnzip(path, folder, true)//解压过程

        GdLog.e("p-> ${pkg.name}解压完毕，结果$success")
        endProcess(chain, success)//结束解压
    }

    @Throws(IOException::class)
    private fun doUnzip(
        archive: String,
        decompressDir: String,
        isDeleteZip: Boolean
    ): Boolean {
        // 非核心代码，不展示了
        GdLog.e("解压过程")
        return true
    }

    override fun endProcess(chain: ResHandlerChain, resBool: Boolean) {
        GdLog.e("解压结束，结果为:$resBool ")
    }

    override fun onProcess(chain: ResHandlerChain, progress: Int) {

    }
}
