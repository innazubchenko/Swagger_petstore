package API_tests.store_tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static API.config.APIConfig.setAuthorizationToken;
import static core.utils.Hooks.destroyCacheContext;
import static core.utils.Hooks.startCacheContext;

public class BaseTest {

    @BeforeMethod
    public void setupConfigurationForTest() {
        startCacheContext();
        setAuthorizationToken();
    }

    @AfterMethod
    public void cleanUpConfiguration() {
        destroyCacheContext();
    }
}
