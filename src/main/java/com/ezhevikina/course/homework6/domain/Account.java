package com.ezhevikina.course.homework6.domain;

import javax.persistence.*;

@Entity
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(nullable = false)
  private String holder;
  @Column(nullable = false)
  private int amount;

  public Account(int id, String holder, int amount) {
    this.id = id;
    this.holder = holder;
    this.amount = amount;
  }

  public Account() {
  }

  public int getId() {
    return id;
  }

  public String getHolder() {
    return holder;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public void setHolder(String holder) {
    this.holder = holder;
  }
}

