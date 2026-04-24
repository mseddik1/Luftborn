package api.base;

import apis.api.ApiClient;

import apis.services.UserService;
import listeners.TestListeners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import utils.ConfigManager;

import java.lang.reflect.Method;
@Listeners(TestListeners.class)
public class BaseTests {

    private static final Logger log = LoggerFactory.getLogger(BaseTests.class);


    protected ApiClient apiClient;
    protected UserService userService;
    private String env;

    @BeforeClass(alwaysRun = true )
    @Parameters({"env"})
    public void initialize(@Optional("prod") String env) {
        this.env = env;

    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method m) {
        log.info("Initiating {} | Thread: {}", m.getName(), Thread.currentThread().threadId());

        apiClient = new ApiClient(ConfigManager.get(env+".api.baseUrl"));
        userService = new UserService(apiClient);
    }
}