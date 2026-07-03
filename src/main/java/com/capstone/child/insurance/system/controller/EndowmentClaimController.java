package com.capstone.child.insurance.system.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.capstone.child.insurance.system.entity.EndowmentClaim;

import com.capstone.child.insurance.system.exceptions.EndowmentClaimException;

import com.capstone.child.insurance.system.service.EndowmentClaimService;


// CORS is handled in SecurityConfig now.
@RestController
@RequestMapping("/api/v1/endowment-claims")
public class EndowmentClaimController {
	@Autowired
	EndowmentClaimService endowmentClaimService;

	// get one endowment claim by id
	@GetMapping("/{claimId}")
	public EndowmentClaim getEndowmentClaimById(@PathVariable("claimId") Integer id) throws EndowmentClaimException {
		try {
			return this.endowmentClaimService.getEndowmentClaimById(id);
		} catch (EndowmentClaimException e) {
			throw e;
		}
	}

	// add a new endowment claim for a child and policy
	@PostMapping("/children/{childId}/policies/{policyId}")
	public EndowmentClaim addNewEndowmentClaim(@RequestBody EndowmentClaim newEndowmentclaim, @PathVariable("childId") Integer childId,@PathVariable("policyId")Integer policyId) throws EndowmentClaimException {
		try {
			return this.endowmentClaimService.addEndowmentClaim(newEndowmentclaim, childId, policyId);
		} catch (EndowmentClaimException e) {
			throw e;
		}
	}

	// get all endowment claims of one child
	@GetMapping("/children/{childId}")
	public Collection<EndowmentClaim> getUserEndowmentClaims(@PathVariable("childId") Integer id) throws EndowmentClaimException {
		try {
			return this.endowmentClaimService.getChildEndowmentClaims(id);
		} catch (EndowmentClaimException e) {
			throw e;
		}
	}

	// get all endowment claims (for admin)
	@GetMapping
	public Collection<EndowmentClaim> getAllEndowmentClaims() throws EndowmentClaimException {
		try {
			return this.endowmentClaimService.getAllEndowmentClaimsForAdmin();
		} catch (EndowmentClaimException e) {
			throw e;
		}
	}
}
