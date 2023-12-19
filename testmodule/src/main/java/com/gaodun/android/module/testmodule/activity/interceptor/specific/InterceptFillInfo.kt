package com.gaodun.android.module.testmodule.activity.interceptor.specific

import com.gaodun.android.module.testmodule.activity.interceptor.BaseInterceptImpl
import com.gaodun.android.module.testmodule.activity.interceptor.InterceptChain
import com.gaodun.android.module.testmodule.activity.interceptor.JobInterceptEntity
import com.gaodun.commonlib.commonutil.mainutil.ToastUtils
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction

class InterceptFillInfo(private val entity: JobInterceptEntity) : BaseInterceptImpl() {

    override fun intercept(chain: InterceptChain) {
        super.intercept(chain)

        if (!entity.isFillInfo) {
            //拦截
            //跳转新页面
            showDialogTips(chain)
        } else {
            //放行- 转交给下一个拦截器
            chain.process()
        }
    }

    private fun showDialogTips(chain: InterceptChain) {
        QMUIDialog.MessageDialogBuilder(chain.activity)
            .setTitle("完善信息")
            .setMessage("你没有完善信息，你要去完善信息")
            .addAction(0, "跳过", QMUIDialogAction.ACTION_PROP_POSITIVE) { dialog, _ ->
                dialog.dismiss()
                //设置用户状态已批准 通过修改JobInterceptEntity实体 控制具体的业务流转
                entity.isMemberApprove = true
                chain.activity
                chain.process()
            }
            .addAction(0, "去完善", QMUIDialogAction.ACTION_PROP_NEGATIVE) { dialog, _ ->
                ToastUtils.showShort("去完善")
            }.create().show()
    }

}
