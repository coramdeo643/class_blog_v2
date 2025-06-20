package com.tenco.blog.repository;

import com.tenco.blog.board.Board;
import com.tenco.blog.board.BoardNativeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

// @Import : 테스트에 사용할수있도록 해당클래스를 로드한다
@Import(BoardNativeRepository.class)
@DataJpaTest // JPA 관련 테스트만 로드하는 TEST
public class BoardNativeRepositoryTest {

    @Autowired // Auto-DI(
    private BoardNativeRepository br;

    // DI : Dependency Injection
//    public BoardNativeRepositoryTest(BoardNativeRepository br) {
//        this.br = br;
//    }

    @Test
    public void deleteById_test() {
        // given
        Long id = 4L;
        // when
        br.deleteById(id);
        // then
        List<Board> boardList = br.findAll();
        Assertions.assertThat(boardList.size()).isEqualTo(3);
    }

    @Test
    public void findById_test() {
        // given
        Long id = 1L;
        // when
        Board board = br.findById(id);
        //then
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1");
        Assertions.assertThat(board.getUsername()).isEqualTo("ssar");
        Assertions.assertThat(board).isNotNull();
    }

    @Test
    public void findAll_test() {
        // given - when - then
        // Given : Preparation of the test
        // 게시글 목록 조회 정상 작동하는지 확인 -> data.sql 파일에 데이터 이미 준비 완료
        // When : TEST
        // 실제 테스트를 하는 행위(전체 게시글 목록 조회)
        List<Board> boardList = br.findAll();
        // Then :  결과 검증(예상대로 동작하는지?)
        // Assertions.assertThat(boardList.size()).isEqualTo(4);
        Assertions.assertThat(boardList.get(3).getUsername()).isEqualTo("ssar");
    }


}
