package org.acreglise.theseed.models;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by kkana on 19/05/2017.
 */

public class FrResourceContent extends RealmObject {

    private int frResourceId;
    private int paragraphNumber;
    private String paragraphContent;
    private boolean isSelected = false;
    private Date created_at;
    private Date updated_at;

    public FrResourceContent() {
    }

    public FrResourceContent(int frResourceId, int paragraphNumber, String paragraphContent, Date created_at, Date updated_at) {
        this.frResourceId = frResourceId;
        this.paragraphNumber = paragraphNumber;
        this.paragraphContent = paragraphContent;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getFrResourceId() {
        return frResourceId;
    }

    public void setFrResourceId(int frResourceId) {
        this.frResourceId = frResourceId;
    }

    public int getParagraphNumber() {
        return paragraphNumber;
    }

    public void setParagraphNumber(int paragraphNumber) {
        this.paragraphNumber = paragraphNumber;
    }

    public String getParagraphContent() {
        return paragraphContent;
    }

    public void setParagraphContent(String paragraphContent) {
        this.paragraphContent = paragraphContent;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
