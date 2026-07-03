package com.capstone.child.insurance.system.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.capstone.child.insurance.system.entity.Payment;
import com.capstone.child.insurance.system.entity.TransactionDetail;
import com.capstone.child.insurance.system.exceptions.ChildPolicyEnrollmentException;
import com.capstone.child.insurance.system.exceptions.PaymentException;
import com.capstone.child.insurance.system.service.PaymentService;

// CORS is handled in SecurityConfig now.
@RequestMapping("/api/v1/payments")
@RestController
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	// add a payment for an enrollment
	@PostMapping("/enrollments/{enrollmentId}")
	public Payment addPayment(@RequestBody Payment newPayment,@PathVariable("enrollmentId") Integer enrollmentId) throws ChildPolicyEnrollmentException{
		return this.paymentService.addPayment(newPayment, enrollmentId);
	}

	// get all payments
	@GetMapping
	public Collection<Payment> getPayments() throws PaymentException {
		return this.paymentService.getPayments();
	}

	// get all payments of one enrollment
	@GetMapping("/enrollments/{enrollmentId}")
	public Collection<Payment> getAllPayments(@PathVariable("enrollmentId") Integer id) throws PaymentException, ChildPolicyEnrollmentException{
		return this.paymentService.getAllPayments(id);
	}

	// create a razorpay transaction for the given amount
	@PostMapping("/transactions/{amount}")
    public TransactionDetail createTransaction(@PathVariable(name="amount") Integer amount) {
		return paymentService.createTransactionAmount(amount);
	}
}
