package entities;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class Camera extends Entity {

	private float zoom;

	public Camera() {
		super();
		setType("camera");
		zoom = 1f;
	}

	public FloatBuffer calculateViewMatrix() {
		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		Matrix4f view = new Matrix4f();
		view.identity();
		view.rotate((float) Math.toRadians(rotX()), new Vector3f(1f, 0, 0))
				.rotate((float) Math.toRadians(rotY()), new Vector3f(0, 1f, 0))
				.rotate((float) Math.toRadians(rotZ()), new Vector3f(0, 0, 1f));
		view.translate(-x(), -y(), -z());
		view.get(fb);

		return fb;

	}

	public float getZoom() {
		return zoom;
	}

	public void zoom(float zoom) {
		this.zoom = zoom;
	}

}
