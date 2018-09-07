//Block class - constructs either pyramid or box depending on arguments
//                   and adds it to the tree

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Block extends Primitive {
 
   public String BlockNo;
   public BoundingSphere bounds;
   public Point3f CurLoc = new Point3f();
   private int shape, color;
   private Vector3f location;
   public Point3f size;
   private Transform3D objTrans;
   private TransformGroup TransNode;
   private Pyramid pyra = null;
   private ClosedBox box = null;
   
    public Block(int shape_in, int color_in, Vector3f location_in, 
	                   Point3f size_in, String BlockNo) {
	shape = shape_in;
	color = color_in;
	location = location_in;
	size = size_in;
	this.BlockNo = BlockNo;  //what was this for?????
    
	this.setUserData(BlockNo);
	
   Constants Const = new Constants();
  
    float xdim = size.x;
	float ydim = size.y;
	float zdim = size.z;
	
	location.get((Tuple3f)CurLoc);

	Appearance blockApp = new Appearance();
	
	switch(color) {
	
	case Const.RED: 
		 blockApp.setMaterial(Const.redMat);
		 break;
    case Const.BLUE: 
		 blockApp.setMaterial(Const.blueMat);
		 break;
	case Const.GREEN: 
		 blockApp.setMaterial(Const.greenMat);
		 break;
	default:
		break;
	}
	
	TransNode = new TransformGroup();
	TransNode.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objTrans = new Transform3D();
	
	objTrans.set(location);
	
	TransNode.setTransform(objTrans);
	
	if (shape == Const.PYRA) {
	   pyra = new Pyramid(xdim, ydim, zdim, blockApp);
	   TransNode.addChild(pyra);
	} else if (shape == Const.BOX) {
		box = new ClosedBox(xdim, ydim, zdim, blockApp);
		TransNode.addChild(box);
	}
	this.addChild(TransNode);
  }
  
  void MoveTo(Point3f newLoc){
	 CurLoc = newLoc;
	 objTrans.set(new Vector3f(newLoc.x, newLoc.y, newLoc.z));
	 TransNode.setTransform(objTrans);
  }
  
  public void setAppearance(Appearance ignored) {}
  
  public Shape3D getShape(int partId) {
    if(pyra != null) {
	  return pyra;
	}
	else if(box != null) {
      return box;
	}
	else { 
	  return null;
	}
  }
  
  public Node cloneNode(boolean forceDuplicate) {
        Block b = new Block(shape, color, location, size, BlockNo);
        b.duplicateNode(this, forceDuplicate);
        return b;
    }
  
  public void duplicateNode(Node originalNode, boolean forceDuplicate){
  		 super.duplicateNode(originalNode, forceDuplicate);
  }
}