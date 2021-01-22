package com.ezhevikina.course.homework6.dao.implementation;

import com.ezhevikina.course.homework6.dao.Dao;
import com.ezhevikina.course.homework6.dao.DaoFactory;
import com.ezhevikina.course.homework6.domain.Account;
import com.ezhevikina.course.homework6.domain.StorageType;
import com.ezhevikina.course.homework6.initializer.JsonAccountDataSourceInit;
import com.ezhevikina.course.homework6.initializer.exceptions.DataSourceInitException;

public class AccountDaoFactory implements DaoFactory<Account> {
  @Override
  public Dao<Account> getDao(StorageType type) throws DataSourceInitException {
    Dao<Account> dao;
    switch (type) {
      case DATABASE:
        dao = new DbAccountDao();
        break;
      case JSON:
        dao = new JsonAccountDao();
        new JsonAccountDataSourceInit().init(dao);
        break;
      default:
        throw new UnsupportedOperationException(String.format(
            "DAO type %s is not supported", type));
    }
    return dao;
  }
}
