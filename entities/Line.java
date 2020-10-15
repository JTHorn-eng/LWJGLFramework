package entities;

import org.joml.Vector3f;

public class Line {

	private Vector3f start;
	private Vector3f end;
	private float thickness;
	private int id;
	
	public Line(Vector3f start, Vector3f end, float thickness, int id) {
		this.start = start;
		this.end = end;
		this.thickness = thickness;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vector3f getStart() {
		return start;
	}

	public void setStart(Vector3f start) {
		this.start = start;
	}

	public Vector3f getEnd() {
		return end;
	}

	public void setEnd(Vector3f end) {
		this.end = end;
	}

	public float getThickness() {
		return thickness;
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

	public Line(Vector3f start, Vector3f end) {
		this.start = start;
		this.end = end;
	}
	
	
}
