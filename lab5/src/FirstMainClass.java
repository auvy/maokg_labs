package lab5;

import com.sun.j3d.utils.universe.*;

import java.awt.Color;
import javax.media.j3d.*;
import javax.media.j3d.Material;
import javax.vecmath.*;
import javax.media.j3d.Background;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.loaders.lw3d.Lw3dLoader;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import javax.swing.JFrame;

public class FirstMainClass extends JFrame {
	static SimpleUniverse universe;
	static Scene scene;
	static Map<String, Shape3D> nameMap;
	static BranchGroup root;
	static Canvas3D canvas;


	static String assetPath = "C:\\Users\\datru\\Desktop\\study2021\\maokg\\lab5\\res\\";
	static String modelName = "camaro2.obj";
	static String bgName = "garage.jpg";
	
	static TransformGroup wholeCar;
	static Transform3D transform3D;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public FirstMainClass() throws IOException {
		configureWindow();
		configureCanvas();
		configureUniverse();
		addModelToUniverse();
		setCarElementsList();
		addAppearance();
		addImageBackground();
		addLightToUniverse();
		addOtherLight();
		ChangeViewAngle();
		root.compile();
		universe.addBranchGraph(root);
	}

	private void configureWindow() {
		setTitle("Car Animation Example");
		setSize(1280, 640);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void configureCanvas() {
		canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		System.out.printf("heigth: %s\n", SimpleUniverse.getPreferredConfiguration().getBounds().getHeight());
		canvas.setDoubleBufferEnable(true);
		canvas.setSize(screenSize);
		getContentPane().add(canvas, BorderLayout.CENTER);
	}

	private void configureUniverse() {
		root = new BranchGroup();
		universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
	}

	private void addModelToUniverse() throws IOException {
		scene = getSceneFromFile(assetPath + modelName);
		root = scene.getSceneGroup();
	}

	private void addLightToUniverse() {
		Bounds bounds = new BoundingSphere();
		Color3f color = new Color3f(96 / 255f, 96 / 255f, 96 / 255f);
		Vector3f lightdirection = new Vector3f(0f, -1f, 0f);
		DirectionalLight dirlight = new DirectionalLight(color, lightdirection);
		dirlight.setInfluencingBounds(bounds);
		root.addChild(dirlight);
	}

	private void printModelElementsList(Map<String, Shape3D> nameMap) {
		for (String name : nameMap.keySet()) {
			System.out.printf("Name: %s\n", name);
		}
	}

	private void setCarElementsList() {
		nameMap = scene.getNamedObjects();

		// Print elements of your model:
		printModelElementsList(nameMap);

		wholeCar = new TransformGroup();
		transform3D = new Transform3D();
		transform3D.setScale(new Vector3d(1, 1, 1));
		wholeCar.setTransform(transform3D);

		
		for (String name : nameMap.keySet()) {
			root.removeChild(nameMap.get(name));
			wholeCar.addChild(nameMap.get(name));
			wholeCar.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		}

		root.addChild(wholeCar);
	}

	Texture getTexture(String path) {
		TextureLoader textureLoader = new TextureLoader(path, canvas);
		Texture texture = textureLoader.getTexture();
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(1.0f, 1.0f, 0.0f, 0.0f));
		return texture;
	}

	Material getMaterial() {
		Material material = new Material();
		
		
	    Color3f ambient = new Color3f(1.0f, 1.0f, 1.0f);
	    Color3f diffuse = new Color3f(1.0f, 1.0f, 1.0f);
	    Color3f specular = new Color3f(1.0f, 1.0f, 1.0f);
		

//        material.setAmbientColor(new Color3f(new Color(221, 221, 221)));
//        material.setDiffuseColor(new Color3f(new Color(200, 200, 200)));
//        material.setSpecularColor(new Color3f(new Color(200, 200, 200)));
	    
		material.setAmbientColor(ambient);
		material.setDiffuseColor(diffuse);
		material.setSpecularColor(specular);
		material.setShininess(1f);
		material.setLightingEnable(true);
		return material;
	}

	private void addAppearance() throws IOException {
		for (String name : nameMap.keySet()) {
			Shape3D car = nameMap.get(name);
			
			Appearance carAppearance = new Appearance();
			carAppearance.setTexture(getTexture(assetPath + "png//" + name + ".png"));
			TextureAttributes texAttr = new TextureAttributes();
			texAttr.setTextureMode(TextureAttributes.COMBINE);
			carAppearance.setTextureAttributes(texAttr);
			carAppearance.setMaterial(getMaterial());
			
			car.setAppearance(carAppearance);
		}

	}

	private void addColorBackground() {
		Background background = new Background(new Color3f(Color.CYAN));
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		background.setApplicationBounds(bounds);
		root.addChild(background);
	}

	private void addImageBackground() {
		TextureLoader t = new TextureLoader(assetPath + bgName, canvas);
		Background background = new Background(t.getImage());
		background.setImageScaleMode(Background.SCALE_FIT_ALL);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		background.setApplicationBounds(bounds);
		root.addChild(background);
	}

	private void ChangeViewAngle() {
		ViewingPlatform vp = universe.getViewingPlatform();
		TransformGroup vpGroup = vp.getMultiTransformGroup().getTransformGroup(0);
		Transform3D vpTranslation = new Transform3D();
		Vector3f translationVector = new Vector3f(0.0F, 0.0F, 6F);
		vpTranslation.setTranslation(translationVector);
		vpGroup.setTransform(vpTranslation);
	}

	private void addOtherLight() {
		Color3f directionalLightColor = new Color3f(Color.BLACK);
		Color3f ambientLightColor = new Color3f(Color.WHITE);
		Vector3f lightDirection = new Vector3f(-1F, -1F, -1F);

		AmbientLight ambientLight = new AmbientLight(ambientLightColor);
		DirectionalLight directionalLight = new DirectionalLight(directionalLightColor, lightDirection);

		Bounds influenceRegion = new BoundingSphere();

		ambientLight.setInfluencingBounds(influenceRegion);
		directionalLight.setInfluencingBounds(influenceRegion);
		root.addChild(ambientLight);
		root.addChild(directionalLight);
	}

	public static Scene getSceneFromFile(String location) throws IOException {
		ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
		file.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
		return file.load(new FileReader(location));
	}

	// Not always works
	public static Scene getSceneFromLwoFile(String location) throws IOException {
		Lw3dLoader loader = new Lw3dLoader();
		return loader.load(new FileReader(location));
	}

	public static void main(String[] args) {
		try {
			FirstMainClass window = new FirstMainClass();
			AnimateCar carMovement = new AnimateCar(wholeCar, transform3D, window);
			window.addKeyListener(carMovement);
			window.setVisible(true);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
