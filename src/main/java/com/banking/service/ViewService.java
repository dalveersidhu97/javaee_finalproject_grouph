package com.banking.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.ui.Model;

public class ViewService {
	
	public String layoutContainer;
	private List<String> viewList = new ArrayList<String>();

	public String getLayoutContainer() {
		return layoutContainer;
	}

	public void setLayoutContainer(String layoutContainer) {
		this.layoutContainer = layoutContainer;
	}
	
	public String views(List<String> views) {
		clear();
		viewList.addAll(views);
		return layoutContainer;
	}
	
	public String view(String view) {
		clear();
		viewList.add(view);
		return layoutContainer;
	}
	
	public String addViews(List views) {
		viewList.addAll(views);
		return layoutContainer;
	}
	
	public ViewService clear() {
		viewList.clear();
		return this;
	}
	
	public ViewService model(Model m) {
		m.addAttribute("viewList", viewList);
		return this;
	}
	
}
