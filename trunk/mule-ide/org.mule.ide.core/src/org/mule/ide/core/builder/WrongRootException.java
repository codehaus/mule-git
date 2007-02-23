/**
 * 
 */
package org.mule.ide.core.builder;

import org.xml.sax.SAXException;

class WrongRootException extends SAXException {

    private static final long serialVersionUID = 3L;

    WrongRootException(String messageText) {
        super(messageText);
    }

}