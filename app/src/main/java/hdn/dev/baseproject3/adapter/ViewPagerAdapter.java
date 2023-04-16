package hdn.dev.baseproject3.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import hdn.dev.baseproject3.fragments.IntroFragment1;
import hdn.dev.baseproject3.fragments.IntroFragment2;
import hdn.dev.baseproject3.fragments.IntroFragment3;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IntroFragment1();
            case 1:
                return new IntroFragment2();
            case 2:
                return new IntroFragment3();
            default:
                return new IntroFragment1();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
