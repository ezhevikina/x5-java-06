package com.ezhevikina.course.homework6.service;

import com.ezhevikina.course.homework6.dao.exceptions.DaoException;
import com.ezhevikina.course.homework6.service.exceptions.NotEnoughMoneyException;
import com.ezhevikina.course.homework6.service.exceptions.UnknownAccountException;

public interface AccountService {
  void withdraw(int accountId, int amount) throws
      NotEnoughMoneyException, UnknownAccountException, DaoException;

  int balance(int accountId) throws UnknownAccountException, DaoException;

  void deposit(int accountId, int amount) throws UnknownAccountException, DaoException;

  void transfer(int from, int to, int amount) throws
      NotEnoughMoneyException, UnknownAccountException, DaoException;
}
