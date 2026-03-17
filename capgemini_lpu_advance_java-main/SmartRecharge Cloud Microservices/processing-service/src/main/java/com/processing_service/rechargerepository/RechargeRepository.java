package com.processing_service.rechargerepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.processing_service.rechargeentity.RechargeRequest;

public interface RechargeRepository extends JpaRepository<RechargeRequest,String> {
}