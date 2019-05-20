/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.btrack.account.controller;

import com.gs.btrack.account.entities.User;
import com.gs.btrack.account.repositories.UserRepository;
import java.util.HashMap;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author LENOVO
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity all() {
        return ok(userRepository.findAll());
    }

    @PostMapping("")
    public ResponseEntity save(@RequestBody User user, HttpServletRequest request) {
        User saved = userRepository.save(user);
        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/vehicles/{id}")
                        .buildAndExpand(saved.getEmail())
                        .toUri())
                .build();
    }
    
    @GetMapping("/{email}")
    public ResponseEntity get(@PathVariable String email){
        return ok(userRepository.findById(email).orElse(null));
    }
    
    @PutMapping("/{email}")
    public ResponseEntity update(@PathVariable String email, @RequestBody User user){
        User found = userRepository.findById(email).orElseThrow(()-> new UserNotFoundException());
        found.setName(user.getName());
        found.setLastName(user.getLastName());
        found.setPassword(user.getPassword());
        userRepository.save(found);
        return noContent().build();
    }
    
    @DeleteMapping("/{email}")
    public ResponseEntity delete(@PathVariable String email){
        User found = userRepository.findById(email).orElseThrow(() -> new UserNotFoundException());
        userRepository.delete(found);
        return noContent().build();
    }
    
    
    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails){
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
            .stream()
            .map(a -> ((GrantedAuthority) a).getAuthority())
            .collect(toList())
        );
        return ok(model);
    }
}
