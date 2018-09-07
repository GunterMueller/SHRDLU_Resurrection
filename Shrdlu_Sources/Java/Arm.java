import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Arm extends Primitive {

  public BoundingSphere bounds;
  private String Grasped;
  public Point3f CurLoc, size;
  public Transform3D location;
  private TransformGroup ArmTrans;
    
  public Arm() {
  
    this.setUserData(new String("arm"));
    Constants Const = new Constants();
  
    ArmTrans = new TransformGroup();
	ArmTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	ArmTrans.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);	
	location = new Transform3D();
	location.set(new Vector3f(500, 500, 750));
	CurLoc = new Point3f(500, 500, 750);
	
	ArmTrans.setTransform(location);
	 
	Appearance armApp = new Appearance();
	Color3f yellow = new Color3f(1, 1, 0);
	armApp.setMaterial(new Material(yellow, Const.black, yellow, Const.white, 100.0f));
		
	Sphere armSphere = new Sphere(30, armApp);
	Box armCrossX = new Box(75, 10, 10, armApp);
	Box armCrossY = new Box(10, 75, 10, armApp);
	
	size = new Point3f(75.0f, 75.0f, 30.0f);
	
	ArmTrans.addChild(armSphere);
	ArmTrans.addChild(armCrossX);
	ArmTrans.addChild(armCrossY);
    this.addChild(ArmTrans); 
  }

  private Point3f newLoc;
  
  public void MoveTo(Point3f newLoc){
//System.out.println("-->Arm:MoveTo()");  
	CurLoc = newLoc;
	this.newLoc = newLoc;
	location.set(new Vector3f(newLoc.x, newLoc.y, newLoc.z));
	ArmTrans.setTransform(location);
  }
    
  public void Grasp(String blockName) {
	  Grasped = blockName;
  }
  
  public void UnGrasp() {
	Grasped = "nothing";
  }
  
  public String getGrasped() { return Grasped;  }
  public int getGraspedNum() {
    String num = Grasped.substring(1, Grasped.length());
	int blockNum = new Integer(num).intValue();
	return blockNum;
  }
  
  public void setAppearance(Appearance ignored) {}
  
  public Shape3D getShape(int partId) {return null;}
  
  public Node cloneNode(boolean forceDuplicate) {
        Arm b = new Arm();
        b.duplicateNode(this, forceDuplicate);
        return b;
    }
  
  public void duplicateNode(Node originalNode, boolean forceDuplicate){
  		 super.duplicateNode(originalNode, forceDuplicate);
  }
}

