package org.acreglise.theseed.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.acreglise.theseed.MainActivity;
import org.acreglise.theseed.R;
import org.acreglise.theseed.adapters.ParagraphListAdapter;
import org.acreglise.theseed.adapters.ReaderAdapter;
import org.acreglise.theseed.models.FrResource;
import org.acreglise.theseed.models.FrResourceContent;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ParagraphList extends Fragment {

    private ParagraphListAdapter adapter;
    private List<FrResourceContent> contents;
    private Realm realm;
    private static ParagraphList instance;
    private RecyclerView recyclerView;

    public ParagraphList() {
    }

    public static ParagraphList newInstance() {
        return new ParagraphList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paragraph_list, container, false);
        realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        contents = new ArrayList<>();
        adapter = new ParagraphListAdapter(getContext(), contents);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setData();

        instance = this;

        return view;
    }

    private void setData() {
        String isbn = getContext().getSharedPreferences(getActivity().getApplicationContext().getPackageName(), Context.MODE_PRIVATE)
                .getString("isbn", "FRN53-0608A");
        FrResource resource = realm.where(FrResource.class).equalTo("isbn", isbn).findFirst();

        if (resource != null) {
            contents.addAll(resource.getResourceContents());
        }
        adapter.notifyDataSetChanged();
    }

    public void updateView() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                realm = Realm.getDefaultInstance();
                contents.clear();
                setData();;
            }
        });
    }

    public static ParagraphList getInstance() {
        return instance;
    }
}
