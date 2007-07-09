/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.jboss.transactions;

import org.mule.umo.manager.UMOTransactionManagerFactory;
import org.mule.tck.AbstractTxThreadAssociationTestCase;

public class JBossArjunaTxThreadAssociationTestCase extends AbstractTxThreadAssociationTestCase
{

    protected UMOTransactionManagerFactory getTransactionManagerFactory()
    {
        return new JBossArjunaTransactionManagerFactory();
    }
}
