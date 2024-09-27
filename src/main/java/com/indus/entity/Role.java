package com.indus.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles",schema = "mockito")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private ERole name;
  
  @Column(name = "created_on", insertable = true, updatable = false)
	private Timestamp createdOn = new Timestamp(System.currentTimeMillis());

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn = new Timestamp(System.currentTimeMillis());

	@Column(name = "updated_by")
	private Integer updatedBy;

	@Column(name = "active_flag")
	private Boolean activeFlag;

  public Role() {

  }

  public Role(ERole name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ERole getName() {
    return name;
  }

  public void setName(ERole name) {
    this.name = name;
  }

public Timestamp getCreatedOn() {
	return createdOn;
}

public void setCreatedOn(Timestamp createdOn) {
	this.createdOn = createdOn;
}

public Integer getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(Integer createdBy) {
	this.createdBy = createdBy;
}

public Timestamp getUpdatedOn() {
	return updatedOn;
}

public void setUpdatedOn(Timestamp updatedOn) {
	this.updatedOn = updatedOn;
}

public Integer getUpdatedBy() {
	return updatedBy;
}

public void setUpdatedBy(Integer updatedBy) {
	this.updatedBy = updatedBy;
}

public Boolean getActiveFlag() {
	return activeFlag;
}

public void setActiveFlag(Boolean activeFlag) {
	this.activeFlag = activeFlag;
}
}