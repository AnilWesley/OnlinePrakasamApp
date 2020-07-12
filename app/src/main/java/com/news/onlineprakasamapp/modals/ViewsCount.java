package com.news.onlineprakasamapp.modals;

public class ViewsCount {


    /**
     * status : true
     * message : Data added successfully!
     * response : 2
     */

    private boolean status;
    private String message;
    private int response;

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

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}
