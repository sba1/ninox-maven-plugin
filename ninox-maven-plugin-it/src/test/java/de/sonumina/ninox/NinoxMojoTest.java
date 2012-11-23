package de.sonumina.ninox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
		verifier.executeGoal("compile");
		verifier.resetStreams();
	
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(testDir.getAbsolutePath(),verifier.getLogFileName()))));
		String l;
		while ((l = br.readLine())!= null)
			System.out.println("NINOXOUT:" + l);
		br.close();
	}
}
