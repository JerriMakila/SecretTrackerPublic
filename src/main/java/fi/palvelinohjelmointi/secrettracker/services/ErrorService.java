package fi.palvelinohjelmointi.secrettracker.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class ErrorService {
	private String message;
	
	// Provides a way to create error messages when validation notices errors in data sent from client
	public String createErrorMessage(BindingResult bindingResult) {
		List<ObjectError> errors = bindingResult.getAllErrors();
		StringBuilder errorMessage = new StringBuilder(errors.size());
		
		for(ObjectError error: errors) {
			errorMessage.append(error.getDefaultMessage() + ". ");
		}
		
		message = errorMessage.toString().trim();
		
		return message;
	}
}
