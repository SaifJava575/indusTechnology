package com.indus.service;

import com.indus.bean.PaginationModel;
import com.indus.bean.SearchFilter;
import com.indus.entity.Role;

public interface IUserService {

	PaginationModel searchUserInfo(PaginationModel paginationModel, Integer currentPage, SearchFilter searchFilter);

	String addRole(Role role);

	String deleteRoleById(Integer id);

}
