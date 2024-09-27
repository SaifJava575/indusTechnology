package com.nit.saif.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.indus.dao.IGenericDao;
import com.indus.entity.ERole;
import com.indus.entity.Role;
import com.indus.service.IUserService;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private IGenericDao iGenericDao;

    @InjectMocks
    private IUserService roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRole_RoleAlreadyExists() {
        Role role = new Role();
        role.setName(ERole.ROLE_ADMIN);

        when(iGenericDao.executeDDLHQL(anyString(), any(Object[].class)))
            .thenReturn(Collections.singletonList(role)); 
        String result = roleService.addRole(role);

        assertEquals("RoleAlreadyExist", result);
        verify(iGenericDao, never()).save(any(Role.class)); 
    }

    @Test
    public void testAddRole_SaveSuccessful() {
        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        when(iGenericDao.executeDDLHQL(anyString(), any(Object[].class)))
            .thenReturn(Collections.emptyList()); 
        String result = roleService.addRole(role);
        assertEquals("savedSucessFully", result);
        verify(iGenericDao).save(role); 
    }

    @Test
    public void testAddRole_ExceptionThrown() {
        Role role = new Role();
        role.setName(ERole.ROLE_MODERATOR);
        when(iGenericDao.executeDDLHQL(anyString(), any(Object[].class)))
            .thenReturn(Collections.emptyList()); 
        doThrow(new RuntimeException("Database error")).when(iGenericDao).save(any(Role.class));
        String result = roleService.addRole(role);
        assertEquals("", result); 
    }
    
    @Test
    public void testDeleteRoleById_RoleExists() {
        Integer roleId = 1;

        when(iGenericDao.executeDDLHQL(anyString(), any(Object[].class)))
            .thenReturn(Collections.singletonList(new Object())); 

        String result = roleService.deleteRoleById(roleId);

        assertEquals("deleteSucessFully", result);
        verify(iGenericDao).executeDMLHQL(anyString(), eq(new Object[]{roleId}));
    }

    @Test
    public void testDeleteRoleById_RoleDoesNotExist() {
        Integer roleId = 2;

        when(iGenericDao.executeDDLHQL(anyString(), any(Object[].class)))
            .thenReturn(Collections.emptyList()); 
        String result = roleService.deleteRoleById(roleId);

        assertEquals("roleNameNotExist", result);
        verify(iGenericDao, never()).executeDMLHQL(anyString(), any(Object[].class)); // Ensure delete was never called
    }
}
