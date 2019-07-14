package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.orhanobut.logger.Logger;
import com.paulniu.ying.R;
import com.paulniu.ying.base.BaseActivity;
import com.paulniu.ying.callback.ISqliteDataCallback;
import com.paulniu.ying.model.AffairModel;
import com.paulniu.ying.util.impl.SQLiteDataBaseImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 22:01
 * Desc: 添加事务
 * Version:
 */
public class AddAffairActivity extends BaseActivity implements View.OnClickListener, ISqliteDataCallback {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AddAffairActivity.class);

        return intent;
    }

    private Toolbar toolbar;
    private TextView tvAddAffariActivitySubmit;
    private EditText etAddAffairActivityNote;
    private TextView tvAddAffairActivityTime;
    private TextView tvAddAffairActivityType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaffair);

        initFindViewById();
        initListener();
        initDataAfterListener();
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
            String time = BaseUtility.getText(tvAddAffairActivityTime);
            String type = BaseUtility.getText(tvAddAffairActivityType);
            AffairModel model = new AffairModel();
            model.setAffairNote(note);
            model.setAffairTime(Long.valueOf(time));
            model.setAffairType(Integer.valueOf(type));
            if (BaseUtility.isEmpty(note)) {
                return;
            }
            SQLiteDataBaseImpl.getInstance().add(model, this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSuccess() {
        // 保存数据成功
        CustomToastUtility.makeTextSucess(getString(R.string.app_save_data_success));
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
    private void DatePicker() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddAffairActivityType:
                // 选择事务类型

                break;
            case R.id.tvAddAffairActivityTime:
                // 选择事务需要的时间
                DatePicker();
                break;
            case R.id.tvAddAffariActivitySubmit:
                // 保存数据到数据库
                saveDataToDataBase();
                break;
        }
    }

}
