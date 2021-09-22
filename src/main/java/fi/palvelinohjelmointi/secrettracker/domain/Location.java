package fi.palvelinohjelmointi.secrettracker.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long locationId;
	private String location;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId")
	private List<Secret> secrets;
	
	public Location() {}

	public Location(String location) {
		super();
		this.location = location;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
