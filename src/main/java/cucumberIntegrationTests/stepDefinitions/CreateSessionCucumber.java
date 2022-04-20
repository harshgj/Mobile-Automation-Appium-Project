package cucumberIntegrationTests.stepDefinitions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.windows.WindowsDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import logger.Log;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;


/**
 * contains all the methods to create a new session and destroy the
 * session after the test(s) execution is over. Each test extends
 * this class.
 */
public class CreateSessionCucumber {

    public static WebDriver driver = null;
    public static Properties configPlatform = new Properties();
    protected FileInputStream configFis, configPlatformFis, configLocatorFis, configUserFis;
    private final String CONFIG_FILE_PATH = "//src//main//java//config//config.properties";
    protected File file = new File("");
    public static Properties configProp = new Properties();
    public static Scenario scenario;
    public final String androidOS = "android";
    public final String iPhoneOS = "iOS";
    public final String windowsOS = "windows";
    public final String macOS = "mac";
    public final String hostIPAddress = "127.0.0.1";
    public AppiumDriverLocalService appiumService;

    /**
     * this method initializes the running scenario properties to the static variable scenario
     */
    @Before
    public void scenarioInitialization(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * this method starts Appium server. Calls startAppiumServer method to start the session depending upon your OS.
     *
     * @throws Exception Unable to start appium server
     */
    @Before
    public void invokeAppium() throws Exception {
        try {
            String OS = System.getProperty("os.name").toLowerCase();
            startAppiumServer(OS);
            Log.info("Appium server started successfully");
        } catch (Exception e) {
            Log.logError(getClass().getName(), "invokeAppium", "Unable to start appium server");
            throw e;
        }
    }

    /**
     * this method stops Appium server.Calls stopAppiumServer method to
     * stop session depending upon your OS.
     *
     * @throws Exception Unable to stop appium server
     */
    @After
    public void stopAppium() throws Exception {
        try {
            stopAppiumServer();
            Log.info("Appium server stopped successfully");
        } catch (Exception e) {
            Log.logError(getClass().getName(), "stopAppium", "Unable to stop appium server");
            throw e;
        }
    }

    /**
     * this method creates the driver depending upon the passed parameter (android or iOS or windows)
     * and loads the properties files (config and test data properties files).
     *
     * @param os         android or iOS or windows
     * @param methodName - name of the method under execution
     * @throws Exception issue while loading properties files or creation of driver.
     */
    public void createDriver(String os, Method methodName) throws Exception {
        try {
            propertiesFileLoad(os);
            File propertiesFile = new File(file.getAbsoluteFile() + "//src//main//java//logger//log4j.properties");
            PropertyConfigurator.configure(propertiesFile.toString());
            Log.info("--------------------------------------");

            configLocatorFis = new FileInputStream(file.getAbsoluteFile()
                    + "/src//main//java//locators//locator.properties");
            configPlatform.load(configLocatorFis);

            configUserFis = new FileInputStream(file.getAbsoluteFile()
                    + "/src//main//java//users//user.properties");
            configPlatform.load(configUserFis);

            if (os.equalsIgnoreCase(androidOS)) {
                String buildPath = choosebuild(os);
                androidDriver(buildPath, methodName);
                Log.info("Android driver created");
            } else if (os.equalsIgnoreCase(iPhoneOS)) {
                String buildPath = choosebuild(os);
                iOSDriver(buildPath, methodName);
                Log.info("iOS driver created");
            } else if (os.equalsIgnoreCase(windowsOS)) {
                String buildPath = choosebuild(os);
                windowsDriver(buildPath, methodName);
                Log.info("Windows driver created");
            } else {
                Log.info(os + "is not supported yet");
            }
        } catch (Exception e) {
            Log.logError(getClass().getName(), "createDriver", "Unable to create driver");
            throw e;
        }
    }

    /**
     * this method creates the android driver
     *
     * @param buildPath  - path to pick the location of the app
     * @param methodName - name of the method under execution
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
     */
    public synchronized void androidDriver(String buildPath, Method methodName) throws MalformedURLException {
        File app = new File(buildPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "net.slideshare.mobile");
        capabilities.setCapability("appActivity", "net.slideshare.mobile.ui.SplashActivity");
        capabilities.setCapability("name", methodName.getName());
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("newCommandTimeout", 90);
        capabilities.setCapability("androidInstallTimeout", 180000);
        driver = new AndroidDriver<>(new URL("http://" + hostIPAddress + ":4723/wd/hub"), capabilities);
        // Start screen recording
        ((AndroidDriver) driver).startRecordingScreen();

    }

    /**
     * this method creates the iOS driver
     *
     * @param buildPath-  path to pick the location of the app
     * @param methodName- name of the method under execution
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
     */
    public void iOSDriver(String buildPath, Method methodName) throws MalformedURLException {
        File app = new File(buildPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "14.4");
        capabilities.setCapability("name", methodName.getName());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 12");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        // Ensure there is no existing WebDriverAgent on the device
        capabilities.setCapability("useNewWDA", true);
        // Allow 3 minutes for WebDriverAgent to launch (per attempt) before retrying (default is 60000)
        capabilities.setCapability("wdaLaunchTimeout", 180000);
        driver = new IOSDriver(new URL("http://" + hostIPAddress + ":4723/wd/hub"), capabilities);
        // Start screen recording
        ((IOSDriver) driver).startRecordingScreen(new IOSStartScreenRecordingOptions().withVideoType("mpeg4"));
    }


    /**
     * this method creates the windows driver
     *
     * @param buildPath-  path to pick the location of the app
     * @param methodName- name of the method under execution
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
     */
    public void windowsDriver(String buildPath, Method methodName) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("name", methodName.getName());
        capabilities.setCapability("app", buildPath + "!App");
        capabilities.setCapability("newCommandTimeout", 90);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);

        driver = new WindowsDriver<>(new URL("http://" + hostIPAddress + ":4723/wd/hub"), capabilities);
    }

    /**
     * this method starts the appium  server depending on your OS.
     *
     * @param os your machine OS (windows/linux/mac)
     * @throws IOException          Signals that an I/O exception of some sort has occurred
     * @throws ExecuteException     An exception indicating that the executing a subprocesses failed
     * @throws InterruptedException Thrown when a thread is waiting, sleeping,
     *                              or otherwise occupied, and the thread is interrupted, either before
     *                              or during the activity.
     */
    public void startAppiumServer(String os) throws Exception {
        try {
            String nodePath;
            String appiumPath;
            int port;

            if (os.contains(windowsOS)) {
                nodePath = "C:\\Program Files\\nodejs\\node.exe";
                appiumPath = "C:\\Users\\sffhhgcessssssssssss\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
                port = 4723;
            } else if (os.contains(macOS)) {
                nodePath = "/Users/runner/hostedtoolcache/node/14.17.3/x64/bin/node";
                appiumPath = "/Users/runner/hostedtoolcache/node/14.17.3/x64/lib/node_modules/appium/build/lib/main.js";
                port = 8080;
            } else {
                throw new Exception("Unable to start the Appium service:" + os + "is not yet supported.");
            }

            appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File(nodePath)).withAppiumJS(new File(appiumPath)).usingPort(port).withIPAddress(hostIPAddress));
            appiumService.start();
            Log.info("Appium service started successfully.");
        } catch (Exception e) {
            Log.logError(getClass().getName(), "startAppiumServer", e.getMessage());
            throw e;
        }
    }

    /**
     * this method stops the appium  server.
     *
     * @throws IOException      Signals that an I/O exception of some sort has occurred.
     * @throws ExecuteException An exception indicating that the executing a subprocesses failed.
     */
    public void stopAppiumServer() {
        try {
            appiumService.stop();
            Log.info("Appium service stopped successfully");
        } catch (Exception e) {
            Log.logError(getClass().getName(), "stopAppiumServer", e.getMessage());
            throw e;
        }
    }

    /**
     * this method loads properties files config and file having test data.
     *
     * @param platform android or ios, to load specific test data file.
     * @throws Exception property files are not loaded successfully
     */
    public void propertiesFileLoad(String platform) throws Exception {
        try {
            configFis = new FileInputStream(file.getAbsoluteFile()
                    + CONFIG_FILE_PATH);
            configProp.load(configFis);

            File f = new File(file.getAbsoluteFile() + "//src//main//java//config//" + platform
                    + "_config.properties");

            if (f.exists() && !f.isDirectory()) {
                configPlatformFis = new FileInputStream(file.getAbsoluteFile()
                        + "/src//main//java//config//" + platform + "_config.properties");
                configPlatform.load(configPlatformFis);
            } else {
                throw new Exception("Properties files loading failed ");
            }
        } catch (Exception e) {
            Log.logError(getClass().getName(), "propertiesFileLoad", "Unable to load the properties files");
            throw e;
        }
    }

    public String choosebuild(String invokeDriver) {
        String appPath = null;
        if (invokeDriver.equals(androidOS)) {
            appPath = configProp.getProperty("AndroidAppPath");
            return appPath;
        } else if (invokeDriver.equals(iPhoneOS)) {
            appPath = configProp.getProperty("iOSAppPath");
            return appPath;
        } else if (invokeDriver.equals(windowsOS)) {
            appPath = configProp.getProperty("WindowsAppPath");
            return appPath;
        }
        return appPath;
    }

    /**
     * this method quit the driver after the execution of test(s)
     */
    @After
    public void quitDriver() {
        try {
            Log.info("Resetting app and shutting down driver");
            if (driver instanceof WindowsDriver) {
                ((WindowsDriver) driver).close();
            } else if (driver instanceof AndroidDriver) {
                ((AndroidDriver) driver).resetApp();
                driver.quit();
            } else if (driver instanceof IOSDriver) {
                ((IOSDriver) driver).removeApp("com.xyz.abc");
                driver.quit();
            }
        } catch (Exception e) {
            Log.logError(getClass().getName(), "quitDriver", "Unable to quit the driver");
            throw e;
        }
    }

    @After
    public void takeScreenshotOnFailure() throws IOException {
        try {
            if (scenario.isFailed()) {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File("target/screenshots/screenshot.png"));
            }
        } catch (Exception e) {
            Log.logError(getClass().getName(), "takeScreenshotOnFailure", "Unable to take screenshot");
            throw e;
        }
    }

    @After
    public void stopRecording() throws IOException {
        try {
            if (System.getProperty("platform").equalsIgnoreCase(androidOS)) {
                String video = ((AndroidDriver) driver).stopRecordingScreen();
                byte[] decode = Base64.getDecoder().decode(video);
                FileUtils.writeByteArrayToFile(new File("target/videos/appiumRecording.mp4"), decode);
            } else if (System.getProperty("platform").equalsIgnoreCase(iPhoneOS)) {
                String video = ((IOSDriver) driver).stopRecordingScreen();
                byte[] decode = Base64.getDecoder().decode(video);
                FileUtils.writeByteArrayToFile(new File("target/videos/appiumRecording.mp4"), decode);
            }
        } catch (Exception e) {
            Log.logError(getClass().getName(), "stopRecording", "Unable to stop screen recording");
            throw e;
        }
    }

    public WebDriver getWebDriver() {
        return this.driver;
    }
}

