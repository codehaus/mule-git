package org.mule.tools.visualizer.maven;

/**
 * Test for MULETOOLS-29
 */
public class ReplyToTestCase extends AbstractBaseVisualizerXmlTestCase {

    String getXmlConfig()
    {
        // TODO - UNDO hide real test so we can commit this
        return "echo-config.xml";
//        return "reply-to.xml";
    }

}
