package com.battle.dao;


import org.springframework.data.repository.CrudRepository;

import com.battle.domain.Admin;




public interface AdminRepository extends CrudRepository<Admin, Long>{

    public Admin findByUsername(String username);

}
