package com.wyc.common.repositories;
import org.springframework.data.repository.CrudRepository;

import com.wyc.common.domain.MyResource;

public interface ResourceRepository extends CrudRepository<MyResource, String>{


}
