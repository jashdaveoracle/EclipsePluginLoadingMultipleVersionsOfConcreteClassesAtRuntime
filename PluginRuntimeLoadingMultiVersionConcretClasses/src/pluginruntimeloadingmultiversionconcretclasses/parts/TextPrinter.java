package pluginruntimeloadingmultiversionconcretclasses.parts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.core.runtime.FileLocator;

import iface.DataAccessIFace;
import pluginruntimeloadingmultiversionconcretclasses.Activator;

public class TextPrinter {
	//Unzip DataAccessLayer.jar into local dir of user.
	static URL dataAccessJarURL = null;
	static {
		try {
			String dataAccessJarPath = "/libs/DataAccessLayer.jar";
			dataAccessJarURL = FileLocator.resolve(getURL(dataAccessJarPath));
			InputStream initialStream = dataAccessJarURL.openConnection().getInputStream();
			byte[] buffer = new byte[initialStream.available()];
			initialStream.read(buffer);
			
			String parentDir = new File(FileLocator.resolve(getURL("/")).getPath()).getParent();
//			URL parentDirURL = new URL(parentDir);
//			Activator.log("Parent::" + parentDir, null);
//			Activator.log("Path::" + parentDirURL.getPath() + " File::" + parentDirURL.getFile(), null);
			URL unzipedDataAccessLayerJarURL = new URL(parentDir + "\\DataAccessLayer.jar");
			File targetFile = new File(unzipedDataAccessLayerJarURL.toURI());
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);
			outStream.flush();
			outStream.close();
			dataAccessJarURL = unzipedDataAccessLayerJarURL;
			Activator.log("DA_URL::" + dataAccessJarURL, null);
		} catch (Exception e) {
			Activator.log("Exception unziping DAL.jar::" + dataAccessJarURL, e);
		}
	}

	public String getString() throws Exception {
		String text = "Some text \n";

		// FileLocator.resolve(fileURL);
		String concreteClassJarV1Path = "file:///C:/Users/jadave.ORADEV/eclipse-workspace_oxygen/PluginRuntimeLoadingWithMultipleVersionsOfConcreteClasses/ConcreteImpl/jars/ConcreteClassV1.jar";
		String concreteClassJarV2Path = "file:///C:/Users/jadave.ORADEV/eclipse-workspace_oxygen/PluginRuntimeLoadingWithMultipleVersionsOfConcreteClasses/ConcreteImpl/jars/ConcreteClassV2.jar";

		URL concreteClassJarV1URL = new URL(concreteClassJarV1Path);
		URL concreteClassJarV2URL = new URL(concreteClassJarV2Path);

		URL[] urlsV1 = new URL[] { dataAccessJarURL, concreteClassJarV1URL };
		URL[] urlsV2 = new URL[] { dataAccessJarURL, concreteClassJarV2URL };

		URLClassLoader uclV1 = new URLClassLoader(urlsV1, getClass().getClassLoader());
		URLClassLoader uclV2 = new URLClassLoader(urlsV2, getClass().getClassLoader());

		DataAccessIFace ifaceV1 = (DataAccessIFace) uclV1.loadClass("dataaccesslayer.DataAccessLayer").newInstance();
		DataAccessIFace ifaceV2 = (DataAccessIFace) uclV2.loadClass("dataaccesslayer.DataAccessLayer").newInstance();

		ifaceV1.printImpl();
		text += "Version " + ifaceV1.getData() + "\n";

		ifaceV2.printImpl();
		text += "Version " + ifaceV2.getData() + "\n";

		uclV1.close();
		uclV2.close();
		return text;
	}

	public static URL getURL(String path) throws MalformedURLException {
		URL url = new URL("platform:/plugin/" + Activator.ID + path); //$NON-NLS-1$
		return url;
	}
}
