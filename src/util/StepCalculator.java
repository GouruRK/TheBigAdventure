package util;

import graphic.controller.GeneralController;
import graphic.view.Draw;

public class StepCalculator {

  public static double getStep(double factor) {
    return Draw.getImageSize()*factor/GeneralController.getFramerate();
  }
  
}
