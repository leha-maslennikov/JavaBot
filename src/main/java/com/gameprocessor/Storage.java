package com.gameprocessor;

public interface Storage {
    Resource createResource(String Id, Object object);
    Object getObject(Resource resource);
    Resource update(Resource resource, Object object);
    void delete(Resource resource);
    void deleteUser(String userId);
}
