import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class ClosedBox extends Shape3D {

  public ClosedBox()
  {
    this(1.0f, 1.0f, 1.0f, null);
  }

  public ClosedBox(float xdim, float ydim, float zdim, Appearance ap) {

	Point3f p1 = new Point3f(-xdim, -ydim, zdim);
	Point3f p2 = new Point3f(-xdim, ydim, zdim);
	Point3f p3 = new Point3f(xdim, ydim, zdim);
	Point3f p4 = new Point3f(xdim, -ydim, zdim);
	
	Point3f p5 = new Point3f(-xdim,-ydim,-zdim);
	Point3f p6 = new Point3f(-xdim, ydim,-zdim);
	Point3f p7 = new Point3f(xdim, ydim,-zdim);
	Point3f p8 = new Point3f(xdim, -ydim,-zdim);
	
	Point3f[] verts = { 
	   p1, p4, p3, p2, //top face
	   p7, p6, p2, p3, //back face
	   p5, p8, p4, p1, //front face
	   p6, p5, p1, p2, //left face
	   p8, p7, p3, p4, //right face
	   p6, p7, p8, p5 //bottom face
	 };
	 
	 QuadArray box = new QuadArray(24, QuadArray.COORDINATES | QuadArray.NORMALS);
		
	 box.setCoordinates(0, verts);

	 Vector3f normal = new Vector3f();
	 Vector3f v1 = new Vector3f();
	 Vector3f v2 = new Vector3f();
	 Point3f [] pts = new Point3f[4];

	 for (int i = 0; i < 4; i++) pts[i] = new Point3f();

	 for (int face = 0; face < 6; face++) {
		box.getCoordinates(face*4, pts);
	    v1.sub(pts[1], pts[0]);
	    v2.sub(pts[2], pts[0]);
	    normal.cross(v1, v2);
	    normal.normalize();
	    for (int i = 0; i < 4; i++) {
		  box.setNormal((face * 4 + i), normal);
	    }
	 }

	this.setGeometry(box);
    
    if (ap == null){
      this.setAppearance(new Appearance());
    }
    else this.setAppearance(ap);
	
	this.setCapability(ALLOW_APPEARANCE_READ);
	this.setCapability(ALLOW_APPEARANCE_WRITE);
	this.setCapability(ALLOW_GEOMETRY_READ);
  }
}