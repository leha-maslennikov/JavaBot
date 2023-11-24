package com.gameprocessor;

public class Resource {
    public String id;

    public Resource(String id){
        this.id = id;
    }

    public Resource(String id, String objectClass){
        this.id = id+":"+objectClass;
    }

    public String getId() {
        String[] args = id.split(":");
        return args[0]+args[1];
    }

    public String getObjectClass() {
        String[] args = id.split(":");
        return args[args.length-1];
    }

    public Object get(){
        return ResourceManager.getObject(this);
    }

    public void update(Object object){
        this.id = ResourceManager.update(this, object).id;
    }

    public void delete(){
        ResourceManager.delete(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj instanceof Resource resource) {
            return id.equals(resource.id);
        }
       return false;
    }
}
