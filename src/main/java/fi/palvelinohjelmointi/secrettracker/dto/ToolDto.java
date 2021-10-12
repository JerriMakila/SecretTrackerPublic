package fi.palvelinohjelmointi.secrettracker.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Validated
public class ToolDto {
	
	@NotNull(message = "Tool must have a name")
	private String tool;
	
	@NotNull(message = "toolTypeId cannot be null")
	@Min(value = 1, message = "toolTypeId must be at least 1")
	private Long tooltypeId;
	
	public ToolDto() {}

	public ToolDto(String tool, Long tooltypeId) {
		super();
		this.tool = tool;
		this.tooltypeId = tooltypeId;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

	public Long getTooltypeId() {
		return tooltypeId;
	}

	public void setTooltypeId(Long tooltypeId) {
		this.tooltypeId = tooltypeId;
	}
	
}
