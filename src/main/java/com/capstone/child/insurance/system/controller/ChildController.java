package com.capstone.child.insurance.system.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.child.insurance.system.entity.Child;
import com.capstone.child.insurance.system.exceptions.ChildException;
import com.capstone.child.insurance.system.service.ChildService;

// children always belong to a parent, so the path is nested under parents.
// CORS is handled in SecurityConfig now.
@RestController
@RequestMapping("/api/v1/parents/{parentId}/children")
public class ChildController {

	@Autowired
	ChildService childService;

	// get one child by id
	@GetMapping("/{childId}")
	public Child getChildByChildId(@PathVariable("childId") Integer childId) throws ChildException {
		return this.childService.getChildByChildId(childId);
	}

	// get all children of a parent
	@GetMapping
	public ResponseEntity<Collection<Child>> getAllChildrenByParentId(@PathVariable Integer parentId) {
		Collection<Child> children = childService.getAllChildrenByParentId(parentId);
		return ResponseEntity.ok(children);
	}

	// add a child to a parent
	@PostMapping
	public ResponseEntity<Child> addChildByParentId(@PathVariable Integer parentId , @RequestBody Child newChild) throws ChildException {
		Child addedChild = childService.addChildByParentId(parentId , newChild);
		return ResponseEntity.status(HttpStatus.CREATED).body(addedChild);
	}

	// update a child
	@PutMapping("/{childId}")
	public ResponseEntity<Child> updateChildByChildId(@PathVariable Integer parentId, @PathVariable Integer childId, @RequestBody Child child) throws ChildException {
		// take the id from the url so the body and url can not disagree
		child.setChildId(childId);
		Child updateChild =  childService.updateChildByChildId(child,parentId);
		return ResponseEntity.ok(updateChild);
	}

	// activate or deactivate a child
	@PatchMapping("/{childId}/status")
	public ResponseEntity<Boolean> updateChildStatus(@PathVariable Integer childId , @RequestParam boolean active) throws ChildException {
	    boolean status = childService.updateChildStatus(childId , active);
	    return ResponseEntity.ok(status);
	}
}
