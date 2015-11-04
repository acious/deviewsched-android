package com.gdgssu.android_deviewsched.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.appevents.AppEventsLogger;
import com.gdgssu.android_deviewsched.DeviewSchedApplication;
import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.example.RecyclerViewFragment;
import com.gdgssu.android_deviewsched.model.UserItem;
import com.gdgssu.android_deviewsched.ui.location.LocationActivity;
import com.gdgssu.android_deviewsched.ui.sche.ScheFragment;
import com.gdgssu.android_deviewsched.ui.setting.SettingActivity;
import com.gdgssu.android_deviewsched.util.GlideCircleTransform;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

public class MainActivity extends AppCompatActivity implements DeviewFragment.OnFragmentInteractionListener {

    private MaterialViewPager mViewPager;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;

    private ImageView avatarImage;
    private TextView nameText;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private UserItem userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        userInfo = (UserItem) intent.getSerializableExtra("UserInfo");

        initMaterialViewPager();
        initToolbar();
        initNavigationView();
    }

    private void initToolbar() {
        setTitle("");

        mToolbar = mViewPager.getToolbar();
        setSupportActionBar(mToolbar);
    }

    private void initMaterialViewPager() {

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return RecyclerViewFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 3) {
                    case 0:
                        return "Deview2015";
                    case 1:
                        return "Hot Sessions";
                    case 2:
                        return "#Deview2015";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.colorPrimary,
                                ContextCompat.getDrawable(getBaseContext(), R.drawable.backwall1));
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.colorPrimary,
                                ContextCompat.getDrawable(getBaseContext(), R.drawable.backwall2));
                    case 2:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.colorPrimary,
                                ContextCompat.getDrawable(getBaseContext(), R.drawable.backwall3));
                }

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

    }

    private void initNavigationView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(mNavigationView);

        avatarImage = (ImageView) findViewById(R.id.profile_image);
        nameText = (TextView) findViewById(R.id.profile_name_text);

//        Glide.with(this)
//                .load(userInfo.user.picture)
//                .transform(new GlideCircleTransform(this))
//                .into(avatarImage);
//        nameText.setText(userInfo.user.name);

        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        showHome();
                        break;

                    case R.id.nav_all_schedule:
                        showSche(getResources().getText(R.string.all_schedule));
                        break;

                    case R.id.nav_my_schedule:
                        showSche(getResources().getText(R.string.my_schedule));
                        break;

                    case R.id.nav_location:
                        showLocation(getResources().getText(R.string.location));
                        break;

                    case R.id.nav_setting:
                        showSetting();
                        break;

                }
                menuItem.setChecked(true);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawers();
                return true;
            }
        });
    }

    public void showHome() {

        /**
         * Todo 아래의 메소드가 호출되면 MainActivity위로 있는 모든 Fragment가 소멸됨
         */

        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    public void showSche(CharSequence title) {

        Fragment allScheFragment = ScheFragment.newInstance(title);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_container, allScheFragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void showLocation(CharSequence title) {

        startActivity(new Intent(MainActivity.this, LocationActivity.class));

    }

    public void showSetting() {

        startActivity(new Intent(MainActivity.this, SettingActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            showHome();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
    }
}
