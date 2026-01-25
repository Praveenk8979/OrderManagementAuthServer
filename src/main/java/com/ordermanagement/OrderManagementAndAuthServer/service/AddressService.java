package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.AddressResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.PageResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.model.Address;

import java.util.List;

public interface AddressService {
    Address addAddress(Address address);
    List<Address> getUserAddresses(Long userId);
    Address updateAddress(Address address);
    PageResponseDto<AddressResponseDto> getAllAddresses(int page, int size);
    void removeAddress(Long addressId);
}
