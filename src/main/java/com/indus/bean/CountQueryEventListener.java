package com.indus.bean;

import java.math.BigInteger;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CountQueryEventListener {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	CountQueryResultHolder countQueryResultHolder;

	@Async
	@EventListener
	public void onCountQueryEvent(CountQueryEvent countQueryEvent) {
		CompletableFuture<Long> asyncCountTask = CompletableFuture.supplyAsync(() -> {
			Long result = null;
			try {
				result = tryGettingCountResultNormally(countQueryEvent.getHql(), countQueryEvent.getParameters(), countQueryEvent.getIdentifier());
			}
			catch(PersistenceException e) {
				result = tryGettingCountResultByProxyCreatedQuery(countQueryEvent.getHql(), countQueryEvent.getParameters(), countQueryEvent.getIdentifier());
			}
			return result;
		});
		countQueryResultHolder.putQueryResult(countQueryEvent.getIdentifier(), asyncCountTask);
	}

//	public Long getCountQueryResult(String hqlQuery, Object[] listofparameter, UUID queryKey) {
//		Query tempQuery = entityManager.createQuery(hqlQuery);
//		for (int c = 0; c < listofparameter.length; c++) {
//			tempQuery.setParameter(c + 1, listofparameter[c]);
//		}
//		tempQuery.setHint("org.hibernate.comment", "pagination" + queryKey.toString());
//		tempQuery.setHint("javax.persistence.query.timeout", 1000);
//		System.out.println(tempQuery.getHints());
//		Long count = null;
//		try {
//			count = Long.valueOf(tempQuery.getResultList().size());
//		} catch (PersistenceException e) {
//			System.out.println("No problem");
//			String countQueryString = countQueryResultHolder.getCountQuery(queryKey.toString());
//			System.out.println("querykey: " + queryKey.toString());
//			System.out.println(countQueryResultHolder.getCountQuery(queryKey.toString()));
//			if (countQueryString != null) {
//				Query countQuery = entityManager
//						.createNativeQuery(countQueryResultHolder.getCountQuery(queryKey.toString()));
//				count = ((BigInteger) countQuery.getSingleResult()).longValue();
//				countQueryResultHolder.removeCountQuery(queryKey.toString());
//			} else {
//				Query countQuery = entityManager.createQuery(hqlQuery);
//				for (int c = 0; c < listofparameter.length; c++) {
//					countQuery.setParameter(c + 1, listofparameter[c]);
//				}
//				count = Long.valueOf(countQuery.getResultList().size());
//			}
//		}
//		return count;
//	}

	@Transactional
	public Long tryGettingCountResultNormally(String hqlQuery, Object[] listofparameter, UUID queryKey) {
		Query tempQuery = entityManager.createQuery(hqlQuery);
		for (int c = 0; c < listofparameter.length; c++) {
			tempQuery.setParameter(c + 1, listofparameter[c]);
		}
		tempQuery.setHint("org.hibernate.comment", "pagination" + queryKey.toString());
		tempQuery.setHint("javax.persistence.query.timeout", 1000);
		System.out.println(tempQuery.getHints());
		return Long.valueOf(tempQuery.getResultList().size());
	}

	@Transactional
	public Long tryGettingCountResultByProxyCreatedQuery(String hqlQuery, Object[] listofparameter, UUID queryKey) {
		System.out.println("No problem");
		String countQueryString = countQueryResultHolder.getCountQuery(queryKey.toString());
		System.out.println("querykey: " + queryKey.toString());
		System.out.println(countQueryResultHolder.getCountQuery(queryKey.toString()));
		if (countQueryString != null) {
			Query countQuery = entityManager
					.createNativeQuery(countQueryResultHolder.getCountQuery(queryKey.toString()));
			Long count = ((BigInteger) countQuery.getSingleResult()).longValue();
			countQueryResultHolder.removeCountQuery(queryKey.toString());
			return count;
		}
		Query countQuery = entityManager.createQuery(hqlQuery);
		for (int c = 0; c < listofparameter.length; c++) {
			countQuery.setParameter(c + 1, listofparameter[c]);
		}
		return Long.valueOf(countQuery.getResultList().size());
	}

}
