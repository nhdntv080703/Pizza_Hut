package com.example.projectbase.converter;

import com.example.projectbase.domain.dto.common.UserDetailImp;
import com.example.projectbase.domain.dto.request.UserCreateDTO;
import com.example.projectbase.domain.dto.request.UserRequestDTO;
import com.example.projectbase.domain.dto.response.UserDto;
import com.example.projectbase.domain.entity.RoleEntity;
import com.example.projectbase.domain.entity.UserEntity;
import com.example.projectbase.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    @Autowired
    ModelMapper modelMapper;
//    @Autowired
//    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    hàm conver đang sai, cần sửa
    public UserEntity converDTOToEntity(UserCreateDTO userDTO, UserEntity userEntity){
        String id = userEntity.getId();
        userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        RoleEntity role = roleRepository.findByRoleName("ROLE_USER");
//        userEntity.setRoleEntity(role);
        userEntity.setId(id);
        return userEntity;
    }

    public UserEntity converDTOToEntity(UserRequestDTO userDTO){
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        RoleEntity role = roleRepository.findByRoleName("ROLE_USER");
//        userEntity.setRoleEntity(role);
        return userEntity;
    }

    public UserRequestDTO converEntityToDTO(UserEntity userEntity){
        UserRequestDTO userDTO = modelMapper.map(userEntity, UserRequestDTO.class);
        return userDTO;
    }

    public UserDto converEntityToResponseDTO(UserEntity userEntity){
        UserDto userDTO = modelMapper.map(userEntity, UserDto.class);
        return userDTO;
    }

    public Object converListEntityToListDTO(List<UserEntity> userEntityList) {
        List<UserRequestDTO> userDTOS = new ArrayList<>();
        for (UserEntity item : userEntityList){
            UserRequestDTO userDTO = modelMapper.map(item, UserRequestDTO.class);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public UserEntity converUserDetailToEntity(UserDetailImp userDetailImp) {
        UserEntity userEntity = modelMapper.map(userDetailImp, UserEntity.class);
        //set role vào cho userEntity
        List<GrantedAuthority> roles = (List<GrantedAuthority>) userDetailImp.getAuthorities();
        RoleEntity roleEntity = new RoleEntity();
        for (GrantedAuthority item : roles){
            roleEntity.setName(item.getAuthority());
        }
        userEntity.setRoleEntity(roleEntity);
        return userEntity;
    }
}
