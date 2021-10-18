package fi.palvelinohjelmointi.secrettracker.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
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
	
	@Type(type="org.hibernate.type.BinaryType") // @Lob-annotation didn't work for some reason, this seems to be a viable alternative
	private byte[] image;
	
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

	public Secret(@NotNull(message = "Secret must have a name") String secret, Boolean cleared,
			@NotNull(message = "Location cannot be null") Location locationId, Tool toolId, byte[] image) {
		super();
		this.secret = secret;
		this.cleared = cleared;
		this.locationId = locationId;
		this.toolId = toolId;
		this.image = image;
	}

	public Secret(@NotNull(message = "Secret must have a name") String secret,
			@NotNull(message = "Location cannot be null") Location locationId, Tool toolId, byte[] image) {
		super();
		this.secret = secret;
		this.locationId = locationId;
		this.toolId = toolId;
		this.image = image;
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
}
