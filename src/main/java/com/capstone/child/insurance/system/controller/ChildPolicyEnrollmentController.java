package com.capstone.child.insurance.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.capstone.child.insurance.system.entity.ChildPolicyEnrollment;
import com.capstone.child.insurance.system.exceptions.ChildPolicyEnrollmentException;
import com.capstone.child.insurance.system.service.ChildPolicyEnrollmentService;
import java.util.Collection;

// CORS is handled in SecurityConfig now.
@RestController
@RequestMapping("/api/v1/enrollments")
public class ChildPolicyEnrollmentController {
	@Autowired
	private ChildPolicyEnrollmentService childPolicyEnrollmentService;

	// get one enrollment by id
	@GetMapping("/{enrollmentId}")
	public ChildPolicyEnrollment getEnrollment(@PathVariable Integer enrollmentId)
			throws ChildPolicyEnrollmentException {
		try {
			return childPolicyEnrollmentService.getEnrollmentById(enrollmentId);
		} catch (ChildPolicyEnrollmentException e) {
			throw e;
		}
	}

	// get all enrollments of a child
	@GetMapping("/children/{childId}")
	public Collection<ChildPolicyEnrollment> getAllChildPolicyEnrollments(@PathVariable Integer childId)
			throws ChildPolicyEnrollmentException {
		try {
			return childPolicyEnrollmentService.getAllChildPolicyEnrollments(childId);
		} catch (ChildPolicyEnrollmentException e) {
			throw e;
		}
	}

	// get all enrollments of a policy
	@GetMapping("/policies/{policyId}")
	public Collection<ChildPolicyEnrollment> getAllEnrollmentsByPolicyId(@PathVariable Integer policyId)
			throws ChildPolicyEnrollmentException {
		try {
			return childPolicyEnrollmentService.getAllEnrollmentsByPolicy(policyId);
		} catch (ChildPolicyEnrollmentException e) {
			throw e;
		}
	}

	// create a new enrollment for a child in a policy
	@PostMapping("/children/{childId}/policies/{policyId}")
	public ChildPolicyEnrollment createChildPolicyEnrollment(@PathVariable("childId") Integer childId,@PathVariable("policyId") Integer policyId,
			@RequestBody ChildPolicyEnrollment enrollment) throws ChildPolicyEnrollmentException {
		try {
			return this.childPolicyEnrollmentService.addEnrollment(enrollment, childId, policyId);
		} catch (ChildPolicyEnrollmentException e) {
			throw e;
		}
	}

	// change the status of an enrollment
	@PatchMapping("/{enrollmentId}")
	public ChildPolicyEnrollment updateStatusOfEnrollment(@PathVariable Integer enrollmentId,@RequestBody ChildPolicyEnrollment childPolicyEnrollment)
			throws ChildPolicyEnrollmentException {
		try {
			return this.childPolicyEnrollmentService.updateStatusOfEnrollment(enrollmentId, childPolicyEnrollment);
		} catch (ChildPolicyEnrollmentException e) {
			throw e;
		}
	}
}
