package com.gameprocessor;

public class ResourceManager {
    public static Resource createResource(String userId, String Id, Object object){
        return new Resource(userId+":"+Id, object.getClass().getName());
    }

    public static Resource createResource(String userId, Object object){
        return new Resource(userId+":"+"1", object.getClass().getName());
    }

    public static Object getObject(Resource resource){
        return new Object();
    }

    public static Resource update(Resource resource, Object object){
        return resource;
    }

    public static void delete(Resource resource){

    }
}
