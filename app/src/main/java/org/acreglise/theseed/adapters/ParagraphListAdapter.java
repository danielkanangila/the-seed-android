package org.acreglise.theseed.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.acreglise.theseed.BrochureSelectionActivity;
import org.acreglise.theseed.MainActivity;
import org.acreglise.theseed.R;
import org.acreglise.theseed.fragments.ReaderFragment;
import org.acreglise.theseed.models.FrResource;
import org.acreglise.theseed.models.FrResourceContent;
import org.acreglise.theseed.utils.DateUtil;

import java.util.List;

import io.realm.Realm;

public class ParagraphListAdapter extends RecyclerView.Adapter<ParagraphListAdapter.MyViewHolder> {

    private Context context;
    private List<FrResourceContent> contents;
    private LayoutInflater layoutInflater;

    public ParagraphListAdapter(Context context, List<FrResourceContent> contents) {
        this.context = context;
        this.contents = contents;
    }

    @Override
    public ParagraphListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());

        View itemView = layoutInflater.inflate(R.layout.paragraph_list_row, parent, false);

        return new ParagraphListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParagraphListAdapter.MyViewHolder holder, final int position) {
        final FrResourceContent content = contents.get(position);
        holder.btnParagraphNumber.setText(String.valueOf(content.getParagraphNumber()));

        holder.btnParagraphNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReaderFragment.getInstance().updateView(position);
                MainActivity.getInstance().updateView();
                BrochureSelectionActivity activity = (BrochureSelectionActivity )context;
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private Button btnParagraphNumber;

        public MyViewHolder(View itemView) {
            super(itemView);

            btnParagraphNumber = (Button) itemView.findViewById(R.id.paragraph_number);
        }
    }
}
