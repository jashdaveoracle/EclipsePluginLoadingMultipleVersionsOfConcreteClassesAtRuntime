package pluginruntimeloadingmultiversionconcretclasses;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {
	public static Activator INSTANCE = new Activator();
	public static String ID = "PluginRuntimeLoadingMultiVersionConcretClasses";
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}


	public static void  log(String msg, Exception e) {
		if(INSTANCE!=null) {
		IStatus status = e != null ? new Status(IStatus.ERROR, ID, msg, e)
				: new Status(IStatus.INFO, ID, msg);
		INSTANCE.getLog().log(status);
		}
	}
}
