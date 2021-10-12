package fi.palvelinohjelmointi.secrettracker.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class Secret {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long secretId;
	
	@NotNull(message = "Secret must have a name")
	private String secret;
	
	private Boolean cleared;
	
	@NotNull(message = "Location cannot be null")
	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location locationId;
	
	@ManyToOne
	@JoinColumn(name = "tool_id")
	private Tool toolId;
	
	public Secret() {}

	public Secret(String secret, Boolean cleared, Location locationId, Tool toolId) {
		super();
		this.secret = secret;
		this.cleared = cleared;
		this.locationId = locationId;
		this.toolId = toolId;
	}
	
	public Secret(String secret, Location locationId, Tool toolId) {
		this.secret = secret;
		this.locationId = locationId;
		this.toolId = toolId;
		this.cleared = false;
	}

	public Long getSecretId() {
		return secretId;
	}

	public void setSecretId(Long secretId) {
		this.secretId = secretId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Boolean getCleared() {
		return cleared;
	}

	public void setCleared(Boolean cleared) {
		this.cleared = cleared;
	}

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
		this.locationId = locationId;
	}

	public Tool getToolId() {
		return toolId;
	}

	public void setToolId(Tool toolId) {
		this.toolId = toolId;
	}
	
}
