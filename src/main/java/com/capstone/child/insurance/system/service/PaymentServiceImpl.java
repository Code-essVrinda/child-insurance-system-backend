package com.capstone.child.insurance.system.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.capstone.child.insurance.system.dao.ChildPolicyEnrollmentRepository;
import com.capstone.child.insurance.system.dao.PaymentRepository;
import com.capstone.child.insurance.system.entity.ChildPolicyEnrollment;
import com.capstone.child.insurance.system.entity.ChildPolicyEnrollment.policyStatus;
import com.capstone.child.insurance.system.entity.Payment;
import com.capstone.child.insurance.system.entity.TransactionDetail;
import com.capstone.child.insurance.system.exceptions.ChildPolicyEnrollmentException;
import com.capstone.child.insurance.system.exceptions.PaymentException;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentServiceImpl implements PaymentService {

	// keys are read from application.properties (which reads them from env vars).
	// this way we do not keep secret keys inside the code.
	@Value("${razorpay.key.id}")
	private String key;

	@Value("${razorpay.key.secret}")
	private String keySecret;

	private static final String currency = "INR";

	@Autowired
	private PaymentRepository paymentRepo;
	@Autowired
	private ChildPolicyEnrollmentRepository childPolicyEnrollmentRepo;

	@Override
	public Payment addPayment(Payment newPayment, Integer EnrollmentId) throws ChildPolicyEnrollmentException {

		Optional<ChildPolicyEnrollment> enrollmentOpt = this.childPolicyEnrollmentRepo.findById(EnrollmentId);
		if (!enrollmentOpt.isPresent()) {
			throw new ChildPolicyEnrollmentException("No enrollment Found");

		}

		newPayment = this.paymentRepo.save(newPayment);
		ChildPolicyEnrollment childPolicyEnrollment = enrollmentOpt.get();

		if (childPolicyEnrollment.getStatus() == policyStatus.PAYMENTPENDING) {

			childPolicyEnrollment.setStatus(policyStatus.Active);
		}

		
		newPayment.setEnrollment(childPolicyEnrollment);
	
		
		this.childPolicyEnrollmentRepo.save(childPolicyEnrollment);
		return newPayment;
	}

	@Override
	public Collection<Payment> getPayments() throws PaymentException {

		Collection<Payment> paymentOpt = this.paymentRepo.findAll();
		
		
		return paymentOpt;

	}

	@Override
	public Collection<Payment> getAllPayments(Integer EnrollmentId)
			throws ChildPolicyEnrollmentException, PaymentException {

		Optional<ChildPolicyEnrollment> enrollmentOpt = this.childPolicyEnrollmentRepo.findById(EnrollmentId);
		if (!enrollmentOpt.isPresent()) {
			throw new ChildPolicyEnrollmentException("No enrollment Found");
		}

		Collection<Payment> payments = paymentRepo.findAllByEnrollment(enrollmentOpt.get());
		if (payments.isEmpty()) {
			throw new PaymentException("No payments found");
		}
		return payments;

	}

	@Override

	public TransactionDetail createTransactionAmount(Integer amount) {
		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("amount", (amount * 100));
			jsonObject.put("currency", currency);

			RazorpayClient razorpayClient = new RazorpayClient(key, keySecret);
			Order order = razorpayClient.orders.create(jsonObject);

			TransactionDetail transactionDetails = prepareTransactionDetails(order);

			return transactionDetails;

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		return null;

	}

	private TransactionDetail prepareTransactionDetails(Order order) {

		String orderId = order.get("id");
		String currency = order.get("currency");
		Integer amount = order.get("amount");
		TransactionDetail transactionDetails = new TransactionDetail(orderId, currency, amount, key);
		return transactionDetails;

	}

}
