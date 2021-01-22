package com.ezhevikina.course.homework6.domain;

import com.ezhevikina.course.homework6.dao.exceptions.DaoException;
import com.ezhevikina.course.homework6.service.AccountManager;
import com.ezhevikina.course.homework6.service.exceptions.NotEnoughMoneyException;
import com.ezhevikina.course.homework6.service.exceptions.UnknownAccountException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Меню взаимодействия с клиентом. Принимает на вход интерфейс AccountService.
 * В консоль выводятся доступные для обработки команды.
 * Производится обработка пользовательского ввода через консоль и преобразуется
 * в команды для AccountManager.
 */

public class AtmScreen {
  private final AccountManager manager;

  public AtmScreen(AccountManager manager) {
    this.manager = manager;
  }

  public void start() {
    System.out.println("Available operations:\n"
        + "* balance [id]\n"
        + "* withdraw [id] [amount]\n"
        + "* deposit [id] [amount]\n"
        + "* transfer [from] [to] [amount]\n"
        + "E to exit");

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      try {
        String[] command = (reader.readLine()).split(" ");
        AccountOperation operation = AccountOperation.valueOf(command[0].toUpperCase());

        switch (operation) {
          case BALANCE: {
            int accountId = Integer.parseInt(command[1]);
            System.out.println(manager.balance(accountId));
            break;
          }
          case WITHDRAW: {
            int accountId = Integer.parseInt(command[1]);
            int amount = Integer.parseInt(command[2]);

            if (amount < 0) {
              throw new IllegalArgumentException("Amount should be positive");
            }

            manager.withdraw(accountId, amount);
            System.out.println("Successful operation");
            break;
          }
          case DEPOSIT: {
            int accountId = Integer.parseInt(command[1]);
            int amount = Integer.parseInt(command[2]);

            if (amount < 0) {
              throw new IllegalArgumentException("Amount should be positive");
            }

            manager.deposit(accountId, amount);
            System.out.println("Successful operation");
            break;
          }
          case TRANSFER: {
            int accountFrom = Integer.parseInt(command[1]);
            int accountTo = Integer.parseInt(command[2]);
            int amount = Integer.parseInt(command[3]);

            if (amount < 0) {
              throw new IllegalArgumentException("Amount should be positive");
            }

            manager.transfer(accountFrom, accountTo, amount);
            System.out.println("Successful operation");
            break;
          }
          case E: {
            reader.close();
            System.exit(0);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();

      } catch (DaoException e) {
        System.out.println("DAO exception");
        e.printStackTrace();

      } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
        System.out.println("Invalid command");

      } catch (UnknownAccountException | NotEnoughMoneyException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}