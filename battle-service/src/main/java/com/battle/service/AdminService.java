package com.battle.service;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.AdminRepository;
import com.battle.domain.Admin;
//import com.nanosic.dante.repositories.AdminRepository;
@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
    public Admin update(Admin admin) {
    	admin.setUpdatedAt(new DateTime());
        return adminRepository.save(admin);
    }
    
    public void add(Admin admin){
    	admin.setId(UUID.randomUUID().toString());
    	admin.setCreatedAt(new DateTime());
    	admin.setUpdatedAt(new DateTime());
    	
    	adminRepository.save(admin);
    	
    }
    
    public Iterable<Admin> findAll() {
       return adminRepository.findAll();
        
    }
    public void delete(Admin admin_delete) {
       adminRepository.delete(admin_delete);
        
    }
    public Admin findOne(Long adminId) {
        return adminRepository.findOne(adminId);
    }
    public Admin getAdminByName(String username) {
        return adminRepository.findByUsername(username);
    }
    public Iterable<Admin> getAll() {
        return adminRepository.findAll();
    }

}
