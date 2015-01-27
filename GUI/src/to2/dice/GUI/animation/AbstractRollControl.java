package to2.dice.GUI.animation;

import com.jme3.math.Quaternion;
import com.jme3.scene.control.AbstractControl;

public abstract class AbstractRollControl extends AbstractControl {
	protected int number;
	protected boolean startRoll;
	protected Quaternion targetRotate;
	
	public void setNumberToRoll(int number) {
		synchronized (spatial) {
			this.number = number;
			setEnabled(true);
			startRoll = true;
		}
	}
}
