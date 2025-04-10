package com.min.app12.entity.exercise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "locker")
@Table(name = "tbl_locker")
public class Locker {
  
  @Id
  @Column(name = "locker_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer lockerId;
  
  @Column(name = "locker_name")
  private String lockerName;
  
  public Locker() {
  }

  public Locker(Integer lockerId, String lockerName) {
    super();
    this.lockerId = lockerId;
    this.lockerName = lockerName;
  }

  public Integer getLockerId() {
    return lockerId;
  }

  public void setLockerId(Integer lockerId) {
    this.lockerId = lockerId;
  }

  public String getLockerName() {
    return lockerName;
  }

  public void setLockerName(String lockerName) {
    this.lockerName = lockerName;
  }

  @Override
  public String toString() {
    return "Locker [lockerId=" + lockerId + ", lockerName=" + lockerName + "]";
  }
  
  
}


