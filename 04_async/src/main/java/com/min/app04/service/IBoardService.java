package com.min.app04.service;

import java.util.Map;

import com.min.app04.dto.BoardDto;

import jakarta.servlet.http.HttpServletRequest;

public interface IBoardService {
  Map<String, Object> getBoardList(HttpServletRequest request);
  BoardDto getBoardById(int boardId);
  Map<String, Object> registBoard(BoardDto boardDto);
  String modifyBoard(BoardDto boardDto);
  String removeBoard(int boardId);
}
