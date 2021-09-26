package fi.palvelinohjelmointi.secrettracker.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fi.palvelinohjelmointi.secrettracker.domain.Secret;
import fi.palvelinohjelmointi.secrettracker.domain.SecretRepository;

@RestController
public class SecretController {
	@Autowired
	private SecretRepository secretRepository;
	
	@GetMapping("/secrets")
	public @ResponseBody List<Secret> secrets(){
		return (List<Secret>) secretRepository.findAll();
	}
	
	@GetMapping("/secret/{id}")
	public @ResponseBody Optional<Secret> getSecretById(@PathVariable("id") Long secretId){
		return secretRepository.findById(secretId);
	}
}
