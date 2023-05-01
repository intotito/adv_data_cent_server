package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.Student;

public interface StudentRepo extends CrudRepository<Student, Integer> {
	@Query(value="SELECT s FROM Student s WHERE s.sid = :#{[0]}")
	public Optional<Student> findStudentBySid(String lid);
}
