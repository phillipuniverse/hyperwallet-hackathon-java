/**
 * 
 */
package com.hyperwallet.dao;

import org.springframework.data.repository.CrudRepository;

import com.hyperwallet.domain.GrantRequest;

import java.util.List;

/**
 * 
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
public interface GrantRequestRepository extends CrudRepository<GrantRequest, Long> {

    List<GrantRequest> findByApproved(boolean status);
    
}