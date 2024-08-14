package com.online_restaurant.backend.service;


import java.util.List;

public interface BaseService<T> {


    T get(int id);
    List<T> getAll(int limit);
    T save(T ob);
    boolean update(T ob);
    boolean delete(int id);
}
