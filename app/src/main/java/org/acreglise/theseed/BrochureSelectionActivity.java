package org.acreglise.theseed;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.acreglise.theseed.adapters.ViewPagerAdapter;
import org.acreglise.theseed.fragments.BrochureList;
import org.acreglise.theseed.fragments.HomeFragment;
import org.acreglise.theseed.fragments.NoteFragment;
import org.acreglise.theseed.fragments.ParagraphList;
import org.acreglise.theseed.fragments.ReaderFragment;
import org.acreglise.theseed.ui.LockableViewPager;
import org.acreglise.theseed.utils.StyleUtil;

public class BrochureSelectionActivity extends AppCompatActivity {

    private static BrochureSelectionActivity instance;
    private LockableViewPager viewPager;

    public static BrochureSelectionActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brochure_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StyleUtil.applyFontForToolbarTitle(toolbar, this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewPager = (LockableViewPager) findViewById(R.id.viewPager);
        viewPager.setSwipeable(false);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        instance = this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();

        } else {

            viewPager.setCurrentItem(0);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), BrochureSelectionActivity.class.getCanonicalName());
        adapter.addFragment(BrochureList.newInstance(), getString(R.string.brochure_list));
        adapter.addFragment(ParagraphList.newInstance(), getString(R.string.paragraph_list));
        viewPager.setAdapter(adapter);
    }

    public void goToParagraphs() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(1);
            }
        });
    }
}
