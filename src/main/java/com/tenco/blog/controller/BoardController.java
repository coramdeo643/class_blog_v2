package com.tenco.blog.controller;

import com.tenco.blog.model.Board;
import com.tenco.blog.repository.BoardNativeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller // IoC 대상 - 싱글톤 패턴으로 관리된다
public class BoardController {

    private BoardNativeRepository boardNativeRepository;

    public BoardController(BoardNativeRepository boardNativeRepository) {
        this.boardNativeRepository = boardNativeRepository;
    }

    @PostMapping("/board/{id}/update-form")
    public String update(@PathVariable(name="id") Long id,
                         @RequestParam(name = "username") String username,
                         @RequestParam(name = "title") String title,
                         @RequestParam(name = "content") String content,
                         HttpServletRequest request) {

        boardNativeRepository.updateById(id, username, title, content);
        // PRG pattern, 수정완료후 해당 게시글 상세보기 페이지로 redirect
        // 게시글 상세보기 url
        return "redirect:/board/" + id;
    }


    // 게시글 수정 화면 요청 GET 방식
    // /board/3/update-form
    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable(name="id") Long id,
                             HttpServletRequest request) {
        Board board = boardNativeRepository.findById(id);
        request.setAttribute("board", board);

        return "board/update-form";
    }


    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id) {
        // Client --> Delete Request --> Response : redirect --> Client : / Path --> Response
        boardNativeRepository.deleteById(id);
        // PRG pattern (Post - Redirect - Get)
        // 삭제 후 메인 페이지로 리다이렉트 하여 중복 삭제 방지를 위한다
        // = 새로고침을 해도 삭제가 다시 실행되지 않음
        return "redirect:/";
    }


    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Long id, HttpServletRequest request) {

        Board board = boardNativeRepository.findById(id);
        request.setAttribute("board", board);

        return "board/detail";
    }

    @PostMapping("/board/save")
    // username, title, content <--- DTO / 기본 DT setting
    // form 태그에서 넘어오는 데이터 받기
    // form tag, name 속성에 key value mapping
    public String save(@RequestParam("username") String username,
                       @RequestParam("title") String title,
                       @RequestParam("content") String content) {
        System.out.println("username = " + username);
        System.out.println("title = " + title);
        System.out.println("content = " + content);

        boardNativeRepository.save(username, title, content);

        return "redirect:/";
    }


    @GetMapping({"/", "/index"})
//    public String index() {
//    public String index(Model model) {
    public String index(HttpServletRequest request) {

        // DB 접근해서 select 결과값을 받아서 mustache file에 data binding 처리해야한다
        List<Board> boardList = boardNativeRepository.findAll();
        // 뷰에 데이터를 전달 > Model 사용가능, but
        request.setAttribute("boardList", boardList);

        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {

        return "board/save-form";
    }

}
