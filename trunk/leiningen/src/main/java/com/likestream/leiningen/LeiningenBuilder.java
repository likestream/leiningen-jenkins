package com.likestream.leiningen;

import hudson.CopyOnWrite;
import hudson.Launcher;
import hudson.Extension;
import hudson.util.FormValidation;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.AbstractProject;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Sample {@link Builder}.
 * 
 * <p>
 * When the user configures the project and enables this builder,
 * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked and a new
 * {@link LeiningenPluginBuilder} is created. The created instance is persisted to the
 * project configuration XML by using XStream, so this allows you to use
 * instance fields (like {@link #name}) to remember the configuration.
 * 
 * <p>
 * When a build is performed, the
 * {@link #perform(AbstractBuild, Launcher, BuildListener)} method will be
 * invoked.
 * 
 * @author Ramakrishna Sharvirala
 */
public class LeiningenBuilder extends Builder {

	
	
	private final String leiningenInstallation;
	//private final String leiningenFile;
	private final String leiningenLibDir;
	private final String leiningenWorkingDir;
    private final String tasks;
    
	// Fields in config.jelly must match the parameter names in the
	// "DataBoundConstructor"
	@DataBoundConstructor
	public LeiningenBuilder(String leiningenInstallation, String tasks, String leiningenLibDir, String leiningenWorkingDir) {
		this.leiningenInstallation = leiningenInstallation;
		this.tasks = tasks;
		this.leiningenLibDir = leiningenLibDir;
		this.leiningenWorkingDir = leiningenWorkingDir;
	}
	
	public String getLeiningenInstallation() {
		return leiningenInstallation;
	}
	public String getLeiningenLibDir() {
		return leiningenLibDir;
	}
	public String getLeiningenWorkingDir() {
		return leiningenWorkingDir;
	}
	public String getTasks() {
		return tasks;
	}
	
	@Override
	public boolean perform(AbstractBuild build, Launcher launcher,
			BuildListener listener) {
		// this is where you 'build' the project
		// since this is a dummy, we just say 'hello world' and call that a
		// build

		// this also shows how you can consult the global configuration of the
		// builder

		return true;
	}

	// overrided for better type safety.
	// if your plugin doesn't really define any property on Descriptor,
	// you don't have to do this.
	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}

	/**
	 * Descriptor for {@link ClojurePluginBuilder}. Used as a singleton. The
	 * class is marked as public so that it can be accessed from views.
	 * 
	 * <p>
	 * See
	 * <tt>views/hudson/plugins/hello_world/ClojurePluginBuilder/*.jelly</tt>
	 * for the actual HTML fragment for the configuration screen.
	 */
	@Extension
	// this marker indicates Hudson that this is an implementation of an
	// extension point.
	public static final class DescriptorImpl extends
			BuildStepDescriptor<Builder> {

		@CopyOnWrite
		private volatile LeiningenInstallation[] installations = new LeiningenInstallation[0];

		/**
		 * Performs on-the-fly validation of the form field 'name'.
		 * 
		 * @param value
		 *            This parameter receives the value that the user has typed.
		 * @return Indicates the outcome of the validation. This is sent to the
		 *         browser.
		 */
		public FormValidation doCheckName(@QueryParameter String value)
				throws IOException, ServletException {
			if (value.length() == 0)
				return FormValidation.error("Please set a name");
			if (value.length() < 4)
				return FormValidation.warning("Isn't the name too short?");
			return FormValidation.ok();
		}

		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			// indicates that this builder can be used with all kinds of project
			// types
			return true;
		}

		/**
		 * This human readable name is used in the configuration screen.
		 */
		@Override
		public String getDisplayName() {
			return "Invoke Leiningen";
		}

		public LeiningenInstallation[] getInstallations() {
			return installations;
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData)
				throws FormException {
			installations = req.bindParametersToList(LeiningenInstallation.class,
					"leiningen.").toArray(new LeiningenInstallation[0]);
			// ^Can also use req.bindJSON(this, formData);
			// (easier when there are many fields; need set* methods for this,
			// like setUseFrench)
			save();
			return super.configure(req, formData);
		}

	}

	
}
