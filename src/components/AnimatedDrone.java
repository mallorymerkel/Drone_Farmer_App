package components;

import java.io.FileInputStream;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimatedDrone implements AnimatedDroneInterface {
	private ImageView drone;
	private double speed_factor = 24; // 1/50 (drone speed (sec/cm)) * 30/25 (conversion (cm/pix)) * 1000 (milli/sec)
										// = 24 milli/pix

	public AnimatedDrone() {
		try {
			this.drone = new ImageView(new Image(new FileInputStream("resources/drone.png")));
			this.drone.setFitHeight(50);
			this.drone.setFitWidth(50);
			this.drone.setPickOnBounds(true);
			this.drone.setPreserveRatio(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AnimatedDrone(ImageView drone) {
		this.drone = drone;
	}

	public void gotoComponent(Component component) {

		double X = component.getLocationX() + component.getWidth() / 4;
		double Y = component.getLocationY() + component.getLength() / 4;

		// Translate to y coordinate (object corner + 1/4 of obj len)
		TranslateTransition goToY = new TranslateTransition();
		goToY.setByY(Y);
		goToY.setDuration(Duration.millis(Y * speed_factor));

		// Rotate 90 degrees
		RotateTransition turn1 = new RotateTransition();
		turn1.setByAngle(-90);
		turn1.setDuration(Duration.millis(500));

		// Translate to x coordinate (object corner + 1/4 of obj len)
		TranslateTransition goToX = new TranslateTransition();
		goToX.setByX(X);
		goToX.setDuration(Duration.millis(X * speed_factor));

		// Rotate 360 degrees
		RotateTransition prettySpin = new RotateTransition();
		prettySpin.setByAngle(-360);
		prettySpin.setDuration(Duration.millis(2000));

		// Pause
		RotateTransition wait = new RotateTransition();
		wait.setByAngle(0);
		wait.setDuration(Duration.millis(1000));

		// Rotate 180 degrees
		RotateTransition turn2 = new RotateTransition();
		turn2.setByAngle(-180);
		turn2.setDuration(Duration.millis(1000));

		// X back distance
		TranslateTransition returnX = new TranslateTransition();
		returnX.setToX(0);
		returnX.setDuration(Duration.millis(X * speed_factor));

		// Rotate 90 degrees
		RotateTransition turn3 = new RotateTransition();
		turn3.setByAngle(90);
		turn3.setDuration(Duration.millis(500));

		// Y back distance
		TranslateTransition returnY = new TranslateTransition();
		returnY.setToY(0);
		returnY.setDuration(Duration.millis(Y * speed_factor));

		// Rotate 180 degrees
		RotateTransition turn4 = new RotateTransition();
		turn4.setByAngle(180);
		turn4.setDuration(Duration.millis(1000));

		// play
		SequentialTransition seq = new SequentialTransition(goToY, turn1, goToX, prettySpin, wait, turn2, returnX,
				turn3, returnY, turn4);
		seq.setNode(drone);
		seq.play();

	}

	public void scanFarm() {
		SequentialTransition seq = new SequentialTransition();

		for (int i = 0; i < 7; i++) {
			TranslateTransition down = new TranslateTransition();
			down.setByY(550);
			down.setDuration(Duration.millis(550 * speed_factor));
			seq.getChildren().add(down);

			RotateTransition turn1 = new RotateTransition();
			turn1.setByAngle(-90);
			turn1.setDuration(Duration.millis(250));
			seq.getChildren().add(turn1);

			TranslateTransition across1 = new TranslateTransition();
			across1.setByX(50);
			across1.setDuration(Duration.millis(50 * speed_factor));
			seq.getChildren().add(across1);

			RotateTransition turn2 = new RotateTransition();
			turn2.setByAngle(-90);
			turn2.setDuration(Duration.millis(250));
			seq.getChildren().add(turn2);

			TranslateTransition up = new TranslateTransition();
			up.setByY(-550);
			up.setDuration(Duration.millis(550 * speed_factor));
			seq.getChildren().add(up);

			RotateTransition turn3 = new RotateTransition();
			turn3.setByAngle(90);
			turn3.setDuration(Duration.millis(250));
			seq.getChildren().add(turn3);

			int acrossVal = 50;
			if (i == 6) {
				acrossVal = 200; // make edges line up
			}
			TranslateTransition across2 = new TranslateTransition();
			across2.setByX(acrossVal);
			across2.setDuration(Duration.millis(acrossVal * speed_factor));
			seq.getChildren().add(across2);

			RotateTransition turn4 = new RotateTransition();
			turn4.setByAngle(90);
			turn4.setDuration(Duration.millis(250));
			seq.getChildren().add(turn4);
		}

		TranslateTransition downFinal = new TranslateTransition();
		downFinal.setByY(550);
		downFinal.setDuration(Duration.millis(550 * speed_factor));
		seq.getChildren().add(downFinal);

		RotateTransition turnFinal1 = new RotateTransition();
		turnFinal1.setByAngle(90);
		turnFinal1.setDuration(Duration.millis(250));
		seq.getChildren().add(turnFinal1);

		TranslateTransition acrossFinal = new TranslateTransition();
		acrossFinal.setToX(0);
		acrossFinal.setDuration(Duration.millis(750 * speed_factor));
		seq.getChildren().add(acrossFinal);

		RotateTransition turnFinal2 = new RotateTransition();
		turnFinal2.setByAngle(90);
		turnFinal2.setDuration(Duration.millis(250));
		seq.getChildren().add(turnFinal2);

		TranslateTransition upFinal = new TranslateTransition();
		upFinal.setToY(0);
		upFinal.setDuration(Duration.millis(550 * speed_factor));
		seq.getChildren().add(upFinal);

		RotateTransition turnFinal3 = new RotateTransition();
		turnFinal3.setByAngle(180);
		turnFinal3.setDuration(Duration.millis(250));
		seq.getChildren().add(turnFinal3);

		seq.setNode(drone);
		seq.play();
	}

}
