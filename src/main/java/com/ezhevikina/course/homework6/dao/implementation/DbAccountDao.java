package com.ezhevikina.course.homework6.dao.implementation;

import com.ezhevikina.course.homework6.dao.Dao;
import com.ezhevikina.course.homework6.dao.DataSource;
import com.ezhevikina.course.homework6.dao.exceptions.DaoException;
import com.ezhevikina.course.homework6.dao.exceptions.NotFoundByIdException;
import com.ezhevikina.course.homework6.domain.Account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

public class DbAccountDao implements Dao<Account> {
  @Override
  public void create(Account account) throws DaoException {
    checkIfNull(account);

    try {
      EntityManager manager = DataSource.getEntityManager();
      manager.getTransaction().begin();
      manager.persist(account);
      manager.getTransaction().commit();
    } catch (PersistenceException | IllegalStateException | IllegalArgumentException e) {
      throw new DaoException("Entity manager exception in DAO layer", e);
    }
  }

  @Override
  public Account getById(int accountId) throws NotFoundByIdException, DaoException {
    try {
      EntityManager manager = DataSource.getEntityManager();
      Account account = manager.find(Account.class, accountId);

      if (account == null) {
        throw new NotFoundByIdException();
      }
      return account;

    } catch (PersistenceException | IllegalStateException | IllegalArgumentException e) {
      throw new DaoException("Entity manager exception in DAO layer", e);
    }
  }

  @Override
  public void update(Account newAccount) throws DaoException, NotFoundByIdException {
    checkIfNull(newAccount);
    Account initialAccount = getById(newAccount.getId());

    try {
      EntityManager manager = DataSource.getEntityManager();
      manager.getTransaction().begin();
      initialAccount.setAmount(newAccount.getAmount());
      manager.merge(initialAccount);
      manager.getTransaction().commit();

    } catch (PersistenceException | IllegalStateException | IllegalArgumentException e) {
      throw new DaoException("Entity manager exception in DAO layer", e);
    }
  }

  private void checkIfNull(Account account) throws DaoException {
    if (account == null) {
      throw new DaoException("Created account should not be null");
    }
  }
}
