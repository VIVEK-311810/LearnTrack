package com.airtribe.learntrack.repository;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository<T> {
    protected List<T> items;

    public BaseRepository() {
        this.items = new ArrayList<>();
    }

    public void save(T item) {
        items.add(item);
    }

    public List<T> findAll() {
        return new ArrayList<>(items);
    }

    public void update(T item) {
        int index = items.indexOf(item);
        if (index >= 0) {
            items.set(index, item);
        }
    }

    public void delete(T item) {
        items.remove(item);
    }
}
