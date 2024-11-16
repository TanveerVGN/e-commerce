package com.e_commerce.serviceImpl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.dto.CustomerOrderDTO;
import com.e_commerce.module.Address;
import com.e_commerce.module.CustomerOrder;
import com.e_commerce.module.Product;
import com.e_commerce.module.User;
import com.e_commerce.repository.CustomerOrderRepository;
import com.e_commerce.service.AddressService;
import com.e_commerce.service.CustomerOrderService;
import com.e_commerce.service.UserService;

import UniversalPackage.UniversalData;
import UniversalPackage.UniversalData_3;
@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

	@Autowired
	private CustomerOrderRepository customerOrderRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;

	@Override
	public CustomerOrder saveBuyNowCustomerOrder(CustomerOrderDTO customerOrderDTO, Principal principal) {
		// TODO Auto-generated method stub
		CustomerOrder customerOrder = new CustomerOrder();

		// Set User here!
		if (principal != null) {
			String email = principal.getName();
			User user = userService.checkEmail(email);
			User loggedInUser = userService.getOrEditUserById(user.getId());
			customerOrder.setUser(loggedInUser);
		}

		// Set Selected Address here!
		try {
			Address loggedInUserAddress = addressService.getOrEditAddressById(customerOrderDTO.getAddressId());
			customerOrder.setAddress(loggedInUserAddress);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("You need to select address!!");
		}

		// OR
		// customerOrder.setAddress(addressService.getOrEditAddressById(customerOrderDTO.getAddressId()));

		// Set Product here!
		List<Product> buyNow = UniversalData_3.buyNow;
		for (Product product : buyNow) {
			customerOrder.setProduct(product);
		}

		// Set Date here!
		customerOrder.setCreatedAt(LocalDateTime.now());

		/*
		 * // Set customer order unique id here in toString() Format! UUID uniqID=
		 * UUID.randomUUID(); String randomId = uniqID.toString();
		 * customerOrder.setCustomerOrderID(randomId);
		 */

		// Set customer order unique id here in Long Format!
		long uniqID = UUID.randomUUID().getMostSignificantBits();
		long uniqeIdGet = Math.abs(uniqID);
		customerOrder.setCustomerOrderID(uniqeIdGet);

		// Save into database!
		CustomerOrder product_Saved = customerOrderRepository.save(customerOrder);

		return product_Saved;
	}

	@Override
	public List<CustomerOrder> saveCartItemsCustomerOrder(CustomerOrderDTO customerOrderDTO, Principal principal) {
		// TODO Auto-generated method stub
		CustomerOrder customerOrder = new CustomerOrder();

		// Set User here!
		if (principal != null) {
			String email = principal.getName();
			User user = userService.checkEmail(email);
			User loggedInUser = userService.getOrEditUserById(user.getId());
			customerOrder.setUser(loggedInUser);
		}

		// Set Selected Address here!
		try {
			Address loggedInUserAddress = addressService.getOrEditAddressById(customerOrderDTO.getAddressId());
			customerOrder.setAddress(loggedInUserAddress);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("You need to select address!!");
		}

		// OR
		// customerOrder.setAddress(addressService.getOrEditAddressById(customerOrderDTO.getAddressId()));

		/*
		 * // Set BuyNow Product here! List<Product> buyNow = UniversalData_3.buyNow;
		 * for(Product product : buyNow) { Long id = product.getId(); Product
		 * productById = productService.getOrEditProductById(id);
		 * customerOrder.setProduct(productById); }
		 */

		// Set Cart Product here!
		List<Product> cart = UniversalData.cart;

		for (Product product : cart) {
			customerOrder.setProduct(product);
		}

		// Set Date here!
		customerOrder.setCreatedAt(LocalDateTime.now());

		/*
		 * // Set customer order unique id here in toString() Format! UUID uniqID=
		 * UUID.randomUUID(); String randomId = uniqID.toString();
		 * customerOrder.setCustomerOrderID(randomId);
		 */

		// Set customer order unique id here in Long Format!
		long uniqID = UUID.randomUUID().getMostSignificantBits();
		long uniqeIdGet = Math.abs(uniqID);
		customerOrder.setCustomerOrderID(uniqeIdGet);

		// Save into database!
//		CustomerOrder product_Saved = customerOrderRepository.save(customerOrder);

		List<CustomerOrder> saveAll = customerOrderRepository.saveAll(List.of(customerOrder));

		return saveAll;
	}

	@Override
	public List<CustomerOrder> listOfCustomerOrder() {
		// TODO Auto-generated method stub
		return customerOrderRepository.findAll();
	}

	@Override
	public List<CustomerOrder> listOfCustomerOrderById(Long id) {
		// TODO Auto-generated method stub
		List<CustomerOrder> findAllCustomerOrderById = customerOrderRepository.findAllByuserId(id);
		return findAllCustomerOrderById;
	}

	@Override
	public CustomerOrder getParticularCustomerOrderById(Long id) {
		// TODO Auto-generated method stub
		Optional<CustomerOrder> findById = customerOrderRepository.findById(id);
		CustomerOrder customerOrder = findById.get();
		if (customerOrder == null) {
			throw new NoSuchElementException("No value present with this product: " + customerOrder);
		} else {
			return customerOrder;
		}

	}
}
