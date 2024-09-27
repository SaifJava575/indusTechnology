package com.indus.dao;

import java.util.List;

import com.indus.bean.PaginationModel;


public interface IGenericDao {

	public <T> void save(final T entity);

	public <T> void update(final T entity);

	public <T> void delete(final T entity);

	public <T> List<T> executeDDLHQL(String hqlquery, Object[] listofparameter);

	public <T> List<T> executeDDLHQLWITHLIMIT(String hqlquery, Object[] listofparameter, Integer recordLimits);

	public void executeDMLHQL(String hqlquery, Object[] listofparameter);

	public <T> List<T> executeDDLSQL(String sqlquery, Object[] listofparameter);

	public void executeDMLSQL(String sqlquery, Object[] listofparameter);

	PaginationModel getPaginationWithQuery(PaginationModel paginationModel, Integer currentPage, String hqlquery,
			Object[] listofparameter);

	PaginationModel getPaginationWithSQLQuery(PaginationModel paginationModel, Integer currentPage, String sqlquery,
			Object[] listofparameter);

}
