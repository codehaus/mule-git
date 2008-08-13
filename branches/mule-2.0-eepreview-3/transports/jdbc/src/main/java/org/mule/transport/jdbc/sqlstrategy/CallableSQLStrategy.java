/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package org.mule.transport.jdbc.sqlstrategy;

/**
 * Implements strategy for handling simple stored procedures (no output parameter support).
 * 
 */

public  class CallableSQLStrategy 
    extends SimpleUpdateSQLStrategy
{
	
    protected static final String STORED_PROCEDURE_PREFIX = "{ ";
    protected static final String STORED_PROCEDURE_SUFFIX = " }";
    
    protected String escapeStatement(String statement)
    {
        return STORED_PROCEDURE_PREFIX  + statement + STORED_PROCEDURE_SUFFIX;	
    }
}
