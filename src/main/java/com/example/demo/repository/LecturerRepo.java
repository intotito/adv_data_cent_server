package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.Lecturer;

public interface LecturerRepo extends CrudRepository<Lecturer, Integer> {
	@Query(value="SELECT l FROM Lecturer l WHERE l.lid = :#{[0]}")
	public Optional<Lecturer> findLecturerByLid(String lid);
	
	@Query(value="SELECT l FROM Lecturer l WHERE l.taxBand = :#{[0]} AND l.salaryScale > :#{[1]}")
	public Iterable<Lecturer> searchLecturers(String taxBand, Integer salaryScale);
}
