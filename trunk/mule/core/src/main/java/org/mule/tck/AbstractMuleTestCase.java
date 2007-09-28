/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck;

import org.mule.MuleManager;
import org.mule.config.MuleConfiguration;
import org.mule.impl.MuleDescriptor;
import org.mule.tck.testmodels.mule.TestConnector;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOException;
import org.mule.umo.UMOSession;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.manager.UMOManager;
import org.mule.umo.model.UMOModel;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.FileUtils;
import org.mule.util.MuleUrlStreamHandlerFactory;
import org.mule.util.StringMessageUtils;
import org.mule.util.StringUtils;
import org.mule.util.SystemUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;
import junit.framework.TestResult;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>AbstractMuleTestCase</code> is a base class for Mule testcases. This
 * implementation provides services to test code for creating mock and test objects.
 */
public abstract class AbstractMuleTestCase extends TestCase implements TestCaseWatchdogTimeoutHandler
{
    /**
     * Top-level directories under <code>.mule</code> which are not deleted on each
     * test case recycle. This is required, e.g. to play nice with transaction manager
     * recovery service object store.
     */
    public static final String[] IGNORED_DOT_MULE_DIRS = new String[] {"transaction-log"};

    /**
     * This flag controls whether the text boxes will be logged when starting each test case.
     */
    private static final boolean verbose;

    protected final Log logger = LogFactory.getLog(this.getClass());

    // A Map of test case extension objects. JUnit creates a new TestCase instance for
    // every method, so we need to record metainfo outside the test.
    private static final Map testInfos = Collections.synchronizedMap(new HashMap());

    // Should be set to a string message describing any prerequisites not met.
    private boolean offline = "true".equalsIgnoreCase(System.getProperty("org.mule.offline"));

    // Barks if the test exceeds its time limit
    private TestCaseWatchdog watchdog;

    static
    {
        String muleOpts = SystemUtils.getenv("MULE_TEST_OPTS");
        if (StringUtils.isNotBlank(muleOpts))
        {
            Map parsedOpts = SystemUtils.parsePropertyDefinitions(muleOpts);
            String optVerbose = (String)parsedOpts.get("mule.verbose");
            verbose = Boolean.valueOf(optVerbose).booleanValue();
        }
        else
        {
            // per default, revert to the old behaviour
            verbose = true;
        }

        // register the custom UrlStreamHandlerFactory.
        MuleUrlStreamHandlerFactory.installUrlStreamHandlerFactory();
    }

    public AbstractMuleTestCase()
    {
        super();

        TestInfo info = (TestInfo) testInfos.get(getClass().getName());
        if (info == null)
        {
            info = this.createTestInfo();
            testInfos.put(getClass().getName(), info);
        }

        info.incTestCount();
    }

    protected TestInfo createTestInfo()
    {
        return new TestInfo(this);
    }

    protected TestInfo getTestInfo()
    {
        return (TestInfo) testInfos.get(this.getClass().getName());
    }

    private void clearInfo()
    {
        testInfos.remove(this.getClass().getName());
    }

    public String getName()
    {
        return super.getName().substring(4).replaceAll("([A-Z])", " $1").toLowerCase() + " ";
    }

    public void run(TestResult result)
    {
        if (this.isExcluded())
        {
            if (verbose)
            {
                logger.info(this.getClass().getName() + " excluded");
            }
            return;
        }

        if (this.isDisabledInThisEnvironment())
        {
            if (verbose)
            {
                logger.info(this.getClass().getName() + " disabled");
            }
            return;
        }

        super.run(result);
    }

    /**
     * Shamelessly copy from Spring's ConditionalTestCase so in MULE-2.0 we can extend
     * this class from ConditionalTestCase.
     * <p/>
     * Subclasses can override <code>isDisabledInThisEnvironment</code> to skip a single test.
     */
    public void runBare() throws Throwable
    {
        // getName will return the name of the method being run. Use the real JUnit implementation,
        // this class has a different implementation
        if (this.isDisabledInThisEnvironment(super.getName()))
        {
            logger.warn(this.getClass().getName() + "." + super.getName() + " disabled in this environment");
            return;
        }

        // Let JUnit handle execution
        super.runBare();
    }

    /**
     * Subclasses can override this method to skip the execution of the entire test class.
     *
     * @return <code>true</code> if the test class should not be run.
     */
    protected boolean isDisabledInThisEnvironment()
    {
        return false;
    }

    /**
     * Indicates whether this test has been explicitly disabled through the configuration
     * file loaded by TestInfo.
     *
     * @return whether the test has been explicitly disabled
     */
    protected boolean isExcluded()
    {
        return getTestInfo().isExcluded();
    }

    /**
     * Should this test run?
     * @param testMethodName name of the test method
     * @return whether the test should execute in the current envionment
     */
    protected boolean isDisabledInThisEnvironment(String testMethodName)
    {
        return false;
    }

    public boolean isOffline(String method)
    {
        if (offline)
        {
            logger.warn(StringMessageUtils.getBoilerPlate(
                "Working offline cannot run test: " + method, '=', 80));
        }
        return offline;
    }

    protected boolean isDisposeManagerPerSuite()
    {
        return getTestInfo().isDisposeManagerPerSuite();
    }

    protected void setDisposeManagerPerSuite(boolean val)
    {
        getTestInfo().setDisposeManagerPerSuite(val);
    }

    protected TestCaseWatchdog createWatchdog()
    {
        return new TestCaseWatchdog(30, TimeUnit.MINUTES, this);
    }

    public void handleTimeout(long timeout, TimeUnit unit)
    {
        logger.fatal("Timeout of " + unit.toMillis(timeout) + "ms exceeded - exiting VM!");
        Runtime.getRuntime().halt(1);
    }

    protected final void setUp() throws Exception
    {
        // start a watchdog thread
        watchdog = createWatchdog();
        watchdog.start();

        if (verbose)
        {
            System.out.println(StringMessageUtils.getBoilerPlate("Testing: " + toString(), '=', 80));
        }

        MuleManager.getConfiguration().getDefaultThreadingProfile().setDoThreading(false);
        MuleManager.getConfiguration().setServerUrl(StringUtils.EMPTY);

        try
        {
            if (getTestInfo().getRunCount() == 0)
            {
                if (getTestInfo().isDisposeManagerPerSuite())
                {
                    // We dispose here jut in case
                    disposeManager();
                }
                suitePreSetUp();
            }
            if (!getTestInfo().isDisposeManagerPerSuite())
            {
                // We dispose here jut in case
                disposeManager();
            }
            doSetUp();
            if (getTestInfo().getRunCount() == 0)
            {
                suitePostSetUp();
            }
        }
        catch (Exception e)
        {
            getTestInfo().incRunCount();
            throw e;
        }
    }

    protected void suitePreSetUp() throws Exception
    {
        // nothing to do
    }

    protected void suitePostSetUp() throws Exception
    {
        // nothing to do
    }

    protected void suitePreTearDown() throws Exception
    {
        // nothing to do
    }

    protected void suitePostTearDown() throws Exception
    {
        // nothing to do
    }

    protected final void tearDown() throws Exception
    {
        try
        {
            if (getTestInfo().getRunCount() == getTestInfo().getTestCount())
            {
                suitePreTearDown();
            }

            doTearDown();

            if (!getTestInfo().isDisposeManagerPerSuite())
            {
                disposeManager();
            }
        }
        finally
        {
            try
            {
                getTestInfo().incRunCount();
                if (getTestInfo().getRunCount() == getTestInfo().getTestCount())
                {
                    try
                    {
                        suitePostTearDown();
                    }
                    finally
                    {
                        clearInfo();
                        disposeManager();
                    }
                }
            }
            finally
            {
                // remove the watchdog thread in any case
                watchdog.cancel();
            }
        }
    }

    protected void disposeManager() throws UMOException
    {
        if (MuleManager.isInstanciated())
        {
            MuleManager.getInstance().dispose();
        }

        // do not delete TM recovery object store, everything else is good to go
        FileUtils.deleteTree(FileUtils.newFile(MuleManager.getConfiguration().getWorkingDirectory()),
                             IGNORED_DOT_MULE_DIRS);
        FileUtils.deleteTree(FileUtils.newFile("./ActiveMQ"));
        MuleManager.setConfiguration(new MuleConfiguration());
    }

    protected void doSetUp() throws Exception
    {
        // template method
    }

    protected void doTearDown() throws Exception
    {
        // template method
    }

    public static UMOManager getManager(boolean disableAdminAgent) throws Exception
    {
        return MuleTestUtils.getManager(disableAdminAgent);
    }

    public static UMOModel getDefaultModel() throws UMOException
    {
        return MuleTestUtils.getDefaultModel();
    }

    public static UMOEndpoint getTestEndpoint(String name, String type) throws Exception
    {
        return MuleTestUtils.getTestEndpoint(name, type);
    }

    public static UMOEvent getTestEvent(Object data) throws Exception
    {
        return MuleTestUtils.getTestEvent(data);
    }

    public static UMOEventContext getTestEventContext(Object data) throws Exception
    {
        return MuleTestUtils.getTestEventContext(data);
    }

    public static UMOTransformer getTestTransformer()
    {
        return MuleTestUtils.getTestTransformer();
    }

    public static UMOEvent getTestEvent(Object data, MuleDescriptor descriptor) throws Exception
    {
        return MuleTestUtils.getTestEvent(data, descriptor);
    }

    public static UMOEvent getTestEvent(Object data, UMOImmutableEndpoint endpoint) throws Exception
    {
        return MuleTestUtils.getTestEvent(data, endpoint);
    }

    public static UMOEvent getTestEvent(Object data, MuleDescriptor descriptor, UMOImmutableEndpoint endpoint)
        throws UMOException
    {
        return MuleTestUtils.getTestEvent(data, descriptor, endpoint);
    }

    public static UMOSession getTestSession(UMOComponent component)
    {
        return MuleTestUtils.getTestSession(component);
    }

    public static TestConnector getTestConnector()
    {
        return MuleTestUtils.getTestConnector();
    }

    public static UMOComponent getTestComponent(MuleDescriptor descriptor)
    {
        return MuleTestUtils.getTestComponent(descriptor);
    }

    public static MuleDescriptor getTestDescriptor(String name, String implementation) throws Exception
    {
        return MuleTestUtils.getTestDescriptor(name, implementation);
    }

    public static UMOManager getTestManager() throws Exception
    {
        return MuleTestUtils.getManager(true);
    }
   
    public static class TestInfo
    {
        /**
         * Whether to dispose the manager after every method or once all tests for
         * the class have run
         */
        private final String name;
        private boolean disposeManagerPerSuite = false;
        private boolean excluded = false;
        private int testCount = 0;
        private int runCount = 0;

        // TODO HH: MULE-2414
        // this is a shorter version of the snippet from:
        // http://www.davidflanagan.com/blog/2005_06.html#000060
        // (see comments; DF's "manual" version works fine too)
        public static URL getClassPathRoot(Class clazz)
        {
            CodeSource cs = clazz.getProtectionDomain().getCodeSource();
            return (cs != null ? cs.getLocation() : null);
        }

        public TestInfo(TestCase test)
        {
            this.name = test.getClass().getName();

            // load test exclusions
            try
            {
                // TODO HH: MULE-2414
                // This may fail with jar files (such as in maven) since
                // IOUtils/ClassUtils uses delegation-first to locate resources in
                // jar files, which may then not be accessible to the actual test class.
                // URL fileUrl = IOUtils.getResourceAsUrl("mule-test-exclusions.txt", test.getClass());

                // TODO HH: MULE-2414
                // We find the physical classpath root URL of the test class and
                // use that to find the correct resource. Works fine everywhere,
                // regardless of classloaders.
                URL[] urls = new URL[]{getClassPathRoot(test.getClass())};
                URL fileUrl = new URLClassLoader(urls).getResource("mule-test-exclusions.txt");

                if (fileUrl != null)
                {
                    // this iterates over all lines in the exclusion file
                    Iterator lines = FileUtils.lineIterator(FileUtils.newFile(fileUrl.getFile()));

                    // ..and this finds non-comments that match the test case name
                    excluded = IteratorUtils.filteredIterator(lines, new Predicate()
                    {
                        public boolean evaluate(Object object)
                        {
                            return StringUtils.equals(name, StringUtils.trimToEmpty((String) object));
                        }
                    }).hasNext();
                }
            }
            catch (IOException ioex)
            {
                // ignore
            }
        }

        public int getTestCount()
        {
            return testCount;
        }

        public void incTestCount()
        {
            testCount++;
        }

        public int getRunCount()
        {
            return runCount;
        }

        public void incRunCount()
        {
            runCount++;
        }

        public String getName()
        {
            return name;
        }

        public boolean isDisposeManagerPerSuite()
        {
            return disposeManagerPerSuite;
        }

        public void setDisposeManagerPerSuite(boolean disposeManagerPerSuite)
        {
            this.disposeManagerPerSuite = disposeManagerPerSuite;
        }

        public boolean isExcluded()
        {
            return excluded;
        }

        public String toString()
        {
            StringBuffer buf = new StringBuffer();
            return buf.append(name).append(", (").append(runCount).append(" / ").append(testCount).append(
                ") tests run, disposePerSuite=").append(disposeManagerPerSuite).toString();
        }
    }
}
