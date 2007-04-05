/* -------------------------------------------------------------------
 * Copyright (c) 2006 Wael Chatila / Icegreen Technologies. All Rights Reserved.
 * This software is released under the LGPL which is available at http://www.gnu.org/copyleft/lesser.html
 * This file has been modified by the copyright holder. Original file can be found at http://james.apache.org
 * -------------------------------------------------------------------
 */
package com.icegreen.greenmail.imap;

/**
 * @author Darrell DeBoer <darrell@apache.org>
 * @version $Revision: 2872 $
 */
public class ProtocolException extends Exception {

    private static final long serialVersionUID = 5500496997400033204L;

    public ProtocolException(String s) {
        super(s);
    }

}
