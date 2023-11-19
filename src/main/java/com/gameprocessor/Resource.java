package com.gameprocessor;

public class Resource {
    private String id;
    private String objectClass;

    public Resource(String id, String objectClass){
        this.id = id;
        this.objectClass = objectClass;
    }

    public String getId() {
        return id;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public Object get(){
        return ResourceManager.getObject(this);
    }

    public void update(Object object){
        ResourceManager.update(this, object);
    }

    public void delete(){
        ResourceManager.delete(this);
    }
}
