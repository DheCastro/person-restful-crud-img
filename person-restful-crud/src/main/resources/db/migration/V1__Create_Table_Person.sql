CREATE TABLE `person-restful-crud`.`person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(80) NOT NULL,
  `first_name` varchar(80) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `last_name` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
)