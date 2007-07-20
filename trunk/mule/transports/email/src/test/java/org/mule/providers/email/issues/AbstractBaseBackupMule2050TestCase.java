/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.email.issues;

import org.mule.MuleManager;
import org.mule.providers.email.AbstractRetrieveMailConnector;
import org.mule.providers.email.ImapConnectorTestCase;
import org.mule.umo.provider.UMOConnector;

import java.io.File;

public abstract class AbstractBaseBackupMule2050TestCase extends ImapConnectorTestCase
{

    private boolean backupEnabled;

    public AbstractBaseBackupMule2050TestCase(boolean backupEnabled)
    {
        this.backupEnabled = backupEnabled;
    }

    public UMOConnector getConnector(boolean init) throws Exception
    {
        UMOConnector connector = super.getConnector(init);
        ((AbstractRetrieveMailConnector) connector).setBackupEnabled(backupEnabled);
        return connector;
    }

    public void doTestReceiver() throws Exception
    {
        File dir = new File(MuleManager.getConfiguration().getWorkingDirectory() + "/mail/INBOX");
        assertFalse("Mail backup file already exists: " + dir.getAbsolutePath(), dir.exists());
        debug(dir);
        super.doTestReceiver();
        debug(dir);
        assertTrue(dir.getAbsolutePath(), dir.exists() == backupEnabled);
    }

    protected void debug(File dir)
    {
        logger.debug(dir.getAbsolutePath() + " exists? " + dir.exists());
    }

}