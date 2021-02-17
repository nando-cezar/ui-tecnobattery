package com.tecnobattery.tbsystem.services;

import java.util.List;
import java.util.stream.Collectors;

import com.tecnobattery.tbsystem.dto.BoardDTO;
import com.tecnobattery.tbsystem.entities.Board;
import com.tecnobattery.tbsystem.repositories.BoardRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

  @Autowired
  private BoardRepository boardRepository;

  @Autowired
  private ModelMapper mapper;

  public BoardDTO save(String brand, String model, Integer power, Integer voltage, Integer width, Integer height,
      Integer length, String imageUrl) {
    Board board = new Board();
    board.setBrand(brand);
    board.setModel(model);
    board.setPower(power);
    board.setVoltage(voltage);
    board.setWidth(width);
    board.setHeight(height);
    board.setLength(length);
    board.setImageUrl(imageUrl);

    return toModel(boardRepository.save(board));
  }

  @Transactional(readOnly = true)
  public List<BoardDTO> findAll() {
    List<Board> boards = boardRepository.findAll();
    return toCollectionDTO(boards);
  }

  private BoardDTO toModel(Board board) {
    return mapper.map(board, BoardDTO.class);
  }

  private List<BoardDTO> toCollectionDTO(List<Board> boards) {
    return boards.stream().map(x -> toModel(x)).collect(Collectors.toList());
  }
}