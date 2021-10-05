package fi.palvelinohjelmointi.secrettracker.dto;

public class ToolDto {
	private String tool;
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
