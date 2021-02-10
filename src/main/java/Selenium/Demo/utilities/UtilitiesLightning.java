package Selenium.Demo.utilities;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UtilitiesLightning {
	private static boolean acceptNextAlert = true;
	
	public static enum INPUT_TEXT_LOCATION{DETAILS,FORM}

	public static boolean isElementPresent(WebDriver driver, By locatorKey) {
		try {
			driver.findElement(locatorKey);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isElementVisible(WebDriver driver, By locatorKey){
		return driver.findElement(locatorKey).isDisplayed();
	}

	public static WebElement clickElmenent(WebDriver driver, By locatorKey){
		WebElement element = null;
		try {
			Thread.sleep(1000);
			element= driver.findElement(locatorKey);
			(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(locatorKey));
			(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(locatorKey));
			System.out.println("Texto="+element.getText());
			element.click();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return element;
	}

	public static WebElement clickElmenentJs(WebDriver driver, WebElement element){
		try {
			Thread.sleep(1000);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", element);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return element;
	}
	public static WebElement clickElmenentJs(WebDriver driver, By locatorKey){
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(locatorKey));
		
		WebElement element = null;
		try {
			Thread.sleep(1000);
			element= driver.findElement(locatorKey);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", element);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return element;
	}
	public static void loginSF(WebDriver driver, String username, String password) {
		//Checks if new identify is present
		if(isElementVisible(driver,By.id("use_new_identity"))) {
			clickElmenentJs(driver, By.id("use_new_identity"));
		}

		//Checks if new identify is present
		if(isElementVisible(driver,By.id("clear_link"))) {
			clickElmenentJs(driver, By.id("clear_link"));
		}

		//Login
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		clickElmenentJs(driver, By.id("Login"));
		
		if(driver.findElements(By.partialLinkText ("I Don't Want to Register My Phone")).size() != 0) {
			driver.findElement(By.partialLinkText ("I Don't Want to Register My Phone")).click();
		}
		
		if(driver.findElements(By.className("continue") ).size() != 0) {
			driver.findElement(By.className("continue")).click();
		}
	}

	public static void setCheckbox(WebDriver driver, String label, boolean checked) {		
		WebElement checkbox=driver.findElement(By.xpath("//label[span=\""+label+"\"]/following-sibling::input"));

		if((checkbox.isSelected() && !checked) || (!checkbox.isSelected() && checked)) {
			checkbox.sendKeys(Keys.SPACE);
		}
		
	}
	public static void setRadioButton(WebDriver driver, String label){
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()=\""+label+"\"]/preceding-sibling::input")));
		WebElement checkbox=driver.findElement(By.xpath("//*[text()=\""+label+"\"]/preceding-sibling::input"));
		checkbox.sendKeys(Keys.SPACE);
	}

	public static void launchApp(WebDriver driver, String app) throws InterruptedException {
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();

		System.out.println("Browser="+browserName);

		if(browserName.equals("firefox")) {
			Thread.sleep(5000);
		}
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".slds-icon-waffle")));
		clickElmenent(driver, By.cssSelector(".slds-icon-waffle"));
		
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Alle anzeigen']")));
		clickElmenentJs(driver,By.xpath("//button[text()='Alle anzeigen']"));
		
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@data-label='"+app+"']")));
		clickElmenentJs(driver,By.xpath("//a[@data-label='"+app+"']"));
	}

	public static void openTab(WebDriver driver, String tab) {
		clickElmenentJs(driver, By.xpath("//a[. = \""+tab+"\"]"));
	}

	public static void openTabObject(WebDriver driver, String tab) {
		clickElmenent(driver, By.xpath("//a[@title = \""+tab+"\"]"));
	}


	public static void openLinkStringName(WebDriver driver, String link) {
		clickElmenentJs(driver, By.xpath("//a[. = \""+link+"\"]"));
	}
	public static void openLinkContainsName(WebDriver driver, String link) {
		clickElmenentJs(driver, By.xpath("//a[contains( text() ,\""+link+"\")]"));
	}
	public static void getColorElement(WebDriver driver,By locator)
	{
		WebElement element = driver.findElement(By.xpath("//li[. =\"Ganada\"]"));
		System.out.println(element.getText());
		System.out.println(element.getCssValue("background"));
	}
	public static void openActionInMoreActions(WebDriver driver, String action) {
		WebElement elementMoreAction =clickElmenentJs(driver, By.xpath("//a[contains( @title, \"more actions\")]"));
		WebDriverWait wait = new WebDriverWait(driver, 30); 
		WebElement elementAction = wait.until(ExpectedConditions.elementToBeClickable(elementMoreAction.findElement(By.xpath("//a[. = \""+action+"\"]"))));
		System.out.println("Texto="+elementAction.getText());
		elementAction.click();		
	}
	public static String getInputTextValue(WebDriver driver,String label){
		String value = null;
		if(isElementPresent(driver, By.xpath("//label[. = \""+label+"\"]/following-sibling::input"))) {
			WebElement element = driver.findElement(By.xpath("//label[. = \""+label+"\"]/following-sibling::input"));
			value = element.getText();
		}
		else{
			WebElement element = driver.findElement(By.xpath("//span[span= \""+label+"\"]/following-sibling::div/div/input"));
			value = element.getText();
		}	


		return value;
	}
	public static String getInputTextAreaValueInSection(WebDriver driver,String section,String label){

		WebElement element = driver.findElement(By.xpath("//h3//span[.=\""+section+"\"]/../../..//span[. = \""+label+"\"]/../following-sibling::div"));
		System.out.println("getInputTextAreaValueInSection="+element.getText());
		return element.getText();
	}
	public static void clickLinkInputTextAreaValueInSection(WebDriver driver,String section,String label){

		WebElement element = driver.findElement(By.xpath("//h3//span[.=\""+section+"\"]/../../..//span[. = \""+label+"\"]/../following-sibling::div"));
		clickElmenentJs(driver,element.findElement(By.tagName("a")));
	}
	public static void inputSelect(WebDriver driver,String label, String value) throws InterruptedException {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[span= \""+label+"\"]/following-sibling::div/div/div/div/a")));
		WebElement element=driver.findElement(By.xpath("//span[span= \""+label+"\"]/following-sibling::div/div/div/div/a"));
		element.sendKeys(Keys.SPACE);
		element.sendKeys(Keys.DOWN);
		element.sendKeys(Keys.DOWN);
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[. = \""+value+"\"]")));
		WebElement elementValue=driver.findElement(By.xpath("//a[. = \""+value+"\"]"));
		elementValue.sendKeys(Keys.SPACE);
		//openLinkStringName(driver,value);
	}
	public static void inputSelectPosition(WebDriver driver,String label, String value,int position) throws InterruptedException {

		WebElement element=driver.findElement(By.xpath("//span[span= \""+label+"\"]/following-sibling::div/div/div/div/a"));
		//element.click();
		//WebElement elementPopUp = driver.findElement(By.cssSelector(".popupTargetContainer"));

		element.sendKeys(Keys.SPACE);
		WebElement elementPopUp=driver.findElement(By.xpath("//a[. = \""+value+"\"]"));
		System.out.println(elementPopUp.getText());

		List<Keys> lk = new ArrayList<Keys>();
		for(int i = 0; i<  position ; i++ ) {
			lk.add(Keys.DOWN);
		}

		lk.add(Keys.SPACE);
		CharSequence[] cs = lk.toArray(new CharSequence[lk.size()]);
		//System.out.println(cs.toString());

		elementPopUp.sendKeys(cs);
		//	elementPopUp.sendKeys(Keys.DOWN,Keys.DOWN, Keys.SPACE);

		Thread.sleep(500);
	}

	public static void inputLookup(WebDriver driver,String lookup, String value) throws InterruptedException {
		if(isElementPresent(driver,By.xpath("//label[span= \""+lookup+"\"]/following-sibling::div/div/div/div/ul/li/a/a"))) {
			clickElmenentJs(driver,By.xpath("//label[span= \""+lookup+"\"]/following-sibling::div/div/div/div/ul/li/a/a"));
			Thread.sleep(1000);
		}
		WebElement elementLookup=driver.findElement(By.xpath("//label[span= \""+lookup+"\"]/following-sibling::div/div/div/div/input"));
		System.out.println(elementLookup.getText());
		//element.click();
		elementLookup.sendKeys(value);
		Thread.sleep(1000);
		elementLookup.sendKeys(Keys.DOWN);
		Thread.sleep(1000);
		elementLookup.sendKeys(Keys.ENTER);
	}

	public static void inputDate(WebDriver driver,String label, String date) {
		WebElement element = null;
		if(isElementPresent(driver,By.xpath("//label[. = \""+label+"\"]/following-sibling::div/input")))
		{
			element = driver.findElement(By.xpath("//label[. = \""+label+"\"]/following-sibling::div/input"));
		}
		else
		{
			element = driver.findElement(By.xpath("//label//span[. = \""+label+"\"]/../following-sibling::div/input"));
		}
		//WebElement element = driver.findElement(By.xpath("//label[. = \""+label+"\"]/following-sibling::div/input"));
		element.clear();	
		element.sendKeys(date);
	}

	public static void inputTextArea(WebDriver driver,String label, String text) {
		WebElement element = driver.findElement(By.xpath("//label[. = \""+label+"\"]/following-sibling::textarea"));
		element.clear();	
		element.sendKeys(text);
	}

	public static void inputText(WebDriver driver,String label,String text, INPUT_TEXT_LOCATION type) {
		/*if(isElementPresent(driver, By.xpath("//label[. = \""+label+"\"]/following-sibling::input"))) {
			inputFromLabel(driver, label, text);
		}
		else{
			inputFromLabelDetails(driver, label, text);
		}*/
		if(type == INPUT_TEXT_LOCATION.FORM) {
			inputFromLabel(driver, label, text);
		}
		else {
			inputFromLabelDetails(driver, label, text);
		}
	}
	public static void inputCheckBox(WebDriver driver,String label){
		WebElement element = null;

		if(isElementPresent(driver, By.xpath("//label[. = \""+label+"\"]/following-sibling::input"))) {
			element = driver.findElement(By.xpath("//label[. = \""+label+"\"]/following-sibling::input"));
		}
		else {
			element = driver.findElement(By.xpath("//label//span[. = \""+label+"\"]/../following-sibling::input"));
		}
		element.click();
	}
	public static void inputMultiPicklist(WebDriver driver, String label, String text,String buttonName) throws InterruptedException{
		//WebElement element = driver.findElement(By.cssSelector("slds-form-element__label slds-form-element__legend"));
		WebElement element = driver.findElement(By.xpath("//div[. = \""+label+"\"]/.."));
		WebElement valueElement = element.findElement(By.xpath("//li//div[. = \""+text+"\"]"));
		//clickElmenentJs(driver,valueElement);
		valueElement.click();
		Thread.sleep(500);
		WebElement button = element.findElement(By.xpath("//button [@title=\""+buttonName+"\"]"));
		//valueElement.click();
		button.click();

		//System.out.println(valueElement.getText());
		Thread.sleep(10000);
	}
	public static boolean isRequiredInput(WebDriver driver,String label) {
		boolean isRequired = false;
		WebElement wELabel = null;
		if(isElementPresent(driver, By.xpath("//label[. = \""+label+"\"]"))) {
			wELabel = driver.findElement(By.xpath("//label[. = \""+label+"\"]"));
		}
		else {
			wELabel= driver.findElement(By.xpath("//span[span= \""+label+"\"]"));
		}

		System.out.println(wELabel.getText());
		if(wELabel.getText().contains("*"))
		{
			isRequired = true;
		}
		return isRequired;
	}
	public static boolean existInputInLayout(WebDriver driver,String label) {
		boolean exist = false;
		WebElement wELabel = null;
		if(isElementPresent(driver, By.xpath("//span[. = \""+label+"\"]"))) {
			wELabel = driver.findElement(By.xpath("//span[. = \""+label+"\"]"));
		}
		else {
			wELabel= driver.findElement(By.xpath("//span[span= \""+label+"\"]"));
		}


		if(wELabel != null)
		{
			exist = true;
		}
		return exist;
	}
	public static void clickOnPopUp(WebDriver driver, String label)
	{

		WebElement element = driver.findElement(By.xpath("//h2[text()=\""+label+"\"]"));
		System.out.println("Title PopUp: " +element.getText());
		clickElmenentJs(driver,element);
		clickElmenentJs(driver,By.xpath("//h2[text()=\""+label+"\"]"));

	}
	public static void inputFromLabel(WebDriver driver, String label,String text) {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[. = \""+label+"\"]/following-sibling::input")));
		WebElement element = driver.findElement(By.xpath("//label[. = \""+label+"\"]/following-sibling::input"));
		element.clear();	
		element.sendKeys(text);	
	}

	public static void inputFromLabelWithInfo(WebDriver driver, String label,String text) {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[span = \""+label+"\"]/following-sibling::input")));
		WebElement element = driver.findElement(By.xpath("//label[span = \""+label+"\"]/following-sibling::input"));
		element.clear();	
		element.sendKeys(text);	
	}

	public static void inputFromLabelDetails(WebDriver driver, String label,String text) {
		WebElement element = driver.findElement(By.xpath("//span[span= \""+label+"\"]/following-sibling::div/div/input"));
		element.clear();	
		element.sendKeys(text);	
	}

	public static void clickButton(WebDriver driver, String title) {
		clickElmenentJs(driver,By.xpath("//button[@title = \""+title+"\"]"));	
	}

	public static void clickNextButton(WebDriver driver, String title) {
		clickElmenentJs(driver,By.xpath("//*[text() = \""+title+"\"]/parent::button"));	
	}

	public static void clickButtonQuickAction(WebDriver driver, String title) {
		clickElmenentJs(driver,By.xpath("//button[span= \""+title+"\"]"));	
	}

	public static void clickOnListViewElementByRow(WebDriver driver, int row) {
		clickElmenent(driver, By.cssSelector("tr:nth-child("+row+") > .lockTrigger .outputLookupLink"));
	}

	public static void clickFilterListViewElement(WebDriver driver, String filter) throws InterruptedException {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.name("search-input")));
		WebElement searchBox = driver.findElement(By.name("search-input"));
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(searchBox));


		searchBox.sendKeys(filter);
		searchBox.sendKeys(Keys.ENTER);
		//clickOnListViewElementByRow(driver,1);
		openLinkStringName(driver, filter);
	}

	public static void changeListView(WebDriver driver, String listView) throws InterruptedException {
		clickElmenentJs(driver,By.xpath(("//a[@title=\"Select List View\"]")));
		Thread.sleep(1000);
		clickElmenentJs(driver,By.xpath(("//a[span=\""+listView+"\"]")));
		Thread.sleep(2000);
	}

	public static void clickButtonListView(WebDriver driver, String button) {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title = \""+button+"\"]")));
		clickElmenentJs(driver, By.xpath("//a[@title = \""+button+"\"]"));
	}

	public static String getElementHighlight(WebDriver driver, String element) {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".slds-page-header__detail-row")));
		WebElement highlightPanel=driver.findElement(By.cssSelector(".slds-page-header__detail-row"));
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[. = \""+element+"\"]/following-sibling::div")));
		WebElement elementHighlihgt = highlightPanel.findElement(By.xpath("//span[. = \""+element+"\"]/following-sibling::div"));
		return elementHighlihgt.getText();
	}

	public static String getElementText(WebDriver driver, By selector) {
		WebElement elementText = driver.findElement(selector);
		return elementText.getText();
	}

	public static void searchInGlobalSearch(WebDriver driver, String searchElement) throws InterruptedException {
		Thread.sleep(1000);
		WebElement globalSearch=driver.findElement(By.cssSelector(".slds-global-header__item--search input.uiInputTextForAutocomplete"));
		globalSearch.sendKeys(searchElement);
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".uiAutocompleteList")));
		WebElement filterListGlobalSearch = driver.findElement(By.cssSelector(".uiAutocompleteList"));
		List<WebElement> elementSearched =(List<WebElement>)filterListGlobalSearch.findElements(By.cssSelector("li span"));

		for(int i=0; i <elementSearched.size(); i++) {
			try {
				WebElement elem= elementSearched.get(i);
				if(elem.getText().trim().equals(searchElement)) {
					elem.click();
				}
			}catch(Exception ex) {
				System.out.println("No lo ha encontrado");
				elementSearched =(List<WebElement>)filterListGlobalSearch.findElements(By.cssSelector("li span"));
				i--;
			}
		}
	}
	public static void selectRecordOnPosition(WebDriver driver,String searchElement ,int position)
	{
		List<WebElement> releatedListContainers = (List<WebElement>) driver.findElements(By.cssSelector(".forceRelatedListCardDesktop"));
		for(WebElement singleContainer : releatedListContainers)
		{
			if(singleContainer.findElement(By.cssSelector("h2 a span")).getText().contains(searchElement)) {
				List<WebElement> listRelatedRecords = 	singleContainer.findElements(By.cssSelector("tr"));
				WebElement elementRecord = listRelatedRecords.get(position).findElement(By.cssSelector("a"));
				elementRecord.click();
			}
		}
	}

	public static void newRecordOnReleatedList(WebDriver driver, String searchElement) throws InterruptedException
	{
		List<WebElement> releatedListContainers = (List<WebElement>) driver.findElements(By.cssSelector(".forceRelatedListCardDesktop"));
		for(WebElement singleContainer : releatedListContainers)
		{
			if(singleContainer.findElement(By.cssSelector("h2 a span")).getText().contains(searchElement)) {
				System.out.println("Container Text: "+singleContainer.getText());
				WebElement button = singleContainer.findElement(By.cssSelector(".forceActionLink"));
				System.out.println("Boton: " +button.getText());
				button.click();
			}

		}
		Thread.sleep(2000);
	}
	public static String getValueOfTheRecordOnRelatedTable(WebDriver driver,String searchElement ,int position,String name)
	{
		String result = null;


		List<WebElement> listRelatedRecords = driver.findElements(By.xpath("//span[.=\""+searchElement+"\"]/../../../../../..//thead//tr//th"));

		int colum = 0;

		for(int i = 0; i<listRelatedRecords.size(); i++)
		{
			try {
				System.out.println("Texto["+i+"]="+listRelatedRecords.get(i).getText());
				if(listRelatedRecords.get(i).getText().equals(name))
				{
					colum = i;
					System.out.println("Column="+i);
					break;
				}
			}catch(StaleElementReferenceException ex){
				System.out.println("Excepci�n="+ex.getMessage());
				listRelatedRecords = driver.findElements(By.xpath("//span[.=\""+searchElement+"\"]/../../../../../..//thead//tr//th"));
				i--;
			}
		}


		listRelatedRecords = driver.findElements(By.xpath("//h2//a//span[.=\""+searchElement+"\"]/../../../../../..//tbody//tr"));
		List<WebElement> listRows = listRelatedRecords.get(position-1).findElements(By.tagName("td"));
		//Lightning builds each row of the following way: th td td td
		result = listRows.get(colum-1).getText();
		System.out.println("Result="+result);


		return result;

	}
	public static void selectRelatedList(WebDriver driver, String searchElement )
	{
		List<WebElement> releatedListContainers = (List<WebElement>) driver.findElements(By.cssSelector(".forceRelatedListCardDesktop"));
		for(WebElement singleContainer : releatedListContainers)
		{
			if(singleContainer.findElement(By.cssSelector("h2 a span")).getText().contains(searchElement)) {
				List<WebElement> listRelatedRecords = singleContainer.findElements(By.cssSelector("a"));
				for(WebElement relatedRecord : listRelatedRecords)
				{
					if(relatedRecord.getText().equals(searchElement))
					{
						System.out.println(relatedRecord.getText());
						relatedRecord.click();
					}
				}
			}
		}
	}
	public static void selectRecordOnRelatedList(WebDriver driver, String searchElement, String searchRecord)
	{
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".forceRelatedListCardDesktop")));
		List<WebElement> releatedListContainers = (List<WebElement>) driver.findElements(By.cssSelector(".forceRelatedListCardDesktop"));
		for(WebElement singleContainer : releatedListContainers)
		{
			if(singleContainer.findElement(By.cssSelector("h2 a span")).getText().contains(searchElement)) {
				List<WebElement> listRelatedRecords = singleContainer.findElements(By.cssSelector("a"));
				for(WebElement relatedRecord : listRelatedRecords)
				{

					if(relatedRecord.getText().equals(searchRecord))
					{
						System.out.println("Record on Related :"+relatedRecord.getText());
						relatedRecord.click();
					}
				}
			}
		}
	}
	public static void logOut(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		WebElement profileMenu=driver.findElement(By.cssSelector(".profileTrigger"));
		profileMenu.click();
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".uiOutputURL")));
		WebElement logOut =driver.findElement(By.cssSelector(".uiOutputURL"));
		logOut.click();
		Thread.sleep(5000);

	}
	public static boolean existRelatedInLayout(WebDriver driver, String searchElement)
	{
		return isElementPresent(driver,By.xpath("//span[@title = \""+searchElement+"\"]"));
	}
	public static String checkActivities(WebDriver driver, String searchElement, String activityName,boolean click)
	{
		String activity = null; 
		WebElement container = driver.findElement(By.cssSelector(".openActivities"));

		List<WebElement> steps = container.findElements(By.xpath(".//div//h3[. = \""+searchElement+"\"]//../following-sibling::div/ul/li"));
		for(WebElement step:steps)
		{
			if(step.findElement(By.xpath(".//a[.=\""+activityName+"\"]")) != null)
			{
				activity = step.findElement(By.xpath(".//a[.=\""+activityName+"\"]")).getText();
				if(click) {
					WebElement stepElement = step.findElement(By.xpath(".//a[.=\""+activityName+"\"]"));
					clickElmenentJs(driver, stepElement);
				}

			}
		}
		return activity;
	}
	public static String getTaskDetails(WebDriver driver, String searchElement,String activityName ,String searchDetail )
	{
		String detail = null;

		WebElement container = driver.findElement(By.cssSelector(".openActivities"));

		List<WebElement> steps = container.findElements(By.xpath(".//div//h3[. = \""+searchElement+"\"]//../following-sibling::div/ul/li"));
		for(WebElement step:steps)
		{
			if(step.findElement(By.xpath(".//a[.=\""+activityName+"\"]")) != null)
			{
				step.findElement(By.xpath(".//div[contains(@class,\"showMore\")]//a[@role=\"button\"]")).click();

				detail = step.findElement(By.xpath(".//div[contains(@class,expandedDetails)]//div[.=\"Descripci�n\"]/..//following-sibling::div")).getText();
				//div[contains(@class,expandedDetails)]//div[.='Descripci�n']/..//following-sibling::div
			}
		}
		return detail;
	}
	public static boolean checkPath(WebDriver driver, String[] pathElements)
	{
		WebElement path = driver.findElement(By.cssSelector(".runtime_sales_pathassistantPathAssistantTabSet"));

		String pathText = path.getText();
		//WebElement list = path.findElement(By.xpath("//ul [@role = \"listbox\"]"));
		WebElement list = path.findElement(By.cssSelector("ul"));
		String listText = list.getText();
		List<WebElement> pathOptions = list.findElements(By.cssSelector("li"));
		//System.out.println(pathOptions.size());
		boolean isEquals = true;
		for(int i = 0; i<pathOptions.size() && isEquals; i++)
		{
			//System.out.println("Path: "+pathOptions.get(i).getText()+" Elementos Deberian estar en la lista: "+pathElements[i]);
			if(!pathOptions.get(i).getText().equals(pathElements[i]))
			{
				isEquals = false;
			}
		}

		return isEquals;
	}


	public static void scroll(WebDriver driver, int offset) throws InterruptedException
	{
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		//This will scroll the web page till end.		
		js.executeScript("window.scrollBy(0,"+offset+")");
	} 
	public static void scrollOnPopUp(WebDriver driver,String label, int offset) throws InterruptedException
	{
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		//This will scroll the web page till end.		
		//js.executeScript(element+".scrollBy(0,"+offset+")");
		js.executeScript("function getElementByXpath(path) {\r\n" + 
				"  return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\r\n" + 
				"}element =getElementByXpath(\"//div//h2[.=\\\""+label+"\\\"]/../following-sibling::div\");element.scrollBy(0,"+offset+")");
	} 
	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public static String closeAlertAndGetItsText(WebDriver driver) {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
