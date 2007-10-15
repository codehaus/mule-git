package org.mule.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.site.renderer.SiteRenderer;

/**
 * @goal test-exclusions
 */
public class TestExclusionsReport extends AbstractMavenReport
{
    private static final String RESOURCE_BUNDLE_NAME = "mule-test-exclusions-report-messages";
    
    /**
     * The Maven Project Object
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * The directory where the report will be generated
     *
     * @parameter expression="${project.reporting.outputDirectory}"
     * @required
     * @readonly
     */
    private File outputDirectory;
    
    /**
     * @parameter expression="${component.org.codehaus.doxia.site.renderer.SiteRenderer}"
     * @required
     * @readonly
     */
    private SiteRenderer siteRenderer;
        
    protected void executeReport(Locale locale) throws MavenReportException
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale);
        
        if (project.isExecutionRoot())
        {
            this.generateAggregateExclusionsReport(resourceBundle);
        }
        
        this.generateExclusionsReport(resourceBundle);
    }

    private void generateAggregateExclusionsReport(ResourceBundle resourceBundle) throws MavenReportException
    {
        List collectedExclusions = new ArrayList();
        Iterator projectIter = project.getCollectedProjects().iterator();
        while (projectIter.hasNext())
        {
            MavenProject proj = (MavenProject) projectIter.next();
            collectedExclusions.addAll(this.readTestExclusions(proj));
        }
        
        Sink sink = this.getSink();
        sink.head();
        sink.title();
        sink.text(resourceBundle.getString("html.aggregate.title"));
        sink.title_();
        sink.head_();
        
        sink.body();
        sink.section1();
        sink.sectionTitle1();
        sink.text(resourceBundle.getString("html.aggregate.summary.title"));
        sink.sectionTitle1_();
        sink.bold();
        String text = MessageFormat.format(resourceBundle.getString("html.aggregate.summary"), 
            new Object[] { new Long(collectedExclusions.size()) });
        sink.text(text);
        sink.bold_();
        sink.section1_();
        
        sink.section1();
        sink.sectionTitle1();
        sink.text(resourceBundle.getString("html.aggregate.section.title"));
        sink.sectionTitle1_();
        this.generateExcludedTestsTable(collectedExclusions, sink);
        sink.section1_();
        sink.body_();

        sink.flush();
        sink.close();
    }

    private void generateExclusionsReport(ResourceBundle resourceBundle) throws MavenReportException
    {
        List excludedTests = this.readTestExclusions(project);

        Sink sink = this.getSink();
        sink.head();
        sink.title();
        sink.text(resourceBundle.getString("html.title"));
        sink.title_();
        sink.head_();
        
        sink.body();
        sink.section1();
        sink.sectionTitle1();
        sink.text(resourceBundle.getString("html.section.title"));
        sink.sectionTitle1_();
        this.generateExcludedTestsTable(excludedTests, sink);
        sink.section1_();
        sink.body_();

        sink.flush();
        sink.close();
    }

    private void generateExcludedTestsTable(List excludedTests, Sink sink)
    {
        sink.table();        
        Iterator exclusionIter = excludedTests.iterator();
        while (exclusionIter.hasNext())
        {
            String test = (String) exclusionIter.next();
            sink.tableRow();
            sink.tableCell();
            sink.text(test);
            sink.tableCell_();
            sink.tableRow_();
        }
        sink.table_();
    }

    private List readTestExclusions(MavenProject proj) throws MavenReportException
    {
        // TODO test with target directory configured somewhere else
        File testExclusionsFile = this.testExclusionsFile(proj);
        if (testExclusionsFile.exists() == false)
        {
            return Collections.EMPTY_LIST;
        }
        
        try
        {
            List excludedTests = new ArrayList();
            BufferedReader reader = new BufferedReader(new FileReader(testExclusionsFile));
            String line = reader.readLine();
            while (line != null)
            {
                if ((StringUtils.isBlank(line) == false) && (line.startsWith("#") == false))
                {
                    excludedTests.add(line);
                }
                
                line = reader.readLine();
            }               
            reader.close();
            
            return excludedTests;
        }
        catch (IOException iox)
        {
            throw new MavenReportException("Error reading mule-test-exclusions.txt", iox);
        }
    }
        
    private File testExclusionsFile(MavenProject proj)
    {
        return new File(proj.getFile().getParent(), "src/test/resources/mule-test-exclusions.txt");
    }
    
    /**
     * This method is used by the superclass to determine whether the report should be generated
     * for the current project. Do it only, if we have a mule-test-exclusions.txt.
     */
    public boolean canGenerateReport()
    {
        if (project.isExecutionRoot())
        {
            return true;
        }
        
        // TODO check for empty file?
        return this.testExclusionsFile(project).exists();
    }

    protected String getOutputDirectory()
    {
        if (outputDirectory.isAbsolute() == false)
        {
            outputDirectory = new File(project.getBasedir(), outputDirectory.getPath());
        }
        return outputDirectory.getAbsolutePath();
    }

    protected MavenProject getProject()
    {
        return project;
    }

    protected SiteRenderer getSiteRenderer()
    {
        return siteRenderer;
    }

    public String getDescription(Locale locale)
    {
        return ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale).getString("description");
    }

    public String getName(Locale locale)
    {
        return ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale).getString("name");
    }

    public String getOutputName()
    {
        return "mule-test-exclusions";
    }
}
