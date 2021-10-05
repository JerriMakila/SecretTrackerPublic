package fi.palvelinohjelmointi.secrettracker.web;

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

import fi.palvelinohjelmointi.secrettracker.domain.Tool;
import fi.palvelinohjelmointi.secrettracker.domain.ToolRepository;
import fi.palvelinohjelmointi.secrettracker.domain.ToolType;
import fi.palvelinohjelmointi.secrettracker.domain.ToolTypeRepository;

@RestController
public class ToolTypeController {
	@Autowired
	private ToolTypeRepository toolTypeRepository;
	
	@Autowired
	private ToolRepository toolRepository;
	
	@GetMapping("/tooltypes")
	public @ResponseBody List<ToolType> toolTypes(){
		return (List<ToolType>) toolTypeRepository.findAll();
	}
	
	@GetMapping("/tooltypes/{id}")
	public @ResponseBody ResponseEntity<Optional<ToolType>> getToolTypeById(@PathVariable("id") Long toolTypeId){
		Optional<ToolType> toolType = toolTypeRepository.findById(toolTypeId);
		
		if(toolType.isEmpty()) {
			return new ResponseEntity<>(toolType, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(toolType, HttpStatus.OK);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<ToolType> addToolType(@RequestBody ToolType toolType){
		return new ResponseEntity<>(toolTypeRepository.save(toolType), HttpStatus.CREATED);
	}
	
	@PutMapping("/tooltypes/{id}")
	public @ResponseBody ResponseEntity<ToolType> editToolType(@RequestBody ToolType requestToolType,
			@PathVariable("id") Long toolTypeId){
		Optional<ToolType> toolType = toolTypeRepository.findById(toolTypeId);
		
		if(toolType.isEmpty()) {
			return new ResponseEntity<>(new ToolType(), HttpStatus.NOT_FOUND);
		}
		
		ToolType newToolType = toolType.get();
		newToolType.setToolType(requestToolType.getToolType());
		toolTypeRepository.save(newToolType);
		return new ResponseEntity<>(newToolType, HttpStatus.OK);
	}
	
	@DeleteMapping("/tooltypes/{id}")
	public @ResponseBody ResponseEntity<String> deleteToolType(@PathVariable("id") Long toolTypeId){
		Optional<ToolType> toolType = toolTypeRepository.findById(toolTypeId);
		
		if(toolType.isEmpty()) {
			return new ResponseEntity<>("No ToolType with the given id found", HttpStatus.NOT_FOUND);
		}
		
		List<Tool> tools = toolRepository.findByTooltypeId(toolType.get());
		
		if(tools.size() > 0) {
			return new ResponseEntity<>("Tooltype has been lready linked with tools", HttpStatus.BAD_REQUEST);
		}
		
		toolTypeRepository.deleteById(toolTypeId);
		return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
	}
}
