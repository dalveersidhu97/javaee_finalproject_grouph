package com.banking.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.banking.beans.CategoryOption;
import com.banking.beans.UtilityCategory;


public class UtilityDao{

	private JdbcTemplate template;
	
	// get category list
	
	public List<UtilityCategory> getCategoryList(){
		
		 return template.query("select * from TransactionCategories",new RowMapper<UtilityCategory>(){  
		    
			 public UtilityCategory mapRow(ResultSet rs, int rownumber) throws SQLException {  
		    	
		    	UtilityCategory uc = new UtilityCategory();  
		    	uc.setId(rs.getInt("ID"));  
		    	uc.setName(rs.getString("categoryName"));
		        return uc;  
		    }  
		 });
	}
	
	// get category options list
	
	public List<CategoryOption> getCategoryOptionsList(UtilityCategory uc){
		
		String sql = "select * from TransactionCategoryOptions tco inner join TransactionCategories tc on tc.ID = tco.categoryID where categoryName='"+uc.getName()+"';";
		
		 return template.query(sql, new RowMapper<CategoryOption>(){  
			    
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
	
	// add category
	
	
	
	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	

	
}
