package com.cydeo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapService<ID, T> {

    protected Map<ID,T> map = new HashMap<>();

    T save(ID id, T object){
        map.put(id,object);
        return object;
    }


    List<T> findAll(){

        return new ArrayList<>(map.values());
    }

    T findBy(ID id){

        return map.get(id);
    }

    void deleteById(ID id){
        map.remove(id);
    }
}