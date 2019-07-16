package com.paulniu.ying.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.niupuyue.mylibrary.callbacks.ISimpleDialogButtonClickCallback;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.widgets.CircleImageView;
import com.niupuyue.mylibrary.widgets.SimpleDialog;
import com.paulniu.ying.R;
import com.paulniu.ying.base.BaseActivity;
import com.paulniu.ying.callback.ILocationCallback;
import com.paulniu.ying.model.AffairModel;
import com.paulniu.ying.model.FestivalModel;
import com.paulniu.ying.model.LocationInfoModel;
import com.paulniu.ying.util.BusinessUtility;
import com.paulniu.ying.util.LocationUtility;
import com.paulniu.ying.util.infomanager.LocationInfoManager;
import com.paulniu.ying.util.infomanager.UserInfoManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Coder: niupuyue
 * Date: 2019/7/10
 * Time: 9:51
 * Desc: 闪屏页
 * Version:
 */
public class SplashActivity extends BaseActivity implements ILocationCallback, View.OnClickListener {

    // 动态请求权限
    final RxPermissions rxPermissions = new RxPermissions(this);

    class SplashActivityHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public SplashActivityHandler(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (null == msg || mWeakReference == null || mWeakReference.get() == null || mWeakReference.get().isFinishing())
                return;
            switch (msg.what) {
                case HANDLER_SHOWAD:
                    // 展示闪屏广告

                    // 开始计时
                    startCount();
                    // 展示广告动画
                    showAdAnimation();
                    break;
                case HANDLER_START_COUNT:
                    BaseUtility.setText(tvSplashActivityJumpCount, String.format(getString(R.string.splash_activity_count), String.valueOf(msg.obj)));
                    break;
                case HANDLER_NEXT_VIEW:
                    // 关闭计时
                    stopCount();
                    Intent intent = MainActivity.getIntent(SplashActivity.this);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }

    // 设置广告动画延时显示时间
    private static final long delayMillisBless = 1500L;

    // 展示闪屏广告
    private static final int HANDLER_SHOWAD = 0x100;
    // 倒计时3秒开始
    private static final int HANDLER_START_COUNT = 0x101;
    // 渠道下一个页面
    private static final int HANDLER_NEXT_VIEW = 0x102;

    // 设置发送handler事件的默认延迟时间
    private static final long SEND_HANDLER_MESSAGE_DELAY = 200L;
    // 设置最后一秒时的时间
    private static final long COUNT_LAST_SECOND = 800L;
    // 设置普通一秒的时间
    private static final long COUNT_NORMAL_SECOND = 1000L;

    // 倒计时子线程 TODO 后面需要修改 这里可能会造成内存泄漏
    private Thread timeCountThread = null;


    // 是否展示欢迎语或者提醒事务
    private boolean isShowedFestivals = true;
    // handler
    private SplashActivityHandler handler = new SplashActivityHandler(SplashActivity.this);

    private ImageView ivSplashActivityAd;
    private RelativeLayout rlSplashBlessContainer;
    private CircleImageView ivSplashActivityAvator;
    private TextView tvSplashActivityBless;
    private LinearLayout llSplashActivityJump;
    private TextView tvSplashActivityJumpCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViewByFindViewById();
        initListener();
        initLocation();

    }

    private void initViewByFindViewById() {
        ivSplashActivityAd = findViewById(R.id.ivSplashActivityAd);
        rlSplashBlessContainer = findViewById(R.id.rlSplashBlessContainer);
        ivSplashActivityAvator = findViewById(R.id.ivSplashActivityAvator);
        tvSplashActivityBless = findViewById(R.id.tvSplashActivityBless);
        llSplashActivityJump = findViewById(R.id.llSplashActivityJump);
        tvSplashActivityJumpCount = findViewById(R.id.tvSplashActivityJumpCount);
    }

    private void initListener() {
        // 开启定位
        LocationUtility.getInstance().setCallback(this);
        // 添加事件监听
        ListenerUtility.setOnClickListener(this, llSplashActivityJump);
    }

    private void initLocation() {
        // 获取定位信息之前判断权限是否获取成功
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            // 获取权限
                            LocationUtility.getInstance().startLocation();
                            toNextView();
                        } else {
                            // 弹窗提示信息
                            SimpleDialog.showSimpleDialog(SplashActivity.this, getString(R.string.permission_ask_fail_content), new ISimpleDialogButtonClickCallback() {
                                @Override
                                public void onLeftButtonClick() {
                                    // 点击取消按钮，进入应用系统设置页面 TODO
                                    Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                                    i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                                    startActivity(i);
                                }

                                @Override
                                public void onRightButtonClick() {
                                    // 重新请求权限
                                    initLocation();
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        }
                    }
                });
    }

    private void toNextView() {
        if (BusinessUtility.isNeedShowWelcomePage()) {
            // 进入欢迎页面
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        } else if (isShowedFestivals) {
            showFestivals();
        } else if (isShowAd()) {
            showAd();
        }
    }

    /**
     * 展示祝福语
     */
    private void showFestivals() {
        // 设置是否展示祝福语为false
        isShowedFestivals = false;
        // 设置广告业隐藏
        BaseUtility.resetVisibility(ivSplashActivityAd, false);
        // 获取当前节日信息
        FestivalModel festivalModel = getFestivalInfo();
        if (festivalModel != null) {
            // 展示祝福语
            BaseUtility.setText(tvSplashActivityBless, festivalModel.getFestivalName() + ":" + festivalModel.getFestivalNote());
        } else {
            // 没有节日，展示日常事宜
            AffairModel affairModel = getAffairInfo();
            if (affairModel != null) {
                // 显示最紧急需要做的事情
                BaseUtility.setText(tvSplashActivityBless, String.format(getString(R.string.splash_activity_bless_affair), affairModel.getAffairNote()));
            }
        }

        // 设置动画 TODO
        // 头像逐渐放大
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.3f, 1, 0.3f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(800);
        scaleAnimation.setRepeatCount(0);
        rlSplashBlessContainer.startAnimation(scaleAnimation);
        // 底下的字渐现
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // 展示渐隐动画
                AlphaAnimation llblessAlphaAnimation = new AlphaAnimation(1f, 0f);
                llblessAlphaAnimation.setDuration(1500);
                llblessAlphaAnimation.setFillAfter(true);
                tvSplashActivityBless.startAnimation(llblessAlphaAnimation);

                isShowedFestivals = false;
                toNextView();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivSplashActivityAvator.startAnimation(alphaAnimation);
    }

    /**
     * 获取节日信息
     *
     * @return
     */
    private FestivalModel getFestivalInfo() {
        FestivalModel model = null;
        List<FestivalModel> sysFestival = BusinessUtility.getSysFestivals();
        // 开始计算节日
        try {
            // 声明日历对象
            Calendar calendar = Calendar.getInstance();
            // 将当前日期设置到日历对象中
            calendar.setTimeInMillis(System.currentTimeMillis());
            if (!BaseUtility.isEmpty(UserInfoManager.getInstance().getFestivals())) {
                for (FestivalModel festivalModel : UserInfoManager.getInstance().getFestivals()) {
                    // 如果当前有记录的节日信息
                    Calendar festivalCal = Calendar.getInstance();
                    festivalCal.setTimeInMillis(Long.valueOf(festivalModel.getFestivalDate()));
                    // 如果当前的日期和节日的日期相等，则是当前需要显示的节日
                    if (festivalCal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && festivalCal.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                        model = new FestivalModel();
                        model.setFestivalDate(System.currentTimeMillis());
                        model.setFestivalName(festivalModel.getFestivalName());
                        model.setFestivalNote(festivalModel.getFestivalNote());
                        break;
                    }
                }
            }
            // 如果有节日和系统节假日有冲突，则优先显示自定义节日
            if (model == null) {
                // 其他节日
                if (!BaseUtility.isEmpty(sysFestival)) {
                    Calendar sysCalender = Calendar.getInstance();
                    for (FestivalModel sysModel : sysFestival) {
                        sysCalender.setTimeInMillis(sysModel.getFestivalDate());
                        if (calendar.get(Calendar.MONTH) == sysCalender.get(Calendar.MONTH) && calendar.get(Calendar.DAY_OF_MONTH) == sysCalender.get(Calendar.DAY_OF_MONTH)) {
                            model = sysModel;
                        }
                    }
                } else {
                    model = null;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return model;
    }

    /**
     * 获取日常事宜信息
     *
     * @return
     */
    private AffairModel getAffairInfo() {
        AffairModel affairModel = null;
        try {
            affairModel = BusinessUtility.getImportAffair();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return affairModel;
    }

    /**
     * 是否展示广告
     *
     * @return
     */
    private boolean isShowAd() {
        return true;
    }

    /**
     * 展示广告
     */
    private void showAd() {
        // 通过Handler执行异步操作
        Message message = Message.obtain();
        message.obj = null;
        message.what = HANDLER_SHOWAD;
        if (null != handler) {
            handler.sendMessageDelayed(message, SEND_HANDLER_MESSAGE_DELAY);
        }
    }

    /**
     * 展示广告动画
     */
    private void showAdAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(delayMillisBless);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                BaseUtility.resetVisibility(tvSplashActivityBless, false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivSplashActivityAd.startAnimation(alphaAnimation);
    }

    /**
     * 开启倒计时
     */
    private void startCount() {
        // 设置倒计时view可见
        BaseUtility.resetVisibility(tvSplashActivityJumpCount, true);
        // 设置初始计时时间
        BaseUtility.setText(tvSplashActivityJumpCount, String.format(getString(R.string.splash_activity_count), String.valueOf(3)));
        timeCountThread = new Thread() {
            @Override
            public void run() {
                try {
                    Message msg;
                    for (int i = 3; i >= 0; i--) {
                        if (i == 0) {
                            // 倒计时结束，去到下一个页面
                            msg = Message.obtain();
                            msg.what = HANDLER_NEXT_VIEW;
                            if (handler != null) {
                                handler.sendMessage(msg);
                            }
                        } else {
                            // 倒计时未结束
                            msg = Message.obtain();
                            msg.what = HANDLER_START_COUNT;
                            msg.obj = i;
                            if (handler != null) {
                                handler.sendMessage(msg);
                            }
                            // 如果是最后一秒，则加快进入下一个页面
                            if (i == 1) {
                                Thread.sleep(COUNT_LAST_SECOND);
                            } else {
                                Thread.sleep(COUNT_NORMAL_SECOND);
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        // 启动线程
        timeCountThread.start();
    }

    /**
     * 停止计时
     */
    private void stopCount() {
        if (timeCountThread != null) {
            timeCountThread.interrupt();
        }
    }

    @Override
    public void onSuccess(AMapLocation aMapLocation) {
        // 定位成功
        LocationInfoModel model = LocationInfoManager.newInstance().getLocationInfo();
        model.setAdCode(aMapLocation.getAdCode());
        model.setAddress(aMapLocation.getAddress());
        model.setAltitude(aMapLocation.getAltitude());
        model.setCityName(aMapLocation.getCity());
        model.setCountry(aMapLocation.getCountry());
        model.setLatitude(aMapLocation.getLatitude());
        model.setLongitude(aMapLocation.getLongitude());
        model.setProvince(aMapLocation.getProvince());
        model.setStreet(aMapLocation.getStreet());
        model.setStreetNum(aMapLocation.getStreetNum());
        LocationInfoManager.newInstance().cacheLocationInfo(model);
    }

    @Override
    public void onFail(AMapLocation aMapLocation) {
        // 定位失败
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llSplashActivityJump:
                if (handler != null) {
                    handler.sendEmptyMessage(HANDLER_NEXT_VIEW);
                }
                break;
        }
    }

}
