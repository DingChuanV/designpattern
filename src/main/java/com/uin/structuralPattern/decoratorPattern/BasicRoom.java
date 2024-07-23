package com.uin.structuralPattern.decoratorPattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicRoom implements Room {

  @Override
  public void decorate() {
    log.info("Room is decorated");
  }
}
