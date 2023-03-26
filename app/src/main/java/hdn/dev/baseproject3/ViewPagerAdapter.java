package hdn.dev.baseproject3;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

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
