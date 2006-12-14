/*
 * $Id $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.boot;

import java.io.File;

public class GuiInstallerLicenseHandler
{
    /**
     * The main method which is called by the GUI. It creates the LicenseHandler
     * object and then seeks to save the license by calling the method
     * saveLicenseAck.
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception
    {
        File muleHome = new File(args[0].toString());
        LicenseHandler handler = new LicenseHandler(muleHome);
        try
        {
            handler.saveLicenseAck("MuleSource Public License", "1.1.3");
        }
        // the "Unable to rename temporary jar" exception is always thrown when the jar file
        // containing the license, for some reason is already present.
        catch (Exception e)
        {
            if (!e.getMessage().startsWith("Unable to rename temporary jar"))
            {
                throw new Exception(e.getMessage());
            }
        }
    }
}