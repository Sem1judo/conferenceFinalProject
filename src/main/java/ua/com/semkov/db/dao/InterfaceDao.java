package ua.com.semkov.db.dao;



import ua.com.semkov.exceptions.DAOException;

import java.util.List;

public interface InterfaceDao<T> {

    List<T> getAll() throws DAOException;
    List<T> getAllPagination(int start,int total) throws DAOException;

    T getById(Long id) throws DAOException;

    T getBySpecificName(String name) throws DAOException;

    T insertEntity(T t) throws DAOException;

    boolean updateEntityById(T t) throws DAOException;

    boolean updateEntityBySpecificName(T k) throws DAOException;

    boolean deleteEntity(Long id) throws DAOException;


}

