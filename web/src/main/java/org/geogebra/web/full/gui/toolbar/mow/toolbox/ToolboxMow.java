package org.geogebra.web.full.gui.toolbar.mow.toolbox;

import org.geogebra.web.full.css.ToolbarSvgResources;
import org.geogebra.web.html5.css.ZoomPanelResources;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.resources.SVGResource;
import org.gwtproject.user.client.ui.FlowPanel;
import org.gwtproject.user.client.ui.RootPanel;
import org.gwtproject.user.client.ui.SimplePanel;

public class ToolboxMow extends FlowPanel {
	private final AppW appW;
	private ToolboxDecorator decorator;
	private ToolboxController controller;
	private IconButton spotlightBtn;
	private IconButton rulerBtn;

	/**
	 * MOW toolbox
	 * @param appW - application
	 */
	public ToolboxMow(AppW appW) {
		this.appW = appW;
		decorator = new ToolboxDecorator(this);
		controller = new ToolboxController(appW, this);
		RootPanel.get().add(this);
		buildGui();
	}

	private void buildGui() {
		decorator.positionLeft();

		addSpotlightButton();

		addDivider();

		addRulerButton();

		addPressButton(ToolbarSvgResources.INSTANCE.mode_pen(), "move mode", "moveBtn",
				() -> appW.setMoveMode());
	}

	private void addPressButton(SVGResource image, String ariaLabel, String dataTest,
			Runnable onHandler) {
		IconButton iconButton = new IconButton(appW.getLocalization(), image, ariaLabel, ariaLabel,
				dataTest, onHandler);
		add(iconButton);
	}

	private IconButton addToggleButton(SVGResource image, String ariaLabel, String dataTitle,
			String dataTest, Runnable onHandler, Runnable offHandler) {
		IconButton iconButton = new IconButton(appW, image, ariaLabel, dataTitle,
				dataTest, onHandler, offHandler);
		add(iconButton);
		return iconButton;
	}

	private void addDivider() {
		SimplePanel divider = new SimplePanel();
		divider.setStyleName("divider");

		add(divider);
	}

	public void switchSpotlightOff() {
		spotlightBtn.setActive(false, appW.getDarkColor());
	}

	private void addSpotlightButton() {
		spotlightBtn = addToggleButton(ZoomPanelResources.INSTANCE.target(), "Spotlight.Tool",
				"Spotlight.Tool", "spotlightTool",
				controller.getSpotlightOnHandler(), () -> {});
	}

	private void addRulerButton() {
		rulerBtn = addToggleButton(ToolbarSvgResources.INSTANCE.mode_ruler(), "Ruler", "Ruler",
				"rulerTool", controller.getRulerOnHandler(), () -> {});
	}

	public void updateRulerBtn(SVGResource image, String txt) {
		rulerBtn.updateImgAndTxt(image, txt);
	}
}