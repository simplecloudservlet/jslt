
/*
  Description:
  Functional sample to run JSLT 
  To compile:
  javac -cp .:core/build/libs/core-0.1.11-all.jar Principal.java
  
  To run:
  java -cp .:core/build/libs/core-0.1.11-all.jar Principal.java transform.jslt input.json

  Author: Lucio A. Rocha
  Last update: 15/12/2021
*/

import com.schibsted.spt.data.jslt.Parser;
import com.schibsted.spt.data.jslt.Expression;
import com.fasterxml.jackson.databind.*;
    
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class Principal {

    private String INPUT;
    private String TRANSFORM;
    public Principal(String [] args){

	INPUT = args[1];
	TRANSFORM = args[0];
	
	StringBuffer templateInput = new StringBuffer();
	BufferedReader ri;
	try {
	    ri = new BufferedReader(new FileReader(INPUT));
	    String line;
	    while((line = ri.readLine())!=null){
		System.out.println(line);
		templateInput.append(line+"\n");
	    }
	    System.out.println("Template:\n" + templateInput.toString());
	} catch (IOException io){
	    System.out.println("Exception reading template from file: " + io.getMessage());
	    io.printStackTrace();
	} catch (Exception e){
	    System.out.println("Uncaught Exception reading template from file: " + e.getMessage());
	    e.printStackTrace();
	}

	//----
	StringBuffer templateJSLT = new StringBuffer();
	BufferedReader rt;
	try {
	    rt = new BufferedReader(new FileReader(TRANSFORM));
	    String line;
	    while((line = rt.readLine())!=null){
		System.out.println(line);
		templateJSLT.append(line+"\n");
	    }
	    System.out.println("Template:\n" + templateJSLT.toString());
	} catch (IOException io){
	    System.out.println("Exception reading template from file: " + io.getMessage());
	    io.printStackTrace();
	} catch (Exception e){
	    System.out.println("Uncaught Exception reading template from file: " + e.getMessage());
	    e.printStackTrace();
	}

	
	
	//Source:
	//https://github.com/schibsted/jslt
	//http://tutorials.jenkov.com/java-json/jackson-jsonnode.html
	try {
	    String json = templateInput.toString(); 
	    ObjectMapper objectMapper = new ObjectMapper();	
	    JsonNode input = objectMapper.readTree(json);
	    Expression jslt = Parser.compileString(templateJSLT.toString());
	    JsonNode output = jslt.apply(input);
	    //System.out.println("["+output.get("hosts").asText()+"]");


	    //Write JsonNode -> JSON
	    ObjectMapper om = new ObjectMapper();
	    //JsonNode jsonNode = readJsonIntoJsonNode();
	    String contents = om.writeValueAsString(output);
	    System.out.println("----"+contents+"----");
	    
	    /*//http://tutorials.jenkov.com/java-json/jackson-jsonnode.html
	    Iterator<Map.Entry<String, JsonNode>> fields = output.fields();
	    while(fields.hasNext()) {
		Map.Entry<String, JsonNode> field = fields.next();
		String   fieldName  = field.getKey();
		JsonNode fieldValue = field.getValue();
		
		System.out.println(fieldName + " = " + fieldValue.asText());
		}*/
	    
	} catch (Exception e){
	    System.out.println("["+e.getMessage()+"]\n");
	    e.printStackTrace();
	}
	
    }
    
    public static void main(String [] args){
	if ( args[0] == null ){
	    System.out.println("Sintax: javac -cp .:core/build/libs/core-0.1.11-all.jar Principal.java transform.jslt input.json");
	    System.exit(1);
	}
	new Principal(args);
    }

    
}
