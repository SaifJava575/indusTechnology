package com.indus.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.indus.bean.PaginationModel;
import com.indus.bean.Response;
import com.indus.bean.SearchFilter;
import com.indus.entity.Role;
import com.indus.service.IUserService;

@RestController
@RequestMapping("/api/user")
public class UserRestControllerApi {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping(value = "/searchUserInfo/{currentPage}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public @ResponseBody PaginationModel searchUserInfo(PaginationModel paginationModel, @PathVariable("currentPage") Integer currentPage,@RequestBody SearchFilter searchFilter) {
		PaginationModel searchUser = null;
		try {
			searchUser = userService.searchUserInfo(paginationModel,currentPage,searchFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchUser;
	}
	
	@PostMapping("/addRole")
	@PreAuthorize("hasRole('ADMIN')")
	public @ResponseBody Response<?> addRole(@RequestBody Role  role) {
		String savedStatus = "";
		try {
			savedStatus = userService.addRole(role);
			if (savedStatus == "RoleAlreadyExist") {
				return new Response<>(String.valueOf(HttpServletResponse.SC_NOT_MODIFIED),
						" ROle already exist please enter valid inpiut");
			} else if (savedStatus == "savedSucessFully") {
				return new Response<>(String.valueOf(HttpServletResponse.SC_OK), "Role created sucessfully");
			} else {
				return new Response<>(String.valueOf(HttpServletResponse.SC_BAD_REQUEST), " something went wrong!! ");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(String.valueOf(HttpServletResponse.SC_EXPECTATION_FAILED), e.toString());
		}
	}
	
	@DeleteMapping(value = "/role/{id}")
	@PreAuthorize("hasRole('MODERATOR')")
	public @ResponseBody Response<?> deleteRoleById(@PathVariable Integer id) {
		String savedStatus = "";
		try {
			savedStatus = userService.deleteRoleById(id);
			if (savedStatus == "roleNameNotExist") {
				return new Response<>(String.valueOf(HttpServletResponse.SC_NOT_FOUND),
						"Master Role  name is not exist please enter valid input");
			} else if (savedStatus == "deleteSucessFully") {
				return new Response<>(String.valueOf(HttpServletResponse.SC_OK), savedStatus);
			} else {
				return new Response<>(String.valueOf(HttpServletResponse.SC_BAD_REQUEST),
						"error occured while Deleting Master Role name");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(String.valueOf(HttpServletResponse.SC_EXPECTATION_FAILED), e.toString());
		}
	}


}
