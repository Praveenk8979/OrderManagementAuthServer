package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.AddressResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.PageResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.model.Address;
import com.ordermanagement.OrderManagementAndAuthServer.repo.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements  AddressService{

    @Autowired
    private AddressRepo addressRepo;
    @Override
    public Address addAddress(Address address) {
        return addressRepo.save(address);
    }

    @Override
    public List<Address> getUserAddresses(Long userId) {
        return addressRepo.findByUserId(userId);
    }

    @Override
    public Address updateAddress(Address address) {
        Address existing = addressRepo.findById(address.getId())
                .orElseThrow(() -> new RuntimeException("Address not found"));
        existing.setName(address.getName());
        existing.setMobile(address.getMobile());
        existing.setStreet(address.getStreet());
        existing.setCity(address.getCity());
        existing.setState(address.getState());
        existing.setCountry(address.getCountry());
        existing.setPincode(address.getPincode());
        existing.setDefaultAddress(address.isDefaultAddress());

        return addressRepo.save(existing);
    }


    @Override
    public PageResponseDto<AddressResponseDto> getAllAddresses(int page, int size) {
        Page<Address> addressPage =
                addressRepo.findAll(PageRequest.of(page, size));

        List<AddressResponseDto> dtoList = addressPage.getContent()
                .stream()
                .map(this::mapToDto)
                .toList();

        return new PageResponseDto<>(
                dtoList,
                addressPage.getNumber(),
                addressPage.getSize(),
                addressPage.getTotalElements(),
                addressPage.getTotalPages()
        );
    }

    private AddressResponseDto mapToDto(Address a) {
        AddressResponseDto dto = new AddressResponseDto();
        dto.setId(a.getId());
        dto.setUserId(a.getUserId());
        dto.setName(a.getName());
        dto.setMobile(a.getMobile());
        dto.setStreet(a.getStreet());
        dto.setCity(a.getCity());
        dto.setState(a.getState());
        dto.setPincode(a.getPincode());
        dto.setDefaultAddress(a.isDefaultAddress());
        return dto;
    }

    @Override
    public void removeAddress(Long addressId) {
        if(!addressRepo.existsById(addressId)) {
            throw new RuntimeException("Address not found");
        }
        addressRepo.deleteById(addressId);
    }
}
