package app.tuxguitar.editor.undo.impl.track;

import app.tuxguitar.action.TGActionContext;
import app.tuxguitar.editor.undo.TGCannotRedoException;
import app.tuxguitar.editor.undo.TGCannotUndoException;
import app.tuxguitar.song.models.TGTrack;
import app.tuxguitar.util.TGContext;

public class TGUndoableMoveTrackUp extends TGUndoableTrackBase {

	private int doAction;
	private int trackNumber;

	private TGUndoableMoveTrackUp(TGContext context){
		super(context);
	}

	public void redo(TGActionContext actionContext) throws TGCannotRedoException {
		if(!canRedo()){
			throw new TGCannotRedoException();
		}
		this.moveTrackUp(actionContext, getSong(), getSongManager().getTrack(getSong(), this.trackNumber + 1));
		this.doAction = UNDO_ACTION;
	}

	public void undo(TGActionContext actionContext) throws TGCannotUndoException {
		if(!canUndo()){
			throw new TGCannotUndoException();
		}
		this.moveTrackDown(actionContext, getSong(), getSongManager().getTrack(getSong(), this.trackNumber));
		this.doAction = REDO_ACTION;
	}

	public boolean canRedo() {
		return (this.doAction == REDO_ACTION);
	}

	public boolean canUndo() {
		return (this.doAction == UNDO_ACTION);
	}

	public static TGUndoableMoveTrackUp startUndo(TGContext context){
		TGUndoableMoveTrackUp undoable = new TGUndoableMoveTrackUp(context);
		undoable.doAction = UNDO_ACTION;

		return undoable;
	}

	public TGUndoableMoveTrackUp endUndo(TGTrack track){
		this.trackNumber = track.getNumber();

		return this;
	}
}
