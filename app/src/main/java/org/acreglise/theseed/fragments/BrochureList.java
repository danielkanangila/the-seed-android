package org.acreglise.theseed.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.acreglise.theseed.BrochureSelectionActivity;
import org.acreglise.theseed.R;
import org.acreglise.theseed.adapters.BrochureListAdapter;
import org.acreglise.theseed.listeners.RecyclerTouchListener;
import org.acreglise.theseed.models.FrResource;
import org.acreglise.theseed.listeners.ClickListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class BrochureList extends Fragment {

    private BrochureListAdapter adapter;
    private List<FrResource> resources;
    private Realm realm;

    public static BrochureList newInstance() {

        return new BrochureList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brochure_list, container, false);

        realm = Realm.getDefaultInstance();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        resources = new ArrayList<>();
        adapter = new BrochureListAdapter(getContext(), resources);

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
                ParagraphList.getInstance().updateView();
                BrochureSelectionActivity.getInstance().goToParagraphs();
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
