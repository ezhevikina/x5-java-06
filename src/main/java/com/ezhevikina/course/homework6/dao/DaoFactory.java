package com.ezhevikina.course.homework6.dao;

import com.ezhevikina.course.homework6.dao.exceptions.DaoException;
import com.ezhevikina.course.homework6.domain.StorageType;
import com.ezhevikina.course.homework6.initializer.exceptions.DataSourceInitException;

public interface DaoFactory<T> {
  Dao<T> getDao(StorageType type) throws DaoException, DataSourceInitException;
}
