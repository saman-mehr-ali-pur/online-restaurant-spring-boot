package com.online_restaurant.backend.service;



public interface BaseService<T> {


    T get(int id);
    T getAll();
    T save(T ob);
    T update(T ob);
    boolean delete(int id);
}
