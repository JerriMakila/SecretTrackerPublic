package fi.palvelinohjelmointi.secrettracker.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;


@Validated
public class SecretDto {
	
	@NotNull(message = "Secret must have a name")
	private String secret;
	private boolean cleared;
	
	@NotNull(message = "Secret must have a location")
	@Min(value = 1, message = "locationId must be at least 1")
	private long locationId;
	
	@Min(value = 1, message = "toolId must be at least 1")
	private long toolId;
	
	public SecretDto() {}

	public SecretDto(String secret, long locationId, long toolId) {
		super();
		this.secret = secret;
		this.locationId = locationId;
		this.toolId = toolId;
	}

	public SecretDto(String secret, boolean cleared, long locationId, long toolId) {
		super();
		this.secret = secret;
		this.cleared = cleared;
		this.locationId = locationId;
		this.toolId = toolId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public boolean isCleared() {
		return cleared;
	}

	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public long getToolId() {
		return toolId;
	}

	public void setToolId(long toolId) {
		this.toolId = toolId;
	}
	
}
