package org.acreglise.theseed.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.acreglise.theseed.MainActivity;
import org.acreglise.theseed.R;
import org.acreglise.theseed.fragments.ReaderFragment;
import org.acreglise.theseed.models.FrResource;
import org.acreglise.theseed.utils.DateUtil;

import java.util.List;

/**
 * Created by kkana on 19/05/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context context;
    private List<FrResource> frResourceList;
    private LayoutInflater layoutInflater;

    public HomeAdapter(Context context, List<FrResource> frResourceList) {
        this.context = context;
        this.frResourceList = frResourceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());

        View itemView = layoutInflater.inflate(R.layout.home_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final FrResource resource = frResourceList.get(position);
        holder.title.setText(resource.getTitle());
        holder.timestamp.setText(context.getString(R.string.timestamp, DateUtil.format(resource.getOriginDate())));
        Glide.with(context).load(resource.getCover()).into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return frResourceList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, timestamp;
        private ImageView cover, homeOverflow;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            homeOverflow = (ImageView) itemView.findViewById(R.id.home_overflow);
        }
    }
}
