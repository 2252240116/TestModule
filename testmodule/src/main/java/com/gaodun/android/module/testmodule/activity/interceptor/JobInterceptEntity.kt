package com.gaodun.android.module.testmodule.activity.interceptor

data class JobInterceptEntity(
    var isNewMember: Boolean = false,
    var isFillInfo: Boolean = false,
    var isMemberApprove: Boolean = false,
    var isNOCVUpload: Boolean = false,
    var isNeedDepost: Boolean = false,
    var isNeedFace: Boolean = false,
    var isUnderCompany: Boolean = false,
    var isNeedWhatApp: Boolean = false,
    var isNeedBankInfo: Boolean = false,
    var isNeedSkill: Boolean = false
)