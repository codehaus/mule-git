/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.core.builder;

import java.io.File;
import java.net.URL;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.mule.ide.core.MuleCorePlugin;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MuleConfigBuilder extends IncrementalProjectBuilder {

    public static final String BUILDER_ID = "org.mule.ide.core.muleConfig";
    public static final String PLUGIN_ID = "org.mule.ide.core";

    static final String SYMPHONY_SOFT_DTD_MULE_CONFIGURATION_XML_V1_0_EN = "-//SymphonySoft //DTD mule-configuration XML V1.0//EN";
    static final String HTTP_WWW_SYMPHONYSOFT_COM_DTDS_MULE = "http://www.symphonysoft.com/dtds/mule/";

    static final String MULESOURCE_DTD_MULE_CONFIGURATION_XML_V1_0_EN = "-//MuleSource //DTD mule-configuration XML V1.0//EN";
    static final String HTTP_MULESOURCE_COM_DTDS_MULE = "http://mule.mulesource.org/dtds/";

    class SampleDeltaVisitor implements IResourceDeltaVisitor {
        /*
         * (non-Javadoc)
         *
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
         */
        public boolean visit(IResourceDelta delta) throws CoreException {
            IResource resource = delta.getResource();
            switch (delta.getKind()) {
            case IResourceDelta.ADDED:
                // handle added resource
                checkXML(resource);
                break;
            case IResourceDelta.REMOVED:
                // handle removed resource
                break;
            case IResourceDelta.CHANGED:
                // handle changed resource
                checkXML(resource);
                break;
            }
            //return true to continue visiting children.
            return true;
        }
    }

    class BuilderResourceVisitor implements IResourceVisitor {
        public boolean visit(IResource resource) {
            checkXML(resource);
            //return true to continue visiting children.
            return true;
        }
    }

    class XMLErrorHandler extends MuleDTDResolverHandler {

        private IJavaModel getJavaModel() {
            return JavaCore.create(ResourcesPlugin.getWorkspace().getRoot());
        }
        private IJavaProject getJavaProject() {
            return getJavaModel().getJavaProject(getProject().getName());
        }
        private IJavaProject javaProject = getJavaProject();
        private Map className2Exists = new java.util.HashMap(); // cache

        private boolean hasClass(String className) {
            Boolean hasClassName = (Boolean)className2Exists.get(className);
            if (hasClassName != null) return hasClassName.booleanValue();

            boolean canInstantiate = false;
            if ((javaProject != null) && javaProject.exists()) {
                try {
                    IType classType = javaProject.findType(className);
                    canInstantiate = classType != null &&
                        ! classType.isInterface() &&
                        ! Flags.isAbstract(classType.getFlags());
                } catch (JavaModelException e) {
                }
            }
            className2Exists.put(className, canInstantiate ? Boolean.TRUE : Boolean.FALSE);
            return canInstantiate;
        }

        private IFile file;

        public XMLErrorHandler(IFile file) {
            this.file = file;
        }

        private void addMarker(SAXParseException e, int severity) {
            MuleConfigBuilder.this.addMarker(file, e.getMessage(), e
                    .getLineNumber(), severity);
        }

        public void error(SAXParseException exception) throws SAXException {
            addMarker(exception, IMarker.SEVERITY_ERROR);
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            addMarker(exception, IMarker.SEVERITY_ERROR);
        }

        public void warning(SAXParseException exception) throws SAXException {
            addMarker(exception, IMarker.SEVERITY_WARNING);
        }

        Locator myLocator = null;

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            String className = (attributes != null) ? attributes.getValue("", "className") : null;
            if (className != null) {
                if (! hasClass(className)) {
                    int line = (myLocator != null) ? myLocator.getLineNumber() : -1;
                    MuleConfigBuilder.this.addMarker(file, "No class '" + className + "' is present in the project '" + file.getProject().getName() + "'", line, IMarker.SEVERITY_ERROR);
                }
            }
        }

        public void setDocumentLocator (Locator locator)
        {
            myLocator = locator;
        }
    }

    class CheckValidHandler extends MuleDTDResolverHandler {

        public CheckValidHandler() {
        }

        public void error(SAXParseException exception) throws SAXException {
            throw new SAXException(exception);
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            throw new SAXException(exception);
        }

        public void warning(SAXParseException exception) throws SAXException {
            throw new SAXException(exception);
        }
    }

    public static URL findResourceURL(String bundleId, String path) {
        Bundle bundle = Platform.getBundle(bundleId);
        if (bundle == null) return null;
        
        // This is required for Eclipse 3.1 compatibility
        return Platform.find(bundle, new Path(path));
    }

    public static URL findResourceURL(String path) {
        return findResourceURL(PLUGIN_ID, path);
    }

    private SAXParserFactory parserFactory;

    private void addMarker(IFile file, String message, int lineNumber,
            int severity) {
        try {
            IMarker marker = file.createMarker(MuleCorePlugin.MARKER_TYPE);
            marker.setAttribute(IMarker.MESSAGE, message);
            marker.setAttribute(IMarker.SEVERITY, severity);
            if (lineNumber == -1) {
                lineNumber = 1;
            }
            marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
        } catch (CoreException e) {
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
     *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
     */
    protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
            throws CoreException {
        if (kind == FULL_BUILD) {
            fullBuild(monitor);
        } else {
            IResourceDelta delta = getDelta(getProject());
            if (delta == null) {
                fullBuild(monitor);
            } else {
                incrementalBuild(delta, monitor);
            }
        }
        return null;
    }

    void checkXML(IResource resource) {
        if (resource instanceof IFile && resource.getName().endsWith(".xml")) {
            IFile file = (IFile) resource;
            deleteMarkers(file);
            XMLErrorHandler reporter = new XMLErrorHandler(file);
            try {
                getParser().parse(file.getContents(), reporter);
            } catch (WrongRootException wre) {
                deleteMarkers(file);
            } catch (Exception e1) {
            }
        }
    }

    public boolean isCorrectXML(IFile resource) {
        if (! resource.getName().endsWith(".xml")) return false;
        try {
            getParser().parse(resource.getContents(), new CheckValidHandler());
        } catch (Exception e1) {
            return false;
        }
        return true;
    }

    private void deleteMarkers(IFile file) {
        try {
            file.deleteMarkers(MuleCorePlugin.MARKER_TYPE, false, IResource.DEPTH_ZERO);
        } catch (CoreException ce) {
        }
    }

    protected void fullBuild(final IProgressMonitor monitor)
            throws CoreException {
        try {
            getProject().accept(new BuilderResourceVisitor());
        } catch (CoreException e) {
        }
    }

    private SAXParser getParser() throws ParserConfigurationException,
            SAXException {
        if (parserFactory == null) {
            parserFactory = SAXParserFactory.newInstance();
        }
        parserFactory.setValidating(true);
        return parserFactory.newSAXParser();
    }

    protected void incrementalBuild(IResourceDelta delta,
            IProgressMonitor monitor) throws CoreException {
        // the visitor does the work.
        delta.accept(new SampleDeltaVisitor());
    }
    

	static public boolean smellsLikeMuleConfigFile(File configFile) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			db.setEntityResolver(new MuleDTDResolverHandler());
			Document doc = db.parse(configFile);
			return "mule-configuration".equals(doc.getDocumentElement().getLocalName());
		} catch (Throwable t) {
			// It's OK to ignore any old exception here
		}
		return false; // It's not a Mule Config file, then
	}

}
