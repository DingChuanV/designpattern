package com.uin.structuralPattern.compositePattern.safaCompositePattern;

public class Client {

  public static void main(String[] args) {
    Graphic circle = new Circle();
    Graphic rectangle = new Rectangle();

    CompositeGraphic composite = new CompositeGraphic();
    composite.add(circle);
    composite.add(rectangle);

    CompositeGraphic subComposite = new CompositeGraphic();
    subComposite.add(new Circle());
    subComposite.add(new Rectangle());

    composite.add(subComposite);

    composite.draw();
  }
}
