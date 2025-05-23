package app.tuxguitar.android.action.impl.gui;

import app.tuxguitar.action.TGActionContext;
import app.tuxguitar.android.action.TGActionBase;
import app.tuxguitar.android.activity.TGActivity;
import app.tuxguitar.util.TGContext;

public class TGFinishAction extends TGActionBase{

	public static final String NAME = "action.gui.finish";

	public static final String ATTRIBUTE_ACTIVITY = TGActivity.class.getName();

	public TGFinishAction(TGContext context) {
		super(context, NAME);
	}

	protected void processAction(final TGActionContext context) {
		TGActivity tgActivity = (TGActivity) context.getAttribute(ATTRIBUTE_ACTIVITY);
		tgActivity.finish();
	}
}
