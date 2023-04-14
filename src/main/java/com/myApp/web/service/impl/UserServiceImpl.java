package com.myApp.web.service.impl;

import com.myApp.web.dto.RegistrationDto;
import com.myApp.web.dto.UserDto;
import com.myApp.web.model.Club;
import com.myApp.web.model.UserEntity;
import com.myApp.web.model.RoleEntity;
import com.myApp.web.repository.RoleRepository;
import com.myApp.web.repository.UserRepository;
import com.myApp.web.security.SecurityUtil;
import com.myApp.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.myApp.web.mapper.UserMapper.mapToUser;

import java.util.Arrays;
import java.util.Optional;

import static com.myApp.web.mapper.ClubMapper.mapToClub;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setAvatarUrl("https://thumbs.dreamstime.com/b/default-avatar-profile-flat-icon-social-media-user-vector-portrait-unknown-human-image-default-avatar-profile-flat-icon-184330869.jpg");
        user.setRating(1000);
        RoleEntity role = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }
    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void updateUser(UserDto user) {
        UserEntity userEntity  = mapToUser(user);
        userRepository.save(userEntity);
    }


}
