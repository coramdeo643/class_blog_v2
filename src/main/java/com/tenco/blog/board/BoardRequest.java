package com.tenco.blog.board;

import lombok.Data;

import java.security.PublicKey;

/**
 * Client 에게 넘어온 Data를
 * Object로 변화해서 전달하는 DTO 역할 담당
 */
public class BoardRequest {

    // 정적 내부 클래스로 기능별로 DTO 관리
    // 게시글 저장 요청 Data 담는 DTO
    // BoardRequest.SaveDTO 변수명
    @Data
    public static class SaveDTO {
        private String title;
        private String content;
        private String username;

        // DTO 에서 Entity로 변환하는 메서드 만들기
        // 계층간 데이터 변환을 명확하게 분리하기위해
        public Board toEntity() {
            return new Board(title, content, username);
        }

    } // SaveDTO

    /**
     *     @Data
     *     public static class UpdateDTO {
     *         private Long id;
     *         private String title;
     *         private String content;
     *     }
     *
     *     @Data
     *     public static class DeleteDTO {
     *         private Long id;
     *     }
     *
     *     @Data
     *     public static class DetailDTO {
     *         private Long id;
     *         private String title;
     *         private String content;
     *         private String username;
     *     }
     */

}
