package org.g9project4.board.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.g9project4.board.repositories.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardPostInfoService {
    private final BoardRepository boardRepository;
    private final HttpServletRequest request;
}
