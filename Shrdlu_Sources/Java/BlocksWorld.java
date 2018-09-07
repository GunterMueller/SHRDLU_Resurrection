import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.vecmath.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.keyboard.*;
import com.sun.j3d.utils.*;
import javax.media.j3d.*;
import java.util.*;

public class BlocksWorld extends Panel {

  private Arm arm;
  private Block B1, B2, B3, B4, B5, B6, B7, B10;
  private boolean grasped = false, animate = false;
  private Box Table;
  private BranchGroup objRoot, behaviorBranch;
  public BranchGroup B1BG, B2BG, B3BG, B4BG, B5BG, B6BG, B7BG, B10BG, armBG; 
  public Canvas3D c;
  private EmptyBox box;
  private GraphicsContext3D gc = null;
  private Point3f NewLoc;
  private PositionPathInterpolator Interp;
  private static TransformGroup objTrans, moverNode;
 
  public BranchGroup createSceneGraph(SimpleUniverse su) {
   Constants Const = new Constants();  
  
	// Create the root of the branch graph
	objRoot = new BranchGroup();

    // Create a Transformgroup to scale all objects so they
    // appear in the scene.
    TransformGroup SceneScale = new TransformGroup();
    Transform3D t3d = new Transform3D();
    
	t3d.setScale(0.004);
	
    SceneScale.setTransform(t3d);
    objRoot.addChild(SceneScale);
	
	TransformGroup keyTrans = su.getViewingPlatform().getViewPlatformTransform();
	KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(keyTrans);
	keyNavBeh.setSchedulingBounds(new BoundingSphere(new Point3d(), 1500.0f));
	objRoot.addChild(keyNavBeh);

	// Create a bounds for the background and lights
	BoundingSphere bounds =
	    new BoundingSphere(new Point3d(250.0,250.0,0.0), 1000.0);

	// Set up the background
	Color3f bgColor = new Color3f(0.7f, 0.7f, 0.7f);
	Background bgNode = new Background(bgColor);
	bgNode.setApplicationBounds(bounds);
	SceneScale.addChild(bgNode);

	// Set up the global lights
	Color3f light1Color = new Color3f(1.0f, 1.0f, 0.9f);
	Vector3f light1Direction  = new Vector3f(4.0f, 7.0f, -12.0f);
	Color3f light2Color = new Color3f(0.0f, 0.0f, 0.0f);
	Vector3f light2Direction  = new Vector3f(-4.0f, 2.0f, -3.0f);
	Color3f ambientColor = new Color3f(0.2f, 0.2f, 0.2f);

	AmbientLight ambientLightNode = new AmbientLight(ambientColor);
	ambientLightNode.setInfluencingBounds(bounds);
	SceneScale.addChild(ambientLightNode);

	DirectionalLight light1
	    = new DirectionalLight(light1Color, light1Direction);
	light1.setInfluencingBounds(bounds);
	SceneScale.addChild(light1);

	DirectionalLight light2
	    = new DirectionalLight(light2Color, light2Direction);
	light2.setInfluencingBounds(bounds);
	SceneScale.addChild(light2);

	objTrans = new TransformGroup();
	objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	objTrans.setCapability(Group.ALLOW_CHILDREN_READ);
	objTrans.setCapability(Group.ALLOW_CHILDREN_WRITE);
	objTrans.setCapability(Group.ALLOW_CHILDREN_EXTEND);
	SceneScale.addChild(objTrans);

//Arm

	 arm = new Arm();
	 armBG = new BranchGroup();
	 armBG.setCapability(BranchGroup.ALLOW_DETACH);
	 armBG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	 armBG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	 armBG.setUserData("armBG");
	 armBG.addChild(arm);
	 objTrans.addChild(armBG);
	
//Table		
	Appearance TableApp = new Appearance();
	TableApp.setMaterial(new Material(Const.gray, Const.black, Const.gray, Const.white, 100.0f));
			
	Table = new Box(700.0f, 700.0f, 20.0f, TableApp);
	Table.setUserData("Table");
	
	TransformGroup TableLocNode = new TransformGroup();
	Transform3D TableLoc = new Transform3D();
	TableLoc.set(new Vector3f(500.0f, 500.0f, -20.0f));
	
	TableLocNode.setTransform(TableLoc);
	TableLocNode.setUserData(new String("TableLocNode"));
	
	objTrans.addChild(TableLocNode);
   TableLocNode.addChild(Table);

//Box - white box - loc = (600, 600, 0) size = (400, 400, 300)
   Point3f BoxLoc = new Point3f(600, 600, 0);
   Point3f BoxSize = new Point3f(400, 400, 300); 
   box = new EmptyBox(BoxLoc, BoxSize);
   box.setUserData(new String("box"));
   objTrans.addChild(box);

//B1 - red Block - loc = (110,100,0) size = (100,100,100)
   Point3f b1size = new Point3f(100/2, 100/2, 100/2);
   Vector3f b1loc = new Vector3f(110+b1size.x, 100+b1size.y, 0+b1size.z);
   B1 = new Block(Const.BOX, Const.RED, b1loc, b1size, "B1");
	 B1BG = new BranchGroup();
	 B1BG.setCapability(BranchGroup.ALLOW_DETACH);
	 B1BG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	 B1BG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	 B1BG.setUserData("B1BG");
	 B1BG.addChild(B1);
	 objTrans.addChild(B1BG);
   
//B2 - green Pyramid - loc = (110, 100, 100), size = (100, 100, 100)
   Point3f b2size = new Point3f(100/2, 100/2, 100/2);
   Vector3f b2loc = new Vector3f(110+b2size.x, 100+b2size.y, 100+b2size.z);
   B2 = new Block(Const.PYRA, Const.GREEN, b2loc, b2size, "B2");
	 B2BG = new BranchGroup();
	 B2BG.setCapability(BranchGroup.ALLOW_DETACH);
	 B2BG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	 B2BG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	 B2BG.setUserData("B2BG");
	 B2BG.addChild(B2);
	 objTrans.addChild(B2BG);
	  
//B3 - green Block - loc = (400, 0, 0) size = (200, 200, 200)
  Point3f b3size = new Point3f(200/2, 300/2, 200/2);
  Vector3f b3loc = new Vector3f(400+b3size.x, 0+b3size.y, 0+b3size.z);
  B3 = new Block(Const.BOX, Const.GREEN, b3loc, b3size, "B3");
	 B3BG = new BranchGroup();
	 B3BG.setCapability(BranchGroup.ALLOW_DETACH);
	 B3BG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	 B3BG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	 B3BG.setUserData("B3BG");
	 B3BG.addChild(B3);
	 objTrans.addChild(B3BG);
	 	 
//B4 - blue Pyramid - loc = (640, 640, 0) size = (200, 200, 200)
   Point3f b4size = new Point3f(200/2, 200/2, 200/2);
   Vector3f b4loc = new Vector3f(640+b4size.x, 640+b4size.y, 0+b4size.z);
   B4 = new Block(Const.PYRA, Const.BLUE, b4loc, b4size, "B4");
	 B4BG = new BranchGroup();
	 B4BG.setCapability(BranchGroup.ALLOW_DETACH);
	 B4BG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	 B4BG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	 B4BG.setUserData("B4BG");
	 B4BG.addChild(B4);
	 objTrans.addChild(B4BG);
	  
//B5 - red Pyramid - loc = (500,100,200) size = (100,100,300)
   	 Point3f b5size = new Point3f(100/2, 100/2, 300/2);
	 Vector3f b5loc = new Vector3f(500+b5size.x, 100+b5size.y, 200+b5size.z);
	 B5 = new Block(Const.PYRA, Const.RED, b5loc, b5size, "B5");
	 B5BG = new BranchGroup();
	 B5BG.setCapability(BranchGroup.ALLOW_DETACH);
	 B5BG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	 B5BG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	 B5BG.setUserData("B5BG");
	 B5BG.addChild(B5);
	 objTrans.addChild(B5BG);
	 
//B6 - red Block - loc = (0, 300, 0) size = (200, 300, 300)
  Point3f b6size = new Point3f(200/2, 300/2, 300/2);
  Vector3f b6loc = new Vector3f(0+b6size.x, 300+b6size.y, 0+b6size.z);
  B6 = new Block(Const.BOX, Const.RED, b6loc, b6size, "B6");
	 B6BG = new BranchGroup();
	 B6BG.setCapability(BranchGroup.ALLOW_DETACH);
	 B6BG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	 B6BG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	 B6BG.setUserData("B6BG");
	 B6BG.addChild(B6);
	 objTrans.addChild(B6BG);
	 
//B7 - green Block - loc = (0, 240, 300) size = (200, 200, 200)
  Point3f b7size = new Point3f(200/2, 200/2, 200/2);
  Vector3f b7loc = new Vector3f(0+b7size.x, 240+b7size.y, 300+b7size.z);
  B7 = new Block(Const.BOX, Const.GREEN, b7loc, b7size, "B7");
	 B7BG = new BranchGroup();
	 B7BG.setCapability(BranchGroup.ALLOW_DETACH);
	 B7BG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	 B7BG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	 B7BG.setUserData("B7BG");
	 B7BG.addChild(B7);
	 objTrans.addChild(B7BG);
	 
//B10 - blue Block - loc = (300, 640, 0) size = (200, 100, 400)
  Point3f b10size = new Point3f(200/2, 100/2, 400/2);
  Vector3f b10loc = new Vector3f(300+b10size.x, 640+b10size.y, 0+b10size.z);
  B10 = new Block(Const.BOX, Const.BLUE, b10loc, b10size, "B10");
	 B10BG = new BranchGroup();
	 B10BG.setCapability(BranchGroup.ALLOW_DETACH);
	 B10BG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	 B10BG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	 B10BG.setUserData("B10BG");
	 B10BG.addChild(B10);
	 objTrans.addChild(B10BG);
	 
//Alpha, Interpolator, and TransformGroup for Animations.

   moverNode = new TransformGroup();
   moverNode.setCapability(Group.ALLOW_CHILDREN_READ);
   moverNode.setCapability(Group.ALLOW_CHILDREN_WRITE);
   moverNode.setCapability(Group.ALLOW_CHILDREN_EXTEND);
   moverNode.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
   moverNode.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   Transform3D moveTrans = new Transform3D();
   moverNode.setTransform(moveTrans);
  
//compile & return
    objRoot.compile();
	return objRoot;
  }

//Move arm and any grasped blocks to newLoc
  public void MoveTo(Point3f NewLoc) {
 
    this.NewLoc = NewLoc;
 
	//move arm and arm.Grasped nodes onto newTG
	armBG.detach();
	moverNode.addChild(armBG);

	if (grasped) {
		int blockNum = arm.getGraspedNum();
		switch (blockNum) {
		  case 1:
	   		B1BG.detach();
	 		moverNode.addChild(B1BG);
			break;
		  case 2:
			B2BG.detach();
			moverNode.addChild(B2BG);
			break;
		  case 3:
			B3BG.detach();
 			moverNode.addChild(B3BG);
			break;
		  case 4:
			B4BG.detach();
			moverNode.addChild(B4BG);
			break;
		  case 5:
			B5BG.detach();
			moverNode.addChild(B5BG);			 
			break;
		  case 6:
			B6BG.detach();
			moverNode.addChild(B6BG);
			break;
		  case 7:
			B7BG.detach();
			moverNode.addChild(B7BG);			 
			break;
		  case 10:
			B10BG.detach();
			moverNode.addChild(B10BG);			 
			break;
		  default:
		    System.out.println("-->Nothing Grasped.");
		    break;
	  }
	}

	//start PositionInterpolator
//   createInterp();  //this doesn't really work yet... 

	//move arm and arm.Grasped nodes back onto objTrans
	Enumeration moverChildren = moverNode.getAllChildren();
	
	while (moverChildren.hasMoreElements()) {
	  BranchGroup tmpBG = (BranchGroup)moverChildren.nextElement();
//System.out.println("-->while(moverChildren...) " + tmpBG.getChild(0).getUserData());	  
	  if(tmpBG.getChild(0).getUserData().equals("arm")) {
	    ((Arm)tmpBG.getChild(0)).MoveTo(NewLoc);
  	    tmpBG.detach();
	    objTrans.addChild(tmpBG);
	  }
 	  else if(tmpBG.getChild(0).getUserData().equals("Interp")) {
  	     tmpBG.detach();
	  } else {
	  	NewLoc.z = arm.CurLoc.z - arm.size.z/2 - ((Block)tmpBG.getChild(0)).size.z;
	    ((Block)tmpBG.getChild(0)).MoveTo(NewLoc);
	    tmpBG.detach();
	    objTrans.addChild(tmpBG);
	  }
	}
  }
  
  private void createInterp() {

   Transform3D newAxis = new Transform3D();
   behaviorBranch = new BranchGroup();
   behaviorBranch.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
   behaviorBranch.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
   behaviorBranch.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
   Alpha alpha = new Alpha(1, 3000);
   Transform3D axisOfTranslation = new Transform3D();
	float knots[] = {0.0f, 1.0f};
	Point3f positions[] = {new Point3f(), new Point3f()};
								 
	Interp = new PositionPathInterpolator(alpha, moverNode, axisOfTranslation, knots, positions);
							 
   Interp.setUserData("Interp"); 
  	
	Point3f lateral_end = new Point3f();
	lateral_end.x = NewLoc.x - arm.CurLoc.x; //moving relative to the origin, not CurLoc
	lateral_end.y = NewLoc.y - arm.CurLoc.y;
	lateral_end.z = NewLoc.z - arm.CurLoc.z;	

	newAxis.set(new Vector3f(lateral_end.x, lateral_end.y, lateral_end.z));
   	Interp.setAxisOfTranslation(newAxis);
	
	Interp.setPosition(1, lateral_end);	

	BoundingSphere bounds =
	    new BoundingSphere(new Point3d(250.0,250.0,0.0), 1000.0);

    Interp.setSchedulingBounds(bounds);

  behaviorBranch.addChild(Interp);
  moverNode.addChild(behaviorBranch);							 					 
    
  }
  
//Grasp block directly below arm
  public void Grasp(String blockName) {  
    grasped = true;
//System.out.println("BW:Grasp()");	
	arm.Grasp(blockName); 
	}
  
//UnGrasp block held by arm
  public void UnGrasp() { 
    grasped = false;
//System.out.println("BW:UnGrasp()");	
  	arm.UnGrasp(); 
  }
 
//Class constructor  
  public BlocksWorld() {
	  setLayout(new BorderLayout());
      GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

      c = new Canvas3D(config);
      add("Center", c);

	// Create a simple scene and attach it to the virtual universe
	  SimpleUniverse u = new SimpleUniverse(c);
	  BranchGroup scene = createSceneGraph(u);
	  
        // This will move the ViewPlatform so the
        // objects in the scene can be viewed.
		Transform3D perspective = new Transform3D();
		Transform3D temp1, temp2, temp3;
		temp1 = new Transform3D();
		temp1.rotZ(Math.PI/4);
		temp2 = new Transform3D();
		temp2.rotY(Math.PI/4);
		temp3 = new Transform3D();
		temp3.rotX(Math.PI/2);
		
		//perspective.set(new Vector3f(5.0f, -2.0f, 2.0f));
		perspective.set(new Vector3f(2.0f, -5.0f, 2.0f));
		
		perspective.mul(temp2);
		perspective.mul(temp3);
		perspective.mul(temp1);
		
		u.getViewingPlatform().getViewPlatformTransform().setTransform(perspective);

	  u.addBranchGraph(scene);
  }
}
