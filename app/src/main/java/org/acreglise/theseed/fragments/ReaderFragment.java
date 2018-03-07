package org.acreglise.theseed.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.acreglise.theseed.MainActivity;
import org.acreglise.theseed.R;
import org.acreglise.theseed.adapters.ReaderAdapter;
import org.acreglise.theseed.models.FrResource;
import org.acreglise.theseed.models.FrResourceContent;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ReaderFragment extends Fragment {

    private ReaderAdapter adapter;
    private FrResource resource;
    private List<FrResourceContent> contents;
    private Realm realm;
    private static ReaderFragment instance;
    private TextView title;
    private RecyclerView recyclerView;
    private ArrayList<FrResourceContent> selectionList = new ArrayList<>();

    public ReaderFragment() {
    }

    public static ReaderFragment newInstance(String param1, String param2) {
        ReaderFragment fragment = new ReaderFragment();
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
        View view = inflater.inflate(R.layout.fragment_reader, container, false);
        realm = Realm.getDefaultInstance();

        setResource();
        recyclerView = (RecyclerView) view.findViewById(R.id.reader_recyclerView);
        title = (TextView) view.findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().fab.setVisibility(View.GONE);
                if (MainActivity.getInstance().isBottomSheetHidden) {
                    MainActivity.getInstance().bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    MainActivity.getInstance().isBottomSheetHidden = false;
                } else {
                    MainActivity.getInstance().bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    MainActivity.getInstance().isBottomSheetHidden = true;
                }
            }
        });

        contents = new ArrayList<>();
        adapter = new ReaderAdapter(getContext(), contents);
        adapter.setResource(resource);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setData();
        scrollToPosition(0);

        instance = this;

        return view;
    }

    private void setResource() {
        String isbn = getContext().getSharedPreferences(getActivity().getApplicationContext().getPackageName(), Context.MODE_PRIVATE)
                .getString("isbn", "FRN53-0608A");
        resource = realm.where(FrResource.class).equalTo("isbn", isbn).findFirst();
    }

    private void setData() {
        setResource();

        if (resource != null) {
            title.setText(resource.getTitle());
            //MainActivity.getInstance().setTitleOnButtonbar(subTitle(resource.getTitle()));
            contents.addAll(resource.getResourceContents());
        }
        adapter.setResource(resource);
        adapter.notifyDataSetChanged();
    }

    public void updateView(final int position) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                realm = Realm.getDefaultInstance();
                contents.clear();
                setData();
                scrollToPosition(position);
            }
        });
    }

    public static ReaderFragment getInstance() {
        return instance;
    }

    public void scrollToPosition(int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        layoutManager.scrollToPosition(position);
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    public void prepareSelection(View view, int position) {
        TextView txtView = (TextView)view;
        /*FrResourceContent item = contents.get(position);

        if (!contains(item)) {
            txtView.setBackgroundColor(Color.LTGRAY);
            selectionList.add(item);
        } else {
            selectionList.remove(item);
            txtView.setBackgroundColor(0);
        }*/

        Log.d("TheSeed", String.valueOf(position));
    }

    private boolean contains(FrResourceContent item) {
        for (FrResourceContent content : selectionList) {
            if (content.getParagraphNumber() == item.getParagraphNumber())
                return true;
        }
        return false;
    }

    private String subTitle(String title) {
        if (!TextUtils.isEmpty(title)) {

            if (title.length() > 20) {
                return title.substring(0, 20)+"...";
            }
        }
        return title;
    }
}
