package com.ezhevikina.course.homework6.dao.implementation;

import com.ezhevikina.course.homework6.dao.Dao;
import com.ezhevikina.course.homework6.dao.exceptions.DaoException;
import com.ezhevikina.course.homework6.dao.exceptions.NotFoundByIdException;
import com.ezhevikina.course.homework6.domain.Account;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonAccountDao implements Dao<Account> {
  private String filePath = "./src/main/resources/accounts.json";

  @Override
  public void create(Account account) throws DaoException {
    checkIfNull(account);

    try  {
      FileReader fileReader = new FileReader(filePath);
      JSONParser parser = new JSONParser();
      JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
      JSONArray accountsList = (JSONArray) jsonObject.get("accounts");
      JSONObject newAccount = new JSONObject();

      newAccount.put("id", account.getId());
      newAccount.put("holder", account.getHolder());
      newAccount.put("balance", account.getAmount());
      accountsList.add(newAccount);
      jsonObject.put("accounts", accountsList);

      fileReader.close();

      FileWriter fileToWrite = new FileWriter(filePath, false);
      fileToWrite.write(jsonObject.toJSONString());
      fileToWrite.close();
    } catch (IOException | ParseException e) {
      throw new DaoException("Exception in dao layer", e);
    }
  }

  @Override
  public Account getById(int id) throws NotFoundByIdException, DaoException {
    String holder = null;
    int balance = 0;

    JSONObject jsonObject = parseAccountsFile();
    JSONArray accountsList = (JSONArray) jsonObject.get("accounts");
    boolean found = false;

    for (Object account : accountsList) {
      JSONObject jsonAccount = (JSONObject) account;

      if (((Long) jsonAccount.get("id")).intValue() == id) {
        balance = ((Long) jsonAccount.get("balance")).intValue();
        holder = (String) jsonAccount.get("holder");
        found = true;
        break;
      }
    }

    if (!found) {
      throw new NotFoundByIdException();
    }
    return new Account(id, holder, balance);
  }

  @Override
  public void update(Account account) throws DaoException, NotFoundByIdException {
    checkIfNull(account);

    JSONObject jsonObject = parseAccountsFile();
    JSONArray accountsList = (JSONArray) jsonObject.get("accounts");
    boolean found = false;

    for (Object accountJson : accountsList) {
      JSONObject jsonAccount = (JSONObject) accountJson;

      if (((Long) jsonAccount.get("id")).intValue() == account.getId()) {
        jsonAccount.put("balance", account.getAmount());
        jsonAccount.put("holder", account.getHolder());

        try (
            FileWriter file = new FileWriter(filePath)
        ) {
          jsonObject.put("accounts", accountsList);
          file.write(jsonObject.toJSONString());
          found = true;

        } catch (IOException e) {
          throw new DaoException("Exception in DAO layer", e);
        }
        break;
      }
    }

    if (!found) {
      throw new NotFoundByIdException();
    }
  }

  private JSONObject parseAccountsFile() throws DaoException {
    JSONParser parser = new JSONParser();
    JSONObject jsonObject = null;

    try {
      jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
    } catch (IOException | ParseException e) {
      throw new DaoException("Exception in DAO layer", e);
    }
    return jsonObject;
  }

  private void checkIfNull(Account account) throws DaoException {
    if(account == null) {
      throw new DaoException("Created account should not be null");
    }
  }
}
