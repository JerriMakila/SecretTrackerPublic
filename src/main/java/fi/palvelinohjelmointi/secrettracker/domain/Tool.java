package fi.palvelinohjelmointi.secrettracker.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tool {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long toolId;
	private String tool;
	@ManyToOne
	@JoinColumn(name = "tooltype_id")
	private ToolType tooltypeId;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="toolId")
	private List<Secret> secrets;
	
	public Tool() {}

	public Tool(String tool, ToolType tooltypeId) {
		super();
		this.tool = tool;
		this.tooltypeId = tooltypeId;
	}

	public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

	public ToolType getTooltypeId() {
		return tooltypeId;
	}

	public void setTooltypeId(ToolType tooltypeId) {
		this.tooltypeId = tooltypeId;
	}
	
}
