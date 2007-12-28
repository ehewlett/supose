package com.soebes.supose.parse.java;

import java.util.HashMap;
import java.util.Iterator;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.soebes.supose.TestBase;

@Test
public class JavaParserTest extends TestBase {

	public void  testFirstJavaFile() throws Exception {
		//We won't name the Java test files ".java", cause the compiler would
		//compile them, so we have no access to the real source file.
		ANTLRFileStream input = new ANTLRFileStream(getFileResource("Test1.java.test"));
		JavaLexer lexer = new JavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaParser parser = new JavaParser(tokens);
		parser.compilationUnit();

		HashMap methods = parser.getMethods();
		assertTrue(methods.size() == 5, "We had expected to get five different methods");
//		for (Iterator iter = methods.keySet().iterator(); iter.hasNext(); ) {
//			String key = (String) iter.next();
//			String value = (String) methods.get(key);
//			System.out.println("Key:" + key + " value:" + value);
//		}
		
		assertTrue(lexer.getComments().size() == 4, "We have expected to get four different comments");
//		for (String item : lexer.getComments()) {
//			System.out.println("Comment: " + item);
//		}
	}

}
