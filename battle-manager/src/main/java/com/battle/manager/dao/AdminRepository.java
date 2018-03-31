package com.battle.manager.dao;


import org.springframework.data.repository.CrudRepository;

import com.battle.manager.domain.Admin;


public interface AdminRepository extends CrudRepository<Admin, Long>{

    public Admin findByUsername(String username);

}
