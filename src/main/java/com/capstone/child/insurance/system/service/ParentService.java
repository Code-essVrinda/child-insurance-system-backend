package com.capstone.child.insurance.system.service;

import java.util.Collection;


import com.capstone.child.insurance.system.entity.Parent;
import com.capstone.child.insurance.system.exceptions.ParentException;


public interface ParentService {
	
	Parent addParent(Parent newParent) throws ParentException;
	Parent getParentById(Integer id) throws ParentException;
	Parent updateParent(Parent newParent) throws ParentException;
	
	boolean updateParentStatus(Integer parentId, boolean active) throws ParentException;
	
	Collection<Parent> getAllParents(); //For Admin

	Parent login(Parent parent) throws ParentException;
	
	
	

}
