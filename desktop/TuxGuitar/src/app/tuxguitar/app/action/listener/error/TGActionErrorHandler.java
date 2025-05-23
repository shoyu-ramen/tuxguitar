package app.tuxguitar.app.action.listener.error;

import app.tuxguitar.action.TGActionContext;
import app.tuxguitar.action.TGActionErrorEvent;
import app.tuxguitar.action.TGActionPostExecutionEvent;
import app.tuxguitar.action.TGActionPreExecutionEvent;
import app.tuxguitar.event.TGEvent;
import app.tuxguitar.event.TGEventException;
import app.tuxguitar.event.TGEventListener;
import app.tuxguitar.util.TGContext;
import app.tuxguitar.util.error.TGErrorHandler;
import app.tuxguitar.util.error.TGErrorManager;

public class TGActionErrorHandler implements TGEventListener {

	public static final String ATTRIBUTE_ACTION_LEVEL= (TGActionErrorHandler.class.getName() + "-level");
	public static final String ATTRIBUTE_ERROR_HANDLER = TGErrorHandler.class.getName();

	private TGContext context;

	public TGActionErrorHandler(TGContext context) {
		this.context = context;
	}

	public void incrementLevel(TGActionContext context) {
		Integer level = this.getLevel(context);
		context.setAttribute(ATTRIBUTE_ACTION_LEVEL, (level != null ? level.intValue() + 1 : 1));
	}

	public void decrementLevel(TGActionContext context) {
		Integer level = this.getLevel(context);
		context.setAttribute(ATTRIBUTE_ACTION_LEVEL, (level != null ? level.intValue() - 1 : 0));
	}

	public Integer getLevel(TGActionContext context) {
		return context.getAttribute(ATTRIBUTE_ACTION_LEVEL);
	}

	public TGActionContext findActionContext(TGEvent event) {
		return event.getAttribute(TGEvent.ATTRIBUTE_SOURCE_CONTEXT);
	}

	public void processErrorEvent(TGEvent event) {
		TGActionContext actionContext = this.findActionContext(event);
		Integer level = this.getLevel(actionContext);
		if( level == null || level.intValue() == 0 ) {
			Throwable throwable = event.getAttribute(TGActionErrorEvent.PROPERTY_ACTION_ERROR);
			TGErrorHandler errorHandler = actionContext.getAttribute(ATTRIBUTE_ERROR_HANDLER);
			if( errorHandler != null ) {
				errorHandler.handleError(throwable);
			} else {
				TGErrorManager.getInstance(this.context).handleError(throwable);
			}
		}
	}

	public void processEvent(TGEvent event) throws TGEventException {
		if( TGActionPreExecutionEvent.EVENT_TYPE.equals(event.getEventType()) ) {
			this.incrementLevel(this.findActionContext(event));
		}
		else if( TGActionPostExecutionEvent.EVENT_TYPE.equals(event.getEventType()) ) {
			this.decrementLevel(this.findActionContext(event));
		}
		else if( TGActionErrorEvent.EVENT_TYPE.equals(event.getEventType()) ) {
			this.decrementLevel(this.findActionContext(event));
			this.processErrorEvent(event);
		}
	}
}
