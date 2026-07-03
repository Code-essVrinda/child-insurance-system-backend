package com.capstone.child.insurance.system.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstone.child.insurance.system.dao.ParentRepository;
import com.capstone.child.insurance.system.entity.Parent;
import com.capstone.child.insurance.system.exceptions.ParentException;


@Service
public class ParentServiceImpl implements ParentService{
	@Autowired
	ParentRepository parentRepository;

	// used to hash the password (BCrypt). the bean is in SecurityConfig
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Parent addParent(Parent newParent) throws ParentException {

		// hash the password before saving so we never store plain text
		newParent.setPassword(this.passwordEncoder.encode(newParent.getPassword()));
		return this.parentRepository.save(newParent);

	}

	@Override	
	public Parent getParentById(Integer id) throws ParentException {

		Optional<Parent> parentOpt = this.parentRepository.findById(id);
		if (!parentOpt.isPresent())
			throw new ParentException("Parent not found for id:" + id);
		Parent parent = parentOpt.get();
		// use Boolean.TRUE.equals so it does not crash when accountActive is null
		if(!Boolean.TRUE.equals(parent.getAccountActive())) {
		throw new ParentException("User Account is not active");}


		return parentOpt.get();
	}

	@Override
	public Parent updateParent(Parent newParent) throws ParentException {
		Optional<Parent> parentOpt = this.parentRepository.findById(newParent.getParentId());
		if(!parentOpt.isPresent())
			throw new ParentException("Parent does not exist");
		return this.parentRepository.save(newParent);
	}

	
	@Override
	public Collection<Parent> getAllParents() {
		
		return this.parentRepository.findAll();
	}

	
	@Override
	public boolean updateParentStatus(Integer parentId, boolean active) throws ParentException {
		Optional<Parent> parentOpt = parentRepository.findById(parentId);
		
		if(!parentOpt.isPresent())
		{
			throw  new ParentException("Parent with ID " + parentId + "not found.");
		}
		
		Parent parent = parentOpt.get();
		parent.setAccountActive(active);
		parentRepository.save(parent);
		return active;
	}

	
	@Override
	public Parent login(Parent parent) throws ParentException {
		Optional<Parent> findParent = Optional.ofNullable(this.parentRepository.findByEmail(parent.getEmail()));
		if(!findParent.isPresent()) {
			throw new ParentException("User Not Found");
		}

		// the stored password is hashed, so we match the raw one against the hash
		if(this.passwordEncoder.matches(parent.getPassword(), findParent.get().getPassword())) {
			return findParent.get();

		}
		else {
			throw new ParentException("Incorrect Password");
		}

	}
	

}
