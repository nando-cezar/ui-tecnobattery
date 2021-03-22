package com.tecnobattery.tbsystem.controllers.management;

import java.util.List;

import javax.validation.Valid;

import com.tecnobattery.tbsystem.dto.request.LoaderRequest;
import com.tecnobattery.tbsystem.dto.response.LoaderResponse;
import com.tecnobattery.tbsystem.entities.Loader;
import com.tecnobattery.tbsystem.services.LoaderService;
import com.tecnobattery.tbsystem.tools.ToolModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/management/api/v1/loaders")
@AllArgsConstructor
public class LoaderManagementController {

  private final LoaderService loaderService;
  private final ToolModelMapper toolModelMapper;

  @PostMapping
  @PreAuthorize("hasAuthority('global:write')")
  @ResponseStatus(HttpStatus.CREATED)
  public LoaderResponse save(@Valid @RequestBody LoaderRequest loaderInput) {
    return loaderService.save(toolModelMapper.toModel(loaderInput, Loader.class), false);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
  public ResponseEntity<List<LoaderResponse>> findAll() {
    return ResponseEntity.ok().body(loaderService.findAll());
  }

  @GetMapping("/{loaderId}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
  public ResponseEntity<LoaderResponse> findById(@PathVariable Long loaderId) {
    return ResponseEntity.ok(loaderService.findById(loaderId));
  }

  @PutMapping("/{loaderId}")
  @PreAuthorize("hasAuthority('global:write')")
  public ResponseEntity<LoaderResponse> update(@Valid @PathVariable Long loaderId,
      @RequestBody LoaderRequest loaderInput) {

    if (!loaderService.existsById(loaderId)) {
      return ResponseEntity.notFound().build();
    }

    Loader loader = new Loader();
    loader = toolModelMapper.toModel(loaderInput, Loader.class);
    loader.setId(loaderId);

    return ResponseEntity.ok(loaderService.save(loader, true));
  }

  @DeleteMapping("/{loaderId}")
  @PreAuthorize("hasAuthority('global:write')")
  public ResponseEntity<Void> deleteById(@PathVariable Long loaderId) {
    if (!loaderService.existsById(loaderId)) {
      return ResponseEntity.notFound().build();
    }
    loaderService.deleteById(loaderId);
    return ResponseEntity.noContent().build();
  }
}
