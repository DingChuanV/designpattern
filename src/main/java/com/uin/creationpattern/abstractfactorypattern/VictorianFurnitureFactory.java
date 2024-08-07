package com.uin.creationpattern.abstractfactorypattern;

// 维多利亚家具工厂
public class VictorianFurnitureFactory implements FurnitureFactory {

  @Override
  public Chair createChair() {
    return new VictorianChair();
  }

  @Override
  public Sofa createSofa() {
    return new VictorianSofa();
  }
}
