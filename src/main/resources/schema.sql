CREATE TABLE IF NOT EXISTS `absences` (
                                          `absenceId` int(11) NOT NULL AUTO_INCREMENT,
                                          `userId` int(11) DEFAULT NULL,
                                          `startDate` date DEFAULT NULL,
                                          `endDate` date DEFAULT NULL,
                                          `type` varchar(255) DEFAULT NULL,
                                          `description` text DEFAULT NULL,
                                          PRIMARY KEY (`absenceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `alerts` (
                                        `alertId` int(11) NOT NULL AUTO_INCREMENT,
                                        `taskId` int(11) DEFAULT NULL,
                                        `alertDate` date DEFAULT NULL,
                                        `message` text DEFAULT NULL,
                                        PRIMARY KEY (`alertId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `permissions` (
                                             `permissionId` int(11) NOT NULL AUTO_INCREMENT,
                                             `permissionName` varchar(255) NOT NULL,
                                             `description` text DEFAULT NULL,
                                             PRIMARY KEY (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `projects` (
                                          `projectId` int(11) NOT NULL AUTO_INCREMENT,
                                          `projectName` varchar(255) NOT NULL,
                                          `startDate` date DEFAULT NULL,
                                          `endDate` date DEFAULT NULL,
                                          `status` varchar(255) DEFAULT NULL,
                                          PRIMARY KEY (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `rolepermissions` (
                                                 `roleId` int(11) NOT NULL,
                                                 `permissionId` int(11) NOT NULL,
                                                 PRIMARY KEY (`roleId`, `permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `roles` (
                                       `roleId` int(11) NOT NULL AUTO_INCREMENT,
                                       `roleName` varchar(255) NOT NULL,
                                       `description` text DEFAULT NULL,
                                       PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `tasks` (
                                       `taskId` int(11) NOT NULL AUTO_INCREMENT,
                                       `projectId` int(11) DEFAULT NULL,
                                       `userId` int(11) DEFAULT NULL,
                                       `taskName` varchar(255) NOT NULL,
                                       `startDate` date DEFAULT NULL,
                                       `endDate` date DEFAULT NULL,
                                       `manDays` int(11) DEFAULT NULL,
                                       `cost` decimal(10,2) DEFAULT NULL,
                                       `progress` decimal(5,2) DEFAULT NULL,
                                       PRIMARY KEY (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `teams` (
                                       `teamId` int(11) NOT NULL AUTO_INCREMENT,
                                       `projectId` int(11) DEFAULT NULL,
                                       `teamName` varchar(255) NOT NULL,
                                       `description` text DEFAULT NULL,
                                       PRIMARY KEY (`teamId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `userroles` (
                                           `userId` int(11) NOT NULL,
                                           `roleId` int(11) NOT NULL,
                                           PRIMARY KEY (`userId`, `roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `users` (
                                       `userId` int(11) NOT NULL AUTO_INCREMENT,
                                       `username` varchar(255) NOT NULL,
                                       `password` longtext NOT NULL,
                                       `email` varchar(255) NOT NULL,
                                       `role` varchar(255) DEFAULT NULL,
                                       PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `userteams` (
                                           `userId` int(11) NOT NULL,
                                           `teamId` int(11) NOT NULL,
                                           PRIMARY KEY (`userId`, `teamId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
 