package com.indus.bean;

public class JavaConstant {

	public final static String SEARCH_USER_INFO="select us.id as \"userId\",us.username as \"userName\",us.email as \"email\",us.active_flag as \"status\", mr.name as \"roleName\" from mockito.users us inner join mockito.user_roles ur on ur.user_id=us.id inner join mockito.roles mr on mr.id=ur.role_id ";
	public final static String CHECK_ROLE_BY_NAME=" from Role where activeFlag=true ";
	public final static String DELETE_ROLE_ID=" from Role where id=?1 ";
	public final static String DELETE_ROLE_NAME=" Delete from Role where id=?1 ";
	
}
