package project.azure;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class App {
	
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions action;
	
    public static void main( String[] args ) throws InterruptedException {


    	System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
    	System.setProperty("webdriver.chrome.silentOutput", "true");
    	
    	HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("plugins.always_open_pdf_externally", true);
        chromePrefs.put("download.default_directory", "C:\\Users\\aswin\\Downloads\\reports");
    	
    	ChromeOptions option = new ChromeOptions();
    	option.addArguments("--disable-notifications");
    	option.addArguments("--start-maximized");
    	option.setExperimentalOption("prefs", chromePrefs);
    	 
    	driver = new ChromeDriver(option);
    	wait = new WebDriverWait(driver, 10);
    	action = new Actions(driver);
    	
    	//1) Go to https://azure.microsoft.com/en-in/
    	
    	driver.get("https://azure.microsoft.com/en-in/"); //Open site
    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	    	
    	//2) Click on Pricing
    	
    	driver.findElement(By.id("navigation-pricing")).click();
    	
    	//3) Click on Pricing Calculator
    	
    	driver.findElement(By.xpath("//a[contains(text(),'Pricing calculator')]")).click();
    	
    	//4) Click on Containers
    	
    	driver.findElement(By.xpath("(//button[contains(text(),'Containers')])[2]")).click();
    	
    	//5) Select Container Instances
    	
    	driver.findElement(By.xpath("(//span[contains(text(),'Easily run containers on Azure without managing servers')])[3]")).click();
    	
    	//6) Click on Container Instance Added View
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='View']"))).click();
    	
    	//7) Select Region as "South India"
    	Thread.sleep(3000);
    	WebElement region = driver.findElement(By.name("region"));
    	Select select = new Select(region);
    	select.selectByValue("south-india");
    	
    	//8) Set the Duration as 180000 seconds
    	
    	driver.findElement(By.name("seconds")).sendKeys(Keys.chord(Keys.CONTROL,"a"),"180000");
    	    	
    	//9) Select the Memory as 4GB
    	
    	WebElement memory = driver.findElement(By.name("memory"));
    	Select memSelect = new Select(memory);
    	memSelect.selectByValue("4");
    	
    	//10) Enable SHOW DEV/TEST PRICING
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("devtest-toggler"))).click();
    	//driver.findElement(By.className("toggler-slide ")).click();
    	
    	//11) Select Indian Rupee  as currency
    	
    	WebElement currency1 = driver.findElement(By.xpath("//select[contains(@class,'select currency-dropdown')]"));
    	Select currSelect = new Select(currency1);
    	currSelect.selectByValue("INR");    	
    	
    	//12) Print the Estimated monthly price
    	
    	String monthlyCost = driver.findElement(By.xpath("//span[text()='Monthly cost']//parent::div/span[2]/span")).getText();
    	String substring = monthlyCost.substring(1, 7);
    	double monthlyPrice = Double.parseDouble(substring);
    	System.out.println("Estimated monthly price is : "+ monthlyPrice);

    	//13) Click on Export to download the estimate as excel
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Export']"))).click();
    	
    	//14) Verify the downloded file in the local folder
    	
    	//https://github.com/TestLeafPages/Research/blob/master/PdfFileDownload.java
    	File estFile = new File("C:\\Users\\aswin\\Downloads\\reports\\ExportedEstimate.xlsx");
    	//Path yourFile = Paths.get("C:\\Users\\aswin\\Downloads\\reports\\ExportedEstimate.xlsx");
    	if(estFile.exists()){
    		//Files.move(yourFile, yourFile.resolveSibling("Estimation1.xlsx"));
    		System.out.println("Estimated monthly cost document is downloaded");
    		//File rnmFile = new File("Estimate.xlsx");
    		//estFile.renameTo(rnmFile);
    	}else {
			System.out.println("Estimated monthly cost document is not downloaded");
		}
    	
    	Thread.sleep(3000);
    	
    	
    	//15) Navigate to Example Scenarios and Select CI/CD for Containers
    	    	
    	WebElement expScenario = driver.findElement(By.xpath("//a[text()='Example Scenarios']"));
    	action.moveToElement(expScenario).click().build().perform();
    	
    	driver.findElement(By.xpath("//span[text()='CI/CD for Containers']")).click();
    	
    	//16) Click Add to Estimate
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add to estimate']"))).click();
    	Thread.sleep(3000);
    	
    	//17) Change the Currency as Indian Rupee    	
    	
    	WebElement currency2 = driver.findElement(By.xpath("//select[contains(@class,'select currency-dropdown')]"));
    	action.moveToElement(currency2).click().build().perform();
    	Select currSelect2 = new Select(currency2);
    	currSelect2.selectByValue("INR");
    	
    	//18) Enable SHOW DEV/TEST PRICING
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("devtest-toggler"))).click();
    	
    	//19) Export the Estimate
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Export']"))).click();
    	
    	//20) Verify the downloded file in the local folder		
    	
    	File newEstFile = new File("C:\\Users\\aswin\\Downloads\\repots\\ExportedEstimate (1).xlsx");
    	if(newEstFile.exists()){
    		System.out.println("Estimated monthly cost document is downloaded");
    	}else {
    		System.out.println("Estimated monthly cost document is not downloaded");
    	}
    	
    	Thread.sleep(5000);
    	driver.close();
    }
}
