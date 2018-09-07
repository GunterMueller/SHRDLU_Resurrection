import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class EmptyBox extends Primitive {

  public EmptyBox(Point3f location, Point3f size) {
    
	Constants Const = new Constants();
	
	Appearance BoxLook = new Appearance();
	BoxLook.setMaterial(new Material(Const.white, Const.white, Const.white, Const.gray, 100.0f));

  	Appearance BoxWallsLook = BoxLook;
	TransparencyAttributes transAttrs =
          new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.15f);
    BoxWallsLook.setTransparencyAttributes(transAttrs);

	Box box = new Box(size.x/2, size.y/2, 1.0f/2, BoxLook);
    Box BW1 = new Box(10.0f/2, size.y/2, size.z/2, BoxWallsLook);
	Box BW2 = new Box(size.x/2, 10.0f/2, size.z/2, BoxWallsLook);
	Box BW3 = new Box(10.0f/2, size.y/2, size.z/2, BoxWallsLook);
	Box BW4 = new Box(size.x/2, 10.0f/2, size.z/2, BoxWallsLook);
 	
 //Box base
	TransformGroup BoxMoveNode = new TransformGroup();
	Transform3D BoxMove = new Transform3D();
	BoxMove.set(new Vector3f((location.x+(size.x/2)), 
										  (location.y+(size.y/2)), 
										  (0.0f+(1.0f/2))));
	BoxMoveNode.setTransform(BoxMove);
	this.addChild(BoxMoveNode);
	BoxMoveNode.addChild(box);
 //Front box wall	
	TransformGroup BW1MoveNode = new TransformGroup();
	Transform3D BW1Move = new Transform3D();
	BW1Move.set(new Vector3f((location.x+(10/2)), 
											(location.y+(size.y/2)), 
											(0.0f+(size.z/2))));
	BW1MoveNode.setTransform(BW1Move);
	this.addChild(BW1MoveNode);
	BW1MoveNode.addChild(BW1);
 //Left box wall
	TransformGroup BW2MoveNode = new TransformGroup();
	Transform3D BW2Move = new Transform3D();
	BW2Move.set(new Vector3f((location.x+(size.x/2)), 
											(location.y+(10/2)),
											(0.0f+(size.z/2))));
	BW2MoveNode.setTransform(BW2Move);
	this.addChild(BW2MoveNode);
	BW2MoveNode.addChild(BW2);
 //Right box wall
	TransformGroup BW3MoveNode = new TransformGroup();
	Transform3D BW3Move = new Transform3D();
	BW3Move.set(new Vector3f((location.x+size.x+(10/2)), 
											(location.y+(size.y/2)), 
											(0.0f+(size.z/2))));
	BW3MoveNode.setTransform(BW3Move);
	this.addChild(BW3MoveNode);
	BW3MoveNode.addChild(BW3);
 //Back box wall
	TransformGroup BW4MoveNode = new TransformGroup();
	Transform3D BW4Move = new Transform3D();
	BW4Move.set(new Vector3f((location.x+(size.x/2)), 
											(location.y+size.y+(10/2)), 
											(0.0f+(size.z/2))));
	BW4MoveNode.setTransform(BW4Move);
	this.addChild(BW4MoveNode);
	BW4MoveNode.addChild(BW4);

  }
  
  public void setAppearance(Appearance ignored) {}
  
  public Shape3D getShape(int partId) {return null;}
  
}