package com.kulloveth.newsfeed.ui.category;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Map;
import java.util.TreeMap;

public class CategoryPagerAdapter extends FragmentStateAdapter {

    public static final int ENTERTAINMENT_PAGE_INDEX = 0;
    public static final int HEALTH_PAGE_INDEX = 1;
    public static final int SPORTS_PAGE_INDEX = 2;
    public static final int TECHNOLOGY_PAGE_INDEX = 3;

    public CategoryPagerAdapter(Fragment fragment) {
        super(fragment);
        createTabFragment = new TreeMap<>();
        createTabFragment.put(ENTERTAINMENT_PAGE_INDEX, new EntertainmentFragment());
        createTabFragment.put(HEALTH_PAGE_INDEX, new HealthFragment());
        createTabFragment.put(SPORTS_PAGE_INDEX, new SportsFragment());
        createTabFragment.put(TECHNOLOGY_PAGE_INDEX, new TechnologyFragment());
    }

    private Map<Integer, Fragment> createTabFragment;


    @NonNull
    @Override
    public Fragment createFragment(int position) {


        return createTabFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return createTabFragment.size();
    }
}
