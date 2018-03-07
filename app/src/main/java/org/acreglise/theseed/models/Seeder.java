package org.acreglise.theseed.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.acreglise.theseed.R;
import org.acreglise.theseed.utils.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class Seeder {

    private Context context;
    private Realm realm;
    private List<FrResource> resources;
    private List<FrResourceContent> contents;
    private DateFormat dateFormat;
    private int[] covers = {
            R.drawable.cover_eagle,
            R.drawable.cover_lake,
            R.drawable.cover_mountain,
            R.drawable.cover_road_in_forest,
            R.drawable.cover_seal,
            R.drawable.cover_wheat,
            R.drawable.cover_wmb
    };

    public Seeder(Context context) {
        this.context = context;
        this.realm = Realm.getDefaultInstance();
        this.resources = new ArrayList<>();
        this.contents = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    private void setResources() {
        final String json = JSONUtil.loadJSONFromAsset(context, "resources.json");

        try {
            JSONObject object = new JSONObject(json);
            JSONObject model = object.getJSONObject("model");
            JSONArray data = model.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject jo = data.getJSONObject(i);

                FrResource resource = new FrResource();
                resource.setId(jo.getInt("id"));
                resource.setIsbn(jo.getString("isbn"));
                resource.setOriginalTitle(jo.getString("origin_title"));
                resource.setOriginDate(dateFormat.parse(jo.getString("origin_date")));
                resource.setType(jo.getString("type"));
                resource.setTitle(jo.getString("title"));
                resource.setDescription(jo.getString("description"));

                if (i <= covers.length) {
                    resource.setCover(covers[i]);
                }

                resource.setCreated_at(dateFormat.parse(jo.getString("created_at")));
                resource.setUpdated_at(dateFormat.parse(jo.getString("updated_at")));

                JSONArray contentsArray = jo.getJSONArray("contents");

                for (int j = 0; j < contentsArray.length(); j++) {
                    JSONObject jc = contentsArray.getJSONObject(j);

                    FrResourceContent content = new FrResourceContent();
                    content.setFrResourceId(jc.getInt("fr_resource_id"));
                    content.setParagraphNumber(jc.getInt("paragraph_number"));
                    content.setParagraphContent(jc.getString("paragraph_content"));
                    content.setCreated_at(dateFormat.parse(jc.getString("created_at")));
                    content.setUpdated_at(dateFormat.parse(jc.getString("updated_at")));
                    contents.add(content);

                }
                resources.add(resource);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void run() {
        setResources();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (FrResource res : resources) {
                    FrResource resource = realm.createObject(FrResource.class);

                    resource.setId(res.getId());
                    resource.setIsbn(res.getIsbn());
                    resource.setOriginalTitle(res.getOriginalTitle());
                    resource.setOriginDate(res.getOriginDate());
                    resource.setType(res.getType());
                    resource.setTitle(res.getTitle());
                    resource.setDescription(res.getDescription());
                    resource.setCover(res.getCover());
                    resource.setCreated_at(res.getCreated_at());
                    resource.setUpdated_at(res.getUpdated_at());

                }

                for (FrResourceContent ct : contents) {
                    FrResourceContent content = realm.createObject(FrResourceContent.class);
                    content.setFrResourceId(ct.getFrResourceId());
                    content.setParagraphNumber(ct.getParagraphNumber());
                    content.setParagraphContent(ct.getParagraphContent());
                    content.setCreated_at(ct.getCreated_at());
                }

                for (FrResource res : realm.where(FrResource.class).findAll()) {
                    List<FrResourceContent> c = realm.where(FrResourceContent.class).equalTo("frResourceId", res.getId()).findAll();
                    res.getResourceContents().addAll(c);
                }
            }
        });

    }
}
