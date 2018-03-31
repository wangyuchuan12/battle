package com.battle.manager.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.manager.dao.AdminRepository;
import com.battle.manager.domain.Admin;
//import com.nanosic.dante.repositories.AdminRepository;
@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
        
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
