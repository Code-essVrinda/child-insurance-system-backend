package com.capstone.child.insurance.system.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstone.child.insurance.system.dao.AdminRepository;
import com.capstone.child.insurance.system.entity.Admin;

import com.capstone.child.insurance.system.exceptions.AdminException;




@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminRepository adminRepository;

	// used to hash the admin password (BCrypt). the bean is in SecurityConfig
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Admin addAdmin(Admin newAdmin) throws AdminException {

		// hash the password before saving so we never store plain text
		newAdmin.setPassword(this.passwordEncoder.encode(newAdmin.getPassword()));
		return this.adminRepository.save(newAdmin);

	}


	
	@Override
	public Admin getAdminById(Integer adminId) throws AdminException {
		
		Optional<Admin> adminOpt = this.adminRepository.findById(adminId);
		if (!adminOpt.isPresent())
			throw new AdminException("Admin not found for id:" + adminId);
		
		return adminOpt.get();
	}



	@Override
	public Admin adminlogin(Admin newAdmin) throws AdminException {
		
		Optional<Admin> findAdmin = Optional.ofNullable(this.adminRepository.findByEmail(newAdmin.getEmail()));
		if(!findAdmin.isPresent()) {
			throw new AdminException("Admin Not Found");
		}
		
		// the stored password is hashed, so we match the raw one against the hash
		if(this.passwordEncoder.matches(newAdmin.getPassword(), findAdmin.get().getPassword())) {
			return findAdmin.get();

		}
		else {
			throw new AdminException("Incorrect Password");
		}
		
		
	
	}

}
