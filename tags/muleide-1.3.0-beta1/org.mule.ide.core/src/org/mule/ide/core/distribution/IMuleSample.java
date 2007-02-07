package org.mule.ide.core.distribution;

import java.io.File;

public interface IMuleSample extends IMuleBundle {
	String getDescription();
	File[] getSourceFolders();
	File[] getConfigFiles();
}
