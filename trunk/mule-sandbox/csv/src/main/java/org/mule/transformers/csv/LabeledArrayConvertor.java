/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.csv;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.collections.ArrayConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

import java.lang.reflect.Array;
import java.util.List;

/**
 * This will serialize a String[] to its XML representation. Each string element will
 * have an attribute containing the label of the field. If no labels are defined, the
 * behaviour of the super class is used.
 */
public class LabeledArrayConvertor extends ArrayConverter
{
    private String[] labels = null;

    public LabeledArrayConvertor(Mapper mapper, String[] labels)
    {
        super(mapper);
        this.labels = labels;
    }

    public LabeledArrayConvertor(Mapper mapper, List labels)
    {
        super(mapper);
        if (labels != null)
        {
            this.labels = (String[])labels.toArray(new String[labels.size()]);
        }
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
    {
        int length = Array.getLength(source);
        for (int i = 0; i < length; i++)
        {
            Object item = Array.get(source, i);
            if (labels != null && i < labels.length)
            {
                writeItem(item, labels[i], context, writer);
            }
            else
            {
                super.writeItem(item, context, writer);
            }
        }
    }

    protected void writeItem(Object item,
                             String label,
                             MarshallingContext context,
                             HierarchicalStreamWriter writer)
    {
        if (item == null)
        {
            writer.startNode(mapper().serializedClass(null));
            writer.addAttribute("label", label);
        }
        else
        {
            writer.startNode(mapper().serializedClass(item.getClass()));
            writer.addAttribute("label", label);
            context.convertAnother(item);
            writer.endNode();
        }
    }
}
