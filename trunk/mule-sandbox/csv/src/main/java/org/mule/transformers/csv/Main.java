
package org.mule.transformers.csv;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.util.ArrayList;

public class Main
{

    /**
     * @param args
     */
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

        /*
         * BufferedReader br = new BufferedReader(fr); CSVReader r = new
         * CSVReader(br, ';'); String[] line = r.readNext(); ClassLoaderReference clr =
         * new ClassLoaderReference(new CompositeClassLoader()); //Mapper mapper =
         * new DefaultMapper(clr); //mapper = new ArrayMapper((ClassMapper)mapper);
         * XStream xs = new XStream(); CsvConverter c = new
         * CsvConverter(xs.getClassMapper(), new String[] {"1", "2", "3", "4", "5",
         * "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
         * "19", "20" }); xs.registerConverter(c); String xml = xs.toXML(line);
         * System.out.println(xml); Object o = xs.fromXML(xml); r.close();
         */
    }

}
