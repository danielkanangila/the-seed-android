package org.acreglise.theseed.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.acreglise.theseed.MainActivity;
import org.acreglise.theseed.R;
import org.acreglise.theseed.fragments.ReaderFragment;
import org.acreglise.theseed.models.FrResource;
import org.acreglise.theseed.models.FrResourceContent;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ReaderAdapter extends RecyclerView.Adapter<ReaderAdapter.ReaderViewHolder> {

    private static final int TYPE_LIST = 0;
    private static final int TYPE_FOOTER = 1;
    private static ReaderAdapter instance;

    private Context context;
    private FrResource resource;
    private List<FrResourceContent> contents = new ArrayList<>();
    private Realm realm;
    private MainActivity mainActivity;

    public ReaderAdapter(Context context, List<FrResourceContent> contents) {
        this.context = context;
        this.contents = contents;
        this.realm = Realm.getDefaultInstance();
        this.mainActivity = (MainActivity) context;

        instance = this;
    }

    @Override
    public int getItemViewType(int position) {
        if (position != getItemCount() - 1)
            return TYPE_LIST;
        return TYPE_FOOTER;
    }

    @Override
    public ReaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        ReaderViewHolder readerViewHolder;

        if (viewType == TYPE_LIST) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.reader_item, parent, false);
            readerViewHolder = new ReaderViewHolder(view, mainActivity, viewType);

            return  readerViewHolder;

        } else if (viewType == TYPE_FOOTER) {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.reader_footer, parent, false);
            readerViewHolder = new ReaderViewHolder(view, mainActivity, viewType);

            return  readerViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final ReaderViewHolder holder, int position) {

        if (holder.view_type == TYPE_LIST) {
            final FrResourceContent content = contents.get(position);

            holder.paragraphNumber.setText(String.valueOf(content.getParagraphNumber()));
            holder.paragraphContent.setText(content.getParagraphContent());

            int txtSize = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
                    .getInt("contentItemSize", 15);

            holder.paragraphContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);

        } else if (holder.view_type == TYPE_FOOTER) {

            holder.description.setText(resource.getDescription());

        }

    }

    @Override
     public int getItemCount() {
        return contents.size() + 1;
    }

    class ReaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int view_type;
        // Variable for list
        private TextView paragraphNumber, paragraphContent;
        private MainActivity mainActivity;

        // Variable for footer
        private TextView description;

        public ReaderViewHolder(View itemView, MainActivity mainActivity, int viewType) {
            super(itemView);

            if (viewType == TYPE_LIST) {
                paragraphNumber = (TextView) itemView.findViewById(R.id.paragraph_number);
                paragraphContent = (TextView) itemView.findViewById(R.id.paragraph_content);
                this.mainActivity = mainActivity;

                paragraphContent.setOnClickListener(this);
                view_type = viewType;

            } else if (viewType == TYPE_FOOTER) {
                description = (TextView) itemView.findViewById(R.id.description);
                view_type = viewType;
            }


        }

        @Override
        public void onClick(View v) {
            mainActivity.enableActionMode();
            ReaderFragment.getInstance().prepareSelection(v, getPosition());
        }
    }

    public static ReaderAdapter getInstance() {
        return instance;
    }

    public void setResource(FrResource resource) {
        this.resource = resource;
    }

}
