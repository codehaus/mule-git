/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.flatfile.i18n;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;

import java.io.File;

public class FlatfileMessages extends MessageFactory
{
    private static final String BUNDLE_PATH = getBundlePath("flatfile");

    public static Message outputPathMustBeSet()
    {
        return createMessage(BUNDLE_PATH, 1);
    }

    public static Message cannotWriteToFile(File outputFile)
    {
        return createMessage(BUNDLE_PATH, 2, outputFile.getAbsolutePath());
    }

    public static Message delimiterLengthExceeded()
    {
        return createMessage(BUNDLE_PATH, 3);
    }

    public static Message qualifierLengthExceeded()
    {
        return createMessage(BUNDLE_PATH, 4);
    }

    public static Message missingMappingFile()
    {
        return createMessage(BUNDLE_PATH, 5);
    }
}


