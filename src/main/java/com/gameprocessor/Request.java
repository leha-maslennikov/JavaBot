package com.gameprocessor;

public class Request {
    public String userId;
    public String callbackData;
    public String action;
    public Response response;

    public String getUserId() {
        return userId;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public String getAction() {
        return action;
    }

    public Resource getUserData(){
        return new Resource(
                this.userId+"UserData",
                UserData.class.getName()
        );
    }

    public Response getResponse() {
        return response;
    }
}
