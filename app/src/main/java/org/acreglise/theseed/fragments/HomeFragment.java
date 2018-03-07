package org.acreglise.theseed.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.acreglise.theseed.BrochureSelectionActivity;
import org.acreglise.theseed.MainActivity;
import org.acreglise.theseed.R;
import org.acreglise.theseed.adapters.HomeAdapter;
import org.acreglise.theseed.listeners.ClickListener;
import org.acreglise.theseed.listeners.RecyclerTouchListener;
import org.acreglise.theseed.models.FrResource;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class HomeFragment extends Fragment {

    private HomeAdapter adapter;
    private List<FrResource> resources;
    private Realm realm;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        realm = Realm.getDefaultInstance();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerView);

        resources = new ArrayList<>();
        adapter = new HomeAdapter(getContext(), resources);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                FrResource resource = resources.get(position);

                getContext().getSharedPreferences(getContext().getApplicationContext().getPackageName(), Context.MODE_PRIVATE).edit()
                        .putString("isbn", resource.getIsbn())
                        .apply();
                ReaderFragment.getInstance().updateView(0);
                MainActivity activity = (MainActivity) getContext();
                activity.updateView();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        setData();

        return view;
    }

    public void setData() {
        resources.addAll(realm.where(FrResource.class).findAll());
        adapter.notifyDataSetChanged();
    }
}
