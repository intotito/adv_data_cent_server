package com.example.demo.services;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.LecturerExistsException;
import com.example.demo.models.Lecturer;
import com.example.demo.repository.LecturerRepo;

@Service
public class LecturerService {
	@Autowired
	private LecturerRepo repo;

	public Iterable<Lecturer> getLecturers() {
		return repo.findAll();
	}

	public Lecturer getLecturer(String lid) {
		Lecturer savedLecturer = null;
		try {
			savedLecturer = repo.findLecturerByLid(lid).get();
		} catch (NoSuchElementException nse) {
			throw new LecturerExistsException(String.format("Lecturer with Lid {%s} does not Exist", lid));
		}
		return savedLecturer;
	}

	public Lecturer saveLecturer(Lecturer lecturer) {
		try {
			return repo.save(lecturer);
		} catch (DataIntegrityViolationException dive) {
			throw new LecturerExistsException(String.format("Lecturer with Lid {%s} Already Exist", lecturer.getLid()));
		}
	}

	public Iterable<Lecturer> searchLecturer(String taxBand, Integer salaryScale) {
		return repo.searchLecturers(taxBand, salaryScale);
	}

	public Lecturer updateLecturer(String lid, Lecturer lecturer) {
		Optional<Lecturer> optionLecturer = repo.findLecturerByLid(lid);
		// StreamSupport.stream(repo.findAll().spliterator(), false).filter(l ->
		// l.getLid().equalsIgnoreCase(lid)).findFirst();
		Lecturer savedLecturer = null;
		try {
			savedLecturer = optionLecturer.get();
		} catch (NoSuchElementException e) {
			throw new LecturerExistsException(String.format("Lecturer with Lid {%s} Does not exist", lid));
		}
		savedLecturer.setName(lecturer.getName());
		if (lecturer.getTaxBand() != null) {
			savedLecturer.setTaxBand(lecturer.getTaxBand());
		}
		if (lecturer.getSalaryScale() != null) {
			savedLecturer.setSalaryScale(lecturer.getSalaryScale());
		}

		return repo.save(savedLecturer);
	}
}
