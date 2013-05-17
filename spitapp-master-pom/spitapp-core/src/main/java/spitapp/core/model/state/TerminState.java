package spitapp.core.model.state;

import java.util.Date;

public interface TerminState {

	public boolean isRelevant();

	public void updateState(Date date);
}
