package org.mule.tools.config.graph.postgraphers;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeServices;
import org.mule.tools.config.graph.components.PostGrapher;
import org.mule.tools.config.graph.config.GraphConfig;
import org.mule.tools.config.graph.config.GraphEnvironment;
import org.mule.tools.config.graph.config.VelocitySupport;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.Arrays;

public abstract class AbstractIndexer extends VelocitySupport  implements PostGrapher {

    protected AbstractIndexer(GraphEnvironment env) throws Exception {
        super(env);
    }

	protected void doRendering(GraphEnvironment env, File[] htmlFiles, String template, String targetFile) {
		try {
        this.env = env;
			VelocityContext velocityContext = new VelocityContext();

			velocityContext.put("fileList", Arrays.asList(htmlFiles));
			// TODO how to retrieve template using classpath ?
			Template t = ve.getTemplate(template);
			File file = new File(targetFile);
			FileWriter writer = new FileWriter(file);

			env.log("generating " + file);

			t.merge(velocityContext, writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected File[] getFiles(final GraphConfig config, final String extension) {
		File[] htmlFiles = config.getOutputDirectory().listFiles(
				new FilenameFilter() {
					public boolean accept(File dir, String name) {
						if (name.toLowerCase().equals("index.html"))
							return false;
						else if (name.toLowerCase().equals("gallery.html"))
							return false;
						else
							return name.endsWith(extension);
					}
				});
		return htmlFiles;
	}
	
	public void init(RuntimeServices arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}


public void logVelocityMessage(int arg0, String arg1) {
    if(env !=null) env.log(arg1);

}
}
