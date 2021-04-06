package lab3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Path;

public class GetPathFromBMP {

	HeaderBitmapImage image; // �������� ����, ��� ������ ��'��� � ����������� ��� ��������� ����������
	private int numberOfPixels; // �������� ���� ��� ���������� ������� ������ � ������ ��������
	
	public GetPathFromBMP(HeaderBitmapImage image) // �������������� ����������� �����������
	{
		this.image = image;
	}
	
	public GetPathFromBMP() {
		// TODO Auto-generated constructor stub
	}

	public Path getPath(String source) throws IOException
	{
		ReadingImageFromFile.loadBitmapImage(source);
		this.image = ReadingImageFromFile.pr.image;
		int width = (int)this.image.getWidth();
		int height = (int)this.image.getHeight();
		int half = (int)image.getHalfOfWidth();
		
		Group root = new Group();	
		Scene scene = new Scene (root, width, height);
		scene.setFill(Color.BLACK);
		Circle cir;
		
		int let = 0;
		int let1 = 0;
		int let2 = 0;
		char[][] map = new char[width][height];
		// �������� ���������� ����� ��� �����
		BufferedInputStream reader = new BufferedInputStream (new FileInputStream("pixels.txt"));
		
	
		for(int i=0;i<height;i++)     // ���� �� ����� ���������� �� �����
		   { 
		   for(int j=0;j<half;j++)         // ���� �� ����� ���������� �� ������
		   {
		          let = reader.read();  // ������� ���� ������ � �����
		          let1=let;
		          let2=let;    
		          let1=let1&(0xf0);   // ������� ���� - ������ ������
		          let1=let1>>4;       // ���� �� 4 ������� 
		          let2=let2&(0x0f);   // �������� ���� - ������ ������          
		           if(j*2<width) // ��� �� 1 ������ ���� 2 ����� ��� ��������� ������ �� �������� ������ ����������
		           {  
		                cir = new Circle ((j)*2,(height-1-i),1, Color.valueOf((returnPixelColor(let1)))); // �� ��������� ������������ 
		                // �������� ���� ������� � 1 ������ �� �������� ���������� �� ��������� ������ returnPixelColor ������� ������
		    			//root.getChildren().add(cir); //������ ��'��� � �����	
		    			 if (returnPixelColor(let1) == "BLACK") // ���� ���� ������ ������, �� ������� � ����� 1
		                 {
		                     map[j*2][height-1-i] = '1';
		                     numberOfPixels++; // �������� ������� ������ ������
		                 }
		                 else
		                 {
		                     map[j*2][height-1-i] = '0'; 
		                 }
		           }
		           if(j*2+1<width) // ��� ������� ������
		           {   
		               cir = new Circle ((j)*2+1,(height-1-i),1,Color.valueOf((returnPixelColor(let2))));
		    		   //root.getChildren().add(cir);
		    		   if (returnPixelColor(let2) == "BLACK")
		                {
		                    map[j*2+1][height-1-i] = '1';
		                    numberOfPixels++;
		                }
		                else
		                {                    
		                    map[j*2+1][height-1-i] = '0'; 
		                }
		           }
		   }
		   }

	    
		reader.close();
		
		int[][] black;
		black = new int[numberOfPixels][2];	
		int lich = 0;
		
		BufferedOutputStream writer = new BufferedOutputStream (new FileOutputStream("map.txt")); // �������� ����� ��� ���� �� �������� � ����
		for(int i=0;i<height;i++)     // ���� �� ����� ���������� �� �����
		{ 
		   for(int j=0;j<width;j++)         // ���� �� ����� ���������� �� ������
		   {
			   if (map[j][i] == '1')
			   {
				   black[lich][0] = j;
				   black[lich][1] = i;
				   lich++;
			   }
			   writer.write(map[j][i]);
		   }
		   writer.write(10);
		}
		writer.close();
		
		System.out.println("number of black color pixels = " + numberOfPixels);
		
		Path path2 = new Path();
		for (int l=0; l<numberOfPixels-1; l++)
		{
		   path2.getElements().addAll(
				new MoveTo(black[l][0],black[l][1]),
				new LineTo (black[l+1][0],black[l+1][1])
				);
		}
		
		return path2;
	}
	
	private String returnPixelColor (int color) // ����� ��� ������������ ������� 16-������ ����������
	{
		String col = "BLACK";
		switch(color)
		   {
		      case 0: return "BLACK";     //BLACK;
		      case 1: return "LIGHTCORAL";  //LIGHTCORAL;
		      case 2: return "GREEN";     //GREEN
		      case 3: return "BROWN";     //BROWN
		      case 4: return "BLUE";      //BLUE;
		      case 5: return "MAGENTA";   //MAGENTA;
		      case 6: return "CYAN";      //CYAN;
		      case 7: return "LIGHTGRAY"; //LIGHTGRAY;
		      case 8: return "DARKGRAY";  //DARKGRAY;
		      case 9: return "RED";       //RED;
		      case 10:return "LIGHTGREEN";//LIGHTGREEN
		      case 11:return "YELLOW";    //YELLOW;
		      case 12:return "LIGHTBLUE"; //LIGHTBLUE;
		      case 13:return "LIGHTPINK";    //LIGHTMAGENTA
		      case 14:return "LIGHTCYAN";    //LIGHTCYAN;
		      case 15:return "WHITE";    //WHITE;
		   }
		   return col;
	}
}