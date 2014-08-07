package geogebra.phone.gui.views;

import static com.google.gwt.query.client.GQuery.$;
import geogebra.html5.gui.ResizeListener;

import com.google.gwt.query.client.css.CSS;
import com.google.gwt.query.client.css.Length;
import com.google.gwt.query.client.plugins.effects.PropertiesAnimation.EasingCurve;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * 
 * A {@link ScrollPanel} containing all existing views (algebra, graphics, worksheets, options)
 *
 */
public class ViewsContainer extends ScrollPanel implements ResizeListener {
	
	public enum View {
		Algebra(0), Graphics(1), Worksheets(2), Options(3);
		
		private int index;
		
		private View(int index) {
			this.index = index;
		}
		
		public int getIndex() {
			return this.index;
		}
	}
		
	private HorizontalPanel content;
	private View currentView = View.Worksheets;
	private View lastView;
	private int scrollOffset;
	
	public ViewsContainer() {
		//FIXME do this with LAF
		this.setPixelSize(Window.getClientWidth(),  Window.getClientHeight());//TouchEntryPoint.getLookAndFeel().getCanvasHeight());
		this.setStyleName("viewContainer");
		this.scrollOffset = Window.getClientWidth();
		
		this.content = new HorizontalPanel();
		this.add(this.content);
		
		$(this.content).css(CSS.LEFT.with(Length.px(0)));
	}

	/**
	 * Adds a new view
	 * @param view
	 */
	public void addView(FlowPanel view) {
		this.content.add(view);
	}

	public void setCurrentView(View view) {
		this.lastView = this.currentView;
		this.currentView = view;
	}
	
	public View getCurrentView() {
		return this.currentView;
	}

	/**
	 * scroll to the given {@link View view}
	 * @param view {@link View}
	 */
	public void scrollTo(View view) {
		animateScroll(view.getIndex()*this.scrollOffset);
		setCurrentView(view);
	}

	private boolean toggle = false;
	
	/**
	 * uses gwt-query to animate scrolling
	 * @param to scrollPosition in pixel
	 */
	private void animateScroll(int to) {
		//FIXME
		$(this.content).animate("{left:'-" + to + "px'}", 300, EasingCurve.swing);
	}

	private View getView(int index) {
		for (View view : View.values()) {
			if (view.getIndex() == index) {
				return view;
			}
		}
		return null;
	}
/** not in use yet - to swipe views	
//	private void swipe(boolean toLeft) {
//		int switchTo = toLeft ? 1 : -1;
//		View view = getView(this.currentView.getIndex() + switchTo);
//		if (view != null) {
//			scrollTo(view);
//		}
//	}
//	
//	@Override
//	public void scrollToLeft() {
//		swipe(true);
//	}
//	
//	@Override
//	public void scrollToRight() {
//		swipe(false);
//	}
//	**/
	
	@Override
	public void onResize() {
		this.setPixelSize(Window.getClientWidth(),  Window.getClientHeight()-43);
		this.scrollOffset = Window.getClientWidth();
	}

	public View getLastView() {
		return this.lastView;
	}
}
