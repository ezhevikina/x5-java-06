package com.ezhevikina.course.homework6.domain;

import com.ezhevikina.course.homework6.dao.Dao;
import com.ezhevikina.course.homework6.dao.implementation.AccountDaoFactory;
import com.ezhevikina.course.homework6.initializer.exceptions.DataSourceInitException;
import com.ezhevikina.course.homework6.service.AccountManager;

public class Main {
  public static void main(String[] args) {
    StorageType accountsStorageType = StorageType.DATABASE;

    try {
      Dao<Account> dao = new AccountDaoFactory().getDao(accountsStorageType);
      AtmScreen atm = new AtmScreen(new AccountManager(dao));
      atm.start();

    } catch (DataSourceInitException e) {
      e.printStackTrace();
    }
  }
}
