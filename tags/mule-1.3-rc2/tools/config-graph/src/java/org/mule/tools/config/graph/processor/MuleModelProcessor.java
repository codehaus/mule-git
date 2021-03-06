package org.mule.tools.config.graph.processor;

import com.oy.shared.lm.graph.Graph;
import com.oy.shared.lm.graph.GraphNode;
import org.jdom.Element;
import org.mule.tools.config.graph.config.ColorRegistry;
import org.mule.tools.config.graph.config.GraphEnvironment;
import org.mule.tools.config.graph.util.MuleTag;

import java.util.Iterator;
import java.util.List;

public class MuleModelProcessor extends TagProcessor {


    private DescriptorProcessor descriptorProcessor;


    public MuleModelProcessor( GraphEnvironment environment ) {
        super(environment);
        descriptorProcessor = new DescriptorProcessor(environment);

    }

    public void process(Graph graph, Element currentElement, GraphNode parent) {

        List models = currentElement.getChildren(MuleTag.ELEMENT_MODEL);
        for (Iterator iter = models.iterator(); iter.hasNext();) {
            Element modelElement = (Element) iter.next();
            if (environment.getConfig().isShowModels()) {
                String name = modelElement.getAttributeValue(MuleTag.ATTRIBUTE_NAME);
                String type = modelElement.getAttributeValue(MuleTag.ATTRIBUTE_TYPE);
                String className = modelElement.getAttributeValue(MuleTag.ATTRIBUTE_CLASS_NAME);
                if (type != null) {
                    if (type.equals("custom")) {
                        name += " (custom: " + className + ")";
                    } else {
                        name += " (" + type + ")";
                    }
                }
                GraphNode node = graph.addNode();
                node.getInfo().setHeader(name);
                node.getInfo().setFillColor(ColorRegistry.COLOR_MODEL);

                StringBuffer caption = new StringBuffer();
                appendProperties(modelElement, caption);
                appendDescription(modelElement, caption);
                node.getInfo().setCaption(caption.toString());

                ExceptionStrategyProcessor processor = new ExceptionStrategyProcessor(environment);
                processor.process(graph, modelElement, node);

            }
            descriptorProcessor.process(graph, modelElement, parent);
        }
        if (models.size() == 0) {
            descriptorProcessor.process(graph, currentElement, parent);
        }
    }
}
