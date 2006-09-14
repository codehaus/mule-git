
package org.mule.transformers.csv;

/**
 * @author WestelinckK All CSV writers should implement this interface.
 */
public interface CSVWriter
{
    public static char DEFAULT_SEPARATOR = ';';

    /**
     * Write the object to a CSV file. The object can be: - An array of arrays, e.g.
     * String[][] - A List of arrays, e.g. List<String[]>
     * 
     * @param o the object to write to the CSV file
     * @throws Exception
     */
    public void write(Object o) throws Exception;
}
