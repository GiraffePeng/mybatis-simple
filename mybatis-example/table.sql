CREATE TABLE `member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `phone` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `member` (`id`, `name`, `phone`) VALUES ('1', '张三', '157xxxxxxxx');

CREATE TABLE `purchase_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL COMMENT '关联的用户外键',
  `purchase_price` decimal(5,2) NOT NULL COMMENT '消费金额',
  `purchase_name` varchar(50) NOT NULL COMMENT '消费商品名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `purchase_log` (`id`, `member_id`, `purchase_price`, `purchase_name`) VALUES ('1', '1', '12.20', '洗发水');
INSERT INTO `purchase_log` (`id`, `member_id`, `purchase_price`, `purchase_name`) VALUES ('2', '1', '22.20', '辣条一包');

ALTER TABLE `member`
ADD COLUMN `detail` varchar(200) NULL COMMENT '会员明细信息' AFTER `phone`;