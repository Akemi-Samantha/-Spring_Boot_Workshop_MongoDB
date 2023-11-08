package com.project.workshop_mongodb.service;

import com.project.workshop_mongodb.domain.User;
import com.project.workshop_mongodb.dto.AuthorDTO;
import com.project.workshop_mongodb.dto.UserDTO;
import com.project.workshop_mongodb.repository.UserRepository;
import com.project.workshop_mongodb.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById( String id){
        Optional<User> userObj = userRepository.findById(id);
        return userObj.orElseThrow(() -> new ObjectNotFoundException("Objeto nao Encontrado :( "));
    }

    public  User insert (User obj){
        return userRepository.insert(obj);
    }

    public void delete(String id){
        findById(id);
        userRepository.deleteById(id);
    }

    public User update(User user) {
        User newUser = findById(user.getId());
        updateData(newUser, user);
        return userRepository.save(newUser);
    }

    public  void updateData (User newUser, User user ){
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
    }

    // Criando um fromDto, dentro da classe service para possiveis manutencoes...?
    public User fromDto(UserDTO userDTO){
       return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getEmail()
        );
    }

    public User fromAuthorDTO(AuthorDTO authorDTO){
        return new User(
                authorDTO.getId(),
                authorDTO.getName()
        );
    }


    public User findByIPosts(String id) {
        Optional<User> userObj = userRepository.findById(id);
        List<User> list = userObj.stream().map(fromAuthorDTO()::new).collect(Collectors.toList());
    }
}
