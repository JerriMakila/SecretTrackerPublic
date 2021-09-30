package fi.palvelinohjelmointi.secrettracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.palvelinohjelmointi.secrettracker.domain.Location;
import fi.palvelinohjelmointi.secrettracker.domain.LocationRepository;
import fi.palvelinohjelmointi.secrettracker.domain.Secret;
import fi.palvelinohjelmointi.secrettracker.domain.SecretRepository;
import fi.palvelinohjelmointi.secrettracker.domain.Tool;
import fi.palvelinohjelmointi.secrettracker.domain.ToolRepository;
import fi.palvelinohjelmointi.secrettracker.domain.ToolType;
import fi.palvelinohjelmointi.secrettracker.domain.ToolTypeRepository;

@SpringBootApplication
public class SecrettrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecrettrackerApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner secretDemo(ToolTypeRepository tpRepo, LocationRepository lRepo, ToolRepository tRepo, SecretRepository sRepo) {
		return(args) ->{
			tpRepo.save(new ToolType("Testityyppi1"));
			tpRepo.save(new ToolType("Testityyppi2"));
			lRepo.save(new Location("Testilokaatio1"));
			lRepo.save(new Location("Testilokaatio2"));
			tRepo.save(new Tool("Testityökalu", tpRepo.findByToolType("Testityyppi1").get(0)));
			sRepo.save(new Secret("Testisalaisuus", false, lRepo.findByLocation("Testilokaatio1").get(0), tRepo.findByTool("Testityökalu").get(0)));
			sRepo.save(new Secret("Testisalaisuus", false, lRepo.findByLocation("Testilokaatio1").get(0), tRepo.findByTool("Testityökalu").get(0)));
		};
	}
}
