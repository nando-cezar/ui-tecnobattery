package com.tecnobattery.tbsystem.services;

import java.util.List;
import java.util.stream.Collectors;

import com.tecnobattery.tbsystem.dto.LoaderDTO;
import com.tecnobattery.tbsystem.entities.Loader;
import com.tecnobattery.tbsystem.repositories.LoaderRepository;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoaderService {

  @Autowired
  private LoaderRepository loaderRepository;

  @Autowired
  private ModelMapper mapper;

  public List<LoaderDTO> findAll() {
    List<Loader> batterys = loaderRepository.findAll();
    return toCollectionDTO(batterys);
  }

  private LoaderDTO toModel(Loader loader) {
    mapper.getConfiguration().setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));
    return mapper.map(loader, LoaderDTO.class);
  }

  private List<LoaderDTO> toCollectionDTO(List<Loader> loaders) {
    return loaders.stream().map(x -> toModel(x)).collect(Collectors.toList());
  }
}
