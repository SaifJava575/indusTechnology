package com.indus.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indus.bean.JavaConstant;
import com.indus.bean.PaginationModel;
import com.indus.bean.SearchFilter;
import com.indus.dao.IGenericDao;
import com.indus.entity.Role;
import com.indus.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	
	@Autowired
	private IGenericDao iGenericDao;
	
	@Override
	public PaginationModel searchUserInfo(PaginationModel paginationModel, Integer currentPage,
			SearchFilter searchFilter) {
		logger.info("fetching SearchUserInformation list: " + new Timestamp(System.currentTimeMillis()));
		PaginationModel searchState = null;

		try {
			String whereCondition = " where 1=1 ";
			if (searchFilter.getId() != 0 && searchFilter.getId() != null)
				whereCondition += " and us.id =" + searchFilter.getId();

			if (searchFilter.getUseName() != null && !searchFilter.getUseName().equals(""))
				whereCondition += "  and us.username like '%" + searchFilter.getUseName() + "%'";

			if (searchFilter.getEmail() != null && !searchFilter.getEmail().equals(""))
				whereCondition += " and us.email like '%" + searchFilter.getEmail() + "%'";

			if (searchFilter.getStatus() != null)
				whereCondition += " and us.active_flag =" + searchFilter.getStatus();

			String query = JavaConstant.SEARCH_USER_INFO;
			query += whereCondition;

			searchState = iGenericDao.getPaginationWithSQLQuery(paginationModel, currentPage, query, new Object[] {});
		} catch (Exception e) {
			logger.error("error occcured while fetching search user Information: " + e.getMessage() + e.getCause());
			e.printStackTrace();
		}
		return searchState;
	}
	
	@Override
	@Transactional
	public String addRole(Role role) {
		logger.info("create Role called: {} " +role.getName());
		String status = "";
		String whereCondition = " and name= '" +role.getName() + "'";
		List<?> existingROle = iGenericDao.executeDDLHQL(JavaConstant.CHECK_ROLE_BY_NAME + whereCondition,
				new Object[] {});
		if (existingROle != null && existingROle.size() > 0) {
			status = "RoleAlreadyExist";
			logger.warn("zone with the name : '{}'" + role.getName() + " already exist");
		} else {
			try {
				role.setActiveFlag(true);
				logger.info(role.toString());
				iGenericDao.save(role);
				status = "savedSucessFully";
				logger.info(status + " " + role.getName());
			} catch (Exception e) {
				logger.error("error occured while registered Role: '{}' " + e.getCause() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
		return status;
	}
	
	@Override
	@Transactional
	public String deleteRoleById(Integer id) {
		String updateStatus = "";
		List<?> fetchMasterDpeotName = iGenericDao.executeDDLHQL(JavaConstant.DELETE_ROLE_ID,
				new Object[] {id });
		if (fetchMasterDpeotName.size()>0) {
			iGenericDao.executeDMLHQL(JavaConstant.DELETE_ROLE_NAME, new Object[] {id});
			logger.info("Role Deleted Successfully : '{}'" );
			updateStatus = "deleteSucessFully";
		} else

		{
			logger.warn("Master Role id: '{}' " + id 
					+ " not available!");
			updateStatus = "roleNameNotExist";
		}
		return updateStatus;
	}

}
