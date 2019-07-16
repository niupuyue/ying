package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.utils.TimeUtility;
import com.niupuyue.mylibrary.widgets.datepicker.CustomDatePicker;
import com.paulniu.ying.R;
import com.paulniu.ying.base.BaseActivity;
import com.paulniu.ying.callback.IRealmQueryAffairCallback;
import com.paulniu.ying.database.SQLiteDataBaseHelper;
import com.paulniu.ying.model.AffairModel;
import com.paulniu.ying.widget.popup.AddAffairTypePop;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 22:01
 * Desc: 添加事务
 * Version:
 */
public class AddAffairActivity extends BaseActivity implements View.OnClickListener, IRealmQueryAffairCallback {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AddAffairActivity.class);

        return intent;
    }

    private Toolbar toolbar;
    private TextView tvAddAffariActivitySubmit;
    private EditText etAddAffairActivityNote;
    private TextView tvAddAffairActivityTime;
    private TextView tvAddAffairActivityType;
    private CustomDatePicker mTimerPicker;

    private long time;
    private int type = AffairModel.AFFAIR_TYPE_MIDDLE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaffair);

        initFindViewById();
        initListener();
        initDataAfterListener();
        initDatePicker();
    }

    private void initFindViewById() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.menu_addaffair_activity);
        setSupportActionBar(toolbar);

        tvAddAffariActivitySubmit = findViewById(R.id.tvAddAffariActivitySubmit);
        etAddAffairActivityNote = findViewById(R.id.etAddAffairActivityNote);
        tvAddAffairActivityTime = findViewById(R.id.tvAddAffairActivityTime);
        tvAddAffairActivityType = findViewById(R.id.tvAddAffairActivityType);
    }

    private void initListener() {
        ListenerUtility.setOnClickListener(this, tvAddAffariActivitySubmit, tvAddAffairActivityTime, tvAddAffairActivityType);
    }

    private void initDataAfterListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addaffair_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cancel) {
            // 关闭页面
            onBackPressed();
        }
        return true;
    }

    private void saveDataToDataBase() {
        try {
            String note = BaseUtility.getText(etAddAffairActivityNote);
            AffairModel model = new AffairModel();
            model.setAffairNote(note);
            model.setAffairTime(time);
            model.setAffairType(type);
            if (BaseUtility.isEmpty(note)) {
                return;
            }
            SQLiteDataBaseHelper.getInstance().addAsyncWithKey(model,this);
        } catch (Exception ex) {
            ex.printStackTrace();
            CustomToastUtility.makeTextError(getString(R.string.app_add_affair_error));
        }
    }

    @Override
    public void onSuccess() {
        // 保存数据成功
        CustomToastUtility.makeTextSucess(getString(R.string.app_save_data_success));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
    }

    @Override
    public void onError() {
        // 保存数据失败
        CustomToastUtility.makeTextError(getString(R.string.app_save_data_error));
    }

    @Override
    public void getResult(boolean isRefresh, List<AffairModel> results) {

    }

    @Override
    public void onBackPressed() {
        // 如果数据保存失败，或者没有保存数据，需要将数据记录到本地数据库中
        super.onBackPressed();
    }

    /**
     * 显示时间
     */
    private void initDatePicker() {
        String beginTime = "2018-10-17 18:00";
        String endTime = TimeUtility.convertToString(System.currentTimeMillis(), TimeUtility.FORMAT_YYYY_MM_DD_HH_MM);

        Log.e("NPL", endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(AddAffairActivity.this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                time = timestamp;
                BaseUtility.setText(tvAddAffairActivityTime, TimeUtility.convertToString(time, TimeUtility.FORMAT_YYYY2MM2DD_HH_MM));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);
    }

    /**
     * 显示选择类型弹窗
     */
    private void showSelectTypePop() {
        AddAffairTypePop pop = new AddAffairTypePop(AddAffairActivity.this, new AddAffairTypePop.AddAffairTypeListener() {
            @Override
            public void affairType(int type) {
                AddAffairActivity.this.type = type;
                switch (type) {
                    case AffairModel.AFFAIR_TYPE_MAX:
                        BaseUtility.setText(tvAddAffairActivityType, getString(R.string.pop_add_affair_type_high));
                        break;
                    case AffairModel.AFFAIR_TYPE_MIDDLE:
                        BaseUtility.setText(tvAddAffairActivityType, getString(R.string.pop_add_affair_type_middle));
                        break;
                    case AffairModel.AFFAIR_TYPE_LOW:
                        BaseUtility.setText(tvAddAffairActivityType, getString(R.string.pop_add_affair_type_low));
                        break;
                }
            }
        });
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddAffairActivityType:
                // 选择事务类型
                showSelectTypePop();
                break;
            case R.id.tvAddAffairActivityTime:
                // 选择事务需要的时间
                mTimerPicker.show(System.currentTimeMillis());
                break;
            case R.id.tvAddAffariActivitySubmit:
                // 保存数据到数据库
                saveDataToDataBase();
                break;
        }
    }

}
