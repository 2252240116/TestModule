package com.gaodun.android.module.testmodule.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.gaodun.android.module.testmodule.activity.CoroutinuesActivity
import com.gaodun.android.module.testmodule.activity.chain.*
import com.gaodun.android.module.testmodule.activity.interceptor.InterceptChain
import com.gaodun.android.module.testmodule.activity.interceptor.JobInterceptEntity
import com.gaodun.android.module.testmodule.activity.interceptor.specific.InterceptFillInfo
import com.gaodun.android.module.testmodule.activity.interceptor.specific.InterceptMemberApprove
import com.gaodun.android.module.testmodule.activity.interceptor.specific.InterceptNewMember
import com.gaodun.android.module.testmodule.activity.interceptor.specific.InterceptSkill
import com.gaodun.android.module.testmodule.app.databinding.ActivityMainBinding
import com.gaodun.android.module.testmodule.router.RouterConsts
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.xbcx.commonbiz.feature.router.RAction
import com.xbcx.commonsdk.feature.router.Router
import com.xbcx.commonsdk.view.BaseActivity
import com.xbcx.commonsdk.vm.BaseViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Function:
 * Author Name: Riven.zhang
 * Date: 2022/1/19
 * Copyright © 2006-2021 高顿网校, All Rights Reserved.
 */
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {
    override fun initVariableId(): Int=0

    override fun initContentView(savedInstanceState: Bundle?): Int =R.layout.activity_main

    override fun initTitleBar(topBarLayout: QMUITopBarLayout?) {
        super.initTitleBar(topBarLayout)
        topBarLayout?.setTitle("测试页面")
    }

    override fun registerView() {
        super.registerView()
        btn_login.setOnClickListener {
            Router.build(RAction.GD_LOGIN).navigation()
        }
        btn_enter.setOnClickListener {
            Router.build(RouterConsts.PATH_HOME_PAGE).navigation()
        }

        //协程的几种使用
        coroutinues.setOnClickListener {
            startActivity(Intent(this, CoroutinuesActivity::class.java))
        }

        //--------------------责任链模式又名拦截器设计模式
        // -------------------以下第一种属于简单点的责任链,处理下载,校验,解压流程业务场景,
        // -------------------第二种复杂一点链路,弹窗的排队展示,责任链设计没有固定模式,
        // --------------------核心思想都是从起点出发,然后沿着任务链传递到每个节点完成任务

        //责任链模式处理下载 校验 解压 流程
        chain.setOnClickListener {
            var offlinePackages = OfflinePackages().apply {
                localPath = "下载地址localPath 1"
                fileMd5 = "fileMd5值 1"
                name = "名称name 1"
                projectName = "projectName 1"
            }
            var offlinePackages1 = OfflinePackages().apply {
                localPath = "下载地址localPath 2"
                fileMd5 = "fileMd5值 2"
                name = "名称name 2"
                projectName = "projectName 2"
            }
            var list = mutableListOf<OfflinePackages>()
            list.add(offlinePackages)
            list.add(offlinePackages1)
            downloadMultiFiles(list)
        }

        //拦截器设计模式处理多个弹窗排队展示
        interceptor.setOnClickListener {
            navIntercept()
        }

        kotlin.setOnClickListener {
            Router.build(RouterConsts.PATH_KOTLIN_HIGH_PAGE).navigation()
        }
    }

    /**
     * 这里处理的是多个责任链(多个下载 校验 解压流程)
     */
    private fun downloadMultiFiles(list: List<OfflinePackages>) {
        list.forEach { packages ->
            // 构建责任链，并且开始执行处理流程
            ResHandler.ResHandlerChain(
                listOf(
                    ResDownloadHandler(),// 下载处理器
                    ResMd5CheckHandler(),// 校验处理器
                    ResUnzipHandler() // 解压处理器
                ), packages
            ).proceed()
        }
    }

    lateinit var newMemberIntercept: InterceptNewMember
    fun navIntercept() {
        Handler(mainLooper).postDelayed({
            val bean = JobInterceptEntity(false, false, false,
                    false, true, true, true,
                    true, true, true)
            createIntercept(bean)
        }, 1500)
    }

    private fun createIntercept(entity: JobInterceptEntity) {
        ///通过修改JobInterceptEntity实体 控制具体的业务流转
        newMemberIntercept = InterceptNewMember(entity)
        val chain = InterceptChain.create(4)
            .attach(this)
            .addInterceptor(newMemberIntercept)//新用户拦截器
            .addInterceptor(InterceptFillInfo(entity))//完善信息拦截器
            .addInterceptor(InterceptMemberApprove(entity))//用户状态拦截器
            .addInterceptor(InterceptSkill(entity))//是否填写技能拦截器
            .build()
        chain.process()
    }

//    override fun startObserve() {
//        LiveEventBus.get("newMember", Boolean::class.java).observe(this) {
//            //调用内部放行的方法
//            newMemberIntercept.resetNewMember()
//        }
//    }

}
