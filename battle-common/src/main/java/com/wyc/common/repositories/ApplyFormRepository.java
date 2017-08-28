package com.wyc.common.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.wyc.common.domain.ApplyForm;

public interface ApplyFormRepository extends CrudRepository<ApplyForm, String>{

	List<ApplyForm> findAllByOpenidOrderByApplyTimeDesc(String openid);

	Page<ApplyForm> findAllByTypeAndStatusOrderByApplyTimeDesc(int type, int status, Pageable pageable);

	List<ApplyForm> findAllByOpenidAndTypeAndStatus(String openid, int type, int status);

}
