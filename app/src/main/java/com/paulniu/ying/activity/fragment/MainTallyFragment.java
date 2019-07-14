package com.paulniu.ying.activity.fragment;

import android.support.v4.widget.PopupWindowCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.orhanobut.logger.Logger;
import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;
import com.paulniu.ying.activity.AddAffairActivity;
import com.paulniu.ying.activity.AddTallyActivity;
import com.paulniu.ying.activity.MainActivity;
import com.paulniu.ying.model.AffairModel;
import com.paulniu.ying.model.Dog;
import com.paulniu.ying.widget.AddAffairOrTallyPop;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 17:09
 * Desc: 记账Fragment
 * Version:
 */
public class MainTallyFragment extends BaseFragment implements View.OnClickListener {

    public static MainTallyFragment getInstance() {
        MainTallyFragment fragment = new MainTallyFragment();

        return fragment;
    }

    private EditText et01;
    private Button btn01, btn02, btn03, btn04;

    private Realm mRealm;
    private RealmAsyncTask task;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tally_tab;
    }

    @Override
    public void initDateExtra() {

    }

    @Override
    public void initViewFindViewById(View view) {
        et01 = view.findViewById(R.id.et01);
        btn01 = view.findViewById(R.id.btn01);
        btn02 = view.findViewById(R.id.btn02);
        btn03 = view.findViewById(R.id.btn03);
        btn04 = view.findViewById(R.id.btn04);
    }

    @Override
    public void initListener() {
        ListenerUtility.setOnClickListener(this, btn01, btn02, btn03, btn04);
    }

    @Override
    public void initData() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn01:
                // 添加一个事务到数据库
                if (mRealm != null) {
//                    mRealm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            AffairModel model = new AffairModel();
//                            model.setAffairType(0);
//                            model.setAffairTime(System.currentTimeMillis());
//                            model.setAffairNote(BaseUtility.getText(et01));
//                        }
//                    });
                    // 当存在主键的时候建议使用下面的方式插入
//                    final AffairModel model = new AffairModel();
//                    model.setAffairNote(BaseUtility.getText(et01));
//                    model.setAffairTime(System.currentTimeMillis());
//                    model.setAffairType(0);
//                    mRealm.copyToRealmOrUpdate(model);
                    /**
                     * 如果我们是通过extends RealmObject的方式，需要这样做
                     * mRealm.executeTransaction(new Realm.Transaction() {
                     *             @Override
                     *             public void execute(Realm realm) {
                     *                 User2 user = realm.createObject(User2.class);
                     *                 user.name = "Micheal";
                     *                 user.age = 30;
                     *             }
                     *         });
                     *  如果在Object中还存在着其他的Object类，例如：
                     *  public class Dog extends RealmObject {
                     *       private String name;
                     *       private int age;
                     *       //getters and setters
                     *  }
                     *  public class User extends RealmObject {
                     *       private String name;
                     *       private int age;
                     *       private Dog dog;
                     *       //getters and setters
                     *  }
                     *  这时候我们需要使用RealmList的方式
                     */
                    // 这里我需要做异步操作和执行之后的回调，需要使用下面的方式
                    final AffairModel model = new AffairModel();
                    model.setAffairType(0);
                    model.setAffairTime(System.currentTimeMillis());
                    model.setAffairNote(BaseUtility.getText(et01));
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(model);
                        }
                    });
                   task =  mRealm.executeTransactionAsync(new Realm.Transaction() {
                       @Override
                       public void execute(Realm realm) {
                           realm.copyToRealm(model);
                       }
                   }, new Realm.Transaction.OnSuccess() {
                       @Override
                       public void onSuccess() {

                       }
                   }, new Realm.Transaction.OnError() {
                       @Override
                       public void onError(Throwable error) {

                       }
                   });
                    /**
                     * realm可以插入一个json对象
                     * // 一个city model
                     * public class City extends RealmObject {
                     *     private String city;
                     *     private int id;
                     *     // getters and setters left out ...
                     * }
                     * // 使用Json字符串插入数据
                     * realm.executeTransaction(new Realm.Transaction() {
                     *     @Override
                     *     public void execute(Realm realm) {
                     *         realm.createObjectFromJson(City.class, "{ city: \"Copenhagen\", id: 1 }");
                     *     }
                     * });
                     * // 使用InputStream插入数据
                     * realm.executeTransaction(new Realm.Transaction() {
                     *     @Override
                     *     public void execute(Realm realm) {
                     *         try {
                     *             InputStream is = new FileInputStream(new File("path_to_file"));
                     *             realm.createAllFromJson(City.class, is);
                     *         } catch (IOException e) {
                     *             throw new RuntimeException();
                     *         }
                     *     }
                     * });
                     */
                }
                break;
            case R.id.btn02:
                // 删除一个事务从数据库

                break;
            case R.id.btn03:
                // 添加记账或者记录todo

                break;
            case R.id.btn04:
                // 查询可以直接操作，不需要task
                RealmResults<AffairModel> models = mRealm.where(AffairModel.class).findAll();
                Logger.e(models.toString());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
        if (mRealm != null) {
            mRealm.close();
        }
    }
}
