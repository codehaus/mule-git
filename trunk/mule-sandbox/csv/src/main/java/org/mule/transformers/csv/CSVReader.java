
package org.mule.transformers.csv;

/**
 * @author WestelinckK All CSV readers should implement this interface.
 */
public interface CSVReader
{
    public static char DEFAULT_SEPARATOR = ';';

    /**
     * Parse the content of the CSV file and return an object containing: - An array
     * of arrays, e.g. String[][] - A List of arrays, e.g. List<String[]>
     * 
     * @return
     * @throws Exception
     */
    public Object parse() throws Exception;
}
