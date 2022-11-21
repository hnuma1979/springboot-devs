CREATE TABLE IF NOT EXISTS `users` (
  `id`          int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username`    varchar(255) COLLATE 'ascii_bin' NOT NULL,
  `email`       varchar(255) COLLATE 'ascii_bin' NOT NULL,
  `password`    varchar(255) COLLATE 'ascii_bin' NOT NULL,
  `enabled`     bit(1) NOT NULL,
  `locked`      bit(1) NOT NULL,
  `created`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
                                ON UPDATE CURRENT_TIMESTAMP,
  `deleted`     date NULL,
  `hash`        varchar(255) COLLATE 'ascii_bin' NULL,
  `hash_exp`    datetime NULL
) COLLATE 'utf8mb4_general_ci';