package com.tecnobattery.tbsystem.controllers.management;

import java.util.List;

import javax.validation.Valid;

import com.tecnobattery.tbsystem.auth.model.User;
import com.tecnobattery.tbsystem.auth.service.UserService;
import com.tecnobattery.tbsystem.dto.request.UserRequest;
import com.tecnobattery.tbsystem.dto.response.UserResponse;
import com.tecnobattery.tbsystem.auth.enumerated.ApplicationUserRoles;
import com.tecnobattery.tbsystem.tools.ToolModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/management/api/v1/users")
public class UserManagementController {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserManagementController(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Autowired
  private UserService userService;

  @Autowired
  private ToolModelMapper toolModelMapper;

  @PostMapping
  @PreAuthorize("hasAuthority('global:write')")
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse save(@Valid @RequestBody UserRequest userInput) {
    User user = toolModelMapper.toModel(userInput, User.class);
    user.setRole(ApplicationUserRoles.ADMIN);
    user.setPassword(passwordEncoder.encode(userInput.getPassword()));
    return userService.save(user, false);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
  public ResponseEntity<List<UserResponse>> findAll() {
    List<UserResponse> list = userService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/{userId}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
  public ResponseEntity<UserResponse> findById(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.findById(userId));
  }

  @PutMapping("/{userId}")
  @PreAuthorize("hasAuthority('global:write')")
  public ResponseEntity<UserResponse> update(@Valid @PathVariable Long userId, @RequestBody UserRequest userInput) {

    if (!userService.existsById(userId)) {
      return ResponseEntity.notFound().build();
    }

    User user = toolModelMapper.toModel(userInput, User.class);
    user.setId(userId);

    return ResponseEntity.ok(userService.save(user, true));
  }

  @DeleteMapping("/{userId}")
  @PreAuthorize("hasAuthority('global:write')")
  public ResponseEntity<Void> deleteById(@PathVariable Long userId) {
    if (!userService.existsById(userId)) {
      return ResponseEntity.notFound().build();
    }
    userService.deleteById(userId);
    return ResponseEntity.noContent().build();
  }
}
