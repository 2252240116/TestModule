package com.gaodun.android.module.testmodule.activity.interceptor.specific

import com.gaodun.android.module.testmodule.activity.interceptor.BaseInterceptImpl
import com.gaodun.android.module.testmodule.activity.interceptor.InterceptChain
import com.gaodun.android.module.testmodule.activity.interceptor.JobInterceptEntity
import com.gaodun.commonlib.commonutil.mainutil.ToastUtils
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction

class InterceptMemberApprove(private val entity: JobInterceptEntity) : BaseInterceptImpl() {

    override fun intercept(chain: InterceptChain) {
        super.intercept(chain)

        if (!entity.isMemberApprove) {
            //拦截
            showDialogTips(chain)
        } else {
            //放行- 转交给下一个拦截器
            chain.process()
        }
    }

    private fun showDialogTips(chain: InterceptChain) {
        QMUIDialog.MessageDialogBuilder(chain.activity)
            .setTitle("状态不对")
            .setMessage("你用户状态不对，联系管理员吗？")
            .addAction(0, "跳过", QMUIDialogAction.ACTION_PROP_POSITIVE) { dialog, _ ->
                dialog.dismiss()
                chain.process()
            }
            .addAction(0, "联系", QMUIDialogAction.ACTION_PROP_NEGATIVE) { dialog, _ ->
                dialog.dismiss()
                ToastUtils.showShort("去拨打电话，当你状态对了才能继续")
            }.create().show()
    }

}
