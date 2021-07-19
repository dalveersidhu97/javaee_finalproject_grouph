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
 * @date 12 July, 2021
 * @description This class interact with userDao bean to provide a layer of
 *              abstraction.
 * 
 */

interface UtilityServiceInterface {
	
	public List<UtilityCategory> getCategoryList();
	public List<CategoryOption> getCategoryOptionsList(UtilityCategory uc);
	public List<CategoryOption> getCategoryOptionsList(String categoryName);
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

	

}