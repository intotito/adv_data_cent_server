package com.example.demo.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.StudentExistsException;
import com.example.demo.models.Student;
import com.example.demo.repository.StudentRepo;

@Service
public class StudentService {
	@Autowired
	private StudentRepo repo;
	
	public Iterable<Student> getStudents(){
		return repo.findAll();
	}
	
	public void deleteStudent(String sid) {
		try {
			Student savedStudent = repo.findStudentBySid(sid).get();
			if(savedStudent.getModules().size() > 0) {
				throw new StudentExistsException(String.format("Student {%s} cannot be deleted. He/she has associated modules", sid));
			}
			repo.delete(savedStudent);
		}catch(NoSuchElementException nsee) {
			throw new StudentExistsException(String.format("Student {%s} Does not exist", sid));
		}
	}
}
