package app;

import java.io.BufferedReader;

import java.io.FileReader;

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
	
	public static void loadUniformVariables() {
		int samplerLocation = glGetUniformLocation(programId, "textureSampler");
		glUniform1i(samplerLocation, 0);
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
