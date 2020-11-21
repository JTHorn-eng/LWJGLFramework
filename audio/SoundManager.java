package audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

public class SoundManager {

	long device;
	long context;
	
	public SoundManager() {
		
		device = ALC10.alcOpenDevice((ByteBuffer) null);
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		context = ALC10.alcCreateContext(device, (IntBuffer) null);
		ALC10.alcMakeContextCurrent(context);
		AL.createCapabilities(deviceCaps);
	}
	
	
	
}
