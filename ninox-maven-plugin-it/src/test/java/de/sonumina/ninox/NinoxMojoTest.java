package de.sonumina.ninox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.junit.Test;

public class NinoxMojoTest//AbstractMojoTestCase
{
	private String LOCAL_REPO_PATH = "/tmp/ninox-maven-plugin-repo";
	
	@Test
	public void testCompile() throws VerificationException, IOException
	{
		File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/compile" );
		System.out.println(testDir.getAbsolutePath());
	
		/* Install the ninox plugin into the local repo */
		File mainDir = new File(testDir,"../../../../ninox-maven-plugin");
		Verifier mainVerifier = new Verifier(mainDir.getAbsolutePath());
		mainVerifier.setLocalRepo(LOCAL_REPO_PATH);
		mainVerifier.executeGoal("install");
		mainVerifier.resetStreams();

		/* Install the ninox parent plugin into the local repo */
		File pomDir = new File(testDir,"../../../..");
		Verifier pomVerifier = new Verifier(pomDir.getAbsolutePath());
		pomVerifier.setLocalRepo(LOCAL_REPO_PATH);
		pomVerifier.setCliOptions(Arrays.asList("-N"));
		pomVerifier.executeGoal("install");
		pomVerifier.resetStreams();

		/* Finally, invoke the goal */
		Verifier verifier = new Verifier(testDir.getAbsolutePath());
//		verifier.setCliOptions(Arrays.asList("-o"));
		verifier.setLocalRepo(LOCAL_REPO_PATH);
		verifier.executeGoal("compile");
		verifier.resetStreams();
	
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(testDir.getAbsolutePath(),verifier.getLogFileName()))));
		String l;
		while ((l = br.readLine())!= null)
			System.out.println("NINOXOUT:" + l);
		br.close();
	}
}
