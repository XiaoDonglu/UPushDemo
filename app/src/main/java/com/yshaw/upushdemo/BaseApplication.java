package com.yshaw.upushdemo;

import android.app.Application;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * @author yshaw
 * @since 2020-06-08
 */
public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    private static final String APP_KEY = "***";
    private static final String UMENG_MESSAGE_SECRET = "***";

    @Override
    public void onCreate() {
        super.onCreate();

        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this, APP_KEY, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, UMENG_MESSAGE_SECRET);

        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        // 如果应用在前台，可以设置不显示通知栏消息
//        mPushAgent.setNotificaitonOnForeground(false);
        // 服务端控制声音
//        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);
        // 免打扰时间, startHour, startMinute, endHour, endMinute
//        mPushAgent.setNoDisturbMode(23, 0, 7, 0);
        // 默认情况下，同一台设备在1分钟内收到同一个应用的多条通知时，不会重复提醒，同时在通知栏里新的通知会替换掉旧的通知。可以通过如下方法来设置冷却时间
//        mPushAgent.setMuteDurationSeconds(30);

        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
    }
}
