package com.gameprocessor;

import java.util.LinkedList;
import java.util.List;

public class Response {
    public String userId = "";
    public String text = "";
    public LinkedList<ResponseObject> objects = new LinkedList<>();

    public static ResponseBuilder builder(){
        return new ResponseBuilder();
    }

    public static class ResponseObject{
        public String sortText;
        public String longText;
        public List<String> actions;
        public String callbackData;

        public ResponseObject(String shortText, String longText, List<String> actions, String callbackData){
            this.sortText = shortText;
            this.longText = longText;
            this.actions = actions;
            this.callbackData = callbackData;
        }
    }

    public static class ResponseBuilder{
        private final Response response = new Response();

        public ResponseBuilder userId(long userId){
            response.userId = Long.toString(userId);
            return this;
        }

        public ResponseBuilder userId(String userId){
            response.userId = userId;
            return this;
        }
        public ResponseBuilder text(String text){
            response.text = text;
            return this;
        }

        public ResponseBuilder addObject(Sendable obj, String callbackData){
            ResponseObject responseObject = new ResponseObject(
                    obj.getShortText(),
                    obj.getLongText(),
                    obj.getActions().getActions(),
                    callbackData
            );
            response.objects.add(responseObject);
            return this;
        }

        public Response build(){
            return response;
        }
    }
}
