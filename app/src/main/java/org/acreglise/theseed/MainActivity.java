package org.acreglise.theseed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.acreglise.theseed.adapters.ReaderAdapter;
import org.acreglise.theseed.adapters.ViewPagerAdapter;
import org.acreglise.theseed.fragments.HomeFragment;
import org.acreglise.theseed.fragments.LibraryFragment;
import org.acreglise.theseed.fragments.NoteFragment;
import org.acreglise.theseed.fragments.ReaderFragment;
import org.acreglise.theseed.fragments.SearchFragment;
import org.acreglise.theseed.models.DB;
import org.acreglise.theseed.models.FrResource;
import org.acreglise.theseed.models.FrResourceContent;
import org.acreglise.theseed.models.Seeder;
import org.acreglise.theseed.ui.LockableViewPager;
import org.acreglise.theseed.utils.StyleUtil;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;
    private TabLayout tabLayout;
    private LockableViewPager viewPager;
    public FloatingActionButton fab;
    private DiscreteSeekBar sizeSeekBar;
    public TextView txtSizeLabel;
    private MenuItem actionDescription;
    private MenuItem actionSearch;
    private MenuItem actionTxtFormat;
    public boolean isBottomSheetHidden = true;
    public boolean isActionModeEnabled = false;
    private int[] tabIcons = {
            R.drawable.ic_home_black_18dp,
            R.drawable.ic_book_black_18dp,
            R.drawable.ic_library_books_black_18dp,
            R.drawable.ic_mode_edit_black_18dp,
            R.drawable.ic_search_black_18dp
    };

    public BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //StyleUtil.applyFontForToolbarTitle(toolbar, this);
        //toolbarParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();

        //buttonBar = (Button) findViewById(R.id.select_brochure_action);
        //buttonBar.setVisibility(View.GONE);
        /*buttonBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BrochureSelectionActivity.class);
                startActivity(intent);
            }
        });*/

        viewPager = (LockableViewPager) findViewById(R.id.viewPager);
        viewPager.setSwipeable(false);
        setupViewPager(viewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        final AppBarLayout.LayoutParams tabLayoutParams = (AppBarLayout.LayoutParams) tabLayout.getLayoutParams();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#4486FA"), PorterDuff.Mode.SRC_IN);
                fab.setVisibility(View.GONE);

                switch (tab.getPosition()) {
                    case 1:
                        fab.setImageDrawable(getDrawable(R.drawable.ic_book_white_18dp));
                        fab.setVisibility(View.VISIBLE);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, BrochureSelectionActivity.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case 3:
                        fab.setImageDrawable(getDrawable(R.drawable.ic_note_add_white_18dp));
                        fab.setVisibility(View.VISIBLE);
                        break;
                    default:
                        fab.setVisibility(View.GONE);

                }

                /*if (tab.getPosition() == 1 ) {
                    //getSupportActionBar().setTitle(null);
                    fab.setVisibility(View.GONE);
                    //actionDescription.setVisible(true);
                    //actionTxtFormat.setVisible(true);
                    //buttonBar.setVisibility(View.VISIBLE);

                    tabLayoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                } else {
                    //getSupportActionBar().setTitle(getString(R.string.app_name));
                    fab.setVisibility(View.GONE);
                    //actionDescription.setVisible(false);
                    //actionTxtFormat.setVisible(false);
                    //buttonBar.setVisibility(View.GONE);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    isBottomSheetHiden = true;
                    tabLayoutParams.setScrollFlags(0);

                    if (isActionModeEnabled()) {
                        disableActionMode();
                    }

                    if (tab.getPosition() == 2) {
                        fab.setVisibility(View.VISIBLE);
                        //getSupportActionBar().setTitle(getString(R.string.note));
                    }

                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#9f9f9f"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        boolean isFirstRun = getSharedPreferences(getPackageName(), MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        DB.deleteAll();

        if (isFirstRun) {

            Seeder seeder = new Seeder(this);
            seeder.run();

            getSharedPreferences(getPackageName(), MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        } else {
            Seeder seeder = new Seeder(this);
            seeder.run();
        }

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_layout));
        sizeSeekBar = (DiscreteSeekBar) findViewById(R.id.seekBar);
        txtSizeLabel = (TextView) findViewById(R.id.labelSize);
        initSeekBar();


        refresh();
        instance = this;
    }

    private void initSeekBar() {
        int size = sizeSeekBar.getProgress();
        getSharedPreferences(getPackageName(), MODE_PRIVATE).edit()
                .putInt("contentItemSize", size)
                .apply();
        txtSizeLabel.setText(getString(R.string.txt_size, String.valueOf(size)));

        sizeSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                txtSizeLabel.setText(getString(R.string.txt_size, String.valueOf(value)));
                getSharedPreferences(getPackageName(), MODE_PRIVATE).edit()
                        .putInt("contentItemSize", value)
                        .apply();
                ReaderAdapter.getInstance().notifyDataSetChanged();
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*getMenuInflater().inflate(R.menu.menu_main, menu);
        actionDescription = menu.findItem(R.id.action_description);
        actionTxtFormat = menu.findItem(R.id.action_txt_format);
        actionSearch = menu.findItem(R.id.action_search);*/

        return true;
    }

    @Override
    public void onBackPressed() {

        if (isActionModeEnabled()) {
            disableActionMode();
        } else {

            if (viewPager.getCurrentItem() == 0) {
                super.onBackPressed();

            } else {
                if (!isBottomSheetHidden) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    isBottomSheetHidden = true;
                    fab.setVisibility(View.VISIBLE);
                } else {
                    viewPager.setCurrentItem(0);
                }
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*int id = item.getItemId();

        if (isActionModeEnabled()) {
            switch (id) {
                case android.R.id.home:
                    disableActionMode();
                    break;
            }
        } else {

            switch (id) {
                case R.id.action_search:
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_txt_format:
                    if (isBottomSheetHiden) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        isBottomSheetHiden = false;
                    } else {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        isBottomSheetHiden = true;
                    }
            }
        }*/

        return super.onOptionsItemSelected(item);

    }

    public void refresh() {
        DB.refresh();
    }

    public void hideBottonSheet() {
        if (!isBottomSheetHidden) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void updateView() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(1);
            }
        });
    }

    public void setTitleOnButtonbar(String title) {
        //buttonBar.setText(title);
    }

    private void setupTabIcons() {

        for (int i = 0; i < 5; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);

            if (i == 0) {
                tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#4486FA"), PorterDuff.Mode.SRC_IN);
            } else {
                tabLayout.getTabAt(i).getIcon().setColorFilter(Color.parseColor("#9f9f9f"), PorterDuff.Mode.SRC_IN);
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), MainActivity.class.getCanonicalName());
        adapter.addFragment(HomeFragment.newInstance("", ""), getString(R.string.home));
        adapter.addFragment(ReaderFragment.newInstance("", ""), getString(R.string.read));
        adapter.addFragment(LibraryFragment.newInstance("", ""), getString(R.string.library));
        adapter.addFragment(NoteFragment.newInstance("", ""), getString(R.string.note));
        adapter.addFragment(SearchFragment.newInstance("", ""), getString(R.string.search));
        viewPager.setAdapter(adapter);
    }

    public boolean isActionModeEnabled() {
        return isActionModeEnabled;
    }

    public boolean enableActionMode() {
       /* //toolbar.getMenu().clear();
        //toolbar.inflateMenu(R.menu.menu_action_mode);
        isActionModeEnabled = true;
        //buttonBar.setVisibility(View.GONE);
        ReaderFragment.getInstance().notifyDataSetChanged();
        toolbarParams.setScrollFlags(0);
        //tabLayout.setVisibility(View.GONE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/

        return true;
    }

    private void disableActionMode() {
        /*toolbar.getMenu().clear();
        onCreateOptionsMenu(toolbar.getMenu());
        isActionModeEnabled = false;
        toolbarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        tabLayout.setVisibility(View.VISIBLE);
        buttonBar.setVisibility(View.VISIBLE);
        actionDescription.setVisible(true);
        actionTxtFormat.setVisible(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }*/
    }
}
