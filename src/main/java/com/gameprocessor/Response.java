package com.gameprocessor;

import java.util.LinkedList;

public class Response {
    public String userId;
    public String text;
    public LinkedList<ResponseObject> objects;

    public ResponseBuilder builder(){
        return new ResponseBuilder();
    }

    public static class ResponseObject{
        public String text;
        public String callbackData;

        public ResponseObject(String text, String callbackData){
            this.text = text;
            this.callbackData = callbackData;
        }
    }

    public static class ResponseBuilder{
        private final Response response = new Response();

        public ResponseBuilder userId(long userId){
            response.userId = Long.toString(userId);
            return this;
        }

        public ResponseBuilder text(String text){
            response.text = text;
            return this;
        }

        public ResponseBuilder addObject(String text, String callbackData){
            response.objects.add(new ResponseObject(text, callbackData));
            return this;
        }

        public Response build(){
            return response;
        }
    }
}
