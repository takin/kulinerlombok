package kl.app.syamsul.com.kulinerlombok.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import kl.app.syamsul.com.kulinerlombok.R;
import kl.app.syamsul.com.kulinerlombok.adapter.CardAdapter;
import kl.app.syamsul.com.kulinerlombok.fragment.CardFragment;
import kl.app.syamsul.com.kulinerlombok.fragment.DetailFragment;
import kl.app.syamsul.com.kulinerlombok.model.StoreModel;


public class HomeActivity extends MainActivity implements ListView.OnItemClickListener, CardAdapter.RecyclerAdapterListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;

    public HomeActivity(){
        super(R.layout.activity_home);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDrawer();
        initNavigation();

        if(savedInstanceState == null) {
            Fragment fragment = CardFragment.newInstance();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.add(R.id.main_content, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return (mDrawerToggle.onOptionsItemSelected(item)) ? true : super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // make the toolbar home button have a proper icon
        mDrawerToggle.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if(mNavigationView.isShown()){
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    private void initDrawer(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,0,0){

            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view){
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initNavigation(){
        mNavigationView = (NavigationView) mDrawerLayout.findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.drawer_home){
                    mActionBar.setTitle(R.string.app_name);
                } else {
                    mActionBar.setTitle(menuItem.getTitle());
                }

                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onRecyclerItemSelected(StoreModel data) {

        Intent det = new Intent(this,DetailActivity.class);
        det.putExtra(DetailActivity.BUNDLE_STRORE_DATA, data);
        startActivity(det);
//        Fragment f = DetailFragment.newInstance(data);
//        FragmentManager fm = getSupportFragmentManager();
//
//        fm.beginTransaction().replace(R.id.main_content,f).addToBackStack("card fragment").commit();
    }
}
