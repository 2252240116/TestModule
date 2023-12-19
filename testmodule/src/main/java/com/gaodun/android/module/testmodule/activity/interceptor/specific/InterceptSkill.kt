package com.gaodun.android.module.testmodule.activity.interceptor.specific

import com.gaodun.android.module.testmodule.activity.interceptor.BaseInterceptImpl
import com.gaodun.android.module.testmodule.activity.interceptor.InterceptChain
import com.gaodun.android.module.testmodule.activity.interceptor.JobInterceptEntity
import com.gaodun.commonlib.commonutil.mainutil.ToastUtils
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction

class InterceptSkill(private val entity: JobInterceptEntity) : BaseInterceptImpl() {

    override fun intercept(chain: InterceptChain) {
        super.intercept(chain)

        if (entity.isNeedSkill) {
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
            .setTitle("你没有填写技能")
            .setMessage("你要去填写技能吗？")
            .addAction(0, "跳过", QMUIDialogAction.ACTION_PROP_POSITIVE) { dialog, _ ->
                dialog.dismiss()
//                chain.process(0)
                chain.process()
            }
            .addAction(0, "去填写", QMUIDialogAction.ACTION_PROP_NEGATIVE) { dialog, _ ->
                dialog.dismiss()
                ToastUtils.showShort("去填写")
            }.create().show()
    }

}
