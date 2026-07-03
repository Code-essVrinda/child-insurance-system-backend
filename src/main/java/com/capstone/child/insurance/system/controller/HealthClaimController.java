package com.capstone.child.insurance.system.controller;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.child.insurance.system.entity.Child;
import com.capstone.child.insurance.system.entity.ChildPolicyEnrollment;
import com.capstone.child.insurance.system.entity.HealthClaim;
import com.capstone.child.insurance.system.exceptions.HealthClaimException;
import com.capstone.child.insurance.system.service.HealthClaimService;


// CORS is handled in SecurityConfig now.
@RestController
@RequestMapping("/api/v1/health-claims")
public class HealthClaimController {

	@Autowired
	HealthClaimService healthClaimservice;

	// get one health claim by id
	@GetMapping("/{claimId}")
	@ResponseStatus(HttpStatus.OK)
	public HealthClaim getHealthClaimById(@PathVariable("claimId") Integer id) throws HealthClaimException {
		try {
			return this.healthClaimservice.getHealthClaimById(id);
		} catch (HealthClaimException e) {
			throw e;
		}
	}

	// add a new health claim for a child and policy
	@PostMapping("/children/{childId}/policies/{policyId}")
	@ResponseStatus(HttpStatus.OK)
	public HealthClaim addNewHealthClaim(@RequestBody HealthClaim newhealthclaim, @PathVariable("childId") Integer childId,@PathVariable("policyId")Integer policyId) throws HealthClaimException {
		try {
			return this.healthClaimservice.addHealthClaim(newhealthclaim, childId, policyId);
		} catch (HealthClaimException e) {
			throw e;
		}
	}

	// update a health claim
	@PutMapping("/children/{childId}/policies/{policyId}")
	@ResponseStatus(HttpStatus.OK)
	public HealthClaim updateHealthClaimById(@RequestBody HealthClaim updateClaim, @PathVariable("childId") Integer childId,@PathVariable("policyId")Integer policyId) throws HealthClaimException {
		try {
			return this.healthClaimservice.updateHealthClaimById(updateClaim, childId, policyId);
		} catch (HealthClaimException e) {
			throw e;
		}
	}

	// approve or change the status of a health claim
	@PatchMapping("/{claimId}/approval")
	@ResponseStatus(HttpStatus.OK)
	public HealthClaim updateHealthClaimStatus(@RequestBody HealthClaim approvalStatus, @PathVariable Integer claimId) throws HealthClaimException{
		try {
			return this.healthClaimservice.updateHealthClaimStatus(claimId, approvalStatus);
		}
		catch(HealthClaimException e) {
			throw e;
		}
	}

	// get all health claims of one child
	@GetMapping("/children/{childId}")
	@ResponseStatus(HttpStatus.OK)
	public Collection<HealthClaim> getUserHealthClaims(@PathVariable("childId") Integer id) throws HealthClaimException {
		try {
			return this.healthClaimservice.getChildHealthClaims(id);
		} catch (HealthClaimException e) {
			throw e;
		}
	}

	// get all health claims (for admin)
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Collection<HealthClaim> getAllHealthClaims() throws HealthClaimException {
		try {
			return this.healthClaimservice.getAllHealthClaimsForAdmin();
		} catch (HealthClaimException e) {
			throw e;
		}
	}

	// get all pending health claims (for admin)
	@GetMapping("/pending")
	@ResponseStatus(HttpStatus.OK)
	public Collection<HealthClaim> getAllPendingHealthClaims() throws HealthClaimException {
		try {
			return this.healthClaimservice.getAllPendingHealthClaimsForAdmin();
		} catch (HealthClaimException e) {
			throw e;
		}
	}

	// get the child that a health claim belongs to
	@GetMapping("/{claimId}/child")
	@ResponseStatus(HttpStatus.OK)
	public Child getChildByHealthClaim(@PathVariable("claimId") Integer claimId) throws HealthClaimException {
		try {
			return this.healthClaimservice.getChildByhealthClaim(claimId);
		} catch (HealthClaimException e) {
			throw e;
		}
	}

	// check if the child has an active enrollment in the policy
	@GetMapping("/enrollments/children/{childId}/policies/{policyId}")
	@ResponseStatus(HttpStatus.OK)
	public ChildPolicyEnrollment checkActiveEnrollments(@PathVariable("childId") Integer childId,@PathVariable("policyId")Integer policyId) throws HealthClaimException {
		try {
			return this.healthClaimservice.checkActiveEnrollments(childId, policyId);
		} catch (HealthClaimException e) {
			throw e;
		}
	}
}
