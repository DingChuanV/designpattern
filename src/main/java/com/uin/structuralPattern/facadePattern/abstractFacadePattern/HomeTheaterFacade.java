package com.uin.structuralPattern.facadePattern.abstractFacadePattern;

import com.uin.structuralPattern.facadePattern.subSystem.DVDPlayer;
import com.uin.structuralPattern.facadePattern.subSystem.Projector;
import com.uin.structuralPattern.facadePattern.subSystem.SoundSystem;
import lombok.extern.slf4j.Slf4j;

/**
 * 具体外观类
 *
 * @author dingchuan
 */
@Slf4j
public class HomeTheaterFacade extends AbstractHomeTheaterFacade {

  public HomeTheaterFacade(DVDPlayer dvd,
      Projector projector,
      SoundSystem sound) {
    super(dvd, projector, sound);
  }

  @Override
  public void watchMovie(String movie) {
    log.info("Get ready to watch a movie...");
    dvdPlayer.on();
    dvdPlayer.play(movie);
    projector.on();
    projector.wideScreenMode();
    soundSystem.on();
    soundSystem.setVolume(5);
    log.info("Movie is ready to watch!");
  }

  @Override
  public void endMovie() {
    log.info("Shutting movie theater down...");
    dvdPlayer.off();
    projector.off();
    soundSystem.off();
  }
}
