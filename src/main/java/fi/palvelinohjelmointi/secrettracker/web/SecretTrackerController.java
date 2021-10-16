package fi.palvelinohjelmointi.secrettracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SecretTrackerController {
	@GetMapping("/locationlist")
	public String locationList() {
		return "locations";
	}
	
	@GetMapping("/secretsbylocation/{id}")
	public String secretsByLocation() {
		return "secretsbylocation";
	}
	
	@GetMapping("/addlocation")
	public String addLocation() {
		return "addlocation";
	}
	
	@GetMapping("/addsecret/{id}")
	public String addSecret() {
		return "addsecret";
	}
	
	@GetMapping("/modifysecret/{id}")
	public String modifySecret() {
		return "modifysecret";
	}
	
	@GetMapping("/addtool")
	public String addTool() {
		return "addtool";
	}
	
	@GetMapping("/addtooltype")
	public String addTooltype() {
		return "addtooltype";
	}
}
