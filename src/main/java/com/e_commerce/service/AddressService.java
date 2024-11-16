package com.e_commerce.service;

import java.security.Principal;
import java.util.List;

import com.e_commerce.dto.AddressDto;
import com.e_commerce.module.Address;


public interface AddressService {
public Address saveAddress(AddressDto addressDTO, Principal principal);
	
	public List<Address> listOfAddress();

	public String deleteAddress(Long id);
	
	public Address getOrEditAddressById(Long id);
	
	public List<Address> findAllAddressByuserId(Long id);
}
