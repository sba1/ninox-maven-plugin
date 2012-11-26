package de.sonumina.ninox;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.obolibrary.obo2owl.OboInOwlCardinalityTools.AnnotationCardinalityException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import owltools.ontologyrelease.OboOntologyReleaseRunner;
import owltools.ontologyrelease.OboOntologyReleaseRunnerCheckException;
import owltools.ontologyrelease.OortConfiguration;

/**
 * Runs oort on the ontologies.
 */
@Mojo(name = "compile")
public class NinoxMojo extends AbstractMojo
{
	@Parameter( defaultValue = "${project.compileSourceRoots}", readonly = true, required = true )
	private List<String> compileSourceRoots;

    public void execute() throws MojoExecutionException
    {
    	File oboDir = new File(new File(compileSourceRoots.get(0)).getParentFile(),"obo");
    	String oboSourcePath = oboDir.getAbsolutePath();

    	/** Instanciate a new oort config */
		OortConfiguration oortConfig = new OortConfiguration();

		getLog().info("Source roots");
		for (String sourceRoot : compileSourceRoots)
			getLog().info(sourceRoot.toString());

		getLog().info("Using " + oboSourcePath);

		
		for (String file : oboDir.list())
		{
			getLog().info("Adding file " + file);
			oortConfig.addPath(new File(oboDir,file).getAbsolutePath());
			oortConfig.setAllowFileOverWrite(true);
		}
		
		try {
			OboOntologyReleaseRunner oorr = new OboOntologyReleaseRunner(oortConfig, oortConfig.getBase());
			oorr.createRelease(oortConfig.getPaths());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OboOntologyReleaseRunnerCheckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AnnotationCardinalityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
