package fi.palvelinohjelmointi.secrettracker.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fi.palvelinohjelmointi.secrettracker.domain.Location;
import fi.palvelinohjelmointi.secrettracker.domain.LocationRepository;
import fi.palvelinohjelmointi.secrettracker.domain.Secret;
import fi.palvelinohjelmointi.secrettracker.domain.SecretRepository;
import fi.palvelinohjelmointi.secrettracker.domain.Tool;
import fi.palvelinohjelmointi.secrettracker.domain.ToolRepository;
import fi.palvelinohjelmointi.secrettracker.dto.SecretDto;
import fi.palvelinohjelmointi.secrettracker.services.ErrorService;

@RestController
public class SecretController {
	@Autowired
	private SecretRepository secretRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private ToolRepository toolRepository;
	
	@Autowired
	private ErrorService errorService;
	
	@GetMapping("/secrets")
	public @ResponseBody List<Secret> secrets(){
		return (List<Secret>) secretRepository.findAll();
	}
	
	@GetMapping("/secrets/{id}")
	public @ResponseBody ResponseEntity<Optional<Secret>> getSecretById(@PathVariable("id") Long secretId){
		Optional<Secret> secret = secretRepository.findById(secretId);
		
		if(secret.isEmpty()) {
			return new ResponseEntity<>(secret, HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(secret, HttpStatus.OK);
		}
	}
	
	@PostMapping("/secrets")
	public @ResponseBody ResponseEntity<Map<String, String>> addSecret(@Valid @RequestBody SecretDto secret, BindingResult bindingResult){
		Map<String, String> response = new HashMap<>();
		String message;
		
		if(bindingResult.hasErrors()) {			
			message = errorService.createErrorMessage(bindingResult);
			response.put("status", "400");
			response.put("message", message);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		Optional<Location> location = locationRepository.findById(secret.getLocationId());
		Optional<Tool> tool = toolRepository.findById(secret.getToolId());
		
		if(location.isEmpty()) {
			message = "Location not found with given id";
			response.put("status", "400");
			response.put("message", message);
			
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		Location newLocation = location.get();
		Tool newTool = null;
		
		if(tool.isPresent()) {
			newTool = tool.get();
		}
		
		Secret newSecret = new Secret(
				secret.getSecret(),
				false,
				newLocation,
				newTool
		);
		
		secretRepository.save(newSecret);
		message = "Secret created succesfully";
		
		response.put("status", "201");
		response.put("message", message);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/secrets/{id}")
	public @ResponseBody ResponseEntity<Map<String, String>> modifySecret(@Valid @RequestBody SecretDto secretDto, BindingResult bindingResult, @PathVariable("id") Long secretId){
		Map<String, String> response = new HashMap<>();
		String message;
		
		if(bindingResult.hasErrors()) {
			message = errorService.createErrorMessage(bindingResult);
			response.put("status", "400");
			response.put("message", message);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		Optional<Secret> secret = secretRepository.findById(secretId);
		Optional<Location> location = locationRepository.findById(secretDto.getLocationId());
		Optional<Tool> tool = toolRepository.findById(secretDto.getToolId());
		
		if(secret.isEmpty()) {
			message = "Secret with the given id not found";
			
			response.put("status", "404");
			response.put("message", message);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		Secret newSecret = secret.get();
		Location newLocation = null;
		Tool newTool = null;
		
		if(location.isEmpty()) {
			message = "Location with the given id not found";
			
			response.put("status", "400");
			response.put("message", message);
			
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			newLocation = location.get();
		}
		
		if(!tool.isEmpty()) {
			newTool = tool.get();
		}
		
		newSecret.setSecret(secretDto.getSecret());
		newSecret.setCleared(secretDto.isCleared());
		newSecret.setLocationId(newLocation);
		newSecret.setToolId(newTool);
		
		secretRepository.save(newSecret);
		
		message = "Secret modified succesfully";
		
		response.put("status", "200");
		response.put("message", message);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/secrets/{id}")
	public @ResponseBody ResponseEntity<Map<String, String>> markSecretAsCleared(@PathVariable("id") Long secretId){
		Map<String, String> response = new HashMap<>();
		String message;
		
		Optional<Secret> secret = secretRepository.findById(secretId);
		
		if(secret.isEmpty()) {
			message = "Secret with the given id was not found";
			response.put("status", "404");
			response.put("message", message);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		Secret clearedSecret = secret.get();
		clearedSecret.setCleared(true);
		secretRepository.save(clearedSecret);
		
		message = "Secret modified succesfully";
		response.put("status", "200");
		response.put("message", message);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/secrets/{id}")
	public @ResponseBody ResponseEntity<Optional<Secret>> deleteSecret(@PathVariable("id") Long secretId){
		Optional<Secret> secret = secretRepository.findById(secretId);
		
		if(secret.isEmpty()) {
			return new ResponseEntity<>(secret, HttpStatus.NOT_FOUND);
		}
		
		secretRepository.delete(secret.get());
		return new ResponseEntity<>(secret, HttpStatus.NO_CONTENT);
	}
}
