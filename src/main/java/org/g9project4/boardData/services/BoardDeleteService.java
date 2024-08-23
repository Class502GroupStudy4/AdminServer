package org.g9project4.boardData.services;

import groovy.transform.Final;
import lombok.RequiredArgsConstructor;
import org.g9project4.boardData.entities.BoardData;
import org.g9project4.boardData.repositories.BoardDataRepository;
import org.g9project4.file.services.FileDeleteService;
import org.g9project4.global.constants.DeleteStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardDeleteService {
    private final BoardDataInfoService infoService;
    private final BoardDataRepository repository;
    private final FileDeleteService deleteService;

    public BoardData delete(Long seq) {
        BoardData data = infoService.get(seq);
        data.setDeletedAt(LocalDateTime.now());

        repository.saveAndFlush(data);

        return data;
    }

    @Transactional
    public BoardData complete(Long seq) {
        BoardData data = infoService.get(seq, DeleteStatus.ALL);

        String gid = data.getGid();

        // 파일 삭제
        deleteService.delete(gid);

        // 게시글 삭제
        repository.delete(data);
        repository.flush();

        return data;
    }

    /**
     * 게시글 복구
     *  - 삭제 일시 -> null
     * @param seq
     * @return
     */
    public BoardData recover(Long seq) {
        BoardData item = infoService.get(seq);
        item.setDeletedAt(null);

        repository.saveAndFlush(item);

        return item;
    }
}
