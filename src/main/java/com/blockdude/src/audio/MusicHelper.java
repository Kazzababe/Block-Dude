package com.blockdude.src.audio;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;

import com.blockdude.src.GlobalOptions;

public class MusicHelper implements MusicListener {
	private static Set<Music> music = new HashSet<Music>();
	
	public static void playMusic(Music music, boolean loop) {
		if (loop) {
			music.loop();
		} else {
			music.play();
		}
		music.setVolume(GlobalOptions.MUSIC_VOLUME * GlobalOptions.MASTER_VOLUME);
		music.addListener(new MusicHelper());
		MusicHelper.music.add(music);
	}
	
	public static void changeVolume() {
		for (Music music : MusicHelper.music) {
			music.setVolume(GlobalOptions.MUSIC_VOLUME * GlobalOptions.MASTER_VOLUME);
		}
	}
	
	public static void stopMusic(Music music) {
		music.stop();
		if (MusicHelper.music.contains(music)) {
			MusicHelper.music.remove(music);
		}
	}
	
	@Override
	public void musicEnded(Music music) {
		MusicHelper.music.remove(music);
		System.out.println("LOL");
	}

	@Override
	public void musicSwapped(Music arg0, Music arg1) {
		
	}
}
