package com.tenco.blog.board;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // 필수 멤버변수 확인해서 생성자에 등록해줌
@Repository //IoC 대상 - Singleton pattern
public class BoardPersistRepository {
    // JPA core Interface
    // @Autowired // = DI // final 쓰면 autowired 사용불가
    private final EntityManager em;

    // 게시글 저장 기능 - 영속성 컨텍스트 활용
    @Transactional
    public Board save(Board board) {
        // v1 -> NativeQuery...

        // 1. 매개변수로 받은 board 는 현재 비영속 상태,
        // = 아직 영속성 context에 관리되지않는 상태
        // = DB와 아직 연관없는 순수 Java 객체 상태
        //
        // 2. em.persist(board) = 영속성 context 에 저장하는 개념
        // = 영속성 context 가 board 객체를 관리한다 .

        em.persist(board);
        // 3. Transaction commit 시점에 Insert Query 실행
        // -> 이때 영속성 context의 변경사항이 자동으로 DB에 반영됨
        // -> board 객체에 id 필드에 자동으로 생성도니 값이 설정됨.


        // 4. 영속 상태로 된 board 객체로 반환
        // -> 이 시점에는 자동으로 board id 멤버 변수에 db pk 값이 할당된 상태이다.

        return board;
    }



}
