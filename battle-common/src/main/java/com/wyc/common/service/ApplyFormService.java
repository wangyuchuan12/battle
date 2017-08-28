package com.wyc.common.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.ApplyForm;
import com.wyc.common.repositories.ApplyFormRepository;

@Service
public class ApplyFormService {
	
	@Autowired
	private ApplyFormRepository applyFormRepository;

	public ApplyForm add(ApplyForm applyForm) {
		
		applyForm.setId(UUID.randomUUID().toString());
		applyForm.setUpdateAt(new DateTime());
		applyForm.setCreateAt(new DateTime());
		return applyFormRepository.save(applyForm);
		
	}

	public List<ApplyForm> findAllByOpenidOrderByApplyTimeDesc(String openid) {
		return applyFormRepository.findAllByOpenidOrderByApplyTimeDesc(openid);
	}

	public ApplyForm update(ApplyForm applyForm) {
		applyForm.setUpdateAt(new DateTime());
		
		return applyFormRepository.save(applyForm);
		
	}

	public Page<ApplyForm> findAllByTypeAndStatusOrderByApplyTimeDesc(int type,
			int status,Pageable pageable) {
		return applyFormRepository.findAllByTypeAndStatusOrderByApplyTimeDesc(type,status,pageable);
	}

	public List<ApplyForm> findAllByOpenidAndTypeAndStatus(String openid, int type,
			int status) {
		return applyFormRepository.findAllByOpenidAndTypeAndStatus(openid,type,status);
	}
	
}
