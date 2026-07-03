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
import com.capstone.child.insurance.system.entity.EducationClaim;

import com.capstone.child.insurance.system.exceptions.EducationClaimException;

import com.capstone.child.insurance.system.service.EducationClaimService;


// CORS is handled in SecurityConfig now.
@RestController
@RequestMapping("/api/v1/education-claims")
public class EducationClaimController {

	@Autowired
	EducationClaimService educationClaimService;

	// get one education claim by id
	@GetMapping("/{claimId}")
	public EducationClaim getEducationClaimById(@PathVariable("claimId") Integer id) throws EducationClaimException {
		try {
			return this.educationClaimService.getEducationClaimById(id);
		} catch (EducationClaimException e) {
			throw e;
		}
	}

	// add a new education claim for a child and policy
	@PostMapping("/children/{childId}/policies/{policyId}")
	public EducationClaim addNewEducationClaim(@RequestBody EducationClaim neweducationclaim,  @PathVariable("childId") Integer childId,@PathVariable("policyId")Integer policyId) throws EducationClaimException {
		try {
			return this.educationClaimService.addEducationClaim(neweducationclaim, childId, policyId);
		} catch (EducationClaimException e) {
			throw e;
		}
	}

	// update an education claim
	@PutMapping("/children/{childId}/policies/{policyId}")
	public EducationClaim updateEducationClaimById(@RequestBody EducationClaim updateClaim,  @PathVariable("childId") Integer childId,@PathVariable("policyId")Integer policyId) throws EducationClaimException {
		try {
			return this.educationClaimService.updateEducationClaimById(updateClaim, childId, policyId);
		} catch (EducationClaimException e) {
			throw e;
		}
	}

	// approve or change the status of an education claim
	@PatchMapping("/{claimId}/approval")
	public EducationClaim updateEducationClaimStatus(@RequestBody EducationClaim approvalStatus, @PathVariable Integer claimId) throws EducationClaimException{
		try {
			return this.educationClaimService.updateEducationClaimStatus(claimId, approvalStatus);
		}
		catch(EducationClaimException e) {
			throw e;
		}
	}

	// get all education claims of one child
	@GetMapping("/children/{childId}")
	public Collection<EducationClaim> getUserEducationClaims(@PathVariable("childId") Integer id) throws EducationClaimException {
		try {
			return this.educationClaimService.getChildEducationClaims(id);
		} catch (EducationClaimException e) {
			throw e;
		}
	}

	// get all education claims (for admin)
	@GetMapping
	public Collection<EducationClaim> getAllEducationClaims() throws EducationClaimException {
		try {
			return this.educationClaimService.getAllEducationClaimsForAdmin();
		} catch (EducationClaimException e) {
			throw e;
		}
	}

	// get all pending education claims (for admin)
	@GetMapping("/pending")
	@ResponseStatus(HttpStatus.OK)
	public Collection<EducationClaim> getAllPendingEducationClaims() throws EducationClaimException {
		try {
			return this.educationClaimService.getAllPendingEducationClaimsForAdmin();
		} catch (EducationClaimException e) {
			throw e;
		}
	}

	// get the child that an education claim belongs to
	@GetMapping("/{claimId}/child")
	@ResponseStatus(HttpStatus.OK)
	public Child getChildByEducationClaim(@PathVariable("claimId") Integer claimId) throws EducationClaimException {
		try {
			return this.educationClaimService.getChildByEducationClaim(claimId);
		} catch (EducationClaimException e) {
			throw e;
		}
	}
}
