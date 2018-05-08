package com.epam.careers.extensions;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

@Slf4j
public class TestsExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback, TestExecutionExceptionHandler {
    private static final String START_TIME = "start time";

    /**
     * Callback that is invoked <em>immediately after</em> each test has been executed.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        long startTime = getStore(context).remove(START_TIME, long.class);
        long duration = (System.currentTimeMillis() - startTime)/1000;

        val testContext = getContext(context);
        val testMethodName = testContext.getDisplayName();
        val testClassName = testContext.getTestClass().get().getSimpleName();

        log.info(String.format("FINISH TEST : '%s' IN CLASS: '%s' within %s seconds", testMethodName, testClassName, duration));
    }

    /**
     * Callback that is invoked <em>immediately before</em> each test is executed.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        getStore(context).put(START_TIME, System.currentTimeMillis());
        val testContext = getContext(context);
        val testMethodName = testContext.getDisplayName();
        val testClassName = testContext.getTestClass().get().getSimpleName();
        log.info(String.format("START TEST : '%s' IN CLASS: '%s'", testMethodName, testClassName));
    }

    /**
     * Handle the supplied {@link Throwable throwable}.
     *
     * <p>Implementors must perform one of the following.
     * <ol>
     * <li>Swallow the supplied {@code throwable}, thereby preventing propagation.</li>
     * <li>Rethrow the supplied {@code throwable} <em>as is</em>.</li>
     * <li>Throw a new exception, potentially wrapping the supplied {@code throwable}.</li>
     * </ol>
     *
     * <p>If the supplied {@code throwable} is swallowed, subsequent
     * {@code TestExecutionExceptionHandlers} will not be invoked; otherwise,
     * the next registered {@code TestExecutionExceptionHandler} (if there is
     * one) will be invoked with any {@link Throwable} thrown by this handler.
     *
     * <p>Note that the {@link ExtensionContext#getExecutionException() execution
     * exception} in the supplied {@code ExtensionContext} will <em>not</em>
     * contain the {@code Throwable} thrown during invocation of the corresponding
     * {@code @Test} method.
     *
     * @param context   the current extension context; never {@code null}
     * @param throwable the {@code Throwable} to handle; never {@code null}
     */
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        val testContext = getContext(context);
        val testMethodName = testContext.getDisplayName();
        val testClassName = testContext.getTestClass().get().getSimpleName();
        log.error(String.format("TEST : '%s' IN CLASS: '%s' FAILED WITH ERROR: %s", testMethodName, testClassName, throwable.getMessage()));
        log.error("Error details: ", throwable);
    }

    private ExtensionContext getContext(ExtensionContext context) {
        if(context.getParent().get().getTestMethod().isPresent()) {
            return context.getParent().get();
        }
        return context;
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }

}
