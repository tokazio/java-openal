package org.urish.openal.jna;

import java.util.LinkedList;
import java.util.List;

import org.urish.openal.ALException;

import com.sun.jna.Pointer;

public class Util {
	public static String getString(Pointer pointer) {
		if (pointer == null) {
			return null;
		}
		return pointer.getString(0, false);
	}

	public static List<String> getStrings(Pointer pointerToStrings) {
		List<String> result = new LinkedList<String>();
		int offset = 0;
		String current = pointerToStrings.getString(offset, false);
		while (current.length() > 0) {
			result.add(current);
			offset += current.length() + 1;
			current = pointerToStrings.getString(offset, false);
		}
		return result;
	}

	public static void checkForALError(AL al) throws ALException {
		int errorCode = al.alGetError();
		if (errorCode != AL.AL_NO_ERROR) {
			throw createALException(al);
		}

	}

	public static ALException createALException(AL al) {
		int errorCode = al.alGetError();
		return new ALException("AL Error " + errorCode + ": " + getString(al.alGetString(errorCode)));
	}

	public static void checkForALCError(ALC alc, ALCdevice device) throws ALException {
		int errorCode = alc.alcGetError(device);
		if (errorCode != ALC.ALC_NO_ERROR) {
			throw createALCException(alc, device);
		}
	}

	public static ALException createALCException(ALC alc, ALCdevice device) {
		int errorCode = alc.alcGetError(device);
		return new ALException("ALC Error " + errorCode + ": " + getString(alc.alcGetString(device, errorCode)));
	}

}