package com.nit.saif.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.indus.bean.PaginationModel;
import com.indus.bean.SearchFilter;
import com.indus.dao.IGenericDao;
import com.indus.service.IUserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceJunitTest {

	@Mock
	private IGenericDao iGenericDao;

	@InjectMocks
	private IUserService userService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSearchUserInfo_Success() {
		PaginationModel paginationModel = new PaginationModel();
		Integer currentPage = 1;
		SearchFilter searchFilter = new SearchFilter();
		searchFilter.setId(1L);
		searchFilter.setUseName("testUser");
		searchFilter.setEmail("test@example.com");
		searchFilter.setStatus(true);

		PaginationModel expectedPaginationModel = new PaginationModel();

		when(iGenericDao.getPaginationWithSQLQuery(any(), anyInt(), anyString(), any(Object[].class)))
				.thenReturn(expectedPaginationModel);

		PaginationModel result = userService.searchUserInfo(paginationModel, currentPage, searchFilter);

		assertNotNull(result);
		assertEquals(expectedPaginationModel, result);
		verify(iGenericDao).getPaginationWithSQLQuery(paginationModel, currentPage,
				"SELECT * FROM users us where 1=1 and us.id =1 and us.username like '%testUser%' "
						+ "and us.email like '%test@example.com%' and us.active_flag =true",
				new Object[] {});
	}

	
	
	@Test
    public void testSearchUserInfo_NoFilters() {
        PaginationModel paginationModel = new PaginationModel();
        Integer currentPage = 1;
        SearchFilter searchFilter = new SearchFilter();
        
        PaginationModel expectedPaginationModel = new PaginationModel(); 

        when(iGenericDao.getPaginationWithSQLQuery(any(), anyInt(), anyString(), any(Object[].class)))
            .thenReturn(expectedPaginationModel);

        PaginationModel result = userService.searchUserInfo(paginationModel, currentPage, searchFilter);

        assertNotNull(result);
        assertEquals(expectedPaginationModel, result);
        verify(iGenericDao).getPaginationWithSQLQuery(paginationModel, currentPage,
                "SELECT * FROM users us where 1=1 ", new Object[] {});
    }

    @Test
    public void testSearchUserInfo_ExceptionHandling() {
        PaginationModel paginationModel = new PaginationModel();
        Integer currentPage = 1;
        SearchFilter searchFilter = new SearchFilter();

        when(iGenericDao.getPaginationWithSQLQuery(any(), anyInt(), anyString(), any(Object[].class)))
            .thenThrow(new RuntimeException("Database error"));

        PaginationModel result = userService.searchUserInfo(paginationModel, currentPage, searchFilter);

        assertNull(result); 
    }
    
    
}


