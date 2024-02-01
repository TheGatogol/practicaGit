package automation;

package evidence;

import org.openqa.selenium.WebDriver;

/**
* Author: Jose Rodrigo Escutia Rios
* Framework: Keywords powered by Selenium (KS)
* Version: 1.0.0
*/

public class formatEvidence{

	public PrintStep(String descripcionPaso, WebDriver webdriver) throws Exception {
		FormatEvidence.addNewDescription(descripcionPaso);
		FormatEvidence.takeSnapShot(webdriver);
	}

