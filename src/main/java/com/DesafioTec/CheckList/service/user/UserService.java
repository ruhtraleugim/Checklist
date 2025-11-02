package com.DesafioTec.CheckList.service.user;

import com.DesafioTec.CheckList.dto.UserRequestDTO;
import com.DesafioTec.CheckList.dto.UserResponseDTO;
import com.DesafioTec.CheckList.dto.UserUpdateDTO;
import com.DesafioTec.CheckList.model.user.UserModel;
import com.DesafioTec.CheckList.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserResponseDTO toResponseDTO(UserModel model) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(model.getUserId());
        dto.setNameUser(model.getNameUser());
        dto.setAccountLevel(model.getRoles());
        return dto;
    }


    public List<UserResponseDTO> getAll(){
        return repo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getById(UUID id){
        return repo.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow();
    }

    public UserResponseDTO save(UserRequestDTO model){
        UserModel newUser = new UserModel();

        newUser.setNameUser(model.getNameUser());

        String hashPassword = passwordEncoder.encode(model.getPassword());
        newUser.setPassword(hashPassword);
        newUser.setRoles(1);
        UserModel savedModel = repo.save(newUser);

        return toResponseDTO(savedModel);
    }

    public void deleteById(UUID id){
        repo.deleteById(id);
    }

    public UserResponseDTO update(UUID id , UserUpdateDTO model) {
        return repo.findById(id).map(existingUser ->{

            if (model.getNameUser() != null){
                existingUser.setNameUser(model.getNameUser());
            }
            if (model.getPassword() != null && !model.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(model.getPassword()));
            }

            return toResponseDTO(repo.save(existingUser));
                }).orElseThrow(() -> new RuntimeException("userNotFound"));
    }
}