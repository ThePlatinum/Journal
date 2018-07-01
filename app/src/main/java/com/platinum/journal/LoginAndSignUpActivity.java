package com.platinum.journal;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LoginAndSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_and_signup);

        ViewPager viewPager = (ViewPager) findViewById(R.id.teb_viewPager);
        setupViewPager(viewPager);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private  void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new SignInFragment(),"Sign In");
        viewPagerAdapter.addFragment(new SignUpFragment(),"Sign Up");
        viewPager.setAdapter(viewPagerAdapter);

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> FRAGMENT_LIST = new ArrayList<>();
        private final List<String> FRAGMENT_TITLE_LIST = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FRAGMENT_LIST.get(position);
        }

        @Override
        public int getCount() {
            return FRAGMENT_LIST.size();
        }

        void addFragment(Fragment fragment, String title){
            FRAGMENT_LIST.add(fragment);
            FRAGMENT_TITLE_LIST.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position){

            return FRAGMENT_TITLE_LIST.get(position);
        }
    }

}
