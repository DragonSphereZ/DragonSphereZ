package ud.bi0.dragonSphereZ.effect.other;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;
import ud.bi0.dragonSphereZ.util.StringParser;
import ud.bi0.dragonSphereZ.util.VectorUtils;

public class Text extends ParticleEffect {
	protected Font font;
	protected String text;
	protected boolean autoFace;
	protected Vector3d axis;
	protected int pixelStepX;	//Default 1
	protected int pixelStepY;	//Default 1
	protected float scaleSize;	//Default 5
	//float size = (float) 1 / scaleSize;
	boolean invert;
	int clr;
	protected BufferedImage image = null;
	Vector3d v = new Vector3d();
	Vector3d v2;
	double x2, z2, cos, sin, angle;
	int y;
	int x;
	boolean realTime = false; //For changing text(not implimented yet)

	public Text(
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
		int pixelStepX,
		int pixelStepY,
		float scaleSize,
		Font font,
		String text,
		boolean invert,
		boolean autoFace)
	{
		super(particleCount, particle, dataMat, dataID, speed, offset, idName, center, players, rainbowMode, visibleRange, displacement, delayTick, pulseTick);
		init(axis, pixelStepX, pixelStepY, scaleSize, font, text, invert, autoFace);

	}
	public Text(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
		init(new Vector3d(0,0,0), 1, 1, 5, new Font("Tahoma", Font.PLAIN, 16), "Boop", false, false);
	}
	
	public void init(Vector3d axis, int pixelStepX, int pixelStepY, float scaleSize, Font font, String text, boolean invert, boolean autoFace) {
		this.axis = axis;
		this.pixelStepX = pixelStepX;
		this.pixelStepY = pixelStepY;
		this.scaleSize = scaleSize;
		this.font = font;
		this.text = text;
		this.invert = invert;
		this.autoFace = autoFace;
		
	}
	
	@Override
	public void onRun() {
		center.update();
        try {
            if (image == null || realTime) {	//For changing text(not implemented yet)
                image = StringParser.stringToBufferedImage(font, text);
            }
            if (rainbowMode)
				offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
            for (int y = 0; y < image.getHeight(); y += pixelStepY) {
                for (int x = 0; x < image.getWidth(); x += pixelStepX) {
                    //Invert the particles to create an outline
                	clr = image.getRGB(x, y);
                    if (!invert && Color.black.getRGB() != clr) {
                        continue;
                    } else if (invert && Color.black.getRGB() == clr) {
                        continue;
                    }
                    v = new Vector3d((float) image.getWidth() / 2 - x, (float) image.getHeight() / 2 - y, 0).mul(1 / scaleSize);
                    
                    //Bukkit.getServer().broadcastMessage("[v1] -->" + v + "<--");
                    v = VectorUtils.rotateVector(v, axis.getX(), axis.getY(), axis.getZ());
                    //Bukkit.getServer().broadcastMessage("[v3] -->" + v + "<--");
                    
                    if (center.isDynamic() && autoFace == true) {
                    	angle = GenericMath.wrapAngleRad(-center.getYaw() * TrigMath.DEG_TO_RAD);
                        cos = TrigMath.cos(angle);
                        sin = TrigMath.sin(angle);
                        x2 = v.getX() * cos + v.getZ() * sin;
                        z2 = v.getX() * -sin + v.getZ() * cos;
                        v2 = new Vector3d(x2, v.getY(), z2);
                    	//VectorUtils.rotateAroundAxisY(v, -center.getYaw() * TrigMath.DEG_TO_RAD);	//Supposed to auto rotate the text
                        Text.this.display(v2);
                    }else{
                    	Text.this.display(v);
                    }
                    //Bukkit.getServer().broadcastMessage("[rotate] " + center.getYaw());	//TODO this returns 0
                    
                }
            }
        } catch (Exception ex) {
            // Cancel effect on bad characters
        	effectManager.stopEffect(idName);
        }
	}
}