delimiter $$

CREATE TABLE `users` (
  `userID` varchar(32) NOT NULL,
  `print1` varbinary(4000) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `id_UNIQUE` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

EDIT `uareu`.`users`;