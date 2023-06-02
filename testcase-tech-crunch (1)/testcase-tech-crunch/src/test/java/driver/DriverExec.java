package driver;


import com.thoughtworks.gauge.*;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DriverExec {
    private static final Logger log = Logger.getLogger(String.valueOf(DriverExec.class));
    public static WebDriver driver;


    @BeforeScenario
    public void setUp() {
        String browser = "chrome";
        driver = LocalBrowserExec.LocalDriver(browser);
        driver.manage().timeouts().pageLoadTimeout(299, TimeUnit.SECONDS).implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }
    @BeforeScenario
    public void beforeScenario(ExecutionContext executionContext){
        log.info("=========================================================================================================");
        log.info("-----------------------------------------SCENARIO--------------------------------------------------------");
        String testCaseName=executionContext.getCurrentScenario().getName();
        String testCaseTagName=executionContext.getCurrentScenario().getTags().toString();
        log.info("SCENARIO NAME:"+ testCaseName);
        log.info("SCENARIO TAG:"+ testCaseTagName);
        System.out.println("\r\n");

    }

    /*@BeforeStep
    public void beforeStep(ExecutionContext executionContext){
        log.info("=========================" + executionContext.getCurrentStep().getDynamicText() +"==============================");

    }
    @AfterStep
    public void afterStep(ExecutionContext executionContext){
        if(executionContext.getCurrentStep().getIsFailing()){
            log.finer(executionContext.getCurrentSpecification().getFileName());
            log.finer("Message:" + executionContext.getCurrentStep().getErrorMessage()
                    + executionContext.getCurrentStep().getStackTrace());

            log.finer("==================================================================================================");
            System.out.println("\r\n");
        }
        }*/


    @AfterScenario
    public void tearDown()  {
       // driver.quit();
    }

}
