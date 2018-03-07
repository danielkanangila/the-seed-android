package org.acreglise.theseed.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class FrResource extends RealmObject {

    private int id;
    private String isbn;
    private String originalTitle;
    private Date originDate;
    private String type;
    private String title;
    private String description;
    private int cover;
    private RealmList<FrResourceContent> resourceContents;
    private Date created_at;
    private Date updated_at;

    public FrResource() {
    }

    public FrResource(int id, String isbn, String originalTitle, Date originDate, String type, String title, String description, int cover, RealmList<FrResourceContent> resourceContents, Date created_at, Date updated_at) {
        this.id = id;
        this.isbn = isbn;
        this.originalTitle = originalTitle;
        this.originDate = originDate;
        this.type = type;
        this.title = title;
        this.description = description;
        this.cover = cover;
        this.resourceContents = resourceContents;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Date getOriginDate() {
        return originDate;
    }

    public void setOriginDate(Date originDate) {
        this.originDate = originDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public RealmList<FrResourceContent> getResourceContents() {
        return resourceContents;
    }

    public void setResourceContents(RealmList<FrResourceContent> resourceContents) {
        this.resourceContents = resourceContents;
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
}
