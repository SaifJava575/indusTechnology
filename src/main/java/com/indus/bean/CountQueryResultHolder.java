package com.indus.bean;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class CountQueryResultHolder {
	
	Map<String, String> countQueryContainer = new ConcurrentHashMap<>();
	Map<UUID, CompletableFuture<Long>> queryResults = new ConcurrentHashMap<>();
	
	public void putResult(String queryIdentifier, String countQuery) {
		countQueryContainer.put(queryIdentifier, countQuery);
	}
	
	public String getCountQuery(String queryIdentifier) {
		return countQueryContainer.get(queryIdentifier);
	}
	
	public void removeCountQuery(String queryIdentifier) {
		countQueryContainer.remove(queryIdentifier);
	}
	
	public void putQueryResult(UUID queryIdentifier, CompletableFuture<Long> result) {
		queryResults.put(queryIdentifier, result);
	}
	
	public CompletableFuture<Long> getQueryResult(UUID queryIdentifier) {
		return queryResults.get(queryIdentifier);
	}
	
	public void removeQueryResult(UUID queryIdentifier) {
		queryResults.remove(queryIdentifier);
	}
	
	public boolean hasQueryTask(UUID identifier) {
		return queryResults.containsKey(identifier);
	}
	

}
