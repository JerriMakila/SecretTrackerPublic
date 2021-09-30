package fi.palvelinohjelmointi.secrettracker.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fi.palvelinohjelmointi.secrettracker.domain.Location;
import fi.palvelinohjelmointi.secrettracker.domain.LocationRepository;
import fi.palvelinohjelmointi.secrettracker.domain.Secret;

@RestController
public class LocationController {
	@Autowired
	private LocationRepository locationRepository;
	
	@GetMapping("/locations")
	public @ResponseBody List<Location> locations(){
		return (List<Location>) locationRepository.findAll();
	}
	
	@GetMapping("/locations/{id}/secrets")
	public @ResponseBody ResponseEntity<List<Secret>> getSecretsByLocation(@PathVariable("id") Long locationId){
		Optional<Location> location = locationRepository.findById(locationId);
		
		if(location.isEmpty()) {
			return new ResponseEntity<>(new ArrayList<Secret>(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(location.get().getSecrets(), HttpStatus.OK);
	}
	
	@PostMapping("/locations")
	public @ResponseBody ResponseEntity<Location> createLocation(@RequestBody Location location){
		return new ResponseEntity<>(location, HttpStatus.CREATED);
	}
	
	@PutMapping("/locations/{id}")
	public @ResponseBody ResponseEntity<Location> modifyLocation(@RequestBody Location requestLocation, @PathVariable("id") Long locationId){
		Optional<Location> location = locationRepository.findById(locationId);
		
		if(location.isEmpty()) {
			return new ResponseEntity<>(new Location(), HttpStatus.NOT_FOUND);
		}
		
		Location newLocation = location.get();
		newLocation.setLocation(requestLocation.getLocation());
		
		return new ResponseEntity<>(locationRepository.save(newLocation), HttpStatus.OK);
	}
	
	@DeleteMapping("/locations/{id}")
	public @ResponseBody ResponseEntity<Optional<Location>> deleteLocation(@PathVariable("id") Long locationId){
		Optional<Location> location = locationRepository.findById(locationId);
		
		if(location.isEmpty()) {
			return new ResponseEntity<>(location, HttpStatus.NOT_FOUND);
		}
		
		Location foundLocation = location.get();
		
		if(foundLocation.getSecrets().size() > 0) {
			return new ResponseEntity<>(location, HttpStatus.BAD_REQUEST);
		}
		
		locationRepository.delete(foundLocation);
		
		return new ResponseEntity<>(location, HttpStatus.NO_CONTENT);
	}
}
