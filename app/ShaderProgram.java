package app;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

/**
 * 
 * End-User will always associate a new shader program with their primitive
 * model. However they do not write them, instead options to add colouring,
 * lighting etc.. will be added functionally.
 *
 */
public class ShaderProgram {

	private static int programId;

	private static HashMap<String, Integer> uniformLocations = new HashMap<>();

	public void createProgram(String vFilename, String fFilename) throws Exception {
		// create a new shader program in OpenGL
		programId = glCreateProgram();

		if (programId == 0) {
			throw new Exception("Could not create Shader");
		}

		loadAndCompileShaderProgram(vFilename, GL_VERTEX_SHADER);
		loadAndCompileShaderProgram(fFilename, GL_FRAGMENT_SHADER);

		glLinkProgram(programId);
		if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
			throw new Exception("Error linking shader programs: " + glGetShaderInfoLog(programId, 2048));

		}

		glValidateProgram(programId);

		if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
			throw new Exception("Error validating shader program: " + glGetShaderInfoLog(programId, 2048));
		}

		loadAttribLocations();
	}

	private void loadAndCompileShaderProgram(String filename, int type) {
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
		glAttachShader(programId, shader);

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

	public static void loadAttribLocations() {
		glBindAttribLocation(programId, 0, "positions");
		glBindAttribLocation(programId, 1, "textureData");

	}

	public static void addUniformVariable(String name) {
		uniformLocations.put(name, glGetUniformLocation(programId, name));
	}

	public static void loadUniformVariables(Model model, Camera camera) throws UniformNotFoundException {
		addUniformVariable("viewMatrix");
		addUniformVariable("projMatrix");
		addUniformVariable("transMatrix");
		addUniformVariable("textureSampler");
		addUniformVariable("baseColour");
		addUniformVariable("isTexture");


		glUniform3fv(uniformLocations.get("baseColour"), model.getBaseColour());
		glUniformMatrix4fv(uniformLocations.get("transMatrix"), false, model.getTransformMatrix());
		glUniformMatrix4fv(uniformLocations.get("viewMatrix"), false, camera.calculateViewMatrix());
		glUniformMatrix4fv(uniformLocations.get("projMatrix"), false, Rendering.getProjMatrix());
		glUniform1i(uniformLocations.get("textureSampler"), 0);
		glUniform1i(uniformLocations.get("isTexture"), model.isTexture());
	}

	public void deleteShaderProgram() {
		glUseProgram(0);
		if (programId != 0) {
			glDeleteProgram(programId);
		}
	}

	public static int getProgram() {
		return programId;
	}

}
