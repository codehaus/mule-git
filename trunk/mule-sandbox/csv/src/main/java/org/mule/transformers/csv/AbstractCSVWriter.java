
package org.mule.transformers.csv;

import java.io.Writer;

/**
 * @author WestelinckK All CSV writers should extend this class.
 */
public abstract class AbstractCSVWriter implements CSVWriter
{
    private Writer out = null;
    private char separator;

    /**
     * Create a new writer that will write our CSV file.
     * 
     * @param out the implementation that will write the character stream
     * @param separator the seperator (e.g. ';', ',' or whatever character)
     */
    public AbstractCSVWriter(Writer out, char separator)
    {
        this.out = out;
        this.separator = separator;
    }
}
