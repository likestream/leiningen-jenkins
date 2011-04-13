/**
 * 
 */
package com.likestream.clojure.plugin;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author c_rsharv
 *
 */
public final class ClojureInstallation implements Serializable {
	private String name;
    private String clojureHome;
    
    @DataBoundConstructor
    public ClojureInstallation(String name, String home) {
        this.name = name;
        this.clojureHome = home;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClojureHome() {
		return clojureHome;
	}

	public void setClojureHome(String clojureHome) {
		this.clojureHome = clojureHome;
	}
    
	
	/**
     * Gets the executable path of this groovy installation on the given target system.
     */
    public String getExecutable() {
    	/*
    	VirtualChannel channel  throws IOException, InterruptedException 
        return channel.call(new Callable<String, IOException>() {

            public String call() throws IOException {
                File exe = getExeFile("groovy");
                if (exe.exists()) {
                    return exe.getPath();
                } else {
                    throw new FileNotFoundException(exe.getPath() + " doesn't exist, please check your Groovy installation");
                }
            }
        });
        */
    	return null;
    }

    private File getExeFile(String execName) {
    	/*
        String groovyHome = Util.replaceMacro(getHome(),EnvVars.masterEnvVars);
        File binDir = new File(groovyHome, "bin/");
        if (File.separatorChar == '\\') {                
            if(new File(binDir, execName + ".exe").exists()) {
                execName += ".exe";
            } else {
                execName += ".bat";
            }
        }
        return new File(binDir, execName);
        */
    	return null;
    }

    /**
     * Returns true if the executable exists.
     */
    public boolean exists() {
    	/*
        try {
            return getExecutable(new LocalLauncher(new StreamTaskListener(new NullStream())).getChannel()) != null;
        } catch (IOException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
        */
    	return true;
    }

    private static final long serialVersionUID = 1L;
}
