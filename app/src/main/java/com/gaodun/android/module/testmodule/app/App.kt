package com.gaodun.android.module.testmodule.app

import android.app.Activity
import com.xbcx.commonbiz.BaseApplication
import com.xbcx.commonbiz.contact.UserCenter
import com.xbcx.commonbiz.feature.log.LogServiceDelegate
import com.xbcx.commonsdk.feature.router.Router
import com.xbcx.commonsdk.feature.web.rainbow.RainBowService

/**
 * Function:
 * Author Name: Riven.zhang
 * Date: 2022/1/19
 * Copyright © 2006-2021 高顿网校, All Rights Reserved.
 */
class App : BaseApplication() {

    override fun providerReceiveActivity(): Class<out Activity> {
        return MainActivity::class.java
    }

    override fun initNotNeedPermissionConfig() {
        super.initNotNeedPermissionConfig()
        UserCenter.getInstance().load()
    }

    override fun initRouterInterceptPaths(): Array<String> {
        return arrayOf()
    }

    override fun initNeedPermissionConfig() {
        super.initNeedPermissionConfig()
        LogServiceDelegate.obtainAliLogSecretInfo()

        Router.getService(RainBowService::class.java, RainBowService.GD_WEB_SERVICE)?.apply {
            addJSApi(ToolsRainBowPlugin())
        }
    }
}