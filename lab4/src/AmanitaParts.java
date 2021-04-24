package lab4;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;

import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;

public class AmanitaParts 
{	
    public static String fullPath = "C:/Users/datru/Desktop/study2021/maokg/lab4/assets/";
	
	public static int getPrimFlags()
	{
		return Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
	}
	
	public static Cone getCone(float height, float radius, boolean top) 
	{   
		int primflags = getPrimFlags();
		return top ? new Cone(radius, height, primflags, getAppearence("top1.jpg"))
				: new Cone(radius, height, primflags, getAppearence("leg.jpg"));
	} 
	
	public static Cylinder getCylinder(float radius, float height, boolean top) 
	{
		int primflags = getPrimFlags();
		return top ? new Cylinder(radius, height, primflags, getAppearence("top1.jpg"))
				: new Cylinder(radius, height, primflags, getAppearence("leg.jpg"));
	}

	public static Sphere getSphere(float radius, boolean top) 
	{
		int primflags = getPrimFlags();
		return top ? new Sphere(radius, primflags, getAppearence("top1.jpg")) 
				: new Sphere(radius, primflags, getAppearence("leg.jpg")); 
	}
	
	public static Box getBox(float l, float w, float h, boolean top)
	{
		int primflags = getPrimFlags();
		return top ? new Box(l,w,h, primflags, getAppearence("mushroom_hat_hq.jpg")) 
				: new Box(l,w,h, primflags, getAppearence("mushroom_stem_hq.jpg")); 
	}
	
	public static Appearance getAppearence(String textureName) {
        TextureLoader loader =  new TextureLoader(fullPath + textureName, new Container());

        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);
        
        Color3f emissive = new Color3f(new Color(255, 255, 255));
        Color3f ambient = new Color3f(new Color(0, 0, 0));
        Color3f diffuse = new Color3f(new Color(0, 0, 0));
        Color3f specular = new Color3f(0.0f, 0.0f, 0.0f);
        
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;

    }
}
