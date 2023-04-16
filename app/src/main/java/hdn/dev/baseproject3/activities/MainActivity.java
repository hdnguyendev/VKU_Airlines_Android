package hdn.dev.baseproject3.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.adapter.NotifyRVAdapter;
import hdn.dev.baseproject3.fragments.HistoryFragment;
import hdn.dev.baseproject3.fragments.HomeFragment;
import hdn.dev.baseproject3.fragments.ProfileFragment;
import hdn.dev.baseproject3.fragments.SettingFragment;

public class MainActivity extends AppCompatActivity {
    private BottomAppBar bottomAppBar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());
        initView();
        initController();
    }

    private void initController() {
    }

    private void initView() {
        bottomAppBar = findViewById(R.id.idBottomAppBar);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.idBottomNavView);


        bottomAppBar.setHideOnScroll(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.idItemHome:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.idItemProfile:
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.idItemSearch:
                        fragment = new HistoryFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.idItemSetting:
                        fragment = new SettingFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });

        // Set background to null
        bottomNavigationView.setBackground(null);
        // Disable the menu item at index 2
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}