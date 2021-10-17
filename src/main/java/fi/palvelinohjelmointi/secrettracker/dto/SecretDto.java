package fi.palvelinohjelmointi.secrettracker.dto;

import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

//Works as a data transfer object when sending Secret-entities in Request Body
@Validated
public class SecretDto {
	
	@NotNull(message = "Secret must have a name")
	private String secret;
	private boolean cleared;
	
	@NotNull(message = "Secret must have a location")
	@Min(value = 1, message = "locationId must be at least 1")
	private long locationId;
	
	private long toolId;
	private String image;
	
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

	public SecretDto(@NotNull(message = "Secret must have a name") String secret, boolean cleared,
			@NotNull(message = "Secret must have a location") @Min(value = 1, message = "locationId must be at least 1") long locationId,
			long toolId, String image) {
		super();
		this.secret = secret;
		this.cleared = cleared;
		this.locationId = locationId;
		this.toolId = toolId;
		this.image = image;
	}

	public SecretDto(@NotNull(message = "Secret must have a name") String secret,
			@NotNull(message = "Secret must have a location") @Min(value = 1, message = "locationId must be at least 1") long locationId,
			long toolId, String image) {
		super();
		this.secret = secret;
		this.locationId = locationId;
		this.toolId = toolId;
		this.image = image;
		this.cleared = false;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}
