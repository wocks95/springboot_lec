-- greenit 계정 삭제하기
-- DROP USER 'greenit'@'%'; -- DROP USER greenit

-- greenit 계정 만들기
-- CREATE USER 'greenit'@'%' IDENTIFIED BY 'greenit';

-- greenit 권한 설정
-- GRANT ALL PRIVILEGES ON db_final.* TO 'greenit'@'%';

DROP DATABASE IF EXISTS db_final;
CREATE DATABASE IF NOT EXISTS db_final DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE db_final;

------------------------------------------------------------
-- 1. 테이블 삭제
------------------------------------------------------------
DROP TABLE IF EXISTS tbl_secondhand_attatch;
DROP TABLE IF EXISTS tbl_user_login_log;
DROP TABLE IF EXISTS tbl_delete_user_log;
DROP TABLE IF EXISTS tbl_attatch;
DROP TABLE IF EXISTS tbl_faq;
DROP TABLE IF EXISTS tbl_qna_reply;
DROP TABLE IF EXISTS tbl_qna;
DROP TABLE IF EXISTS tbl_notice;
DROP TABLE IF EXISTS tbl_review;
DROP TABLE IF EXISTS tbl_product_inq_reply;
DROP TABLE IF EXISTS tbl_product_inq;
DROP TABLE IF EXISTS tbl_pay;
DROP TABLE IF EXISTS tbl_cancel_reason;
DROP TABLE IF EXISTS tbl_cart_item;    -- 신규 추가: 장바구니 항목 테이블 삭제(2025-03-01)
DROP TABLE IF EXISTS tbl_cart;         -- 수정된 tbl_cart(2025-03-01)
DROP TABLE IF EXISTS tbl_order_item;
DROP TABLE IF EXISTS tbl_like;
DROP TABLE IF EXISTS tbl_secondhand;
DROP TABLE IF EXISTS tbl_product;
DROP TABLE IF EXISTS tbl_delivery_address;
DROP TABLE IF EXISTS tbl_user;
DROP TABLE IF EXISTS tbl_order_status;
DROP TABLE IF EXISTS tbl_genre;
DROP TABLE IF EXISTS tbl_publisher;
DROP TABLE IF EXISTS tbl_author;
DROP TABLE IF EXISTS tbl_search;
DROP TABLE IF EXISTS tbl_cancel_definition;

-- 트리거 삭제 (존재할 경우) 신규 추가(2025-03-01)
DROP TRIGGER IF EXISTS trg_create_cart_after_user_insert;
DROP TRIGGER IF EXISTS trg_after_delete_user;

------------------------------------------------------------
-- 2. 테이블 생성
------------------------------------------------------------

-- 2-1. 취소사유 정의 (cancel_definition)
CREATE TABLE IF NOT EXISTS tbl_cancel_definition
(
    cancel_definition_id       INT NOT NULL AUTO_INCREMENT COMMENT '취소정의고유 ID',
    cancel_reason_definition   VARCHAR(50) NULL COMMENT '취소 사유 이름(예: 고객 변심, 재고 부족 등)',

    CONSTRAINT pk_tbl_cancel_definition PRIMARY KEY (cancel_definition_id)
) ENGINE=InnoDB COMMENT='취소 사유 정의 테이블';

-- 2-2. 작가 (author)
CREATE TABLE IF NOT EXISTS tbl_author
(
    author_id    INT NOT NULL AUTO_INCREMENT COMMENT '작가 고유 ID',
    author_name  VARCHAR(20) NOT NULL COMMENT '작가 이름',
    author_birth DATE NULL COMMENT '작가 생년월일',
    biography    TEXT NULL COMMENT '작가의 간단한 소개',
    major_works  TEXT NULL COMMENT '작가의 주요작품',

    CONSTRAINT pk_tbl_author PRIMARY KEY (author_id)
) ENGINE=InnoDB COMMENT='작가 테이블';

-- 2-3. 출판사 (publisher)
CREATE TABLE IF NOT EXISTS tbl_publisher
(
    publisher_id   INT NOT NULL AUTO_INCREMENT COMMENT '출판사 고유 ID',
    publisher_name VARCHAR(20) NOT NULL COMMENT '출판사 이름',
    website        VARCHAR(255) NULL COMMENT '출판사 웹사이트 URL',

    CONSTRAINT pk_tbl_publisher PRIMARY KEY (publisher_id)
) ENGINE=InnoDB COMMENT='출판사 테이블';

-- 2-4. 장르 (genre)
CREATE TABLE IF NOT EXISTS tbl_genre
(
    genre_id   INT NOT NULL AUTO_INCREMENT COMMENT '상품 장르 아이디',
    genre_name VARCHAR(20) NOT NULL COMMENT '도서 장르 이름',

    CONSTRAINT pk_tbl_genre PRIMARY KEY (genre_id)
) ENGINE=InnoDB COMMENT='장르 테이블';

-- 2-5. 주문 상태 정의 (order_status)
CREATE TABLE IF NOT EXISTS tbl_order_status
(
    order_status_id INT NOT NULL AUTO_INCREMENT COMMENT '주문 상태 고유 ID',
    status_name     VARCHAR(50) NULL COMMENT '주문 진행 상태 (결제완료, 배송완료, 취소접수 등)',

    CONSTRAINT pk_tbl_order_status PRIMARY KEY (order_status_id)
) ENGINE=InnoDB COMMENT='주문 상태 테이블';

-- 2-6. 회원 (user)
CREATE TABLE IF NOT EXISTS tbl_user
(
    user_id               INT NOT NULL AUTO_INCREMENT COMMENT '회원 아이디',
    user_email            VARCHAR(50) NOT NULL COMMENT '이메일',
    user_pw               VARCHAR(64) NOT NULL COMMENT '비밀번호',
    user_name             VARCHAR(30) NOT NULL COMMENT '회원이름',
    user_birthdate        VARCHAR(8)  NOT NULL COMMENT '회원 생년월일',
    user_phone            VARCHAR(11) NOT NULL COMMENT '회원 휴대폰 번호',
    user_nickname         VARCHAR(50) NOT NULL COMMENT '회원닉네임',
    profile_img           VARCHAR(100) NOT NULL COMMENT '프로필 이미지 경로',
    profile_img_ori_name  VARCHAR(100) NULL COMMENT '프로필 이미지 원본 이름',
    profile_img_sys_name  VARCHAR(300) NULL COMMENT '프로필 이미지 시스템 이름',
    session_id            VARCHAR(100) NOT NULL COMMENT '자동로그인 정보',
    user_role             VARCHAR(20) NULL COMMENT '관리자 여부 (ADMIN, USER, ...)',
    create_dt             DATETIME NULL COMMENT '회원가입일자',
    change_dt             DATETIME NULL COMMENT 'password change date',

    CONSTRAINT pk_tbl_user PRIMARY KEY (user_id)
) ENGINE=InnoDB COMMENT='회원 테이블';

-- 2-7. 검색어 로그 (search)
CREATE TABLE IF NOT EXISTS tbl_search
(
    search         VARCHAR(30) NOT NULL COMMENT '검색어',
    user_id        INT NULL COMMENT '회원 아이디',
    search_count   INT NULL COMMENT '검색 카운트',
    search_dt      DATETIME NULL COMMENT '마지막 검색 일자',

    CONSTRAINT pk_tbl_search PRIMARY KEY (search),
    CONSTRAINT fk_search_user FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='검색어 로그 테이블';

-- 2-8. 배송지 정보 (delivery_address)
CREATE TABLE IF NOT EXISTS tbl_delivery_address
(
    deli_addr_id    INT NOT NULL AUTO_INCREMENT COMMENT '배송지 고유 ID',
    user_id         INT NULL COMMENT '회원 아이디',
    road_addr       VARCHAR(255) NULL COMMENT '도로명주소',
    detail_addr     VARCHAR(255) NULL COMMENT '상세주소',
    post_code       VARCHAR(15) NULL COMMENT '우편번호',
    addr_name       VARCHAR(50) NULL COMMENT '배송지(수취인) 이름',
    receiver_phone  VARCHAR(11) NULL COMMENT '수취인전화번호',
    primary_addr    BOOLEAN NULL COMMENT '대표배송지 여부',

    CONSTRAINT pk_tbl_delivery_address PRIMARY KEY (deli_addr_id),
    CONSTRAINT fk_deladdr_user FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='배송지 테이블';

-- 2-9. 주문 (order)
CREATE TABLE IF NOT EXISTS tbl_order
(
    order_id       INT NOT NULL AUTO_INCREMENT COMMENT '주문 고유 식별자',
    user_id        INT NULL COMMENT '주문한 사용자 (회원)',
    deli_addr_id   INT NULL COMMENT '배송지 고유 ID',
    total_price    INT NULL COMMENT '주문의 총 금액',
    payment_id     INT NULL COMMENT '해당 주문의 결제 정보(실제 FK는 별도 pay테이블에서 연결될 수 있음)',
    create_dt      DATETIME NULL COMMENT '주문 생성 날짜',
    modify_dt      DATETIME NULL COMMENT '주문 상태 변경 날짜',

    CONSTRAINT pk_tbl_order PRIMARY KEY (order_id),
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE SET NULL,
    CONSTRAINT fk_order_delivery FOREIGN KEY (deli_addr_id) REFERENCES tbl_delivery_address (deli_addr_id) ON DELETE SET NULL
) ENGINE=InnoDB COMMENT='주문 테이블';

-- 2-10. 상품 (product)
CREATE TABLE IF NOT EXISTS tbl_product
(
    product_id       INT NOT NULL AUTO_INCREMENT COMMENT '도서 고유 ID',
    user_id          INT NULL COMMENT '상품 등록자 아이디',
    genre_id         INT NOT NULL COMMENT '상품 장르 아이디',
    publisher_id     INT NOT NULL COMMENT '출판사 고유 ID',
    author_id        INT NOT NULL COMMENT '작가 고유 ID',
    product_name     VARCHAR(100) NOT NULL COMMENT '도서 이름',
    product_image    VARCHAR(300) NULL COMMENT '도서 이미지',
    description      VARCHAR(1000) NULL COMMENT '도서 설명',
    product_price    INT NOT NULL COMMENT '도서 가격',
    stock            INT NULL COMMENT '도서 재고',
    create_dt        DATE NOT NULL COMMENT '도서상품 등록일',
    publication_date DATE NULL COMMENT '도서상품 발매일',
    total_pages      INT NULL COMMENT '전체 페이지 수',
    
    CONSTRAINT pk_tbl_product PRIMARY KEY (product_id),
    CONSTRAINT fk_product_user FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE SET NULL,
    CONSTRAINT fk_product_genre      FOREIGN KEY (genre_id)     REFERENCES tbl_genre (genre_id) ON DELETE CASCADE,
    CONSTRAINT fk_product_publisher  FOREIGN KEY (publisher_id) REFERENCES tbl_publisher (publisher_id) ON DELETE CASCADE,
    CONSTRAINT fk_product_author     FOREIGN KEY (author_id)     REFERENCES tbl_author (author_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='상품(도서) 테이블';

-- 2-11. 중고상품 (secondhand)
CREATE TABLE IF NOT EXISTS tbl_secondhand
(
    secondhand_id            INT NOT NULL AUTO_INCREMENT COMMENT '중고상품 아이디',
    user_id                  INT NULL COMMENT '중고상품 등록자 아이디',
    genre_id                 INT NOT NULL COMMENT '상품 장르 아이디',
    publisher_id             INT NOT NULL COMMENT '출판사 고유 ID',
    author_id                INT NOT NULL COMMENT '작가 고유 ID',
    secondhand_name          VARCHAR(100) NULL COMMENT '상품 이름',
    secondhand_image         VARCHAR(300) NULL COMMENT '상품 이미지',
    secondhand_description   VARCHAR(1000) NULL COMMENT '상품 설명',
    secondhand_price         INT NULL COMMENT '상품 가격',
    secondhand_date          DATETIME NULL COMMENT '상품 발매일',
    create_dt                DATETIME NULL COMMENT '상품 등록일',

    CONSTRAINT pk_tbl_secondhand PRIMARY KEY (secondhand_id),
    CONSTRAINT fk_secondhand_user FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE SET NULL,
    CONSTRAINT fk_secondhand_genre      FOREIGN KEY (genre_id)     REFERENCES tbl_genre (genre_id) ON DELETE CASCADE,
    CONSTRAINT fk_secondhand_publisher  FOREIGN KEY (publisher_id) REFERENCES tbl_publisher (publisher_id) ON DELETE CASCADE,
    CONSTRAINT fk_secondhand_author     FOREIGN KEY (author_id)     REFERENCES tbl_author (author_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='중고상품 테이블';

-- 좋아요 (like)
CREATE TABLE IF NOT EXISTS tbl_like
(
    like_id     INT NOT NULL AUTO_INCREMENT COMMENT '좋아요 ID',
    user_id     INT NOT NULL  COMMENT '회원 아이디',
    product_id  INT NOT NULL  COMMENT '도서 고유 ID',

    CONSTRAINT pk_like_id PRIMARY KEY (like_id),
    CONSTRAINT fk_like_user      FOREIGN KEY (user_id)    REFERENCES tbl_user (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_like_product   FOREIGN KEY (product_id) REFERENCES tbl_product (product_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='좋아요 테이블';

-- 2-12. 주문 상세 항목 (order_item)
CREATE TABLE IF NOT EXISTS tbl_order_item
(
    orderItem_id    INT NOT NULL AUTO_INCREMENT COMMENT '주문 상세 항목 고유 ID',
    order_id        INT NULL COMMENT '주문 고유 식별자',
    product_id      INT NULL COMMENT '주문한 상품 (tbl_product.product_id)',
    orderStatus_id  INT NULL COMMENT '주문 상태 고유 ID',
    quantity        INT NULL COMMENT '주문한 상품 개수',
    price           INT NULL COMMENT '개별 상품 가격',
    modify_dt       DATETIME NULL COMMENT '주문 상세 수정 날짜',

    CONSTRAINT pk_tbl_order_item PRIMARY KEY (orderItem_id),
    CONSTRAINT fk_orderitem_order         FOREIGN KEY (order_id)        REFERENCES tbl_order (order_id) ON DELETE CASCADE,
    CONSTRAINT fk_orderitem_product       FOREIGN KEY (product_id)     REFERENCES tbl_product (product_id) ON DELETE CASCADE,
    CONSTRAINT fk_orderitem_status        FOREIGN KEY (orderStatus_id)  REFERENCES tbl_order_status (order_status_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='주문 상세 항목 테이블';

-- 2-13. 장바구니 (cart) - 수정된 부분(2025-03-01)
DROP TABLE IF EXISTS tbl_cart;
CREATE TABLE IF NOT EXISTS tbl_cart 
(
    cart_id         INT NOT NULL AUTO_INCREMENT COMMENT '장바구니 고유 ID',
    user_id         INT NOT NULL COMMENT '회원 아이디',
    cart_created_dt DATETIME COMMENT '장바구니 생성일',
    cart_updated_dt DATETIME COMMENT '장바구니 수정일',
    CONSTRAINT pk_tbl_cart PRIMARY KEY (cart_id),
    CONSTRAINT uq_tbl_cart_user UNIQUE (user_id),
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES tbl_user(user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='회원별 장바구니 테이블';

-- 2-13-1. 장바구니 항목 (cart_item) - 신규 추가(2025-03-01)
DROP TABLE IF EXISTS tbl_cart_item;
CREATE TABLE IF NOT EXISTS tbl_cart_item (
    cart_item_id         INT NOT NULL AUTO_INCREMENT COMMENT '장바구니 항목 고유 ID',
    cart_id              INT NOT NULL COMMENT '장바구니 고유 ID',
    product_id           INT NOT NULL COMMENT '상품 고유 ID',
    product_quantity     INT NULL COMMENT '장바구니에 담긴 상품 개수',
    cart_item_created_dt DATETIME COMMENT '장바구니 항목 등록일',
    cart_item_updated_dt DATETIME COMMENT '장바구니 항목 수정일',
    CONSTRAINT pk_tbl_cart_item PRIMARY KEY (cart_item_id),
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES tbl_cart(cart_id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_item_product FOREIGN KEY (product_id) REFERENCES tbl_product(product_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='장바구니 항목 테이블';

-- 2-14. 취소 사유 상세 (cancel_reason)
CREATE TABLE IF NOT EXISTS tbl_cancel_reason
(
    cancel_reason_id       INT NOT NULL AUTO_INCREMENT COMMENT '취소 사유 고유 ID',
    cancel_definition_id   INT NULL COMMENT '주문 상태(취소 정의) 고유 ID',
    orderItem_id           INT NOT NULL COMMENT '주문 상세 항목 고유 ID',
    cancel_reason          VARCHAR(255) NULL COMMENT '취소 사유 상세 설명',
    create_dt              DATETIME NULL COMMENT '취소신청일시',
    modify_dt              DATETIME NULL COMMENT '취소수정일시(취소완료,취소거부)',

    CONSTRAINT pk_tbl_cancel_reason PRIMARY KEY (cancel_reason_id),
    CONSTRAINT fk_cancelreason_definition  FOREIGN KEY (cancel_definition_id) REFERENCES tbl_cancel_definition (cancel_definition_id) ON DELETE SET NULL,
    CONSTRAINT fk_cancelreason_orderitem   FOREIGN KEY (orderItem_id)         REFERENCES tbl_order_item (orderItem_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='취소 사유 상세 테이블';

-- 2-15. 결제 (pay)
CREATE TABLE IF NOT EXISTS tbl_pay
(
    pay_Id        INT NOT NULL AUTO_INCREMENT COMMENT '결제 정보 고유 ID',
    order_id      INT NULL COMMENT '주문 고유 식별자',
    user_id       INT NULL COMMENT '회원 아이디',
    imp_uid       VARCHAR(50) NULL COMMENT '아임포트 결제 식별 ID',
    merchant_uid  VARCHAR(50) NULL COMMENT '주문별 고유 식별자',
    amount        INT NULL COMMENT '실제 결제 금액',
    pay_status    VARCHAR(20) NULL COMMENT '결제 상태(결제 완료, 결제 취소 등)',
    create_dt     DATETIME NULL COMMENT '결제 발생 날짜',
    modify_dt     DATETIME NULL COMMENT '결제 상태 변경 날짜',

    CONSTRAINT pk_tbl_pay PRIMARY KEY (pay_Id),
    CONSTRAINT fk_pay_order FOREIGN KEY (order_id) REFERENCES tbl_order (order_id) ON DELETE SET NULL,
    CONSTRAINT fk_pay_user  FOREIGN KEY (user_id)  REFERENCES tbl_user (user_id) ON DELETE SET NULL
) ENGINE=InnoDB COMMENT='결제 테이블';

-- 2-16. 상품 문의 (product_inq)
CREATE TABLE IF NOT EXISTS tbl_product_inq
(
    inquire_id       INT NOT NULL AUTO_INCREMENT COMMENT '상품 문의 아이디',
    product_id       INT NOT NULL COMMENT '상품 고유 아이디',
    user_id          INT NOT NULL COMMENT '문의글 작성 회원 아이디',
    inquire_title    VARCHAR(100) NULL COMMENT '문의 제목',
    inquire_content  VARCHAR(800) NULL COMMENT '문의 내용',
    inquire_image    VARCHAR(300) NULL COMMENT '문의에 포함된 이미지',
    create_dt        DATETIME NULL COMMENT '문의글 작성일',
    modify_dt        DATETIME NULL COMMENT '문의글 수정일',
    inquire_reply_yn CHAR(1) NULL COMMENT '문의글 답변 여부 (Y/N)',

    CONSTRAINT pk_tbl_product_inq PRIMARY KEY (inquire_id),
    CONSTRAINT fk_inq_product FOREIGN KEY (product_id) REFERENCES tbl_product (product_id) ON DELETE CASCADE,
    CONSTRAINT fk_inq_user    FOREIGN KEY (user_id)    REFERENCES tbl_user (user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='상품 문의 테이블';

-- 2-17. 상품 문의 답변 (product_inq_reply)
CREATE TABLE IF NOT EXISTS tbl_product_inq_reply
(
    inquiry_reply_id       INT NOT NULL AUTO_INCREMENT COMMENT '상품문의 답변 아이디',
    inquire_id             INT NOT NULL COMMENT '상품 문의 아이디',
    inquiry_reply_content  VARCHAR(300) NULL COMMENT '상품 문의 답변 내용',
    inquiry_reply_dt       DATETIME NULL COMMENT '상품 문의 답변 일자',

    CONSTRAINT pk_tbl_product_inq_reply PRIMARY KEY (inquiry_reply_id),
    CONSTRAINT fk_inqreply_inq FOREIGN KEY (inquire_id) REFERENCES tbl_product_inq (inquire_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='상품 문의 답변 테이블';

-- 2-18. 후기 (review)
CREATE TABLE IF NOT EXISTS tbl_review
(
    review_id       INT NOT NULL AUTO_INCREMENT COMMENT '후기 고유 ID',
    user_id         INT NULL COMMENT '후기 작성자',
    product_id      INT NULL COMMENT '상품 아이디',
    orderItem_id    INT NULL COMMENT '주문 상세 항목 고유 ID',
    review_rating   INT NULL COMMENT '1~5점 평점',
    review_comment  TEXT NULL COMMENT '후기 상세 내용',
    create_dt       DATETIME NULL COMMENT '후기 작성 날짜',

    CONSTRAINT pk_tbl_review PRIMARY KEY (review_id),
    CONSTRAINT fk_review_user      FOREIGN KEY (user_id)      REFERENCES tbl_user (user_id) ON DELETE SET NULL,
    CONSTRAINT fk_review_product   FOREIGN KEY (product_id)   REFERENCES tbl_product (product_id) ON DELETE SET NULL,
    CONSTRAINT fk_review_orderitem FOREIGN KEY (orderItem_id) REFERENCES tbl_order_item (orderItem_id) ON DELETE SET NULL
) ENGINE=InnoDB COMMENT='후기 테이블';

-- 2-19. 공지사항 (notice)
CREATE TABLE IF NOT EXISTS tbl_notice
(
    notice_id         INT NOT NULL AUTO_INCREMENT COMMENT '공지사항 아이디',
    user_id           INT NOT NULL COMMENT '작성자 (회원 아이디)',
    notice_title      VARCHAR(100) NOT NULL COMMENT '공지사항 제목',
    notice_content    TEXT NULL COMMENT '공지사항 내용',
    notice_create_dt  DATETIME NULL COMMENT '공지사항 작성일자',
    notice_update_dt  DATETIME NULL COMMENT '공지사항 수정일자',

    CONSTRAINT pk_tbl_notice PRIMARY KEY (notice_id),
    CONSTRAINT fk_notice_user FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='공지사항 테이블';

-- 2-20. Q&A (qna)
CREATE TABLE IF NOT EXISTS tbl_qna
(
    qna_id         INT NOT NULL AUTO_INCREMENT COMMENT 'Q&A 아이디',
    user_id        INT NOT NULL COMMENT '작성자 (회원 아이디)',
    qna_title      VARCHAR(100) NOT NULL COMMENT 'Q&A 제목',
    qna_content    TEXT NULL COMMENT 'Q&A 내용',
    qna_create_dt  DATETIME NULL COMMENT 'Q&A 작성일자',
    qna_update_dt  DATETIME NULL COMMENT 'Q&A 수정일자',
    qna_reply_yn   CHAR(1) NULL DEFAULT 'N' COMMENT 'Q&A 답변 여부 (Y/N)',

    CONSTRAINT pk_tbl_qna PRIMARY KEY (qna_id),
    CONSTRAINT fk_qna_user FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='Q&A 테이블';

-- 2-21. Q&A 답글 (qna_reply)
CREATE TABLE IF NOT EXISTS tbl_qna_reply
(
    qna_reply_id        INT NOT NULL AUTO_INCREMENT COMMENT 'Q&A 답글 아이디',
    user_id             INT NOT NULL COMMENT '작성자 (회원 아이디)',
    qna_id              INT NOT NULL COMMENT 'Q&A 아이디',
    qna_reply_contents  VARCHAR(300) NULL COMMENT 'Q&A 답글 내용',
    create_dt           DATETIME DEFAULT NOW() COMMENT 'Q&A 답글 작성일',
    
    CONSTRAINT pk_tbl_qna_reply PRIMARY KEY (qna_reply_id),
    CONSTRAINT fk_qna_reply_user FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_qnareply_qna FOREIGN KEY (qna_id) REFERENCES tbl_qna (qna_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='Q&A 답글 테이블';

-- 2-22. FAQ (faq)
CREATE TABLE IF NOT EXISTS tbl_faq
(
    faq_id         INT NOT NULL AUTO_INCREMENT COMMENT 'FAQ 아이디',
    user_id        INT NOT NULL COMMENT '작성자(회원 아이디)',
    faq_title      VARCHAR(100) NOT NULL COMMENT 'FAQ 제목',
    faq_content    TEXT NULL COMMENT 'FAQ 내용',
    faq_create_dt  DATETIME NULL COMMENT 'FAQ 작성일자',
    faq_update_dt  DATETIME NULL COMMENT 'FAQ 수정일자',

    CONSTRAINT pk_tbl_faq PRIMARY KEY (faq_id),
    CONSTRAINT fk_faq_user FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='FAQ 테이블';

-- 2-23. 첨부 파일 (attatch)
CREATE TABLE IF NOT EXISTS tbl_attatch
(
    attatch_id        INT NOT NULL AUTO_INCREMENT COMMENT '첨부 고유 ID',
    file_path         VARCHAR(300) NOT NULL COMMENT '첨부 파일의 저장 경로',
    original_filename VARCHAR(300) NULL COMMENT '첨부 파일의 원래 이름',
    filesystem_name   VARCHAR(40) NULL COMMENT '첨부 파일의 저장 이름',
    product_id        INT NOT NULL COMMENT '상품 고유 ID',

    CONSTRAINT pk_tbl_attatch PRIMARY KEY (attatch_id),
    CONSTRAINT fk_attatch_product FOREIGN KEY (product_id) REFERENCES tbl_product (product_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='첨부 파일 테이블';

-- 2-24. 탈퇴 회원 로그 (delete_user_log)
CREATE TABLE IF NOT EXISTS tbl_delete_user_log
(
    user_id            INT NOT NULL COMMENT '회원 아이디',
    user_name          VARCHAR(30) NULL COMMENT '회원이름',
    user_nickname      VARCHAR(50) NULL COMMENT '회원닉네임',
    user_email         VARCHAR(50) NULL COMMENT '회원이메일',
    withdrawal_reason  VARCHAR(300) NULL COMMENT '탈퇴사유',
    delete_dt          DATETIME NULL COMMENT '탈퇴일자',

    CONSTRAINT pk_tbl_delete_user_log PRIMARY KEY (user_id)
) ENGINE=InnoDB COMMENT='탈퇴 회원 로그 테이블';

-- 2-25. 사용자 로그인 로그 (user_login_log)
CREATE TABLE IF NOT EXISTS tbl_user_login_log
(
	login_log_id   INT NOT NULL AUTO_INCREMENT COMMENT '로그인 로그 아이디',
    user_id        INT NOT NULL COMMENT '회원 아이디',
    login_dt       DATETIME NULL COMMENT '로그인 날짜',
    login_browser  VARCHAR(100) NULL COMMENT '로그인 브라우저 정보',
    ip_addr        VARCHAR(50) NULL COMMENT '로그인 아이피',

    CONSTRAINT pk_tbl_user_login_log PRIMARY KEY (login_log_id),
    CONSTRAINT fk_loginlog_user FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='사용자 로그인 로그 테이블';

-- 2-26. 중고상품 첨부 (secondhand_attatch)
CREATE TABLE IF NOT EXISTS tbl_secondhand_attatch
(
    secondhand_attatch_id   INT NOT NULL AUTO_INCREMENT COMMENT '중고상품 첨부 아이디',
    secondhand_id           INT NOT NULL COMMENT '중고상품 아이디',
    secondhand_file_path    VARCHAR(300) NOT NULL COMMENT '중고상품 첨부경로',
    secondhand_org_filename VARCHAR(300) NOT NULL COMMENT '중고상품 원본 파일이름',
    secondhand_sys_filename VARCHAR(40) NOT NULL COMMENT '중고상품 저장 파일이름',

    CONSTRAINT pk_tbl_secondhand_attatch PRIMARY KEY (secondhand_attatch_id),
    CONSTRAINT fk_secondhand_attach FOREIGN KEY (secondhand_id) REFERENCES tbl_secondhand (secondhand_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='중고상품 첨부 테이블';

-- 트리거: 회원 가입 후 자동으로 장바구니 생성 - 신규 추가
DELIMITER //
CREATE TRIGGER trg_create_cart_after_user_insert
AFTER INSERT ON tbl_user
FOR EACH ROW
BEGIN
    INSERT INTO tbl_cart(user_id, cart_created_dt) 
    VALUES (NEW.user_id, NOW());
END;
//
DELIMITER ;

-- 트리거: 회원 삭제(탈퇴)시 탈퇴 테이블에 입력
DELIMITER $$
CREATE TRIGGER trg_after_delete_user
AFTER DELETE ON tbl_user
FOR EACH ROW
BEGIN
    -- 삭제된 회원 정보를 탈퇴 회원 로그 테이블에 삽입
    INSERT INTO tbl_delete_user_log (user_id, user_name, user_nickname, user_email, withdrawal_reason, delete_dt)
    VALUES (
        OLD.user_id,            -- 삭제된 회원의 아이디
        OLD.user_name,          -- 삭제된 회원의 이름
        OLD.user_nickname,      -- 삭제된 회원의 닉네임
        OLD.user_email,         -- 삭제된 회원의 이메일
        '사용자 탈퇴',          -- 탈퇴 사유 (기본값 설정)
        NOW()                   -- 현재 시간 (탈퇴 일자)
    );
END$$
DELIMITER ;
-- ----------------------------------------------------------
-- 생성 완료
-- ----------------------------------------------------------
