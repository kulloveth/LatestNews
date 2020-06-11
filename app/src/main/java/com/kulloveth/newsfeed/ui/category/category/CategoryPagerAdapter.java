package com.kulloveth.newsfeed.ui.category.category;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kulloveth.newsfeed.ui.category.entertainment.EntertainmentFragment;
import com.kulloveth.newsfeed.ui.category.health.HealthFragment;
import com.kulloveth.newsfeed.ui.category.sports.SportsFragment;
import com.kulloveth.newsfeed.ui.category.technology.TechnologyFragment;

import java.util.Map;
import java.util.TreeMap;

public class CategoryPagerAdapter extends FragmentStateAdapter {

    private Map<Integer, Fragment> createTabFragment;

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
