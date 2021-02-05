-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 27, 2021 at 01:33 PM
-- Server version: 8.0.21
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotelmanagement`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
CREATE TABLE IF NOT EXISTS `booking` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `IC_Passport` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Contact_Number` varchar(255) NOT NULL,
  `Checkin` date NOT NULL,
  `Checkout` date NOT NULL,
  `Stay` int NOT NULL,
  `Room_Id` int NOT NULL,
  `Payment_Status` varchar(10) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `Room_Id` (`Room_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`Id`, `Name`, `IC_Passport`, `Email`, `Contact_Number`, `Checkin`, `Checkout`, `Stay`, `Room_Id`, `Payment_Status`) VALUES
(38, 'Jamal ', '010212101543', 'jamal@mail.com', '0132323232', '2021-01-30', '2021-02-19', 20, 108, 'Pending'),
(42, 'Maire', '990102141543', 'marie@mail.com', '01932341110', '2021-01-29', '2021-01-30', 1, 206, 'Pending'),
(44, 'Dyson', '981212121422', 'vac@gmail.com', '0163443999', '2021-01-29', '2021-01-31', 2, 104, 'Paid'),
(46, 'James', '010912101568', 'james@gmail.com', '0191300133', '2021-01-29', '2021-01-30', 1, 109, 'Paid'),
(47, 'Manson', '981010101293', 'man@yahoo.com', '0132911377', '2021-01-29', '2021-01-31', 2, 107, 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  `Id` int NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`Id`) VALUES
(101),
(102),
(103),
(104),
(105),
(106),
(107),
(108),
(109),
(110),
(201),
(202),
(203),
(204),
(205),
(206),
(207),
(208),
(209),
(210);

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `Id` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`Id`) VALUES
('deeddededdedd'),
('deeddededdedd'),
('deeddededdedd'),
('deeddededdedd'),
('deeddededdedd');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `room` FOREIGN KEY (`Room_Id`) REFERENCES `room` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
