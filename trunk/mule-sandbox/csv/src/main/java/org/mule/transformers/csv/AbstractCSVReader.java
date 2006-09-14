
package org.mule.transformers.csv;

import java.io.Reader;

/**
 * @author WestelinckK All CSV readers should extend this class.
 */
public abstract class AbstractCSVReader implements CSVReader
{
    private Reader in = null;
    private char separator;

    /**
     * Create a new reader that will read our CSV file.
     * 
     * @param in the implementation that will read the character stream
     * @param separator the seperator (e.g. ';', ',' or whatever character)
     */
    public AbstractCSVReader(Reader in, char separator)
    {
        this.in = in;
        this.separator = separator;
    }
}
