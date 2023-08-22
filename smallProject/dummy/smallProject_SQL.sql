-- 더미테이블 집어넣기

-- 회원 더미테이블
INSERT INTO smallproject.`member`
(member_id, created_by, modified_by, reg_time, update_time, address, email, name, password, `role`)
VALUES(1, 'anonymousUser', 'anonymousUser', '2023-08-17 15:54:14.730963', '2023-08-17 15:54:14.730963', '관리자', 'admin@admin.com', '관리자', '$2a$10$pl3GjBLQij5Sb.joDaHsH.Lw2/GPqczzxLLk.mwvUvWkuTK4wLLGi', 'ADMIN');



-- 아이템 더미테이블

INSERT INTO smallproject.item
(item_id, created_by, modified_by, reg_time, update_time, item_detail, item_nm, item_sell_status, price, stock_number)
VALUES(10, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:37:05.036739', '2023-08-22 15:37:05.036739', '추억의 재미를 그대로!
포트리스 닌텐도 스위치', '포트리스', 'SELL', 55900, 100);
INSERT INTO smallproject.item
(item_id, created_by, modified_by, reg_time, update_time, item_detail, item_nm, item_sell_status, price, stock_number)
VALUES(16, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:13.639339', '2023-08-22 15:42:13.639339', '닌텐도 스위치로 돌아오다!
대 마계촌!', '마계촌 스위치', 'SELL', 39800, 100);
INSERT INTO smallproject.item
(item_id, created_by, modified_by, reg_time, update_time, item_detail, item_nm, item_sell_status, price, stock_number)
VALUES(22, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:54.517482', '2023-08-22 15:42:54.517482', '포켓몬스터 스칼렛', '포켓몬스터 스칼렛', 'SOLD_OUT', 69900, 0);
INSERT INTO smallproject.item
(item_id, created_by, modified_by, reg_time, update_time, item_detail, item_nm, item_sell_status, price, stock_number)
VALUES(28, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:44:30.828126', '2023-08-22 15:44:30.828126', '극강의 FPS 스플래툰3!!', '극강의 FPS 스플래툰3', 'SELL', 29900, 50);
INSERT INTO smallproject.item
(item_id, created_by, modified_by, reg_time, update_time, item_detail, item_nm, item_sell_status, price, stock_number)
VALUES(34, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:05.055639', '2023-08-22 15:46:05.055639', '다같이 목장을 운영하며 힐링해보자', '목장이야기 - 원더풀 라이프', 'SELL', 35000, 30);
INSERT INTO smallproject.item
(item_id, created_by, modified_by, reg_time, update_time, item_detail, item_nm, item_sell_status, price, stock_number)
VALUES(40, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:56.746595', '2023-08-22 15:46:56.746595', '젤다는 사실 표지 주인공 이름이 아니다.', '젤다의 전설 -  티어스 오브 더 킹덤', 'SELL', 68900, 1000);


-- 아이템 이미지 더미테이블

INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(11, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:37:05.217868', '2023-08-22 15:37:05.217868', '1e028b46-8eb2-43a9-afe1-f36bdae47778.jpg', '/images/item/1e028b46-8eb2-43a9-afe1-f36bdae47778.jpg', 'fotress.jpg', 'Y', 10);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(12, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:37:05.419747', '2023-08-22 15:37:05.419747', '71697a2a-a449-401c-b90f-97b208b2322b.jpg', '/images/item/71697a2a-a449-401c-b90f-97b208b2322b.jpg', 'fotress2.jpg', 'N', 10);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(13, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:37:05.546753', '2023-08-22 15:37:05.546753', '', '', '', 'N', 10);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(14, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:37:05.631770', '2023-08-22 15:37:05.631770', '', '', '', 'N', 10);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(15, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:37:05.757714', '2023-08-22 15:37:05.757714', '', '', '', 'N', 10);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(17, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:13.693805', '2023-08-22 15:42:13.693805', '9b6c4ac1-c9f1-4782-a268-19ee7912562f.jpg', '/images/item/9b6c4ac1-c9f1-4782-a268-19ee7912562f.jpg', 'magae.jpg', 'Y', 16);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(18, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:13.725276', '2023-08-22 15:42:13.725276', '1fb4d882-8436-4065-a4f1-de130e4d279e.jpg', '/images/item/1fb4d882-8436-4065-a4f1-de130e4d279e.jpg', 'magae2.jpg', 'N', 16);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(19, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:13.762357', '2023-08-22 15:42:13.762357', '', '', '', 'N', 16);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(20, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:13.790533', '2023-08-22 15:42:13.790533', '', '', '', 'N', 16);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(21, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:13.819433', '2023-08-22 15:42:13.819433', '', '', '', 'N', 16);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(23, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:54.613980', '2023-08-22 15:42:54.613980', 'e78042fe-9528-4732-9582-d933c5e16acb.jpg', '/images/item/e78042fe-9528-4732-9582-d933c5e16acb.jpg', 'poketmon.jpg', 'Y', 22);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(24, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:54.727028', '2023-08-22 15:42:54.727028', 'e5568e77-f424-412c-a545-a3cfb7142f03.jpg', '/images/item/e5568e77-f424-412c-a545-a3cfb7142f03.jpg', 'poketmon2.jpg', 'N', 22);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(25, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:54.836014', '2023-08-22 15:42:54.836014', '', '', '', 'N', 22);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(26, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:55.027753', '2023-08-22 15:42:55.027753', '', '', '', 'N', 22);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(27, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:42:55.107831', '2023-08-22 15:42:55.107831', '', '', '', 'N', 22);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(29, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:44:31.100370', '2023-08-22 15:44:31.100370', '5429e038-23af-4ff9-9e84-6f6a72a01412.jpg', '/images/item/5429e038-23af-4ff9-9e84-6f6a72a01412.jpg', 'splatoon3.jpg', 'Y', 28);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(30, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:44:31.247483', '2023-08-22 15:44:31.247483', '8dc51dee-abab-44da-989d-0e364469e95c.jpg', '/images/item/8dc51dee-abab-44da-989d-0e364469e95c.jpg', 'splatoon3_2.jpg', 'N', 28);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(31, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:44:31.345491', '2023-08-22 15:44:31.345491', '', '', '', 'N', 28);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(32, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:44:31.489506', '2023-08-22 15:44:31.489506', '', '', '', 'N', 28);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(33, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:44:31.829532', '2023-08-22 15:44:31.829532', '', '', '', 'N', 28);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(35, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:05.469587', '2023-08-22 15:46:05.469587', '9fdcc2b5-ddff-400c-814a-aaa64d86521c.jpg', '/images/item/9fdcc2b5-ddff-400c-814a-aaa64d86521c.jpg', 'wonderful.jpg', 'Y', 34);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(36, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:06.032772', '2023-08-22 15:46:06.032772', '5c3d81a0-f34a-4549-b4fe-ce1880c75950.jpg', '/images/item/5c3d81a0-f34a-4549-b4fe-ce1880c75950.jpg', 'wonderful2.jpg', 'N', 34);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(37, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:06.245582', '2023-08-22 15:46:06.245582', '', '', '', 'N', 34);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(38, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:07.103770', '2023-08-22 15:46:07.103770', '', '', '', 'N', 34);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(39, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:07.463834', '2023-08-22 15:46:07.463834', '', '', '', 'N', 34);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(41, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:56.758068', '2023-08-22 15:46:56.758068', '99a5c20f-43a9-4010-b4be-1654ca0787c0.jpg', '/images/item/99a5c20f-43a9-4010-b4be-1654ca0787c0.jpg', 'zelda1.jpg', 'Y', 40);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(42, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:56.775951', '2023-08-22 15:46:56.775951', 'e28cbd52-b31f-477f-94b1-5d5bb3d9b22a.jpg', '/images/item/e28cbd52-b31f-477f-94b1-5d5bb3d9b22a.jpg', 'zelda2.jpg', 'N', 40);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(43, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:56.789963', '2023-08-22 15:46:56.789963', '', '', '', 'N', 40);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(44, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:56.806979', '2023-08-22 15:46:56.806979', '', '', '', 'N', 40);
INSERT INTO smallproject.item_img
(item_img_id, created_by, modified_by, reg_time, update_time, img_name, img_url, ori_img_name, repimg_yn, item_id)
VALUES(45, 'admin@admin.com', 'admin@admin.com', '2023-08-22 15:46:56.818543', '2023-08-22 15:46:56.818543', '', '', '', 'N', 40);
