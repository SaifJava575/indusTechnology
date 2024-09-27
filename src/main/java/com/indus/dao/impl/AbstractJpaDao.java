package com.indus.dao.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;

import com.indus.bean.CountQueryEvent;
import com.indus.bean.CountQueryResultHolder;
import com.indus.bean.PaginationModel;

public abstract class AbstractJpaDao {

	@Autowired
	Environment environment;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	CountQueryResultHolder countQueryResultHolder;

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	/***** Save data method *******/

	public <T> void save(T entity) {
		entityManager.persist(entity);
	}

	/***** update data method *******/

	public <T> void update(T entity) {
		entityManager.merge(entity);
	}

	/***** delete data method *******/

	public <T> void delete(T entity) {
		entityManager.remove(entity);
	}

	/***** Execute DDL query in HQL method *******/

	@SuppressWarnings("unchecked")
	public <T> List<T> executeDDLHQL(String hqlquery, Object[] listofparameter) {
		List<T> list = null;
		System.out.println("DAO ==============================\n" + hqlquery);
		try {
			Query query = entityManager.createQuery(hqlquery);
			for (int c = 0; c < listofparameter.length; c++) {
				query.setParameter(c + 1, listofparameter[c]);
			}
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/***** Execute DDL query in HQL with limit method *******/

	@SuppressWarnings("unchecked")
	public <T> List<T> executeDDLHQLWITHLIMIT(String hqlquery, Object[] listofparameter, Integer recordLimits) {
		List<T> list = null;
		try {
			Query query = entityManager.createQuery(hqlquery);
			for (int c = 0; c < listofparameter.length; c++) {
				query.setParameter(c + 1, listofparameter[c]);
			}
			query.setMaxResults(recordLimits);
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/***** Execute DML query in HQL method *******/

	public void executeDMLHQL(String hqlquery, Object[] listofparameter) {
		try {
			System.out.println("DAO ==============================\n" + hqlquery);
			Query query = entityManager.createQuery(hqlquery);
			for (int c = 0; c < listofparameter.length; c++) {
				query.setParameter(c + 1, listofparameter[c]);
			}
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getPageLimit() throws IOException {
		if (environment.getProperty("PAGE_LIMIT") != null)
			return Integer.parseInt(environment.getProperty("PAGE_LIMIT"));
		else
			return 10;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public <T> List<T> executeDDLSQL(String sqlquery, Object[] listofparameter) {
		List<T> list = null;
		try {
			Query query = entityManager.createNativeQuery(sqlquery);
			query.unwrap(org.hibernate.query.Query.class)
					.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			for (int c = 0; c < listofparameter.length; c++) {
				query.setParameter(c + 1, listofparameter[c]);
			}
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/***** Execute DML query method *******/

	public void executeDMLSQL(String sqlquery, Object[] listofparameter) {
		try {
			Query query = entityManager.createNativeQuery(sqlquery);
			for (int c = 0; c < listofparameter.length; c++) {
				query.setParameter(c + 1, listofparameter[c]);
			}
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***** Pagination for Query *******/
	public PaginationModel getPaginationWithQuery(PaginationModel paginationModel, Integer currentPage, String hqlquery,
			Object[] listofparameter) {
		try {
			Integer pageLimit = getPageLimit();
			Query query = entityManager.createQuery(hqlquery);
			for (int c = 0; c < listofparameter.length; c++) {
				query.setParameter(c + 1, listofparameter[c]);
			}
			UUID identifier = UUID.randomUUID();
			CountQueryEvent countQueryEvent = new CountQueryEvent(this, hqlquery, listofparameter, identifier);
			applicationEventPublisher.publishEvent(countQueryEvent);
			while (!countQueryResultHolder.hasQueryTask(identifier)) {
			}
			CompletableFuture<Long> result = countQueryResultHolder.getQueryResult(identifier);
			Long totalRecords = result.get();
			System.out.println("TotalRecords: " + totalRecords);
			Integer totalPages = ((int) (Math.ceil((totalRecords + pageLimit - 1) / pageLimit)));
			paginationModel.setCurrentPageNo(currentPage);
			paginationModel.setTotalRecords(totalRecords);
			query.setFirstResult((currentPage * pageLimit) - pageLimit);
			query.setMaxResults(pageLimit);
			paginationModel.setTotalPages(totalPages);
			paginationModel.setPageLimit(pageLimit);
			paginationModel.setPaginationListRecords(query.getResultList());
			countQueryResultHolder.removeCountQuery(identifier.toString());
			countQueryResultHolder.removeQueryResult(identifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paginationModel;
	}

	/***** Pagination for SQL Query *******/
	@SuppressWarnings("deprecation")
	public PaginationModel getPaginationWithSQLQuery(PaginationModel paginationModel, Integer currentPage,
			String sqlquery, Object[] listofparameter) {
		try {
			Integer pageLimit = getPageLimit();
			Query query = entityManager.createNativeQuery(sqlquery);
			query.unwrap(org.hibernate.query.Query.class)
					.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			for (int c = 0; c < listofparameter.length; c++) {
				query.setParameter(c + 1, listofparameter[c]);
			}
			Query countQuery = entityManager.createNativeQuery("select count(*) from ( " + sqlquery + " ) x");
			for (int c = 0; c < listofparameter.length; c++) {
				countQuery.setParameter(c + 1, listofparameter[c]);
			}
			Long totalRecords = ((BigInteger) countQuery.getSingleResult()).longValue();
			Integer totalPages = ((int) (Math.ceil((totalRecords + pageLimit - 1) / pageLimit)));
			paginationModel.setCurrentPageNo(currentPage);
			paginationModel.setTotalRecords(Long.valueOf(totalRecords));
			query.setFirstResult((currentPage * pageLimit) - pageLimit);
			query.setMaxResults(pageLimit);
			paginationModel.setTotalPages(totalPages);
			paginationModel.setPageLimit(pageLimit);
			paginationModel.setPaginationListRecords(query.getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paginationModel;
	}

}
