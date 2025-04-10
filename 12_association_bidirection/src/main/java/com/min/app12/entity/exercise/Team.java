package com.min.app12.entity.exercise;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "team")
@Table(name = "tbl_team")
public class Team {

  @Id
  @Column(name = "tem_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer teamId;
  
  @Column(name = "team_name")
  private String teamName;
  
  public Team() {
    // TODO Auto-generated constructor stub
  }

  public Team(Integer teamId, String teamName) {
    super();
    this.teamId = teamId;
    this.teamName = teamName;
  }

  public Integer getTeamId() {
    return teamId;
  }


  public void setTeamId(Integer teamId) {
    this.teamId = teamId;
  }


  public String getTeamName() {
    return teamName;
  }


  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  @Override
  public String toString() {
    return "Team [teamId=" + teamId + ", teamName=" + teamName + "]";
  }



  
}
