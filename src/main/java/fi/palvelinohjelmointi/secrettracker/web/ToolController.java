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

import fi.palvelinohjelmointi.secrettracker.domain.Secret;
import fi.palvelinohjelmointi.secrettracker.domain.SecretRepository;
import fi.palvelinohjelmointi.secrettracker.domain.Tool;
import fi.palvelinohjelmointi.secrettracker.domain.ToolRepository;
import fi.palvelinohjelmointi.secrettracker.domain.ToolType;
import fi.palvelinohjelmointi.secrettracker.domain.ToolTypeRepository;
import fi.palvelinohjelmointi.secrettracker.dto.ToolDto;

@RestController
public class ToolController {
	@Autowired
	private ToolRepository toolRepository;
	
	@Autowired
	private ToolTypeRepository toolTypeRepository;
	
	@Autowired
	private SecretRepository secretRepository;
	
	@GetMapping("/tools")
	public @ResponseBody List<Tool> tools(){
		return (List<Tool>) toolRepository.findAll();
	}
	
	@GetMapping("tools/id")
	public @ResponseBody ResponseEntity<Optional<Tool>> getToolById(@PathVariable("id") Long toolId){
		Optional<Tool> tool = toolRepository.findById(toolId);
		
		if(tool.isEmpty()) {
			return new ResponseEntity<>(tool, HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(tool, HttpStatus.OK);
		}
	}
	
	@PostMapping("/tools")
	public @ResponseBody ResponseEntity<Tool> addTool(@RequestBody ToolDto toolDto){
		Optional<ToolType> toolType = toolTypeRepository.findById(toolDto.getTooltypeId());
		
		if(toolType.isEmpty()) {
			return new ResponseEntity<>(new Tool(null, null), HttpStatus.BAD_REQUEST);
		}
		
		Tool newTool = new Tool(toolDto.getTool(), toolType.get());
		return new ResponseEntity<>(toolRepository.save(newTool), HttpStatus.CREATED);
	}
	
	@PutMapping("/tools/{id}")
	public @ResponseBody ResponseEntity<Tool> modifyTool(@RequestBody ToolDto toolDto, @PathVariable("id") Long toolId){
		Optional<Tool> tool = toolRepository.findById(toolId);
		Optional<ToolType> toolType = toolTypeRepository.findById(toolDto.getTooltypeId());
		
		
		
		if(tool.isEmpty()) {
			return new ResponseEntity<>(new Tool(), HttpStatus.NOT_FOUND);
		}
		
		if(toolType.isEmpty()) {
			return new ResponseEntity<>(new Tool(), HttpStatus.BAD_REQUEST);
		}
		
		Tool newTool = tool.get();
		newTool.setTool(toolDto.getTool());
		newTool.setTooltypeId(toolType.get());
		toolRepository.save(newTool);
		
		return new ResponseEntity<>(newTool, HttpStatus.OK);
	}
	
	@DeleteMapping("/tools/{id}")
	public @ResponseBody ResponseEntity<String> deleteTool(@PathVariable("id") Long toolId){
		
		Optional<Tool> tool = toolRepository.findById(toolId);
		
		if(tool.isEmpty()) {
			return new ResponseEntity<>("No ToolType found with the given id", HttpStatus.NOT_FOUND);
		}
		
		List<Secret> secrets = secretRepository.findByToolId(tool.get());
		
		if(secrets.size() > 0) {
			return new ResponseEntity<>("Tool has already been linked with a secret", HttpStatus.BAD_REQUEST);
		}
		
		toolRepository.deleteById(toolId);
		return new ResponseEntity<>("Tool deleted succesfully", HttpStatus.NO_CONTENT);
	}
}
