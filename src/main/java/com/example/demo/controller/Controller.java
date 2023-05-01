package com.example.demo.controller;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.exceptions.LecturerExistsException;
import com.example.demo.exceptions.StudentExistsException;
import com.example.demo.models.Lecturer;
import com.example.demo.models.Message;
import com.example.demo.models.Student;
import com.example.demo.services.LecturerService;
import com.example.demo.services.StudentService;
import com.example.demo.validation.LecturerPOSTValidation;
import com.example.demo.validation.LecturerPUTValidation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
@RestController
@Validated
public class Controller {
	@Autowired
	private LecturerService ls;
	@Autowired
	private StudentService ss;

	@CrossOrigin
	@GetMapping("lecturers")
	public Iterable<Lecturer> getLecturers() {
		return ls.getLecturers();
	}
	@CrossOrigin
	@GetMapping("lecturer/{lid}")
	public Lecturer getLecturer(@PathVariable(value="lid") String lid) {
		Lecturer aa = ls.getLecturer(lid);
		System.out.println("This is the response " +  aa);
		return aa;
	}
	@PostMapping("lecturer")
	@Validated(LecturerPOSTValidation.class)
	public ResponseEntity<Lecturer> saveLecturer(@Valid @RequestBody Lecturer lecturer) {
		try {
			return ResponseEntity.ok(ls.saveLecturer(lecturer));
		}catch(LecturerExistsException lee) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, lee.getMessage());
		}
		
	}
	@CrossOrigin
	@PutMapping("/lecturer/{lid}")
	@Validated(LecturerPUTValidation.class)
	public ResponseEntity<Lecturer> udpateLectuter(@PathVariable(value="lid") String lid, @Valid @RequestBody Lecturer lecturer){
		//lecturer.setLid(lid);
		return ResponseEntity.ok(ls.updateLecturer(lid, lecturer));
	}
	
	@CrossOrigin
	@GetMapping("/module/lecturer/{lid}")
	public ResponseEntity<List<com.example.demo.models.Module>> getModuleForLecturer(@PathVariable(value="lid") String lid){
		//lecturer.setLid(lid);
		return ResponseEntity.ok(ls.getLecturer(lid).getModules());
	}
	
	@CrossOrigin
	@GetMapping("/module/student/{sid}")
	public ResponseEntity<List<com.example.demo.models.Module>> getModuleForStudent(@PathVariable(value="sid") String sid){
		//lecturer.setLid(lid);
		return ResponseEntity.ok(ss.getStudent(sid).getModules());
	}
	
	
	
	@GetMapping("lecturer/search")
	public ResponseEntity<Iterable<Lecturer>> searchLecturer(@NotNull(message="") @RequestParam String taxBand, @RequestParam Optional<Integer> salaryScale){
		return ResponseEntity.ok(ls.searchLecturer(taxBand, salaryScale.orElse(0)));
	}
	@CrossOrigin
	@GetMapping("students")
	public ResponseEntity<Iterable<Student>> getStudents(){
		return ResponseEntity.ok(ss.getStudents());
	}

	
	@CrossOrigin
	@GetMapping("student/{sid}")
	public ResponseEntity<Student> getStudent(@PathVariable(value="sid") String sid){
		return ResponseEntity.ok(ss.getStudent(sid));
	}
	
	@CrossOrigin
	@DeleteMapping("students/{sid}")
	public ResponseEntity<Message> deleteStudent(@PathVariable(value="sid") String sid){
		try {
		ss.deleteStudent(sid);
		}catch(StudentExistsException see) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, see.getMessage());
		}
		Message message = new Message();
		message.setMessage(String.format("Student with Sid {%s} Deleted successfully", sid));
		message.setPath(String.format("/students/%s", sid));
		message.setTimestamp(Instant.now().toString());
		return ResponseEntity.ok(message);
	}
}
