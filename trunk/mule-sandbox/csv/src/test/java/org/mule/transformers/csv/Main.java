/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.csv;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.util.ArrayList;

public class Main
{

    public static void main(String[] args) throws Exception
    {
        File f = new File("H:/workspace/csv-example.csv");
        ArrayList l = new ArrayList();
        l.add("1");
        l.add("2");
        CSVToXML t = new CSVToXML();
        t.setFieldNames(l);
        String xml = (String)t.transform(f, null, null);

        XStream xs = new XStream();
        Object o = xs.fromXML(xml);

        XMLToCSV t2 = new XMLToCSV();
        t2.setSeparator(';');
        String csv = (String)t2.transform(xml, null, null);
        System.out.println(csv);
    }

}
