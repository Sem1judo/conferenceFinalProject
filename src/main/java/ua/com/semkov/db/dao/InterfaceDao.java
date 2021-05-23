package ua.com.semkov.db.dao;



import ua.com.semkov.exceptions.DAOException;

import java.util.List;

public interface InterfaceDao<T> {

    List<T> getAll() throws DAOException;
    List<T> getAllPagination(int start,int total) throws DAOException;

    T getById(Long id) throws DAOException;

    T getBySpecificName(String name) throws DAOException;

    int insertEntityReturningId(T t) throws DAOException;

    void updateEntityById(T t) throws DAOException;

    void updateEntityBySpecificName(T k) throws DAOException;

    void deleteEntity(Long id) throws DAOException;


}

