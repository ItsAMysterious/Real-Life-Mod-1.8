package itsamysterious.mods.reallifemod.core.vehicles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class CustomRotationAxes {
	private float rotationYaw;
	private float rotationPitch;
	private float rotationRoll;

	public CustomRotationAxes(float x, float y, float z) {
		setAngles(x, y, z);
	}

	public CustomRotationAxes() {
	}

	public void setAngles(float x, float y, float z) {
		rotationYaw = x;
		rotationPitch = y;
		rotationRoll = z;
	}

	// Find a global vector in terms of this basis.
	public Vector3f findGlobalVectorLocally(Vector3f in) {
		// Create a new matrix and use the first column to store the vector we
		// are rotating
		Matrix4f mat = new Matrix4f();
		mat.m00 = in.x;
		mat.m10 = in.y;
		mat.m20 = in.z;
		// Do the rotations used to obtain this basis in reverse
		mat.rotate(-rotationYaw * 3.14159265F / 180F, new Vector3f(0F, 1F, 0F));
		mat.rotate(-rotationPitch * 3.14159265F / 180F, new Vector3f(0F, 0F, 1F));
		mat.rotate(-rotationRoll * 3.14159265F / 180F, new Vector3f(1F, 0F, 0F));
		return new Vector3f(mat.m00, mat.m10, mat.m20);
	}

	// Find a local vector in terms of the global axes.
	public Vector3f findLocalVectorGlobally(Vector3f in) {
		// Create a new matrix and use the first column to store the vector we
		// are rotating
		Matrix4f mat = new Matrix4f();
		mat.m00 = in.x;
		mat.m10 = in.y;
		mat.m20 = in.z;
		// Do the rotations used to obtain this basis
		mat.rotate(rotationRoll * 3.14159265F / 180F, new Vector3f(1F, 0F, 0F));
		mat.rotate(rotationPitch * 3.14159265F / 180F, new Vector3f(0F, 0F, 1F));
		mat.rotate(rotationYaw * 3.14159265F / 180F, new Vector3f(0F, 1F, 0F));
		return new Vector3f(mat.m00, mat.m10, mat.m20);
	}

	public float getYaw() {
		return rotationYaw;
	}

	public float getPitch() {
		return rotationPitch;
	}

	public float getRoll() {
		return rotationPitch;
	}

	@Override
	protected CustomRotationAxes clone() {
		return new CustomRotationAxes(this.rotationYaw, this.rotationPitch, this.rotationRoll);
	}
}
