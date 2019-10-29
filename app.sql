-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 29, 2019 at 01:10 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `app`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `account_number` varchar(6) NOT NULL,
  `balance` double DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `pin` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`account_number`, `balance`, `name`, `pin`) VALUES
('000001', 1490, 'Rizky', '000001'),
('000002', 1640, 'Yoga', '000002'),
('000003', 1000, 'Oktora', '000003'),
('000004', 1000, 'Jean', '000004'),
('000005', 1000, 'Bruce', '000005'),
('000006', 1000, 'Diana', '000006'),
('000007', 1000, 'Clark', '000007'),
('000008', 1000, 'Hal', '000008'),
('000009', 1000, 'Tiara', '000009'),
('000010', 1000, 'Tony', '000010'),
('000011', 1000, 'Steve', '000011'),
('000012', 1000, 'Natasha', '000012'),
('000013', 1000, 'Mike', '000013'),
('000014', 1000, 'Angel', '000014'),
('000015', 1000, 'Leo', '000015'),
('000016', 1000, 'Buble', '000016'),
('000017', 1000, 'Risa', '000017'),
('000018', 1000, 'Dola', '000018'),
('100000', 1000, 'java', '100000'),
('112233', 1000, 'John Doe', '012108'),
('112244', 1300, 'Jane Doe', '932012'),
('200000', 1000, 'java2', '200000');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` bigint(20) NOT NULL,
  `amount` double DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `account` varchar(6) NOT NULL,
  `destination_account` varchar(6) DEFAULT NULL,
  `reference` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `amount`, `date`, `type`, `account`, `destination_account`, `reference`) VALUES
(299, 10, '2019-10-29 12:52:04', 'TRANSFER', '100000', '200000', '000000'),
(300, 20, '2019-10-29 12:52:04', 'TRANSFER', '100000', '200000', '000001'),
(301, 30, '2019-10-29 12:52:04', 'TRANSFER', '100000', '200000', '000002'),
(302, 40, '2019-10-29 12:52:04', 'TRANSFER', '100000', '200000', '000003'),
(303, 50, '2019-10-29 12:52:04', 'TRANSFER', '100000', '200000', '000004'),
(304, 10, '2019-10-29 12:53:51', 'WITHDRAW', '000001', NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_number`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKflw7pgdaxqqtc83ru6m214qh9` (`account`),
  ADD KEY `FK96h2rq3ilp8xwf6rt0eq19swq` (`destination_account`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=305;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `FK96h2rq3ilp8xwf6rt0eq19swq` FOREIGN KEY (`destination_account`) REFERENCES `account` (`account_number`),
  ADD CONSTRAINT `FKflw7pgdaxqqtc83ru6m214qh9` FOREIGN KEY (`account`) REFERENCES `account` (`account_number`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
