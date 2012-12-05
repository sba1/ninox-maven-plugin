package de.sonumina.ninox;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Packages the build.
 * 
 * @author Sebastian Bauer
 */
@Mojo(name = "package")
public class NinoxPackageMojo  extends AbstractMojo
{
	/** The directory, in which the compile phase stored all generated files */
	@Parameter(defaultValue="${project.build.directory}",required=true)
	private File outputDirectory;

	/** The maven project instance */
	@Parameter(defaultValue="${project}",required=true,readonly=true)
	private MavenProject project;
	
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		File [] files = outputDirectory.listFiles();
		boolean hasProperArtifact = false;

		/* Here we simply use the first owl file */
		for (File f : files)
		{
			if (f.getName().endsWith(".owl"))
			{
				getLog().info("Setting artifact to \"" + f.getAbsolutePath() +"\"");
				project.getArtifact().setFile(f);
				hasProperArtifact = true;
				break;
			}
		}
		
		if (!hasProperArtifact)
			getLog().warn("No generated artifact was found!");
	}
}
