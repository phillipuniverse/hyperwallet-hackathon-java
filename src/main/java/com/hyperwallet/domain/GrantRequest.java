/**
 * 
 */
package com.hyperwallet.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
@Entity
@Data
public class GrantRequest {

    public GrantRequest() {
        
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;
    
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public String zip;
    public String phone;
    public String email;
    public String disasterAddress;
    public String requestReason;
    
    public String congregation;
    public String congregationCity;
    public String congregationPastor;
    public int amountRequested;
    
    public String emergencyNeed;
    public String otherAttributes;
    
    public String preparedBy;
    
    public String approvedBy;
    
    public boolean approved;
    
    
    public String token;
    
}
