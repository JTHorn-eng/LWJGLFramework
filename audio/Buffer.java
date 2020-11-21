package audio;

import java.nio.FloatBuffer;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;
import org.lwjgl.stb.STBVorbisInfo;

import static org.lwjgl.stb.STBVorbis.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.stb.STBVorbis;



/**
 * 
 * .OGG format for sound files ONLY
 * Will have to convert files
 */

public class Buffer {

	private final int bufferId;

	public Buffer(String file) throws Exception {
		this.bufferId = AL10.alGenBuffers();

		try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
			ShortBuffer pcm = readVorbis(file, info);

			// Copy to buffer
			AL10.alBufferData(bufferId, info.channels() == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16, pcm,
					info.sample_rate());
		}
	}

	ShortBuffer readVorbis(String resource, STBVorbisInfo info) throws Exception {

		ByteBuffer vorbis = null;
		ShortBuffer pcm = null;

		try (MemoryStack stack = MemoryStack.stackPush()) {
			vorbis = resToBuffer(resource);
			IntBuffer error = stack.mallocInt(1);
			long decoder = stb_vorbis_open_memory(vorbis, error, null);
			if (decoder == NULL) {
				throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
			}

			stb_vorbis_get_info(decoder, info);

			int channels = info.channels();

			int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

			pcm = MemoryUtil.memAllocShort(lengthSamples);

			pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
			stb_vorbis_close(decoder);

			return pcm;
		}

	}

	static ByteBuffer resToBuffer(String filename) {
		Path path = Paths.get(filename);
		byte[] data;
		ByteBuffer buffer = null;
		try {

			data = Files.readAllBytes(path);
			buffer = BufferUtils.createByteBuffer(data.length);
			buffer.put(data);
			buffer.flip();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public int getBufferId() {
		return this.bufferId;
	}

	public void destroy() {
		AL10.alDeleteBuffers(this.bufferId);
	}

}
