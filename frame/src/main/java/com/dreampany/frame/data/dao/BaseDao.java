package com.dreampany.frame.data.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Hawladar Roman on 29/4/18.
 * Dreampany
 * dreampanymail@gmail.com
 */
public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<T> ts);

    @Update
    void update(T item);

    @Delete
    void delete(T item);
}
