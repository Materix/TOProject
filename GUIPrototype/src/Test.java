import java.awt.Canvas;
import java.util.Random;

import com.jme3.app.SimpleApplication;


import com.jme3.asset.plugins.FileLocator;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
 
/** Sample 1 - how to get started with the most simple JME 3 application.
 * Display a blue 3D cube and view from all sides by
 * moving the mouse and pressing the WASD keys. */
public class Test extends SimpleApplication {
 
	public Test() {
		super();
		createCanvas();
		startCanvas(true);
	}
	
	public Canvas getCanvas() {
		return ((JmeCanvasContext)this.getContext()).getCanvas();
	}
 
	@Override
	public void simpleInitApp() {
		this.setDisplayStatView(false);
		this.setDisplayFps(false);
		this.settings.setBitsPerPixel(32);
		this.inputManager.setCursorVisible(true);
		this.flyCam.setEnabled(false);
		this.cam.setLocation(new Vector3f(0, 0, 7));
		this.cam.lookAt(new Vector3f(6, 0, 0), Vector3f.UNIT_Z);
		Spatial[] dices = new Spatial[5];
		this.assetManager.registerLocator("./assets", FileLocator.class);
		Random rand=  new Random();
		for (int i = 0; i < 5; i++) {
			dices[i] = this.assetManager.loadModel("Model/dice.j3o");
			dices[i].rotate(0, 0, rand.nextFloat() * 3.14f);
			dices[i].setLocalTranslation(6, -4 + 2 * i, 0);
			rootNode.attachChild(dices[i]);
		}
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(6f, 0f, -7f).normalizeLocal());
		rootNode.addLight(sun);
	}
}