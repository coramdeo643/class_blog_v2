package com.tenco.blog.Board;

import com.tenco.blog.board.Board;
import com.tenco.blog.board.BoardPersistRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(BoardPersistRepository.class)
@DataJpaTest
public class BoardPersistRepositoryTest {

    @Autowired
    private BoardPersistRepository br;

    @Test
    public void save_test() {
        // given
        Board board = new Board("제목111", "내용111", "작성자111");

        // 저장전 객체의 상태값 확인
        Assertions.assertThat(board.getId()).isNull();
        System.out.println("DB 저장 전 board = " + board);
        // when
        // 영속성 context를 통한 Entity save
        Board savedBoard = br.save(board);
        // then
        // 1. 저장 후에 자동 생성된 ID 확인
        Assertions.assertThat(savedBoard.getId()).isNotNull();
        Assertions.assertThat(savedBoard.getId()).isGreaterThan(0);
        // 2. 내용 일치 여부 확인
        Assertions.assertThat(savedBoard.getTitle()).isEqualTo("제목111");
        // 3. JPA 자동으로 생성된 생성시간 확인
        Assertions.assertThat(savedBoard.getCreatedAt()).isNotNull();
        // 4. 원본 객체 - board, savedBoard(Persist context)
        Assertions.assertThat(board).isSameAs(savedBoard);
    }

}
