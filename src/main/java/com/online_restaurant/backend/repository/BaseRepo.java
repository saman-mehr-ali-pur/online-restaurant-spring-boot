package com.online_restaurant.backend.repository;

import java.util.List;

public interface BaseRepo <T>{

    List<T> getAll(T ob);
    T get(int id);
    T save(T ob);
    boolean delete(T ob);
    boolean update(T ob);
}
