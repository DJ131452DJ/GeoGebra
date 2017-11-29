package org.geogebra.web.web.javax.swing;

import java.util.ArrayList;
import java.util.List;

import org.geogebra.web.html5.gui.util.AriaHelper;
import org.geogebra.web.html5.gui.util.AriaMenuItem;
import org.geogebra.web.html5.gui.util.NoDragImage;
import org.geogebra.web.html5.util.Dom;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;

/**
 * Adds a menu item with a checkmark on its end.
 * 
 * @author laszlo
 * 
 */
public class GCollapseMenuItem {

	private AriaMenuItem menuItem;
	private List<AriaMenuItem> items;
	private FlowPanel itemPanel;
	private boolean expanded;
	private String text;
	private Image imgExpand;
	private Image imgCollapse;
	/**
	 * @param text
	 *            Title
	 * @param expandUrl
	 *            image of expand
	 * @param collapseUrl
	 *            image of collapse
	 * @param expanded
	 *            initial value.
	 * @param cmd
	 *            The command to run.
	 */
	public GCollapseMenuItem(String text, String expandUrl,
			String collapseUrl,
			boolean expanded,
			final ScheduledCommand cmd) {
		this.text = text;
		imgExpand = new NoDragImage(expandUrl);
		imgExpand.setStyleName("expandImg");
		imgCollapse = new NoDragImage(collapseUrl);
		imgCollapse.addStyleName("collapseImg");

		items = new ArrayList<>();
		itemPanel = new FlowPanel();
		itemPanel.addStyleName("collapseMenuItem");
		menuItem = new AriaMenuItem(itemPanel.toString(), true,
				new ScheduledCommand() {

					@Override
					public void execute() {
						toggle();
						if (cmd != null) {
							cmd.execute();
						}
					}
				});
		setExpanded(expanded);
	}

	/**
	 * Sets the item checked/unchecked.
	 * 
	 * @param value
	 *            to set.
	 */
	public void setExpanded(boolean value) {
		expanded = value;
		itemPanel.clear();
		itemPanel.add(new HTML(text));
		itemPanel.add(expanded ? imgCollapse : imgExpand);
		menuItem.setHTML(itemPanel.toString());
		updateItems();
	}



	/**
	 * 
	 * @return The standard menu item with checkmark.
	 */
	public AriaMenuItem getMenuItem() {
		return menuItem;
	}

	/**
	 * 
	 * @return if the menu is expanded or not.
	 */
	public boolean isExpanded() {
		return expanded;
	}

	/**
	 * Toggles the menu - expand/collapse.
	 */
	public void toggle() {
		setExpanded(!expanded);
	}

	/**
	 * Collapse submenu
	 */
	public void updateItems() {
		for (AriaMenuItem mi : items) {
			Dom.toggleClass(mi, "gwt-MenuItem", expanded);
			Dom.toggleClass(mi, "expanded", "collapsed", expanded);
			AriaHelper.setHidden(mi, !expanded);
		}
	}

	/**
	 * 
	 * @param item
	 *            to add.
	 */
	public void addItem(AriaMenuItem item) {
		items.add(item);
	}
}
