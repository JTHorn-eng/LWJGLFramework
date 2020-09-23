package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class OBJLoader {

	static ArrayList<Vector3f> vertices = new ArrayList<>();
	static ArrayList<Vector2f> textureCoords = new ArrayList<>();
	static ArrayList<Vector3f> normals = new ArrayList<>();
	static ArrayList<Integer> finalIndices = new ArrayList<>();
	static ArrayList<Vector3f> finalVertices = new ArrayList<>();
	static ArrayList<Vector3f> finalNormals = new ArrayList<>();
	static ArrayList<Vector2f> finalTextureCoords = new ArrayList<>();
	static float[] fVertices;
	static float[] fNormals;
	static float[] fTextureCoords;
	static int[] fIndices;

	public static void loadObjModel(String fileName) {	
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("models/"+fileName+".obj"));
			String line;
			
			while ((line = br.readLine()) != null) {
				
				if (line.startsWith("v ")) {
					String[] currentLine = line.split(" ");

					Vector3f vertex = new Vector3f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
					

				} else if (line.startsWith("vt ")) {
					String[] currentLine = line.split(" ");

					Vector2f texture = new Vector2f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]));
					textureCoords.add(texture);
					

				} else if (line.startsWith("vn ")) {
					String[] currentLine = line.split(" ");

					Vector3f normal = new Vector3f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));
					normals.add(normal);
					

				} else if (line.startsWith("f ")) {
					
					
					//indexes in .OBJ format start at 1 !
					String analyse = line.replace("f ", "");
					String[] currentLine = analyse.split(" ");
					
					
					for (int x = 0; x < 3;x++) {
						
						String[] value = currentLine[x].split("/");
						
						 finalIndices.add(Integer.parseInt(value[0]) - 1);
						 finalVertices.add(vertices.get(Integer.parseInt(value[0]) - 1));
						 finalTextureCoords.add(textureCoords.get(Integer.parseInt(value[1]) - 1));
						 finalNormals.add(normals.get(Integer.parseInt(value[2]) - 1));
						
					}	
				}	
			}	
			
			fVertices = new float[finalVertices.size() * 3];
			fNormals = new float[finalVertices.size() * 3];
			fTextureCoords = new float[finalTextureCoords.size() * 2];
			fIndices = new int[finalIndices.size()];
			
			//load vertex and normal data
			for (int i = 0; i < finalVertices.size() * 3; i+=3) {
				fVertices[i] = finalVertices.get(i).x;
				fVertices[i+1] = finalVertices.get(i).y;
				fVertices[i+2] = finalVertices.get(i).z;
				fNormals[i] = finalNormals.get(i).x;
				fNormals[i+1] = finalNormals.get(i).y;
				fNormals[i+2] = finalNormals.get(i).z;
				
			}
			
			//load texture data
			for (int i = 0; i < finalTextureCoords.size() * 2; i+=2) {
				fTextureCoords[i] = finalNormals.get(i).x;
				fTextureCoords[i + 1] =finalNormals.get(i).y;
			}
		
			for (int i = 0; i < finalIndices.size(); i++) {
				fIndices[i] = finalIndices.get(i);
			}
			
			
			
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
	}

	public static float[] getfVertices() {
		return fVertices;
	}


	public static float[] getfNormals() {
		return fNormals;
	}

	

	public static float[] getfTextureCoords() {
		return fTextureCoords;
	}

	

	public static int[] getfIndices() {
		return fIndices;
	}

	
}
