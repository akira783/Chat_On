package akira.fr.chat_on;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ViewPager viewPager;

    private TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);

       setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }*/

        setTitle(Html.fromHtml("<font color='#FFFFFFF'>Chat On</font>"));


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter  = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ToolbarFragment(), "");
        viewPager.setAdapter( viewPagerAdapter );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_commun, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            //case R.id.action_panier:
                //Intent intent = new Intent(A_Achat.this, A_MonPanier.class);
                //startActivity(intent);
                //return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*****************************************************************************************************/
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> listeFrgs = new ArrayList<>();
        private final List<String> titreFrgs = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return listeFrgs.get(position);
        }

        // retur le nombre de tab layout
        @Override
        public int getCount() {
            return listeFrgs .size();
        }

        // retur le titre du tab layout
        @Override
        public CharSequence getPageTitle(int position) {
            return titreFrgs.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            listeFrgs.add(fragment);
            titreFrgs.add(title);
        }
    }


}
