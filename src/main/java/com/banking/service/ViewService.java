package com.banking.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.ui.Model;

/**
 * 
 * @author Group-H
 * @date August 03, 2021
 * @description ViewService makes us able to use one layout file for all the
 *              pages
 */

public class ViewService {

	public String layoutContainer;

	// viewList will be passed to ModelAttribute object which then will be used by
	// layout file to include all the view in viewList
	private List<String> viewList = new ArrayList<String>();

	// add list of views to viewList
	public String views(List<String> views) {
		clear();
		ListIterator<String> i = views.listIterator();
		// add .jsp after each view of views
		while (i.hasNext()) {
			viewList.add(i.next() + ".jsp");
		}
		return layoutContainer;
	}

	// add single view to viewList
	public String view(String view) {
		clear();
		// add .jsp after view
		viewList.add(view + ".jsp");
		return layoutContainer;
	}

	// as the class is singleton we have to clear viewList everytiime
	public ViewService clear() {
		viewList.clear();
		return this;
	}

	// add list of views to model attribute which then looped by layout jsp file to
	// include all the views in viewList
	public ViewService model(Model m) {
		m.addAttribute("viewList", viewList);
		return this;
	}

	// getters and setters
	public String getLayoutContainer() {
		return layoutContainer;
	}

	public void setLayoutContainer(String layoutContainer) {
		this.layoutContainer = layoutContainer;
	}

}
