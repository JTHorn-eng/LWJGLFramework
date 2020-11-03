package app;

import java.io.BufferedReader;

import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Vector;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import entities.Camera;
import entities.Light;
import entities.LightManager;
import entities.Model;
import exceptions.UniformNotFoundException;

import static org.lwjgl.opengl.GL20.*;

/**
 * 
 * End-User will always associate a new shader program with their primitive
 * model. However they do not write them, instead options to add colouring,
 * lighting etc.. will be added functionally.
 *
 */
public class ShaderProgram {
	
	private static HashMap<String, Integer> programIDs = new HashMap<>();
	

	private static HashMap<String, HashMap <String, Integer>> uniformLocations = new HashMap<>();

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

	private void loadAndCompileShaderProgram(String name, String filename, int type) {
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

	private String loadFile(String filename) {
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
		glBindAttribLocation(programIDs.get(name) , 1, "textureData");
		glBindAttribLocation(programIDs.get(name) , 2, "normals");
	}

	public static void addUniformVariable(String program, String name) {

		uniformLocations.get(program).put(name, glGetUniformLocation(programIDs.get(program) , name));
	}
	
	private static FloatBuffer loadVector3f(Vector3f vector) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
		vector.get(buffer);
		return buffer;
	}

	public static void loadDefaultUniformVariables(Model model, Camera camera, Light light) throws UniformNotFoundException {
		addUniformVariable("default","viewMatrix");
		addUniformVariable("default","projMatrix");
		addUniformVariable("default","transMatrix");
		addUniformVariable("default","textureSampler");
		addUniformVariable("default","base");

		addUniformVariable("default","isTexture");
		addUniformVariable("default","lightColour");
		addUniformVariable("default","lightPosition");
		addUniformVariable("default", "scalarBrightness");
		
		glUniform3fv(uniformLocations.get("default").get("lightColour"), loadVector3f(LightManager.getTest().getColor()));
		glUniform3fv(uniformLocations.get("default").get("lightPosition"), loadVector3f(LightManager.getTest().getPosition()));

		glUniformMatrix4fv(uniformLocations.get("default").get("transMatrix"), false, model.getTransformMatrix());
		glUniformMatrix4fv(uniformLocations.get("default").get("viewMatrix"), false, camera.calculateViewMatrix());
		glUniformMatrix4fv(uniformLocations.get("default").get("projMatrix"), false, Rendering.getProjMatrix());
		glUniform1i(uniformLocations.get("default").get("textureSampler"), 0);
		glUniform3fv(uniformLocations.get("default").get("base"), model.getBase());
		glUniform1i(uniformLocations.get("default").get("isTexture"), model.getTexture());
	
		glUniform1f(uniformLocations.get("default").get("scalarBrightness"), light.getBrightness());
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
