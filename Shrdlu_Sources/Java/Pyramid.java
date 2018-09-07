import javax.media.j3d.*;
import javax.vecmath.*;

public class Pyramid extends Shape3D {

	public Pyramid() {
	  this(1.0f, 1.0f, 1.0f, new Appearance());
	}
	
	public Pyramid(float xdim, float ydim, float zdim, Appearance app) {

     Point3f p1 = new Point3f(-xdim, -ydim, -zdim);
	 Point3f p2 = new Point3f(-xdim, ydim, -zdim);
	 Point3f p3 = new Point3f(xdim, ydim, -zdim);
	 Point3f p4 = new Point3f(xdim, -ydim, -zdim);
	 Point3f top = new Point3f(0,0,zdim); 

	 Point3f[] verts = { 
	   p3, p2, top, top, //back face
	   p1, p4, top, top, //front face
	   p2, p1, top, top, //left face
	   p4, p3, top, top, //right face
	   p1, p2, p3, p4 //bottom face
	 };
	 
	 QuadArray pyra = new QuadArray(20, QuadArray.COORDINATES | QuadArray.NORMALS);
		
	 pyra.setCoordinates(0, verts);

	 Vector3f normal = new Vector3f();
	 Vector3f v1 = new Vector3f();
	 Vector3f v2 = new Vector3f();
	 Point3f [] pts = new Point3f[4]  ;
	 for (int i = 0; i < 4; i++) pts[i] = new Point3f();

	 for (int face = 0; face < 5; face++) {
		pyra.getCoordinates(face*4, pts);
	    v1.sub(pts[1], pts[0]);
	    v2.sub(pts[2], pts[0]);
	    normal.cross(v1, v2);
	    normal.normalize();
	    for (int i = 0; i < 4; i++) {
		  pyra.setNormal((face * 4 + i), normal);
	    }
	 }

	 this.setGeometry(pyra);
	 this.setAppearance(app);
	 
	 this.setCapability(ALLOW_APPEARANCE_READ);
	 this.setCapability(ALLOW_APPEARANCE_WRITE);
	 this.setCapability(ALLOW_GEOMETRY_READ);
	}
}
