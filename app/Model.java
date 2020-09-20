package app;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Model {

	private int vaoID;
	private ModelType type;
	private int textureID;
	private float[] viewMatrix = {
			1.0f, 0f, 0f, 0f,
			0f, 1.00f, 0f, 0f,
			0f, 0f, 1.0f, 0f,
			0f, 0f, 0f, 1.0f	
	};

	public Model(ModelType t, int vaoID, int textureID) {
		type = t;
		this.vaoID = vaoID;
		this.textureID = textureID;
	}
	
	public ModelType getType() {
		return type;
	}
	
	public void setVAOID(int id) {
		vaoID = id;
	}
	
	public int getVAOID() {
		return vaoID;
	}
	
	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}
	
	public FloatBuffer getViewMatrix() {
		 Matrix4f m = new Matrix4f();		 
		 

		 m.m00(1.0f);
		 m.m01(0.0f);
		 m.m02(0.0f);
		 m.m03(0.0f);
		 m.m10(0.0f);
		 m.m11(1.0f);
		 m.m12(0.0f);
		 m.m13(0.0f);
		 m.m20(0.0f);
		 m.m21(0.0f);
		 m.m22(1.0f);
		 m.m23(0.0f);
		 m.m30(0.0f);
		 m.m31(0.0f);
		 m.m32(0.0f);
		 m.m33(1.0f); 
		 
		 FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		
		 m.get(buffer);
		 
		 buffer.flip();
		 
		 return buffer;
		
	}
	
	
}
