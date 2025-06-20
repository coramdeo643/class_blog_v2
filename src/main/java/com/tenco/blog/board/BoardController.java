package com.tenco.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RequiredArgsConstructor
@Controller // IoC 대상 - 싱글톤 패턴으로 관리된다
public class BoardController {

    // 생성자 의존 주입 - DI 처리
    private final BoardPersistRepository br;

    // 게시글 작성 화면 연결 처리
    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    // 게시글 작성 액션(수행) 처리
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO) {
        // HTTP 요청 본문 : title=값&content=값&username=값
        // form tag MIME type(application/x-www-form-urlencoded)

        // reqDTO = 사용자가 던진 데이터 상태가 있음
        // DTO > Board > DB
//        Board board = new Board(reqDTO.getTitle(), reqDTO.getContent(), reqDTO.getUsername());
        Board board = reqDTO.toEntity();
        br.save(board);

        // PGR
        return "redirect:/";
    }


}
