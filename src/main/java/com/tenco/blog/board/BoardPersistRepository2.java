//package com.tenco.blog.board;
//
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@RequiredArgsConstructor // 필수 멤버변수 확인해서 생성자에 등록해줌
//@Repository //IoC 대상 - Singleton pattern
//public class BoardPersistRepository2 {
//    private final EntityManager em;
//
//    public void updateById(Long id, String title, String content, String username) {
//        String jpql = "update Board b set b.title = :title, b.content = :content, b.username = :username where b.id = :id ";
//        Query q = em.createQuery(jpql);
//        q.setParameter("title", title).setParameter("content", content).setParameter("username", username).setParameter("id", id);
//        q.executeUpdate();
//    }
//}
