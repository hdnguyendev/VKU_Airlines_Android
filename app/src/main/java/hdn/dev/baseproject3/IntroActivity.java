package hdn.dev.baseproject3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.relex.circleindicator.CircleIndicator;

public class IntroActivity extends AppCompatActivity {

    private TextView tvSkip;
    private ViewPager viewPager;
    private RelativeLayout layoutBottom;
    private CircleIndicator circleIndicator;
    private LinearLayout layoutNext;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initView();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        circleIndicator.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (position == 2) {
                            tvSkip.setVisibility(View.GONE);
                            layoutBottom.setVisibility(View.GONE);
                        } else {
                            tvSkip.setVisibility(View.VISIBLE);
                            layoutBottom.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                }
        );
    }

    private void initView() {
        tvSkip = findViewById(R.id.idTVSkip);
        viewPager = findViewById(R.id.idVPPage);
        layoutBottom = findViewById(R.id.idRLFunction);
        circleIndicator = findViewById(R.id.idCICircleIndicator);
        layoutNext = findViewById(R.id.idLLNext);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });

        layoutNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() < 2) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });
    }
}