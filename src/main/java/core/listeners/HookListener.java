package core.listeners;

import core.custom_annotations.NonAuthorized;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;

import static API.APIConfig.deleteAuthorizationToken;
import static API.APIConfig.setAuthorizationToken;

public class HookListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        Method testMethod = method.getTestMethod().getConstructorOrMethod().getMethod();
        if (testMethod.isAnnotationPresent(NonAuthorized.class)) {
            deleteAuthorizationToken();
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        Method testMethod = method.getTestMethod().getConstructorOrMethod().getMethod();
        if (testMethod.isAnnotationPresent(NonAuthorized.class)) {
            setAuthorizationToken();
        }
    }
}
