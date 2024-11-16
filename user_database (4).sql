-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 16, 2024 at 03:20 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `user_database`
--

-- --------------------------------------------------------

--
-- Table structure for table `analisiskesehatan`
--

CREATE TABLE `analisiskesehatan` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `umur` int(11) NOT NULL,
  `jenis_kelamin` enum('Pria','Wanita') NOT NULL,
  `berat` decimal(5,2) NOT NULL,
  `tinggi` int(11) NOT NULL,
  `bmi` decimal(5,2) NOT NULL,
  `kategori_bmi` varchar(50) NOT NULL,
  `risiko_bmi` text NOT NULL,
  `sistolik` int(11) NOT NULL,
  `diastolik` int(11) NOT NULL,
  `status_tekanan_darah` varchar(50) NOT NULL,
  `gula_darah` int(11) NOT NULL,
  `tipe_gula_darah` enum('puasa','sewaktu') NOT NULL,
  `status_gula_darah` varchar(50) NOT NULL,
  `tanggal_pemeriksaan` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `edukasi_kesehatan`
--

CREATE TABLE `edukasi_kesehatan` (
  `id` int(11) NOT NULL,
  `judul` varchar(255) NOT NULL,
  `konten` text NOT NULL,
  `gambar` varchar(255) DEFAULT NULL,
  `video` varchar(255) DEFAULT NULL,
  `tanggal_dibuat` timestamp NOT NULL DEFAULT current_timestamp(),
  `video_file` varchar(255) DEFAULT NULL,
  `user_id` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `edukasi_kesehatan`
--

INSERT INTO `edukasi_kesehatan` (`id`, `judul`, `konten`, `gambar`, `video`, `tanggal_dibuat`, `video_file`, `user_id`) VALUES
(49, 'KESEHATAN JASMANI', 'qwert', 'uploads/Screenshot 2024-11-08 234833.png', 'https://youtu.be/IRr35PDkU8g?si=hc2cyahp-eKQFFjv', '2024-11-14 00:06:34', '', 13),
(50, 'KESEHATAN JASMANI', 'qwert', '', 'https://youtu.be/IRr35PDkU8g?si=hc2cyahp-eKQFFjv', '2024-11-14 00:50:54', '', 13);

-- --------------------------------------------------------

--
-- Table structure for table `inventaris_uks`
--

CREATE TABLE `inventaris_uks` (
  `id_barang` int(11) NOT NULL,
  `nama_barang` varchar(255) NOT NULL,
  `kategori` varchar(100) DEFAULT NULL,
  `jumlah` int(11) NOT NULL,
  `satuan` varchar(50) DEFAULT NULL,
  `tanggal_masuk` date DEFAULT NULL,
  `tanggal_kadaluarsa` date DEFAULT NULL,
  `kondisi` enum('Baik','Rusak','Habis') DEFAULT 'Baik',
  `lokasi` varchar(100) DEFAULT NULL,
  `keterangan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `inventaris_uks`
--

INSERT INTO `inventaris_uks` (`id_barang`, `nama_barang`, `kategori`, `jumlah`, `satuan`, `tanggal_masuk`, `tanggal_kadaluarsa`, `kondisi`, `lokasi`, `keterangan`) VALUES
(14, 'tempat tidur', 'Peralatan', 2, 'Unit', '2024-11-14', '2024-11-30', 'Baik', 'Lemari 1', 'KONDISI BAIK');

-- --------------------------------------------------------

--
-- Table structure for table `monitoringkesehatan`
--

CREATE TABLE `monitoringkesehatan` (
  `id` int(11) NOT NULL,
  `tanggal` datetime DEFAULT current_timestamp(),
  `nama` varchar(100) NOT NULL,
  `nis` varchar(100) DEFAULT NULL,
  `kelas` varchar(50) NOT NULL,
  `keluhan` text DEFAULT NULL,
  `diagnosis` text DEFAULT NULL,
  `pertolongan_pertama` text DEFAULT NULL,
  `suhu` float DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `pengingat_id` int(15) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `monitoringkesehatan`
--

INSERT INTO `monitoringkesehatan` (`id`, `tanggal`, `nama`, `nis`, `kelas`, `keluhan`, `diagnosis`, `pertolongan_pertama`, `suhu`, `status`, `pengingat_id`, `user_id`) VALUES
(123456800, '2024-11-14 15:52:59', 'MUSA HABIBULLOH AL FARUQ', '1234567', '12 IPS 2', 'Pusing', 'DEMAM', 'ISTIRAHAT', 37, 'Sakit', 49, 13);

-- --------------------------------------------------------

--
-- Table structure for table `penghubung`
--

CREATE TABLE `penghubung` (
  `id` int(11) NOT NULL,
  `patient_id` varchar(20) NOT NULL,
  `device_token` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pengingatobat`
--

CREATE TABLE `pengingatobat` (
  `id` int(11) NOT NULL,
  `patient_id` varchar(20) NOT NULL,
  `condition_name` varchar(255) NOT NULL,
  `severity` varchar(50) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp(),
  `monitoring_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pengingatobat`
--

INSERT INTO `pengingatobat` (`id`, `patient_id`, `condition_name`, `severity`, `timestamp`, `monitoring_id`, `user_id`) VALUES
(49, '1234567', 'DEMAM', 'Medium', '2024-11-14 08:52:59', 123456800, 13);

-- --------------------------------------------------------

--
-- Table structure for table `rekam_kesehatan`
--

CREATE TABLE `rekam_kesehatan` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `nis` varchar(20) NOT NULL,
  `keluhan` text NOT NULL,
  `diagnosis` text NOT NULL,
  `Pertolongan_Pertama` text NOT NULL,
  `tanggal` timestamp NOT NULL DEFAULT current_timestamp(),
  `user_id` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rekam_kesehatan`
--

INSERT INTO `rekam_kesehatan` (`id`, `nama`, `nis`, `keluhan`, `diagnosis`, `Pertolongan_Pertama`, `tanggal`, `user_id`) VALUES
(123456812, 'MUSA HABIBULLOH AL FARUQ', '1234567', 'Pusing', 'DEMAM', 'ISTIRAHAT', '2024-11-14 08:52:59', 13);

-- --------------------------------------------------------

--
-- Table structure for table `siswa`
--

CREATE TABLE `siswa` (
  `id` int(11) NOT NULL,
  `Nama` text NOT NULL,
  `Kelas` text NOT NULL,
  `Suhu` double NOT NULL,
  `Status` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `stok_obat`
--

CREATE TABLE `stok_obat` (
  `id_stok` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jumlah` int(10) NOT NULL,
  `diperbarui` date NOT NULL,
  `tanggal_kadaluarsa` varchar(20) NOT NULL,
  `id_pengingat` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `nama_lengkap` varchar(100) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `nis` varchar(20) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `telepon` varchar(20) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `tanggal_lahir` date DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'siswa'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `foto`, `nama_lengkap`, `username`, `nis`, `email`, `telepon`, `alamat`, `tanggal_lahir`, `password`, `role`) VALUES
(13, NULL, NULL, 'admin', NULL, 'admin@example.com', NULL, NULL, NULL, '0192023a7bbd73250516f069df18b500', 'admin'),
(23, 'avatar_Nanda.png', 'Nanda Rayhan', 'Nanda', '123456789', 'budinanda177@gmail.com', '081357510843', 'perum taman gading', '2005-04-21', 'e10adc3949ba59abbe56e057f20f883e', 'siswa'),
(24, NULL, 'Nanda Rayhan', 'rafi', '123456', 'smamuhammadiyah@gmail.com', NULL, NULL, NULL, 'e10adc3949ba59abbe56e057f20f883e', 'siswa'),
(25, NULL, NULL, 'mencoba', NULL, 'mencoba', NULL, NULL, NULL, 'mencoba', 'siswa'),
(26, NULL, NULL, 'bismillah1', NULL, 'bismillah1', NULL, NULL, NULL, 'ea2fdc3273d3c1343956181037d1fdc26b65d01297e14404b7231d0651003ccf', 'siswa'),
(27, NULL, NULL, 'apalah', NULL, 'apalah', NULL, NULL, NULL, '0a1d4156cf574caf8439d0ec921c8cf32abc0c228999dc8e0dccaf1c3e866fb8', 'siswa'),
(28, NULL, NULL, 'test', NULL, 'test', NULL, NULL, NULL, '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 'siswa'),
(29, NULL, NULL, 'bisakah', NULL, 'bisakah', NULL, NULL, NULL, 'de6f606aa1f24627637cf9b11413fa170af9664fd3d35742093846fddd17cad4', 'siswa'),
(30, NULL, NULL, 'musahabibulloh', NULL, 'musahabibulloh@gmail.com', NULL, NULL, NULL, '9d4b2db6ead822d585b0d6b78b2f804b3f94dc485e19feb3698886635819f235', 'siswa'),
(31, NULL, 'apasih', 'apasih', '432424', 'apasih@gmail.com', NULL, NULL, NULL, 'acb00107158d8d08c1492c67b1676c15', 'admin'),
(32, NULL, NULL, 'hehehe', NULL, 'hehehe@gmail.com', NULL, NULL, NULL, 'f58262c8005bb64b8f99ec6083faf050c502d099d9929ae37ffed2fe1bb954fb', 'siswa'),
(33, NULL, NULL, 'bimasaka', NULL, 'bimasaka@gmail.com', NULL, NULL, NULL, '3c229d20ebd4d27659d764fff0128d9711e1e947286a6e9cc477a855302a720e', 'admin'),
(34, NULL, 'bimasakaa', 'bimasakaa', '21312312', 'danmuslihh5@gmail.com', NULL, NULL, NULL, '3edc831ca50b9712b8d489328e4ceb17', 'admin'),
(35, NULL, NULL, 'Musa', NULL, 'musahabibullah3@gmail.com', NULL, NULL, NULL, '9d4b2db6ead822d585b0d6b78b2f804b3f94dc485e19feb3698886635819f235', 'siswa');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `analisiskesehatan`
--
ALTER TABLE `analisiskesehatan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `edukasi_kesehatan`
--
ALTER TABLE `edukasi_kesehatan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `inventaris_uks`
--
ALTER TABLE `inventaris_uks`
  ADD PRIMARY KEY (`id_barang`);

--
-- Indexes for table `monitoringkesehatan`
--
ALTER TABLE `monitoringkesehatan`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nis` (`nis`),
  ADD KEY `user_id` (`pengingat_id`),
  ADD KEY `fk_monitoring_user` (`user_id`);

--
-- Indexes for table `penghubung`
--
ALTER TABLE `penghubung`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id_2` (`user_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `pengingatobat`
--
ALTER TABLE `pengingatobat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `monitoring_id` (`monitoring_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `rekam_kesehatan`
--
ALTER TABLE `rekam_kesehatan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `siswa`
--
ALTER TABLE `siswa`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stok_obat`
--
ALTER TABLE `stok_obat`
  ADD KEY `id_pengingat` (`id_pengingat`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nis` (`nis`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `analisiskesehatan`
--
ALTER TABLE `analisiskesehatan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `edukasi_kesehatan`
--
ALTER TABLE `edukasi_kesehatan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `inventaris_uks`
--
ALTER TABLE `inventaris_uks`
  MODIFY `id_barang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `monitoringkesehatan`
--
ALTER TABLE `monitoringkesehatan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123456846;

--
-- AUTO_INCREMENT for table `penghubung`
--
ALTER TABLE `penghubung`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pengingatobat`
--
ALTER TABLE `pengingatobat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=79;

--
-- AUTO_INCREMENT for table `rekam_kesehatan`
--
ALTER TABLE `rekam_kesehatan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123456831;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `edukasi_kesehatan`
--
ALTER TABLE `edukasi_kesehatan`
  ADD CONSTRAINT `edukasi_kesehatan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `monitoringkesehatan`
--
ALTER TABLE `monitoringkesehatan`
  ADD CONSTRAINT `fk_monitoring_pengingat` FOREIGN KEY (`pengingat_id`) REFERENCES `pengingatobat` (`id`),
  ADD CONSTRAINT `fk_monitoring_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `pengingatobat`
--
ALTER TABLE `pengingatobat`
  ADD CONSTRAINT `pengingatobat_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `rekam_kesehatan`
--
ALTER TABLE `rekam_kesehatan`
  ADD CONSTRAINT `rekam_kesehatan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `stok_obat`
--
ALTER TABLE `stok_obat`
  ADD CONSTRAINT `stok_obat_ibfk_1` FOREIGN KEY (`id_pengingat`) REFERENCES `pengingatobat` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
