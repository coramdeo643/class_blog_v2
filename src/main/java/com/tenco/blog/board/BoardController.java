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

    // 게시글 수정하기 화면 연결
    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable(name = "id") Long id,
                             HttpServletRequest request) {
       Board board = br.findById(id);
       request.setAttribute("board", board);
       return "board/update-form";
    }

    @PostMapping("/board/{id}/update-form")
    public String update(@PathVariable(name="id") Long id,
                         @RequestParam(name = "title") String title,
                         @RequestParam(name = "content") String content,
                         @RequestParam(name = "username") String username) {
        br.updateById(id, title, content, username);
        return "redirect:/board/" + id;
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id) {
        br.deleteById(id);
        return "redirect:/";
    }


    // 게시글 상세 보기(주소설계)
    // GET : http://localhost:8080/boarder/3
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        Board board = br.findById(id);
        request.setAttribute("board", board);
        // 1차 캐시 효과 - DB 접근하지않고 바로 영속성 context 에서 가져옴
        return "board/detail";
    }

    // 목록 화면 연결
    // 1. index.mustache 파일 변환 기능
    // 주소 = http://localhost:8080/, http://localhost:8080/index
    @GetMapping({"/","/index"})
    public String boardList(HttpServletRequest request) {
        List<Board> boardList = br.findAll();
        request.setAttribute("boardList", boardList);
        return "index";
    }

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
