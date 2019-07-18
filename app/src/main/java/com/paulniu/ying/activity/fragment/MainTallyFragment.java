package com.paulniu.ying.activity.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;
import com.paulniu.ying.adapter.TallyViewpagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 17:09
 * Desc: 记账Fragment
 * Version:
 */
public class MainTallyFragment extends BaseFragment {

    public static MainTallyFragment getInstance() {
        MainTallyFragment fragment = new MainTallyFragment();

        return fragment;
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<String> title = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private TallyViewpagerAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tally_tab;
    }

    @Override
    public void initDateExtra() {

    }

    @Override
    public void initViewFindViewById(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        title.add("记账");
        title.add("事务");

        fragments.add(TallyListFragment.getInstance());
        fragments.add(AffairListFragment.getInstance());

        adapter = new TallyViewpagerAdapter(getChildFragmentManager(), title, fragments);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
