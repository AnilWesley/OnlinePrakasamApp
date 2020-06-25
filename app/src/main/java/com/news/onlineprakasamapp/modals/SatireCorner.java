package com.news.onlineprakasamapp.modals;

import java.util.List;

public class SatireCorner {


    /**
     * status : true
     * message : Data Fetched successfully!
     * response : [{"id":"40","user_type":"1","role_user_id":"1","language_id":"2","category_id":"1","sub_category_id":"1","child_category_id":"1","title":"Title","description":"<p>Sample Testing<\/p>\r\n","image":"http://apnewsnviews.com/onlineprakasam/storage/news/FB_IMG_1464503729311.jpg","source":"1","source_logo":"","status":"Active","delete_status":"1","created_on":"2020-06-13 20:28:43","modified_on":"0000-00-00 00:00:00"}]
     */

    private boolean status;
    private String message;
    private List<ResponseBean> response;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * id : 40
         * user_type : 1
         * role_user_id : 1
         * language_id : 2
         * category_id : 1
         * sub_category_id : 1
         * child_category_id : 1
         * title : Title
         * description : <p>Sample Testing</p>
         * image : http://apnewsnviews.com/onlineprakasam/storage/news/FB_IMG_1464503729311.jpg
         * source : 1
         * source_logo :
         * status : Active
         * delete_status : 1
         * created_on : 2020-06-13 20:28:43
         * modified_on : 0000-00-00 00:00:00
         */

        private String id;
        private String user_type;
        private String role_user_id;
        private String language_id;
        private String category_id;
        private String sub_category_id;
        private String child_category_id;
        private String title;
        private String description;
        private String image;
        private String source;
        private String source_logo;
        private String status;
        private String delete_status;
        private String created_on;
        private String modified_on;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getRole_user_id() {
            return role_user_id;
        }

        public void setRole_user_id(String role_user_id) {
            this.role_user_id = role_user_id;
        }

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getSub_category_id() {
            return sub_category_id;
        }

        public void setSub_category_id(String sub_category_id) {
            this.sub_category_id = sub_category_id;
        }

        public String getChild_category_id() {
            return child_category_id;
        }

        public void setChild_category_id(String child_category_id) {
            this.child_category_id = child_category_id;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSource_logo() {
            return source_logo;
        }

        public void setSource_logo(String source_logo) {
            this.source_logo = source_logo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDelete_status() {
            return delete_status;
        }

        public void setDelete_status(String delete_status) {
            this.delete_status = delete_status;
        }

        public String getCreated_on() {
            return created_on;
        }

        public void setCreated_on(String created_on) {
            this.created_on = created_on;
        }

        public String getModified_on() {
            return modified_on;
        }

        public void setModified_on(String modified_on) {
            this.modified_on = modified_on;
        }
    }
}
