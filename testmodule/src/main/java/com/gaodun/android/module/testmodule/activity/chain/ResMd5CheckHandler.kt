package com.gaodun.android.module.testmodule.activity.chain;

import com.gaodun.commonlib.log.GdLog
import java.io.File

/**
 * 校验处理器
 */
class ResMd5CheckHandler : ResHandler() {

    override fun handle(chain: ResHandlerChain) {
        checkMd5(chain)
    }

    /**
     * 校验ZIP MD5值
     */
    private fun checkMd5(chain: ResHandlerChain) {
        val offlinePackages = chain.pkg
        val path = offlinePackages.localPath
        val targetMD5 = offlinePackages.fileMd5
//        val result = validateMd5(path, targetMD5)// 对比MD5值，看是否篡改过
//        if (result) {
        if (true) {
            callNextHandler(chain)
        } else {
            endProcess(chain, false)
        }
    }

    /**
     * 获取单个文件的MD5值！
     */
    private fun getFileMD5(file: File): String? {
        //... 非核心代码，不展示了
        return "md5值"
    }

    /**
     * 校验下载下来的文件的MD5值
     */
    private fun validateMd5(oriFile: String, targetMd5: String): Boolean {
        val m = getFileMD5(File(oriFile))
        if (m == null) {
            return false
        }
        return targetMd5.contains(m)
    }

    override fun endProcess(chain: ResHandlerChain, resBool: Boolean) {
        GdLog.e("校验结束，结果为:$resBool ")
    }

    override fun onProcess(chain: ResHandlerChain, progress: Int) {
    }
}
