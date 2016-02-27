package com.lba;


public class Message {
  private long id;
  private String msg;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getMessage() {
    return msg;
  }

  public void setMessage(String msg) {
    this.msg = msg;
  }

 
  @Override
  public String toString() {
    return msg;
  }
} 
