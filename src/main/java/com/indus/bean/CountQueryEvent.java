package com.indus.bean;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

public class CountQueryEvent extends ApplicationEvent {

	private static final long serialVersionUID = -7355630106447606659L;

	private String hql;
	
	private Object[] parameters;
	
	private UUID identifier;
	
	public CountQueryEvent(Object source, String hql, Object[] parameters, UUID identifier) {
		super(source);
		this.hql = hql;
		this.parameters = parameters;
		this.identifier = identifier;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public UUID getIdentifier() {
		return identifier;
	}

	public void setIdentifier(UUID identifier) {
		this.identifier = identifier;
	}	
	
	
}

