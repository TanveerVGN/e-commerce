package com.e_commerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e_commerce.dto.AddressDto;
import com.e_commerce.module.Address;
import com.e_commerce.module.User;
import com.e_commerce.service.AddressService;
import com.e_commerce.service.UserService;

@Controller
public class AddressController {

	@Autowired
	private AddressService addressService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/address/getAddressPage")
	public String showAddressPage(Model model, Principal principal) 
	{
		AddressDto addressDTO = new AddressDto();
		model.addAttribute("addressDTO", addressDTO);

		String email = principal.getName();
		User user = userService.checkEmail(email);
		model.addAttribute("user", user);
		return "add-address1";
	}
	
	@PostMapping("/address/addAddress")
	public String addAddress(@ModelAttribute("addressDTO") AddressDto addressDTO, Principal principal) 
	{
		addressService.saveAddress(addressDTO, principal);
		return "redirect:/ViewMyProfile";
	}
	
	@GetMapping("/admin/address/listOfAddress")
	public String listOfAddress(Model model, Principal principal) 
	{
//		List of address on the basis of (address primary id), whether user currently login or not that period, actually it return all the saved address list !!
//		List<Address> listOfAddress = addressService.listOfAddress();
//		model.addAttribute("listOfAddress", listOfAddress);
		
		String email = principal.getName();
		User user = userService.checkEmail(email);
		model.addAttribute("user", user);
		Long id = user.getId();
		
// 		List of address on the basis of who currently login that moment, that means user id, By the way it return only authenticated user address !!
		List<Address> listOfAddress = addressService.findAllAddressByuserId(id);
		model.addAttribute("listOfAddress", listOfAddress);
		
		return "manage-address1";
	}
	
	@GetMapping("/address/listOfAddress")
	public String listOfAddresss(Model model, Principal principal) 
	{
//		List of address on the basis of (address primary id), whether user currently login or not that period, actually it return all the saved address list !!
//		List<Address> listOfAddress = addressService.listOfAddress();
//		model.addAttribute("listOfAddress", listOfAddress);
		
		String email = principal.getName();
		User user = userService.checkEmail(email);
		model.addAttribute("user", user);
		Long id = user.getId();
		
// 		List of address on the basis of who currently login that moment, that means user id, By the way it return only authenticated user address !!
		List<Address> listOfAddress = addressService.findAllAddressByuserId(id);
		model.addAttribute("listOfAddress", listOfAddress);
		
		return "Manage-Addresses";
	}
	
	@GetMapping("/address/deleteAddress/{id}")
	public String deleteAddress(@PathVariable(value = "id") Long id) {
		addressService.deleteAddress(id);
		return "redirect:/address/listOfAddress";
	}
	
	@GetMapping("/address/getOrEditAddress/{id}")
	public String getOrEditAddressById(@PathVariable(value = "id") Long id, AddressDto addressDTO, Model model,
			Principal principal) 
	{
		Address oldaddress = addressService.getOrEditAddressById(id);

		addressDTO.setId(oldaddress.getId());
		addressDTO.setFirstName(oldaddress.getFirstName());
		addressDTO.setLastName(oldaddress.getLastName());
		addressDTO.setStreetAddress(oldaddress.getStreetAddress());
		addressDTO.setCity(oldaddress.getCity());
		addressDTO.setState(oldaddress.getState());
		addressDTO.setPincode(oldaddress.getPincode());
		addressDTO.setCountry(oldaddress.getCountry());
		addressDTO.setUserId(oldaddress.getUser().getId());
		addressDTO.setMobile(oldaddress.getMobile());

		String email = principal.getName();
		User user = userService.checkEmail(email);
		model.addAttribute("user", user);

		model.addAttribute("addressDTO", addressDTO);
		return "add-address";

	}
	
	@PostMapping("/address/buyNowPageaddAddress")
	public String BuyNowPageaddAddress(@ModelAttribute("addressDTO") AddressDto addressDTO, Principal principal, RedirectAttributes redirectAttributes) 
	{
		addressService.saveAddress(addressDTO, principal);

		return "redirect:/buyNowDeliveryAddressPage";
	}
}
