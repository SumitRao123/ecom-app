package com.ecom.ecom_application.service;

import com.ecom.ecom_application.dto.UserRequest;
import com.ecom.ecom_application.dto.UserResponse;
import com.ecom.ecom_application.model.User;
import com.ecom.ecom_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecom.ecom_application.model.Address;
import com.ecom.ecom_application.dto.AddressDTO;

import java.util.Optional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public  String  saveUser(UserRequest user){
          return  (userRepository.save(MapUserRequestToUser(user)) != null) ? "User stored  Succesfully" : "Not Saved" ;
    }

    public UserResponse getUserById(Long id){
         Optional<User> user =  userRepository.findById(id);
         return MapUserToUserReponse(user.get());
    }
    public  List<UserResponse>getAllUser(){
        List<User> users = userRepository.findAll();
        return users.stream().map(this::MapUserToUserReponse).collect(Collectors.toList());
    }
    public  boolean updateUser(Long id, UserRequest user){
        return userRepository.findById(id).map(ExistingUser ->{
             UpdateUserFromUseRequest(ExistingUser,user);
             userRepository.save(ExistingUser);
             return true;
        }).orElse(false);
    }

    private void UpdateUserFromUseRequest(User existingUser, UserRequest request) {
        existingUser.setFirstName(
                request.getFirstName() == null ? existingUser.getFirstName() : request.getFirstName()
        );

        existingUser.setLastName(
                request.getLastName() == null ? existingUser.getLastName() : request.getLastName()
        );

        existingUser.setEmail(
                request.getEmail() == null ? existingUser.getEmail() : request.getEmail()
        );

        existingUser.setPhone(
                request.getPhone() == null ? existingUser.getPhone() : request.getPhone()
        );
        if (request.getAddressDTO() != null) {
            if (existingUser.getAddress() == null) {
                existingUser.setAddress(toAddressEntity(request.getAddressDTO()));
            } else {
                Address existingAddress = existingUser.getAddress();
                AddressDTO dto = request.getAddressDTO();

                existingAddress.setStreet(
                        dto.getStreet() == null ? existingAddress.getStreet() : dto.getStreet()
                );
                existingAddress.setCity(
                        dto.getCity() == null ? existingAddress.getCity() : dto.getCity()
                );
                existingAddress.setState(
                        dto.getState() == null ? existingAddress.getState() : dto.getState()
                );
                existingAddress.setCountry(
                        dto.getCountry() == null ? existingAddress.getCountry() : dto.getCountry()
                );
                existingAddress.setPincode(
                        dto.getPincode() == null ? existingAddress.getPincode() : dto.getPincode()
                );
            }
        }

    }


    public User MapUserRequestToUser(UserRequest request){
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(toAddressEntity(request.getAddressDTO()));
        return user;
    }
    private  Address toAddressEntity(AddressDTO dto) {
        if (dto == null) return null;

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setPincode(dto.getPincode());
        return address;
    }

    private UserResponse MapUserToUserReponse(User user){
        UserResponse response = new UserResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setAddressDTO(toAddressDTO(user.getAddress()));
        return  response;
    }
    AddressDTO toAddressDTO(Address address) {
        if (address == null) return null;

        AddressDTO dto = new AddressDTO();
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setCountry(address.getCountry());
        dto.setPincode(address.getPincode());
        return dto;
    }

}
