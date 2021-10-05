package fi.palvelinohjelmointi.secrettracker.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
public class SecretController {
	@Autowired
	private SecretRepository secretRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private ToolRepository toolRepository;
	
	@GetMapping("/secrets")
	public @ResponseBody List<Secret> secrets(){
		return (List<Secret>) secretRepository.findAll();
	}
	
	@GetMapping("/secret/{id}")
	public @ResponseBody ResponseEntity<Optional<Secret>> getSecretById(@PathVariable("id") Long secretId){
		Optional<Secret> secret = secretRepository.findById(secretId);
		
		if(secret.isEmpty()) {
			return new ResponseEntity<>(secret, HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(secret, HttpStatus.OK);
		}
	}
	
	@PostMapping("/secret")
	public @ResponseBody ResponseEntity<Secret> addSecret(@RequestBody SecretDto secret){
		Optional<Location> location = locationRepository.findById(secret.getLocationId());
		Optional<Tool> tool = toolRepository.findById(secret.getToolId());
		
		if(location.isEmpty() || tool.isEmpty()) {
			return new ResponseEntity<>(new Secret(
					secret.getSecret(), false, null, null), HttpStatus.BAD_REQUEST);
		}
		
		Secret newSecret = new Secret(
				secret.getSecret(),
				false,
				location.get(),
				tool.get()
		);
		
		return new ResponseEntity<>(secretRepository.save(newSecret), HttpStatus.CREATED);
	}
	
	@PutMapping("/secrets/{id}")
	public @ResponseBody ResponseEntity<Secret> modifySecret(@RequestBody SecretDto secretDto, @PathVariable("id") Long secretId){
		Optional<Secret> secret = secretRepository.findById(secretId);
		Optional<Location> location = locationRepository.findById(secretDto.getLocationId());
		Optional<Tool> tool = toolRepository.findById(secretDto.getToolId());
		
		if(secret.isEmpty()) {
			return new ResponseEntity<>(new Secret(), HttpStatus.NOT_FOUND);
		}
		
		Secret newSecret = secret.get();
		Location newLocation = null;
		Tool newTool = null;
		
		if(!location.isEmpty()) {
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
		
		return new ResponseEntity<>(newSecret, HttpStatus.OK);
	}
	
	@PatchMapping("/secrets/{id}")
	public @ResponseBody ResponseEntity<Optional<Secret>> markSecretAsCleared(@PathVariable("id") Long secretId){
		Optional<Secret> secret = secretRepository.findById(secretId);
		
		if(secret.isEmpty()) {
			return new ResponseEntity<>(secret, HttpStatus.NOT_FOUND);
		}
		
		Secret clearedSecret = secret.get();
		clearedSecret.setCleared(true);
		secretRepository.save(clearedSecret);
		return new ResponseEntity<>(secret, HttpStatus.OK);
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
