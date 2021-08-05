package com.banking.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.banking.beans.*;
import com.banking.dao.CustomerDao;
import com.banking.dao.UtilityDao;

/**
 * 
 * @author Group-H
 * @date August 03, 2021
 * @description UtilityService communicates with utilityDao performs all
 *              the operations regarding Categories of transactions by using utilityDao
 */

interface UtilityServiceInterface {
	
	public List<UtilityCategory> getCategoryList();
	public List<CategoryOption> getCategoryOptionsList(UtilityCategory uc);
	public List<CategoryOption> getCategoryOptionsList(String categoryName);
	public CategoryOption getCategoryOption(int optionId) ;
}

public class UtilityService implements UtilityServiceInterface {
	@Autowired
	public UtilityDao utilityDao;

	public List<UtilityCategory> getCategoryList() {
		
		return utilityDao.getCategoryList();
	}

	// get option list from category object
	public List<CategoryOption> getCategoryOptionsList(UtilityCategory uc) {
		return utilityDao.getCategoryOptionsList(uc);
	}
	
	// get option list from category name
	public List<CategoryOption> getCategoryOptionsList(String categoryName) {
		UtilityCategory utilityCategory = new UtilityCategory();
		utilityCategory.setName(categoryName);
		return utilityDao.getCategoryOptionsList(utilityCategory);
	}

	public CategoryOption getCategoryOption(int optionId) {
		return utilityDao.getCategoryOption(optionId);
	}

	

}