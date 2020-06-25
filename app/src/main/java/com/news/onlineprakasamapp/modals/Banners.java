package com.news.onlineprakasamapp.modals;

import java.io.Serializable;
import java.util.List;

public class Banners implements Serializable {


    /**
     * status : true
     * message : Data Fetched successfully!
     * response : [{"id":"2","user_type":"1","role_user_id":"1","language_type":"1","title":"గులాబి, ఆర్చిడ్\u200c, కట్\u200cఫ్లవర్స్\u200c, జర్బేరా, కీరదోస, క్యాప్సికం, చెర్రీ, టమాట, ఆకుకూర","image":"http://apnewsnviews.com/onlineprakasam/storage/banner_adds/1528372777389324223.jpg","status":"Active","delete_status":"1","created_on":"2020-06-04 01:56:55","modified_on":null},{"id":"1","user_type":"1","role_user_id":"1","language_type":"1","title":"కట్\u200cఫ్లవర్స్\u200c, జర్బేరా, కీరదోస, క్యాప్సికం, చెర్రీ, టమాట, ఆకుకూర","image":"http://apnewsnviews.com/onlineprakasam/storage/banner_adds/3daction.jpg","status":"Active","delete_status":"1","created_on":"2020-06-02 09:54:36","modified_on":null}]
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
         * id : 2
         * user_type : 1
         * role_user_id : 1
         * language_type : 1
         * title : గులాబి, ఆర్చిడ్‌, కట్‌ఫ్లవర్స్‌, జర్బేరా, కీరదోస, క్యాప్సికం, చెర్రీ, టమాట, ఆకుకూర
         * image : http://apnewsnviews.com/onlineprakasam/storage/banner_adds/1528372777389324223.jpg
         * status : Active
         * delete_status : 1
         * created_on : 2020-06-04 01:56:55
         * modified_on : null
         */

        private String id;
        private String user_type;
        private String role_user_id;
        private String language_type;
        private String title;
        private String image;
        private String status;
        private String delete_status;
        private String created_on;
        private Object modified_on;

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

        public String getLanguage_type() {
            return language_type;
        }

        public void setLanguage_type(String language_type) {
            this.language_type = language_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public Object getModified_on() {
            return modified_on;
        }

        public void setModified_on(Object modified_on) {
            this.modified_on = modified_on;
        }
    }
}
