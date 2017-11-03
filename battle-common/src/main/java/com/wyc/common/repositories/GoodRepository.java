package com.wyc.common.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wyc.common.domain.Good;

public interface GoodRepository extends CrudRepository<Good, String>{

	List<Good> findAllByStatusAndTypeOrderByIndexAsc(Integer status, Integer type);

	List<Good> findAllByStatusOrderByIndexAsc(Integer status);

}
