package de.sonumina.ninox;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import owltools.ontologyrelease.OortConfiguration;

/**
 * Packages the ontologies.
 * 
 * @goal compile
 */
@Mojo(name = "compile")
public class NinoxMojo extends AbstractMojo
{
    public void execute() throws MojoExecutionException
    {
    	/** Instanciate a new oort config */
		OortConfiguration oortConfig = new OortConfiguration();

        getLog().info( "Hello, world." );
    }
}
