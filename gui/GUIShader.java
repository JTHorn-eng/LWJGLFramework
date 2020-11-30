package gui;

import java.io.BufferedReader;

import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class GUIShader {
	
	static HashMap<String, Integer> programIDs = new HashMap<>();
	static HashMap<String, HashMap <String, Integer>> uniformLocations = new HashMap<>();

	public void createProgram(String name, String vFilename, String fFilename) throws Exception {
		// create a new shader program in OpenGL
		
		programIDs.put(name, glCreateProgram());
		uniformLocations.put(name, new HashMap<String, Integer>());
		
		if (programIDs.get(name) == 0) {
			throw new Exception("Could not create Shader");
		}

		loadAndCompileShaderProgram(name, vFilename, GL_VERTEX_SHADER);
		loadAndCompileShaderProgram(name, fFilename, GL_FRAGMENT_SHADER);

		glLinkProgram(programIDs.get(name));
		if (glGetProgrami(programIDs.get(name) , GL_LINK_STATUS) == 0) {
			throw new Exception("Error linking shader programs: " + glGetShaderInfoLog(programIDs.get(name) , 2048));

		}

		glValidateProgram(programIDs.get(name) );

		if (glGetProgrami(programIDs.get(name) , GL_VALIDATE_STATUS) == 0) {
			throw new Exception("Error validating shader program: " + glGetShaderInfoLog(programIDs.get(name) , 2048));
		}

		loadAttribLocations(name);
	}

	void loadAndCompileShaderProgram(String name, String filename, int type) {
		// load and compile the two shaders
		int shader = glCreateShader(type);

		String shaderCode = loadFile(filename);

		// upload code to OpenGL and associate code with shader variables in OpenGL
		glShaderSource(shader, shaderCode);

		// compile source code into object code
		glCompileShader(shader);

		// check for compilation errors
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			throw new IllegalStateException("Error compiling shader" + filename);
		}
		glAttachShader(programIDs.get(name) , shader);

	}

	String loadFile(String filename) {
		String content = "";
		try {
			String line = "";
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine()) != null) {
				content += line + "\n";
			}
			br.close();

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

		return content;
	}

	public static void loadAttribLocations(String name) {
		glBindAttribLocation(programIDs.get(name) , 0, "positions");
		glBindAttribLocation(programIDs.get(name) , 1, "textureCoords");

	}
	
	public static void addUniformVariable(String program, String name) {

		uniformLocations.get(program).put(name, glGetUniformLocation(programIDs.get(program) , name));
	}
	
	public static void loadGUIUniforms(FloatBuffer translate, float rotation, FloatBuffer scaling) {
		addUniformVariable("guis", "textureSampler");
		addUniformVariable("guis", "translate");
		addUniformVariable("guis", "rotation");
		addUniformVariable("guis", "scale");
		glUniform2fv(uniformLocations.get("guis").get("translate"), translate);
		glUniform1f(uniformLocations.get("guis").get("textureSampler"), rotation);
		glUniform2fv(uniformLocations.get("guis").get("translate"), scaling);

	}
	
	
	public static void deleteShaderPrograms() {
		glUseProgram(0);
		for (String name : programIDs.keySet()) {
			if (programIDs.get(name) != 0) {
				glDeleteProgram(programIDs.get(name) );
			}
		}
	}

	public static int getProgram(String name) {
		return programIDs.get(name) ;
	}

}
