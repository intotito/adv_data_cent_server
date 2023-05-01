package com.example.demo.exceptions;

public class StudentExistsException extends RuntimeException {
	public StudentExistsException(String msg) {
		super(msg);
	}
}
