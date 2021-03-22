package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import javafx.geometry.Point2D;


public class Main extends Application{
	
	
	double WIDTH = 640;
	double HEIGHT = 480;
		
		
	public static void main (String args[]) 
	{
//		launch(args); // main method
	}
	
	public double convertDegToRad(double deg)
	{
		return deg * Math.PI / 180;
	}
	
	
	
	public void paintSunRays(Group root, double radius, Point2D center, double number, Color col)
	{
		//rays are icosceles triangles
		Polygon ray;
		//allows for spacing between rays; random coefficient
		double k1 = 0.375;
		double k2 = 1 - k1;

		for(int i = 0; i < number; i++)
		{
			ray = new Polygon();
			double angle1 = convertDegToRad(360 / number * (i - k1) + 90);
			double angle2 = convertDegToRad(360 / number * (i + 1 - k2) + 90);
			double angle3 = convertDegToRad(360 / number * i + 90);

	        ray.getPoints().addAll(new Double[]{        
	        		   radius * Math.cos(angle1) + center.getX(), radius * Math.sin(angle1) + center.getY(), //right point
	        		   radius * Math.cos(angle2) + center.getX(), radius * Math.sin(angle2) + center.getY(), //left point
	        		   (radius * 2) * Math.cos(angle3) + center.getX(), (radius * 2) * Math.sin(angle3) + center.getY() //top point
	        });
	        
	        ray.setFill(col);
	        root.getChildren().add(ray);
		}
		
	}
	
	
	public void paintNavalSunRays(Group root, double radius, Point2D center, double number, Color col)
	{
		//rays are right triangles
		Polygon ray;
		double k1 = 0.375;
		double k2 = 1 - k1;

		double radius2 = radius - 5 / 2; //maximum length of ray
		
		for(int i = 0; i < number; i++)
		{
			ray = new Polygon();
			double angle2 = convertDegToRad(360 / number * (i + 1 - k2) + 90 - 5 / 2);
			double angle3 = convertDegToRad(360 / number * i + 90);

	        ray.getPoints().addAll(new Double[]{        
	        		   radius2 * Math.cos(angle3) + center.getX(), radius2 * Math.sin(angle3) + center.getY(), 
	        		   radius2 * Math.cos(angle2) + center.getX(), radius2 * Math.sin(angle2) + center.getY(), 
	        		   (radius2 * 2 - 5 * 2) * Math.cos(angle3) + center.getX(), (radius2 * 2 - 5 * 2) * Math.sin(angle3) + center.getY()
	        });
	        
	        ray.setFill(col);
	        root.getChildren().add(ray);
		}
		
	}
	

	public void paintRandomStars(Group root, double radius, Point2D center, Color col, int number)
	{
		Polygon star;
		Point2D starCenter;
		double x = 0;
		double y = 0;
		double starR = 0;
		
		double randR = 0;
		double randP = 0;
		
		double lower = radius * 2; //zone for coordinates
		double upper = radius * 3.5;
		
		for(int i = 0; i < number; i++)
		{
			randR = Math.random() * (upper - lower) + lower; //random radius from lower to upper
			randP = convertDegToRad(Math.random() * 360);
			
			x = randR * Math.cos(randP) + center.getX();
			y = randR * Math.sin(randP) + center.getY();
			
			starCenter = new Point2D(x, y);
			
			starR = Math.random() * 5 * 2 + 5 * 0.125;

			star = getFourPointStar(starR, starCenter);

	        star.setFill(col);
	        root.getChildren().add(star);
		}
	}
	
	public Polygon getFourPointStar(double radius, Point2D center)
	{
		double k = radius / 9;
		Polygon star = new Polygon();
        star.getPoints().addAll(new Double[]{        
     		    center.getX(), center.getY() - radius, //north x,y
     		    center.getX() + k, center.getY() - k,  //north east
     		    center.getX() + radius, center.getY(), //east
     		    center.getX() + k, center.getY() + k,  //south east
        		center.getX(), center.getY() + radius, //south
        		center.getX() - k, center.getY() + k,  //south west
        		center.getX() - radius, center.getY(), //west
        		center.getX() - k, center.getY() - k   //south west
        });
        return star;
	}
	
	public void paintEye(Group root, double radius, Point2D center, Color sun, Color bg)
	{
	      double edgeLeftX = center.getX() - radius + 5 * 2;
	      double edgeLeftY = center.getY();

	      double edgeRightX = center.getX() + radius - 5 * 2;
	      double edgeRightY = center.getY();
	      
	      double controlPoint1X = center.getX();
	      double controlPoint1Y = center.getY() - radius * 5 / 6;

	      double controlPoint2X = center.getX();
	      double controlPoint2Y = center.getY() + radius / 6 * 5;
	    
	      
	      QuadCurve curve1 = new QuadCurve();  
	       
	      curve1.setStartX(edgeLeftX); 
	      curve1.setStartY(edgeLeftY); 
	      curve1.setEndX(edgeRightX); 
	      curve1.setEndY(edgeRightY); 
	      curve1.setControlX(controlPoint1X); 
	      curve1.setControlY(controlPoint1Y);  
	      
	      curve1.setFill(bg);
	      root.getChildren().add(curve1);
	      
	      
	      QuadCurve curve2 = new QuadCurve();  
	       
	      curve2.setStartX(edgeLeftX); 
	      curve2.setStartY(edgeLeftY); 
	      curve2.setEndX(edgeRightX); 
	      curve2.setEndY(edgeRightY); 
	      curve2.setControlX(controlPoint2X); 
	      curve2.setControlY(controlPoint2Y);  
	      
	      curve2.setFill(bg);
	      root.getChildren().add(curve2);
	      
	      
			Circle iris = new Circle(center.getX(), center.getY(), radius / 2.75);
			iris.setFill(sun);        
			root.getChildren().add(iris);	      
	      
	      Polygon pupil = new Polygon();
	      pupil.getPoints().addAll(new Double[]{        
	    		  center.getX(), center.getY() - radius / 10, 
	    		  center.getX() + radius / 20, center.getY(), 
	    		  center.getX(), center.getY() + radius / 10,
	    		  center.getX() - radius / 20, center.getY() 
	      });
	      pupil.setFill(bg);
	      root.getChildren().add(pupil);      
	}
	
	@Override
	public void start(Stage stage) // start - is the main entry point for all JavaFX applications
	{
		double radius = HEIGHT / 8;
		
		Point2D center = new Point2D(WIDTH / 2, HEIGHT / 2);
//		double xc = WIDTH / 2;
//		double yc = HEIGHT / 2;
//		
		double starRayAmount = 12;
		
		Color bgCol = Color.rgb(242,230,208);
		Color sunCol = Color.rgb(43,44,64);

			
		Group root = new Group(); // making the root of all scene's objects
		Scene scene = new Scene (root, WIDTH, HEIGHT); // making the scene for root object with size of 500*400 pixels;   
		
		scene.setFill(bgCol);
//		Polygon star = getFourPointStar(radius, center);
//        star.setFill(sunCol);
//        root.getChildren().add(star);
		
		//stars as backdrop
        paintRandomStars(root, radius, center, sunCol, 16);
		
        //drawing the sun: rays
        paintSunRays(root, radius, center, starRayAmount, sunCol);
        
        //main circle
		Circle c1 = new Circle(center.getX(), center.getY(), radius);
		c1.setFill(sunCol);        
		root.getChildren().add(c1);
        
		//details for rays
		paintNavalSunRays(root, radius, center, starRayAmount, bgCol);

		//two circles for creating a circuit
		Circle c2 = new Circle(center.getX(), center.getY(), radius - 5 / 2);
		c2.setFill(bgCol);        
        root.getChildren().add(c2);
        
		Circle c3 = new Circle(center.getX(), center.getY(), radius - 5);
		c3.setFill(sunCol);        
        root.getChildren().add(c3);
        
        //two curves: top and bottom for eye sclera
        paintEye(root, radius, center, sunCol, bgCol);
        
        //detail for eye's iris
        paintSunRays(root, radius / 6, center, 24, bgCol);
 
        stage.setScene(scene);
        stage.show();
		
	}
}

