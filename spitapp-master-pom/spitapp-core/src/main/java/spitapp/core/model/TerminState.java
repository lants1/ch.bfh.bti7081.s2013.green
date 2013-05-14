package spitapp.core.model;

import java.util.Date;

public interface TerminState {

	public boolean isRelevant();

	public void updateState(Date date);
}
