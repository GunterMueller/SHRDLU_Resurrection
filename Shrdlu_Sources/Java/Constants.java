//////////////////////////////////////////////////////////////////////////
// CLASS		Constants
// VERSION		0.0.1
// DATE			11/21/99
// 
// PURPOSE: constant values used througout application		
//////////////////////////////////////////////////////////////////////////

import javax.media.j3d.*;
import javax.vecmath.*;

public class Constants {

   public final int RED = 1;
   public final int BLUE = 2;
   public final int GREEN = 3;
   
   public final int PYRA = 4;
   public final int BOX = 5;
  
  	// Color Constants
   public Color3f gray = new Color3f(0.3f, 0.3f, 0.3f);
   public Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
   public Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
   public Color3f red = new Color3f(1.0f, 0.0f, 0.0f);
   public Color3f blue = new Color3f(0.0f, 0.0f, 1.0f);
   public Color3f green = new Color3f(0.0f, 1.0f, 0.0f);
 
  //Material Constants
   public Material redMat = new Material(red, black, red, white, 100.0f);
   public Material blueMat = new Material(blue, black, blue, white, 100.0f);
   public Material greenMat = new Material(green, black, green, white, 100.0f);
   
  //Appearance Constants
   public Appearance redApp = new Appearance();
   public Appearance blueApp = new Appearance();
   public Appearance greenApp = new Appearance();
   
 public Constants() {
    redApp.setMaterial(redMat);
    blueApp.setMaterial(blueMat);
    greenApp.setMaterial(greenMat);
	
 }
}
