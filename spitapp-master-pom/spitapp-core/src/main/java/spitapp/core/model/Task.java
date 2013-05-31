package spitapp.core.model;

import java.util.Date;

/**
 * Hibernate Mapping Class for table Task
 * 
 * @author green
 *
 */
public class Task implements SpitappSaveable{
	private Long taskId;

	private String description;
	
	private Date starttime;
	
	private int duration;
	
	private boolean done;


	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

}
