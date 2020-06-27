/*
 * Copyright (C) 2017 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.news.onlineprakasamapp.modals;

/**
 * The {@link MenuItem1} class.
 * <p>Defines the attributes for a restaurant menu item.</p>
 */
public class MenuItem1 {

    private String id;
    private String language_id;
    private String title;
    private String description;
    private String image_path;
    private String status;
    private String created_on;
    private Object updated_on;


    public MenuItem1(String id, String language_id, String title, String description, String image_path, String status, String created_on, Object updated_on) {
        this.id = id;
        this.language_id = language_id;
        this.title = title;
        this.description = description;
        this.image_path = image_path;
        this.status = status;
        this.created_on = created_on;
        this.updated_on = updated_on;
    }

    public MenuItem1() {
    }

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

