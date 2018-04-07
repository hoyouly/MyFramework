package top.hoyouly.framework.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import top.hoyouly.framework.OpenApiFragment;

public class ViewPageAdapter extends FragmentPagerAdapter {
    private List<String> tabTitleList;
    public ViewPageAdapter(FragmentManager fm,List<String> tabTitleList) {
        super(fm);
        this.tabTitleList=tabTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return OpenApiFragment.getInstance(tabTitleList.get(position));
    }

    @Override
    public int getCount() {
        return tabTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleList.get(position);
    }
}
