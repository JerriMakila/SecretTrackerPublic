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
public class ToolType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long toolTypeId;
	private String toolType;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="tooltypeId")
	private List<Tool> tools;
	
	public ToolType() {}

	public ToolType(String toolType) {
		super();
		this.toolType = toolType;
	}

	public Long getToolTypeId() {
		return toolTypeId;
	}

	public void setToolTypeId(Long toolTypeId) {
		this.toolTypeId = toolTypeId;
	}

	public String getToolType() {
		return toolType;
	}

	public void setToolType(String toolType) {
		this.toolType = toolType;
	}
}
