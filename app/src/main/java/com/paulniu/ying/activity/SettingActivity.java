package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;

import com.paulniu.ying.R;
import com.paulniu.ying.base.BaseActivity;
import com.paulniu.ying.widget.popup.AddAffairOrTallyPop;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 16:51
 * Desc: 设置页面
 * Version:
 */
public class SettingActivity extends BaseActivity {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAffairOrTallyPop pop = new AddAffairOrTallyPop(SettingActivity.this, new AddAffairOrTallyPop.AddAffairOrTallyListener() {
                    @Override
                    public void gotoAffair() {
                        startActivity(AddAffairActivity.getIntent(SettingActivity.this));
                    }

                    @Override
                    public void gotoTally() {
                        startActivity(AddTallyActivity.getIntent(SettingActivity.this));
                    }
                });
//                PopupWindowCompat.showAsDropDown(pop,btn03,0,10, Gravity.START);
                pop.showAtLocation((View) findViewById(R.id.test).getParent(), Gravity.CENTER, 100, 100);
                pop.update();
            }
        });
    }
}
