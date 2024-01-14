package com.example.crud.exceptions;

public class DuplicateCpfException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateCpfException(String message) {
        super(message);
    }
}
