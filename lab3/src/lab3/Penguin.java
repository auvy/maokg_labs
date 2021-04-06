package lab3;

import java.io.IOException;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Penguin extends Application {
    public static void main (String args[]) {
        launch(args);
    }

	double WIDTH = 854;
	double HEIGHT = 480;
    
	private static Color beakColor = Color.rgb(247,189,33);
	private static Color bodyColor = Color.rgb(23,151,229);
	private static Color bodyAccentColor = Color.rgb(207,228,249);
	private static Color wingColor = Color.rgb(4,75,178);
	private static Color eyeColor = Color.WHITE;
	private static Color irisColor = Color.BLACK;

    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Group root = new Group();
        // Scene (root, x, y)
        Scene scene = new Scene (root, WIDTH, HEIGHT);

        double centerx = WIDTH / 2;
        double centery = HEIGHT / 2;


        
        //head
        Ellipse head = new Ellipse(centerx, centery - 27 - 49, 85, 99);
        head.setFill(bodyColor);
        root.getChildren().add(head);
        
        
        Ellipse body = new Ellipse(centerx, centery, 103, 103);
        body.setFill(bodyColor);

        root.getChildren().add(body);
        
        Ellipse body2 = new Ellipse(centerx, centery + 10, 73, 93);
        body2.setFill(bodyAccentColor);
        root.getChildren().add(body2);    
        
        
        // beak
        Polygon beak = new Polygon(centerx, centery - 34,
                centerx - 46, centery - 82,
                centerx + 46, centery - 82);
        beak.setFill(beakColor);
        root.getChildren().add(beak);
        
        //eyes
        
        Ellipse white1 = new Ellipse(centerx - 24, centery - 102, 25, 37);
        white1.setFill(eyeColor);
        root.getChildren().add(white1);    
        
        Ellipse white2 = new Ellipse(centerx + 32, centery - 100, 31, 25);
        white2.setFill(eyeColor);
        root.getChildren().add(white2); 
        
        Ellipse iris1 = new Ellipse(centerx - 10, centery - 95, 8, 10);
        iris1.setFill(irisColor);
        root.getChildren().add(iris1); 
        
        Ellipse iris2 = new Ellipse(centerx + 10, centery - 95, 8, 10);
        iris2.setFill(irisColor);
        root.getChildren().add(iris2); 

        
        // wings
        

        //rotating the 2nd rectangle.   
        
        Ellipse wing1 = new Ellipse(centerx - 112, centery - 20, 17, 50);
        wing1.setFill(wingColor);
        wing1.getTransforms().add(new Rotate(45, centerx - 112, centery - 20));  
        root.getChildren().add(wing1); 
        
        Ellipse wing2 = new Ellipse(centerx + 112, centery - 20, 17, 50);
        wing2.setFill(wingColor);
        wing2.getTransforms().add(new Rotate(-45, centerx + 112, centery - 20));  
        root.getChildren().add(wing2); 
        
        
        
        //legs
        
        Rectangle leg12 = new Rectangle(centerx + 8, centery + 82, 96, 37);
        leg12.setFill(beakColor);
        leg12.setArcWidth(30); 
        leg12.setArcHeight(20); 
        root.getChildren().add(leg12); 
        
        Ellipse leg11 = new Ellipse(centerx + 56, centery + 92, 48, 27);
        leg11.setFill(beakColor);
        root.getChildren().add(leg11); 

        Rectangle leg22 = new Rectangle(centerx + 8 - 56 - 56, centery + 82, 96, 37);
        leg22.setFill(beakColor);
        leg22.setArcWidth(30); 
        leg22.setArcHeight(20); 
        root.getChildren().add(leg22); 
        
        Ellipse leg21 = new Ellipse(centerx - 56, centery + 92, 48, 27);
        leg21.setFill(beakColor);
        root.getChildren().add(leg21); 

        
        
        
//         Animation
        
        int cycleCount = 1;
        int time = 5000;
        
        Path penguinPath = new GetPathFromBMP().getPath("D:/eclipse_labs/ffs/sources/trajectory2.bmp");
		PathTransition pengTransition = new PathTransition();
		pengTransition.setDuration(Duration.millis(time));
		pengTransition.setPath(penguinPath);
		pengTransition.setNode(root);
        pengTransition.setCycleCount(cycleCount);
        pengTransition.setAutoReverse(true);        
        
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(1000), root);
        translateTransition1.setToX(0);
        translateTransition1.setToY(0);
        translateTransition1.setCycleCount(cycleCount);
        translateTransition1.setAutoReverse(true);
        
        
        
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1000), root);
        scaleTransition.setToX(0.25);
        scaleTransition.setToY(0.25);
        scaleTransition.setAutoReverse(true);
        

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1), root);
        rotateTransition.setByAngle(360f);
        rotateTransition.setCycleCount(cycleCount * time);
        rotateTransition.setAutoReverse(false);

        ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(time), root);
        scaleTransition1.setToX(1);
        scaleTransition1.setAutoReverse(true);


        SequentialTransition parallelTransition = new SequentialTransition();
        parallelTransition.getChildren().addAll(
        		scaleTransition,
        		pengTransition,
        		translateTransition1,
        		rotateTransition,
        		scaleTransition1
        );
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();
//         End of animation


        primaryStage.setTitle("penguin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
