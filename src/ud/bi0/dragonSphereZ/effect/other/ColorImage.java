package ud.bi0.dragonSphereZ.effect.other;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector3d;

import ch.njol.skript.Skript;
import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.math.shape.Gif;
import ud.bi0.dragonSphereZ.math.shape.Image;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;
import ud.bi0.dragonSphereZ.util.VectorUtils;

public class ColorImage extends ParticleEffect {

	protected Plane plane;
	protected File file;
	protected boolean enableRotation;
	protected Vector3d axis;
	protected int pixelStepX;
	protected int pixelStepY;
	protected float scaleSize;
    
	float size = (float) 1 / scaleSize;
	float angularVelocityX = (float) (TrigMath.PI / 200);
	float angularVelocityY = (float) (TrigMath.PI / 170);
	float angularVelocityZ = (float) (TrigMath.PI / 155);
    
    
	protected BufferedImage image = null;
	Image img;
	Gif gif;
	protected boolean isGif = false;
	protected File gifFile = null;
	protected int step = 0;
	protected int delay = 0;
    
	protected float rotationStep = 0;
	boolean invert = false;
	int clr;
	Vector3d v = new Vector3d();
	int y;
	int x;
	float xRot = 0;			//Holds the current rotation angle for the random rotation.
	float yRot = 0;
	float zRot = 0;
	int r;
	int g;
	int b;

	public ColorImage(
		//super
		int particleCount,
		String particle,
		Material dataMat,
		byte dataID,
		float speed,
		Vector3d offset,
		String idName,
		DynamicLocation center,
		List<Player> players,
		boolean rainbowMode,
		double visibleRange,
		Vector3d displacement,
		long delayTick,
		long pulseTick,
		//this
		Vector3d axis,
		boolean enableRotation,
		Plane plane,
		File file,
		int pixelStepX,
		int pixelStepY,
		float scaleSize)
	{
		super(particleCount, particle, dataMat, dataID, speed, offset, idName, center, players, rainbowMode, visibleRange, displacement, delayTick, pulseTick);
		init(axis, enableRotation, plane, file, pixelStepX, pixelStepY, scaleSize);

	}
	public ColorImage(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
		init(new Vector3d(0,0,1), false, ColorImage.Plane.XY, null, 10, 10, 40);
	}
	
	public void init(Vector3d axis, boolean enableRotation, Plane plane, File file, int pixelStepX, int pixelStepY, float scaleSize) {
		this.axis = axis.normalize();
		this.enableRotation = enableRotation;
		this.plane = plane;
		this.file = file;
		this.pixelStepX = pixelStepX;
		this.pixelStepY = pixelStepY;
		this.scaleSize = scaleSize;
		loadFile(file);
		
		
		
	}


	public void loadFile(File file) {
		isGif = file.getName().endsWith(".gif");
        try {
            if (file.getName().endsWith(".gif")){
            	
            	//gifFile = file;
            	//-----------------------
            	ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
                ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
                ImageInputStream in = ImageIO.createImageInputStream(file);
                reader.setInput(in);
                for (int i = 0, count = reader.getNumImages(true); i < count; i++) {
                    image = reader.read(i);
                    images.add(image);
                }
            	gif = new Gif(images);
            	reader.dispose();
                in.close();
            }else{
            	image = ImageIO.read(file);
            	img = new Image(image);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            image = null;
        }
    }
	
	@Override
	public void onRun() {
		if (!center.isDynamic() || !center.hasMoved()) {
			
			if (image == null) {
				effectManager.stopEffect(idName);
	        	Skript.warning("[DragonSphereZ] Error: The image failed to load, try another? :c");
	        	return;
	        }
	        if (isGif) {
	            if (delay == 5) {
	                step++;
	                delay = 0;
	            }
	            delay++;
	        }
	        
	        center.update();
	        
	        
	        
	        for (y = 0; y < image.getHeight(); y += pixelStepY) {
	            for (x = 0; x < image.getWidth(); x += pixelStepX) {
	            	clr = image.getRGB(x, y);
	            	//Removes the image mask/transparency
	                if (!invert && (clr == 0 || clr == 16777215)) {
	                    continue;
	                } else if (invert &&  (clr != 0 || clr == 16777215)) {
	                    continue;
	                }
	                //Bukkit.getServer().broadcastMessage("[mask test] pixel -->" + clr + "<--");  //TODO Keep this line for later
	                
	                v = new Vector3d((float) image.getWidth() / 2 - x, (float) image.getHeight() / 2 - y, 0).mul(size);
	                
	                VectorUtils.rotateVector(v, axis.getX(), axis.getY(), axis.getZ());
	                if (enableRotation) {
	                    //v = Quaterniond.fromAxesAnglesDeg(xRot, yRot, zRot).rotate(v); //Rotates the vector.
						//xRot = 	GenericMath.wrapAngleDeg(xRot + stepXRot);	//Calculates the next rotation angle.
						//yRot = GenericMath.wrapAngleDeg(yRot + stepYRot);
						//zRot = GenericMath.wrapAngleDeg(zRot + stepZRot);
	                    switch (plane) {
	                        case X:
	                        	xRot = angularVelocityX * rotationStep;
	                            break;
	                        case Y:
	                        	yRot = angularVelocityY * rotationStep;
	                            break;
	                        case Z:
	                        	zRot = angularVelocityZ * rotationStep;
	                            break;
	                        case XY:
	                        	xRot = angularVelocityX * rotationStep;
	                        	yRot = angularVelocityY * rotationStep;
	                            break;
	                        case XZ:
	                        	xRot = angularVelocityX * rotationStep;
	                        	zRot = angularVelocityZ * rotationStep;
	                            break;
	                        case XYZ:
	                        	xRot = angularVelocityX * rotationStep;
	                        	yRot = angularVelocityY * rotationStep;
	                        	zRot = angularVelocityZ * rotationStep;
	                            break;
	                        case YZ:
	                        	yRot = angularVelocityY * rotationStep;
	                        	zRot = angularVelocityZ * rotationStep;
	                            break;
	                    }
	                    VectorUtils.rotateVector(v, xRot, yRot, zRot);
	                }
	                r = (new Color(image.getRGB(x, y))).getRed();
	                g = (new Color(image.getRGB(x, y))).getGreen();
	                b = (new Color(image.getRGB(x, y))).getBlue();
	                ParticleEffectUtils.valueOf(particle).display(center, visibleRange, players, r, g, b);
	            }
	        }
	        rotationStep++;
		} else center.update();
	}
	
	private BufferedImage getImg(int inStep) throws IOException {
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
        ImageInputStream in = ImageIO.createImageInputStream(gifFile);
        reader.setInput(in);
        for (int i = 0, count = reader.getNumImages(true); i < count; i++) {
            BufferedImage image = reader.read(i);
            images.add(image);
        }
        if (step >= reader.getNumImages(true)) {
            step = 0;
            return images.get(inStep - 1);
        }
        reader.dispose();
        in.close();
        return images.get(inStep);
    }
	public enum Plane {
		X, Y, Z, XY, XZ, XYZ, YZ;
	}
}