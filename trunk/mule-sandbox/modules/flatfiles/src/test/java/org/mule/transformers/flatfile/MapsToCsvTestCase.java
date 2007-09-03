/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.flatfile;

import org.mule.umo.transformer.TransformerException;
import org.mule.util.FileUtils;
import org.mule.util.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import net.sf.flatpack.InitialisationException;

import org.apache.commons.lang.SystemUtils;

public class MapsToCsvTestCase extends TestCase
{
    public void testInvalidDelimiter()
    {
        MapsToCsv transformer = new MapsToCsv();
        transformer.setOutputPath("/tmp");
        transformer.setDelimiter("too long");
        try
        {
            transformer.transform(Collections.EMPTY_LIST);
            Assert.fail("delimiter may only be one char long");
        }
        catch (TransformerException te)
        {
            Assert.assertEquals(3, te.getMessageCode());
        }
    }

    public void testInvalidQualifier()
    {
        MapsToCsv transformer = new MapsToCsv();
        transformer.setOutputPath("/tmp");
        transformer.setDelimiter(",");
        transformer.setQualifier("too long");
        try
        {
            transformer.transform(Collections.EMPTY_LIST);
            Assert.fail("qualifier may only be one char long");
        }
        catch (TransformerException te)
        {
            Assert.assertEquals(4, te.getMessageCode());
        }
    }
    
    public void testInvalidMappingFile() throws Exception
    {
        File tempFile = File.createTempFile("out", "csv");
        tempFile.deleteOnExit();
                
        MapsToCsv transformer = new MapsToCsv();
        transformer.setMapping(this.getTestResource("BrokenMapping.pzmap.xml").getAbsolutePath());
        transformer.setOutputPath(tempFile.getAbsolutePath());
        try
        {
            transformer.transform(Collections.EMPTY_LIST);
            Assert.fail("transfomer must fail when invalid mapping file is passed in");
        }
        catch (InitialisationException ie)
        {
            // expected exception
        }
    }
    
    private File getTestResource(String filename)
    {
        return FilenameUtils.fileWithPathComponents(new String[] 
            {System.getProperty("user.dir"), "src", "test", "resources", filename});        
    }
    
    public void testMapsToCsv() throws Exception
    {
        Map row = new HashMap();
        row.put("FIRSTNAME", "John");
        row.put("LASTNAME", "Doe");
        row.put("ADDRESS", "123 Street");
        row.put("CITY", "Nocity");
        row.put("STATE", "CA");
        row.put("ZIP", "12345");

        List input = new ArrayList();
        input.add(row);
        
        File tempFile = File.createTempFile("flatfile", "csv");
        
        MapsToCsv transformer = new MapsToCsv();
        transformer.setOutputPath(tempFile.getAbsolutePath());
        transformer.setMapping(this.getTestResource("DelimitedWithHeader.pzmap.xml").getAbsolutePath());
        File result = (File)transformer.transform(input);
        
        String csvString = FileUtils.readFileToString(result);
        
        String expected = "FIRSTNAME;LASTNAME;ADDRESS;CITY;STATE;ZIP"
            + SystemUtils.LINE_SEPARATOR
            + "John;Doe;123 Street;Nocity;CA;12345"
            + SystemUtils.LINE_SEPARATOR;
        
        Assert.assertEquals(expected, csvString);
    }
}
