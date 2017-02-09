package org.geogebra.common.jre.main;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.geogebra.common.kernel.commands.Commands;
import org.geogebra.common.main.App;
import org.geogebra.common.main.Localization;
import org.geogebra.common.util.StringUtil;
import org.geogebra.common.util.debug.Log;

/**
 * common jre localization
 */
public abstract class LocalizationJre extends Localization {

	private ResourceBundle rbmenu, rbmenuTT, rbcommand, rbcommandOld, rberror,
			rbcolors, rbsymbol;

	private Locale tooltipLocale = null;
	protected App app;

	/**
	 * @param dimension
	 *            3 for 3D
	 */
	public LocalizationJre(int dimension) {
		this(dimension, 15);
	}

	public LocalizationJre(int dimension, int maxFigures) {
		super(dimension, maxFigures);
	}

	/**
	 * @param app
	 *            application
	 */
	final public void setApp(App app) {
		this.app = app;
	}

	@Override
	final public void setTooltipFlag() {
		if (tooltipLocale != null) {
			tooltipFlag = true;
		}
	}

	@Override
	final public String getCommand(String key) {

		app.initTranslatedCommands();

		try {
			return rbcommand.getString(key);
		} catch (Exception e) {
			return key;
		}
	}

	@Override
	final public String getMenu(String key) {

		if (tooltipFlag) {
			return getMenuTooltip(key);
		}

		if (rbmenu == null) {
			rbmenu = createBundle(getMenuRessourcePath(), currentLocale);
		}

		try {
			return rbmenu.getString(key);
		} catch (Exception e) {
			return key;
		}
	}

	/**
	 * 
	 * @param key
	 *            key name
	 * @param locale
	 *            locale
	 * @return bundle for key & locale
	 */
	abstract protected ResourceBundle createBundle(String key, Locale locale);

	abstract protected String getMenuRessourcePath();

	abstract protected String getCommandRessourcePath();

	abstract protected String getColorRessourcePath();

	abstract protected String getErrorRessourcePath();

	abstract protected String getPlainRessourcePath();

	abstract protected String getSymbolRessourcePath();

	@Override
	final public String getMenuTooltip(String key) {

		if (tooltipLocale == null) {
			return getMenu(key);
		}

		if (rbmenuTT == null) {
			rbmenuTT = createBundle(getMenuRessourcePath(), tooltipLocale);
		}

		try {
			return rbmenuTT.getString(key);
		} catch (Exception e) {
			return key;
		}
	}

	@Override
	final public String getError(String key) {
		if (rberror == null) {
			rberror = createBundle(getErrorRessourcePath(), currentLocale);
		}

		try {
			return rberror.getString(key);
		} catch (Exception e) {
			return key;
		}
	}

	@Override
	final public String getSymbol(int key) {
		if (rbsymbol == null) {
			initSymbolResourceBundle();
		}

		String ret = null;

		try {
			ret = rbsymbol.getString("S." + key);
		} catch (Exception e) {
			// do nothing
		}

		if ("".equals(ret)) {
			return null;
		}
		return ret;
	}

	@Override
	final public String getLanguage() {
		return getLocale().getLanguage();
	}

	@Override
	final public String getLocaleStr() {
		return getLocale().toString();
	}

	@Override
	final public String getSymbolTooltip(int key) {
		if (rbsymbol == null) {
			initSymbolResourceBundle();
		}

		String ret = null;

		try {
			ret = rbsymbol.getString("T." + key);
		} catch (Exception e) {
			// do nothing
		}

		if ("".equals(ret)) {
			return null;
		}
		return ret;
	}

	private void initSymbolResourceBundle() {
		rbsymbol = createBundle(getSymbolRessourcePath(), currentLocale);
	}

	@Override
	final public void initCommand() {
		if (rbcommand == null) {
			rbcommand = createBundle(getCommandRessourcePath(), currentLocale);
		}

	}

	private void initColorsResourceBundle() {
		rbcolors = createBundle(getColorRessourcePath(), currentLocale);
	}

	@Override
	final protected void updateResourceBundles() {
		if (rbmenu != null) {
			rbmenu = createBundle(getMenuRessourcePath(), currentLocale);
		}
		if (rberror != null) {
			rberror = createBundle(getErrorRessourcePath(), currentLocale);
		}

		if (rbcommand != null) {
			rbcommand = createBundle(getCommandRessourcePath(), currentLocale);
		}
		if (rbcolors != null) {
			rbcolors = createBundle(getColorRessourcePath(), currentLocale);
		}
		if (rbsymbol != null) {
			rbsymbol = createBundle(getSymbolRessourcePath(), currentLocale);
		}
	}

	/**
	 * @return whether properties bundles were initiated (at least plain)
	 */
	final public boolean propertiesFilesPresent() {
		return rbmenu != null;
	}

	/**
	 * @param s
	 *            language for tooltips
	 * @return success
	 */
	final public boolean setTooltipLanguage(String s) {
		Locale locale = null;

		for (int i = 0; i < getSupportedLocales().size(); i++) {
			if (getSupportedLocales().get(i).toString().equals(s)) {
				locale = getSupportedLocales().get(i);
				break;
			}
		}

		boolean updateNeeded = (rbmenuTT != null);

		rbmenuTT = null;

		if (locale == null) {
			tooltipLocale = null;
		} else if (currentLocale.toString().equals(locale.toString())) {
			tooltipLocale = null;
		} else {
			tooltipLocale = locale;
		}
		return updateNeeded;
	}

	/**
	 * @return tootlip loacle
	 */
	final public Locale getTooltipLocale() {
		return tooltipLocale;
	}

	@Override
	final public String getTooltipLanguageString() {
		if (tooltipLocale == null) {
			return null;
		}
		return tooltipLocale.toString();
	}

	@Override
	final public String getColor(String key) {

		if (key == null) {
			return "";
		}

		if ((key.length() == 5)
				&& StringUtil.toLowerCase(key).startsWith("gray")) {
			return StringUtil.getGrayString(key.charAt(4), this);
		}

		if (rbcolors == null) {
			initColorsResourceBundle();
		}

		try {
			return rbcolors.getString(StringUtil.toLowerCase(key));
		} catch (Exception e) {
			return key;
		}
	}

	@Override
	final public String reverseGetColor(String locColor) {
		String str = StringUtil.removeSpaces(StringUtil.toLowerCase(locColor));
		if (rbcolors == null) {
			initColorsResourceBundle();
		}

		try {

			Enumeration<String> enumer = rbcolors.getKeys();
			while (enumer.hasMoreElements()) {
				String key = enumer.nextElement();
				if (str.equals(StringUtil.removeSpaces(
						StringUtil.toLowerCase(rbcolors.getString(key))))) {
					return key;
				}
			}

			return str;
		} catch (Exception e) {
			return str;
		}
	}

	@Override
	final protected boolean isCommandChanged() {
		return rbcommandOld != rbcommand;
	}

	@Override
	final protected void setCommandChanged(boolean b) {
		rbcommandOld = rbcommand;

	}

	@Override
	final protected boolean isCommandNull() {
		return rbcommand == null;
	}

	@Override
	final protected String getLanguage(Locale locale) {
		return locale.getLanguage();
	}

	@Override
	final protected String getCountry(Locale locale) {
		return locale.getCountry();
	}

	@Override
	protected String getVariant(Locale locale) {
		return locale.getVariant();
	}

	public void test(ResourceBundle rbcommandEnglish) {
		String internalName = "LineBisector";
		Commands toTest = Commands.stringToCommand(internalName);
		Log.debug("toTest = " + toTest + " " + toTest.getClass());

		for (Commands c : Commands.values()) {
			Commands cInternal = Commands.englishToInternal(c);

			Log.debug(c.name() + " " + cInternal + " " + toTest + " "
					+ internalName);

			if (toTest.equals(cInternal)
					&& !c.name().equals(cInternal.toString())) {
				Log.debug("English name is " + c.name());
				return;
			}
		}

		Log.debug("nothing found, English name must be " + internalName);

		if (true)
			return;

		// internalName = "CircularSector";
		// cInt = Commands.stringToCommand(internalName);
		// Log.debug("cInt = " + cInt + " " + cInt.getClass());

		Iterator<String> it2 = rbcommand.keySet().iterator();
		boolean done = false;
		while (it2.hasNext()) {
			String internalName2 = it2.next();
			if (internalName2.indexOf(".Syntax") == -1) {
				Commands c = Commands.stringToCommand(internalName2);
				if (c == null) {
					// Log.debug("null lookup: internalName2 = " +
					// internalName2);
				}
				Commands cInt = Commands.englishToInternal(c);


				if (toTest == cInt) {
					// Log.debug("OK: " + cInt.name() + " " + c.name());
					done = true;
					break;
				}
			}

		}

		if (!done) {
			Log.error("not OK" + toTest.getClass());
		}


	}

	public void test2(ResourceBundle rbcommandEnglish) {
		Iterator<String> it = rbcommand.keySet().iterator();

		while (it.hasNext()) {
			String internalName = it.next();
			if (internalName.indexOf(".Syn") == -1) {
				String englishName = rbcommandEnglish.getString(internalName);
				if (!englishName.equals(internalName)) {
					Log.debug(englishName + " " + internalName);
				}

				Commands cEng = Commands.stringToCommand(englishName);
				Commands toTest = Commands.englishToInternal(cEng);

				Iterator<String> it2 = rbcommand.keySet().iterator();
				boolean done = false;
				while (it2.hasNext()) {
					String internalName2 = it2.next();
					Commands cInt = Commands.stringToCommand(internalName2);

					if (toTest == cInt) {
						Log.debug("OK: " + cInt);
						done = true;
						break;
					}

				}

				if (!done) {
					Log.error("not OK" + cEng.getClass() + " "
							+ toTest.getClass());
				}

			}
		}

	}

}
