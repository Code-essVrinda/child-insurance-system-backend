package com.capstone.child.insurance.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.child.insurance.system.entity.Reminder;
import com.capstone.child.insurance.system.service.ChildPolicyEnrollmentService;
import com.capstone.child.insurance.system.service.ReminderService;


// CORS is handled in SecurityConfig now.
@RestController
@RequestMapping("/api/v1/reminders")
public class ReminderController {

	@Autowired
    ReminderService reminderService;

	@Autowired
	ChildPolicyEnrollmentService enrollmentsService;

    public ReminderController(ReminderService reminderService) {
    	this.reminderService=reminderService;
    }

	// send a reminder email
	@PostMapping("/email")
	public ResponseEntity<String> sendEmail(@RequestBody Reminder emailMessage){
		this.reminderService.sendEmail(emailMessage.getTo(),emailMessage.getSubject(), emailMessage.getMessage());
		return ResponseEntity.ok("Success");
	}
}
