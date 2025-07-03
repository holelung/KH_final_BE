package com.kh.saintra.anonymous.controller;

import com.kh.saintra.anonymous.model.dto.AnonymousDto;
import com.kh.saintra.anonymous.model.service.AnonymousService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@CrossOrigin(origins = "*")
public class AnonymousController {

    private final AnonymousService anonymousService;

    public AnonymousController(AnonymousService anonymousService) {
        this.anonymousService = anonymousService;
    }

    @GetMapping
    public List<AnonymousDto> getBoardList() {
        return anonymousService.getBoardList();
    }

    @GetMapping("/search")
    public List<AnonymousDto> searchBoard(@RequestParam String keyword) {
        return anonymousService.searchBoard(keyword);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnonymousDto> getBoardById(@PathVariable Long id) {
        AnonymousDto dto = anonymousService.getBoardById(id);
        return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> writeBoard(@RequestBody AnonymousDto dto) {
        anonymousService.writeBoard(dto);
        return ResponseEntity.ok("등록 완료");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody AnonymousDto dto) {
        dto.setId(id);
        anonymousService.updateBoard(dto);
        return ResponseEntity.ok("수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id) {
        anonymousService.deleteBoard(id);
        return ResponseEntity.ok("삭제 완료");
    }

    @GetMapping("/search/my")
    public List<AnonymousDto> searchMyBoard(@RequestParam String keyword, @RequestParam Long userId) {
        return anonymousService.searchMyBoard(keyword, userId);
    }
}
