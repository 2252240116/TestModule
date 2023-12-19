package com.gaodun.android.module.testmodule.activity

import android.os.Bundle
import com.gaodun.android.module.testmodule.R
import com.gaodun.android.module.testmodule.databinding.TestmoduleActivityHomeBinding
import com.gaodun.android.module.testmodule.router.RouterConsts.PATH_HOME_PAGE
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.sankuai.waimai.router.annotation.RouterUri
import com.xbcx.commonsdk.view.BaseActivity
import com.xbcx.commonsdk.vm.BaseViewModel

/**
 * Function:
 * Author Name: Riven.zhang
 * Date: 2022/1/19
 * Copyright © 2006-2021 高顿网校, All Rights Reserved.
 */
@RouterUri(path = [PATH_HOME_PAGE])
class HomeActivity : BaseActivity<TestmoduleActivityHomeBinding, BaseViewModel>() {

    override fun initVariableId(): Int {
        return 0
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.testmodule_activity_home
    }

    override fun initTitleBar(topBarLayout: QMUITopBarLayout?) {
        super.initTitleBar(topBarLayout)
        topBarLayout?.setTitle(R.string.testmodule_title_home_page)
    }
}
