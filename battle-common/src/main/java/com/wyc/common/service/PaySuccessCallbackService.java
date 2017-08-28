package com.wyc.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.repositories.PaySuccessCallbackRepository;

@Service
public class PaySuccessCallbackService {

	@Autowired
	private PaySuccessCallbackRepository paySuccessCallbackRepository;
}
