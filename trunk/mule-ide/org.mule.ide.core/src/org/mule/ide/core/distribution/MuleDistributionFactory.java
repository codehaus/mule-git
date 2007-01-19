/**
 * 
 */
package org.mule.ide.core.distribution;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author jsm
 *
 */
public class MuleDistributionFactory {

	protected static MuleDistributionFactory INSTANCE;
	
	public static MuleDistributionFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MuleDistributionFactory();
		}
		return INSTANCE;
	}
	
	public IMuleDistribution createMuleDistribution(File location) {
		if (location != null && location.isDirectory()) {
			try {
				MuleFullDistribution muleFullDistribution = new MuleFullDistribution(location);
				muleFullDistribution.initialize();
				return muleFullDistribution;
			} catch (IOException e) {
				// Drop through and get the dummy
			}
		}
		return new DummyMuleDistribution();		
	}
	
	class DummyMuleDistribution implements IMuleDistribution {

		public void close() {
		}

		public InputStream getDTDContents(String dtdName) throws IOException {
			throw new IllegalStateException("Forgot to call isValid!");
		}

		public String[] getDependencies(String[] moduleAndTransportNames) {
			throw new IllegalStateException("Forgot to call isValid!");
		}

		public URL[] getFullClasspath() {
			throw new IllegalStateException("Forgot to call isValid!");
		}

		public File getLocation() {
			throw new IllegalStateException("Forgot to call isValid!");
		}

		public String[] getSuppliedModules() {
			throw new IllegalStateException("Forgot to call isValid!");
		}

		public String[] getSuppliedTransports() {
			throw new IllegalStateException("Forgot to call isValid!");
		}

		public String getVersion() {
			return "";
		}

		public boolean isValid() {
			return false;
		}}
}
