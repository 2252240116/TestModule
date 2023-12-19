package com.gaodun.android.module.testmodule.app;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.gaodun.commonlib.commonutil.mainutil.JsonUtilsV2;
import com.gaodun.commonlib.log.GdLog;
import com.xbcx.commonbiz.contact.UserCenter;
import com.xbcx.commonbiz.feature.auth.contract.AuthService;
import com.xbcx.commonsdk.feature.router.Router;
import com.xbcx.commonsdk.feature.web.bean.JsBridgeAuthorizeBean;
import com.xbcx.commonsdk.feature.web.bean.JsBridgeUserInfoBean;
import com.xbcx.commonsdk.feature.web.contract.JsBridgeError;
import com.xbcx.commonsdk.feature.web.presenter.JBBasePresenter;
import com.xbcx.commonsdk.feature.web.rainbow.RainBowEventFilter;
import com.xbcx.commonsdk.feature.web.rainbow.RainBowHandler;
import com.xbcx.commonsdk.feature.web.rainbow.RainBowJSAPIHandler;
import com.xbcx.commonsdk.feature.web.rainbow.RainBowSendCallBack;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Function:
 * Author Name: Riven.zhang
 * Date: 2022/1/19
 * Copyright © 2006-2021 高顿网校, All Rights Reserved.
 */
public class ToolsRainBowPlugin extends RainBowJSAPIHandler {

    private static final String TAG = ToolsRainBowPlugin.class.getSimpleName();
    private static final String REGISTER_NAME_AUTHORIZE = "authorize";
    private static final String REGISTER_NAME_GET_USER_INFO = "getUserInfo";
    private Disposable authDisposable;

    @Override
    public void onPrepare(RainBowEventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(REGISTER_NAME_AUTHORIZE);
        filter.addAction(REGISTER_NAME_GET_USER_INFO);
    }

    @Override
    public void handleEvent(RainBowHandler rainBowHandler, Context context) {
        try {
            switch (rainBowHandler.getHandlerName()) {
                case REGISTER_NAME_AUTHORIZE:
                    dealAuthorizeEvent(rainBowHandler.getCallBack());
                    break;
                case REGISTER_NAME_GET_USER_INFO:
                    dealGetUserInfoEvent(rainBowHandler.getCallBack());
                    break;
            }
        } catch (Exception e) {
            GdLog.t(TAG).e(e, JBBasePresenter.ERROR_STR, rainBowHandler.getHandlerName());
        }
    }

    @Override
    public void onRelease() {
        super.onRelease();
        if (authDisposable != null && !authDisposable.isDisposed()) {
            authDisposable.dispose();
        }
    }

    private void dealAuthorizeEvent(final RainBowSendCallBack callBackFunc) throws RuntimeException {
        AuthService authService = Router.getService(AuthService.class, AuthService.SERVICE_AUTH);
        if (authService == null) {
            callBackFunc.fail(JsBridgeError.ERROR_TOKEN.getCode(), JsBridgeError.ERROR_TOKEN.getMsg());
            return;
        }
        authDisposable = authService.getBasicTokenAsync()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String accessToken) throws Exception {
                        JsBridgeAuthorizeBean bridgeAuthorizeBean = new JsBridgeAuthorizeBean();
                        bridgeAuthorizeBean.setAccessToken(accessToken);
                        String jsonStr = JsonUtilsV2.toJSONString(bridgeAuthorizeBean);
                        callBackFunc.success(JSONObject.parseObject(jsonStr));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackFunc.fail(JsBridgeError.ERROR_TOKEN.getCode(), JsBridgeError.ERROR_TOKEN.getMsg());
                    }
                });
    }

    private void dealGetUserInfoEvent(RainBowSendCallBack callBackFunc) throws RuntimeException {
        JsBridgeUserInfoBean bean = new JsBridgeUserInfoBean();
        String studentID = UserCenter.getInstance().getStudentId();
        if (TextUtils.isEmpty(studentID)) {
            callBackFunc.fail(JsBridgeError.ERROR_USER_INFO.getCode(), JsBridgeError.ERROR_USER_INFO.getMsg());
            return;
        }
        bean.setUid(UserCenter.getInstance().getStudentId());
        bean.setStudentID(UserCenter.getInstance().getStudentId());
        bean.setSessionID(UserCenter.getInstance().getSessionId());
        bean.setNickName(UserCenter.getInstance().getNickName());
        bean.setUserImage(UserCenter.getInstance().getAvatar());
        bean.setUserPhone(UserCenter.getInstance().getPhone());
        String jsonStr = JsonUtilsV2.toJSONString(bean);
        callBackFunc.success(JSONObject.parseObject(jsonStr));
    }
}
