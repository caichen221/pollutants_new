-- test_jooq.jooq_test_table definition

CREATE TABLE `jooq_test_table` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;