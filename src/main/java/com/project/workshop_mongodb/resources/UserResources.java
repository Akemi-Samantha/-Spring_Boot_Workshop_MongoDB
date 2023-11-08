package com.project.workshop_mongodb.resources;

import com.project.workshop_mongodb.domain.Post;
import com.project.workshop_mongodb.domain.User;
import com.project.workshop_mongodb.dto.UserDTO;
import com.project.workshop_mongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//Recurso Rest
@RestController
@RequestMapping(value = "/users")
public class UserResources {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> findAll(){
        List<User> x = userService.findAll();
        //List<User> list = userService.findAll();
        //Conversao por expressao lambda ->
        // List<UserDTO> userDTOList = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        List<UserDTO> userDTOList = x.stream().map(UserDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(userDTOList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> findById (@PathVariable String id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO){
        User user = userService.fromDto(userDTO);
        user = userService.insert(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity
                .created(uri) // -> Retorna o codigo 201 + o cabecalho (uri) contendo a localizacao do novo recurso criado
                .build();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete (@PathVariable String id){
        userService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update (@RequestBody UserDTO userDTO, @PathVariable String id){
        User user = userService.fromDto(userDTO);
        user.setId(id);
        user = userService.update(user);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> findByIPosts (@PathVariable String id){
        User user = userService.findByIPosts(id);
        return ResponseEntity.ok().body(user.getPosts());
    }



}

