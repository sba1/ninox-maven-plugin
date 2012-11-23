package de.sonumina.ninox;

import java.io.File;
import java.io.IOException;

import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.junit.Test;

public class NinoxMojoTest//AbstractMojoTestCase
{
	@Test
	public void testCompile() throws VerificationException, IOException
	{
		File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/compile" );
		Verifier verifier = new Verifier(testDir.getAbsolutePath());
	}
}
