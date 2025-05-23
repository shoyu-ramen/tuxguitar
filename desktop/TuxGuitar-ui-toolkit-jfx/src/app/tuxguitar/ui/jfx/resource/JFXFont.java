package app.tuxguitar.ui.jfx.resource;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import app.tuxguitar.ui.jfx.JFXComponent;
import app.tuxguitar.ui.resource.UIFont;
import app.tuxguitar.ui.resource.UIFontModel;

public class JFXFont extends JFXComponent<UIFontModel> implements UIFont {

	private JFXFontMetrics fontMetrics;

	public JFXFont(String name, float height, boolean bold, boolean italic){
		super(new UIFontModel(JFXFont.checkName(name), height, bold, italic));
	}

	public JFXFont(UIFontModel model){
		this(model.getName(), model.getHeight(), model.isBold(), model.isItalic());
	}

	public JFXFont(Font font){
		this(font.getFamily(), Math.round(font.getSize()), false, false);
	}

	public String getName() {
		return this.getControl().getName();
	}

	public float getHeight() {
		return this.getControl().getHeight();
	}

	public boolean isBold() {
		return this.getControl().isBold();
	}

	public boolean isItalic() {
		return this.getControl().isItalic();
	}

	public JFXFontMetrics getFontMetrics() {
		if( this.fontMetrics == null ) {
			this.fontMetrics = new JFXFontMetrics(this.getHandle());
		}
		return this.fontMetrics;
	}

	public Font getHandle() {
		return Font.font(this.getName(), this.isBold() ? FontWeight.BOLD : null, this.isItalic() ? FontPosture.ITALIC : null, this.getHeight());
	}

	public static String checkName(String name) {
		if( name != null && name.length() > 0 && !UIFontModel.DEFAULT_NAME.equals(name)) {
			return name;
		}
		return Font.getDefault().getName();
	}
}
