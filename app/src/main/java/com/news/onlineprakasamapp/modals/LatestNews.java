package com.news.onlineprakasamapp.modals;

public class LatestNews {


    private String id;
    private String language_id;
    private String title;
    private String description;
    private String image_path;
    private String status;
    private String created_on;
    private Object updated_on;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
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

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public Object getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Object updated_on) {
        this.updated_on = updated_on;
    }
}
