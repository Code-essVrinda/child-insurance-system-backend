package com.capstone.child.insurance.system.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.child.insurance.system.entity.Policy;
import com.capstone.child.insurance.system.exceptions.PolicyException;
import com.capstone.child.insurance.system.service.PolicyService;

// CORS is handled in SecurityConfig now.
@RestController
@RequestMapping("/api/v1/policies")
public class PolicyController {
	@Autowired
	private PolicyService policyService;

	// get one policy by id
	@GetMapping("/{policyId}")
	public ResponseEntity<Policy> getPolicyById(@PathVariable Integer policyId) throws PolicyException {
		try {
			Policy policy = policyService.getPolicyById(policyId);
			return ResponseEntity.ok(policy);
		} catch (PolicyException e) {
			throw e;
		}
	}

	// activate or deactivate a policy
	@PatchMapping("/{policyId}/status")
	public ResponseEntity<Boolean> updatePolicyStatus(@PathVariable Integer policyId, @RequestParam boolean active)
			throws PolicyException {
		boolean status = this.policyService.updatePolicyStatus(policyId, active);
		return ResponseEntity.ok(status);
	}

	// add a new policy
	@PostMapping
	public ResponseEntity<Policy> addPolicy(@RequestBody Policy newPolicy) throws PolicyException {
		Policy addedPolicy = this.policyService.addPolicy(newPolicy);
		return new ResponseEntity<>(addedPolicy, HttpStatus.CREATED);
	}

	// get all policies
	@GetMapping
	public ResponseEntity<Collection<Policy>> getAllPolicies() throws PolicyException {
		try {
			Collection<Policy> policies = policyService.getAllPolicies();
			return ResponseEntity.ok(policies);
		} catch (PolicyException e) {
			throw e;
		}
	}
}
