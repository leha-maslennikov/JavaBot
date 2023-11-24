package com.gameprocessor;

import java.util.HashMap;

public class ResourceManager {
    public static final HashMap<String, Object> storage = new HashMap<>();
    public static Resource createResource(String userId, String Id, Object object){
        Resource resource = new Resource(userId+":"+Id, object.getClass().getName());
        storage.put(resource.getId(), object);
        return resource;
    }

    public static Resource createResource(String userId, Object object) {
        Resource resource = new Resource(userId+":"+storage.size(), object.getClass().getName());
        storage.put(resource.getId(), object);
        return resource;
    }

    public static Object getObject(Resource resource){
        return storage.get(resource.getId());
    }

    public static Resource update(Resource resource, Object object){
        storage.put(resource.getId(), object);
        return new Resource(resource.getId(), object.getClass().getName());
    }

    public static void delete(Resource resource){
        storage.remove(resource.getId());
    }
}
