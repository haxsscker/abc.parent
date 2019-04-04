package com.autoserve.abc.dao;

import java.util.List;

/**
 * <pre>
 * base dao interface
 * 
 * named specification:
 * 1. insert find update delete ==> crud sql.
 * 2. count  ==> find num of records.
 * 3. countByXXX => find num of records by specified conditions.
 * 4. findByXXX ==> find records by specified conditions.
 * </pre>
 * 
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2013-5-23
 * @param <K>
 * @param <K>
 */
public interface BaseDao<T, K> {

    /**
     * insert
     * 
     * @param t dataobject
     * @return pk value.
     */
    K insert(T t);

    int batchInsert(final List<T> list);

    /**
     * find by id.
     * 
     * @param id
     * @return
     */
    T findById(K id);

    /**
     * update
     * 
     * @param t dataobject
     * @return num of updated .
     */
    int update(T t);

    /**
     * delete by id
     * 
     * @param id
     * @return num of deleted.
     */
    int delete(K id);

    /**
     * count rows
     *
     * @param t
     * @return num of rows
     */
    int count(T t);

    <R> int updateDeleted(K id, R deleted);

    <R> int updateDeleted(K id, R deleted, String modifier);

    <R> int batchUpdateDeleted(List<K> id, R deleted, String modifier);
}
