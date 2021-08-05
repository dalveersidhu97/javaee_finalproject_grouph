package com.banking.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.banking.beans.CategoryOption;
import com.banking.beans.UtilityCategory;

/**
 * 
 * @author Group-H
 * @date 03-07-2021
 * @description UtilityDao provides functionality for getting information
 *              regarding Utility categories from database such as getting all
 *              Categories List, input fields for specific category, info about
 *              category option etc.
 * 
 */

interface UtilityDaoInterface {
	
	List<UtilityCategory> getCategoryList();
	
	CategoryOption getCategoryOption(int optionId);
	
	List<CategoryOption> getCategoryOptionsList(UtilityCategory uc);
	
}

public class UtilityDao implements UtilityDaoInterface{

	private JdbcTemplate template;

	// Get list of UtilityCategory model
	public List<UtilityCategory> getCategoryList() {

		return template.query("select * from TransactionCategories", new RowMapper<UtilityCategory>() {

			public UtilityCategory mapRow(ResultSet rs, int rownumber) throws SQLException {

				UtilityCategory uc = new UtilityCategory();
				uc.setId(rs.getInt("ID"));
				uc.setName(rs.getString("categoryName"));
				return uc;
			}
		});
	}
	
	// Get details of specific transaction field
	public CategoryOption getCategoryOption(int optionId) {
		String sql = "select * from TransactionCategoryOptions where ID=" + optionId;
		return template.query(sql, new ResultSetExtractor<CategoryOption>() {
			public CategoryOption extractData(ResultSet rs) throws SQLException, DataAccessException {

				if (rs.next()) {

					CategoryOption co = new CategoryOption();
					co.setId(rs.getInt("ID"));
					co.setCategoryId(rs.getInt("categoryID"));
					co.setTitle(rs.getString("optionTitle"));
					co.setInputName(rs.getString("inputName"));
					co.setInputType(rs.getString("inputType"));
					return co;
				}
				return null;
			}
		});
	}

	// Get list of Category Option Model
	public List<CategoryOption> getCategoryOptionsList(UtilityCategory uc) {

		String sql = "select * from TransactionCategoryOptions tco inner join TransactionCategories tc on tc.ID = tco.categoryID where categoryName='"
				+ uc.getName() + "';";

		return template.query(sql, new RowMapper<CategoryOption>() {

			public CategoryOption mapRow(ResultSet rs, int rownumber) throws SQLException {

				CategoryOption co = new CategoryOption();
				co.setId(rs.getInt("ID"));
				co.setCategoryId(rs.getInt("categoryID"));
				co.setTitle(rs.getString("optionTitle"));
				co.setInputName(rs.getString("inputName"));
				co.setInputType(rs.getString("inputType"));
				return co;
			}
		});
	}
	
	// getters and setters
	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

}
