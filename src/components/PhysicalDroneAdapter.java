package components;

import java.io.IOException;
import main.java.surelyhuman.jdrone.control.physical.tello.TelloDrone;

public class PhysicalDroneAdapter implements AnimatedDroneInterface {

	private double CM_PER_PIX = 1.2;
	private int drone_height = 50;

	public PhysicalDroneAdapter() {

	}

	public void setDroneHeight(int max_height) {
		this.drone_height = (int) (max_height * CM_PER_PIX) + 10;
	}

	public void gotoComponent(Component component) {
		try {

			int X = (int) ((component.getLocationX() + component.getWidth() / 4) * CM_PER_PIX);
			int Y = (int) ((component.getLocationY() + component.getLength() / 4) * CM_PER_PIX);

			TelloDrone tello = new TelloDrone();
			tello.activateSDK();
			tello.streamOn();
			tello.streamViewOn();
			tello.takeoff();
			tello.increaseAltitude(drone_height);
			tello.setSpeed(50);
			tello.flyForward(Y);
			tello.turnCCW(90);
			tello.flyForward(X);
			tello.turnCCW(360);
			tello.hoverInPlace(3);
			tello.turnCCW(180);
			tello.flyForward(X);
			tello.turnCW(90);
			tello.flyForward(Y);
			tello.turnCW(180);

			tello.land();
			tello.streamOff();
			tello.streamViewOff();
			tello.end();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void scanFarm() {
		try {

			int len = (int) (550 * CM_PER_PIX);
			int across = (int) (50 * CM_PER_PIX);
			int acrossFinal = (int) (750 * CM_PER_PIX);

			TelloDrone tello = new TelloDrone();
			tello.activateSDK();
			tello.streamOn();
			tello.streamViewOn();
			tello.hoverInPlace(3);
			tello.takeoff();
			tello.increaseAltitude(drone_height);
			tello.setSpeed(50);

			for (int i = 0; i < 7; i++) {
				tello.flyForward(len);
				tello.turnCCW(90);
				tello.flyForward(across);
				tello.turnCCW(90);
				tello.flyForward(len);
				tello.turnCW(90);
				if (i == 6)
					tello.flyForward(across);
				else
					tello.flyForward(2 * across); // account for end of farm
				tello.turnCW(90);
			}

			tello.flyForward(len);
			tello.turnCW(90);
			tello.flyForward(acrossFinal);
			tello.turnCW(90);
			tello.flyForward(len);
			tello.turnCW(180);

			tello.land();
			tello.streamOff();
			tello.streamViewOff();
			tello.end();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
