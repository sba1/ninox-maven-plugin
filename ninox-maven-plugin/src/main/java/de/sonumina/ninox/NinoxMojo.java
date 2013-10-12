package de.sonumina.ninox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.obolibrary.obo2owl.OboInOwlCardinalityTools.AnnotationCardinalityException;
import org.obolibrary.oboformat.parser.OBOFormatParserException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import owltools.ontologyrelease.OboOntologyReleaseRunner;
import owltools.ontologyrelease.OboOntologyReleaseRunnerCheckException;
import owltools.ontologyrelease.OortConfiguration;
import owltools.ontologyrelease.logging.Log4jHandler;
import owltools.ontologyrelease.logging.LogHandler;

/**
 * Runs oort on the ontologies.
 */
@Mojo(name = "compile")
public class NinoxMojo extends AbstractMojo
{
	@Parameter( defaultValue = "${project.compileSourceRoots}", readonly = true, required = true )
	private List<String> compileSourceRoots;

	@Parameter(defaultValue="${project.build.directory}",required=true)
	private File outputDirectory;

	@Parameter( defaultValue = "true", readonly = true, required = false)
	private boolean checkConsistency;
	
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

		/* Scan dir in obo directory and add every to  */
		for (String file : oboDir.list())
		{
			getLog().info("Adding file " + file);
			oortConfig.addPath(new File(oboDir,file).getAbsolutePath());
			oortConfig.setCheckConsistency(checkConsistency);
			oortConfig.setAllowFileOverWrite(true);
			oortConfig.setBase(outputDirectory);
			
			getLog().info(oortConfig.toString());
		}
		
		try {
			/* Default log handler */
			Log4jHandler log4jHandler = new Log4jHandler(Logger.getLogger(OboOntologyReleaseRunner.class), true);
			List<LogHandler> handlers = new ArrayList<LogHandler>();
			handlers.add(log4jHandler);

			OboOntologyReleaseRunner oorr = new OboOntologyReleaseRunner(oortConfig, oortConfig.getBase(), handlers);
			oorr.createRelease();
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
		} catch (OBOFormatParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
