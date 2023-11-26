package com.gameprocessor;

import java.util.HashMap;

public class DefaultStorage implements Storage{
    private final HashMap<String, Object> storage = new HashMap<>();
    public Resource createResource(String id, Object object){
        Resource resource = new Resource(id, object.getClass().getName());
        storage.put(resource.getId(), object);
        return resource;
    }

    public Object getObject(Resource resource){
        return storage.get(resource.getId());
    }

    public Resource update(Resource resource, Object object){
        storage.put(resource.getId(), object);
        return new Resource(resource.getId(), object.getClass().getName());
    }

    public void delete(Resource resource){
        storage.remove(resource.getId());
    }

    public void delete(String userId) {
        for(String key: storage.keySet()) {
            if(key.startsWith(userId)) {
                storage.remove(userId);
            }
        }
    }


}
