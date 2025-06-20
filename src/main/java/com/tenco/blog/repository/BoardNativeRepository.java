package com.tenco.blog.repository;

import com.tenco.blog.controller.BoardController;
import com.tenco.blog.model.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // IoC 대상
public class BoardNativeRepository {

//    // DI : 의존성 주입 : 스프링이 자동으로 객체를 주입
//    private BoardNativeRepository boardNativeRepository;
//    public BoardController(BoardNativeRepository boardNativeRepository) {
//        this.boardNativeRepository = boardNativeRepository;
//    }

    // JPA 의 핵심 인터페이스
    // 데이터 베이스와의 모든 작업을 담당
    private EntityManager em;

    public BoardNativeRepository(EntityManager em) {
        this.em = em;
    }

    // 특정 게시글을 삭제하는 Method
    @Transactional
    public void deleteById(Long id) {
        String delStr = "delete from board_tb where id = ? ";
        Query query = em.createNativeQuery(delStr);
        query.setParameter(1, id);
        // Insert, Update, Delete
        query.executeUpdate();
    }

    public Board findById(Long id) {
        // WHERE 조건절 활용 단건 Data select
        String sqlStr = "select * from board_tb where id = ? ";
        Query query = em.createNativeQuery(sqlStr, Board.class);
        // SQL Injection 방지 - parameter binding
        // 직접 문자열을 연결하지않고 ? 사용해서 안전하게 값 전달한다
        query.setParameter(1, id);
        // query.setParameter(1, id);

        // 주의 : null 리턴 된다면 예외 발생 > need to use try-catch
        // 주의 : 혹시 결과가 2개 행의 리턴이 된다면 예외 발생
        return (Board) query.getSingleResult(); // 단일 결과 반환

    }


    // 게시글 목록 조회
    public List<Board> findAll() {
        // 쿼리 기술 --> 네이티브 쿼리
        // Board.class
        Query query = em.createNativeQuery("select * from board_tb order by id desc ", Board.class);
        // query.getResultList(); : 여러 행의 결과를 List 객체로 반환
        // 하나만? query.getSingleResult(); 단일 결과만 반환(한개의 row 데이터만 있을때)
//         List<Board> list = query.getResultList();
//         return list;
        return query.getResultList();
    }

    // 생성자를 확인해서 자동으로 Entity Manager 객체를 주입시킨다
    // DI 처리 Dependent injection

    @Transactional
    public void save(String username, String title, String content) {
        Query query = em.createNativeQuery(
                "insert into board_tb(username, title, content, created_at) " +
                        " values(?, ?, ?, now()) ");

        query.setParameter(1, username);
        query.setParameter(2, title);
        query.setParameter(3, content);

        query.executeUpdate();

    }
    @Transactional
    public void updateById(Long id, String username, String title, String content) {
        // update query, where
        String updateStr = "update board_tb set username = ?, title = ?, content = ? where id = ? ";
        Query query = em.createNativeQuery(updateStr);

        query.setParameter(1, username);
        query.setParameter(2, title);
        query.setParameter(3, content);
        query.setParameter(4, id);

        int updateRows = query.executeUpdate();
        System.out.println("수정된 행의 개수 = " + updateRows);
    }
}
