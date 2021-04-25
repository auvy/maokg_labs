package lab5;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.vecmath.*;

public class AnimateCar implements ActionListener, KeyListener {
    private Button go;
    private TransformGroup wholeCar;
    private Transform3D translateTransform;
    private Transform3D rotateTransformX;
    private Transform3D rotateTransformY;
    private Transform3D rotateTransformZ;

    
    private JFrame mainFrame;
    
    private float zoom=2.0f;
    private float xloc=0.0f;
    private float yloc=0.0f;
    private float zloc=0.0f;

    private int moveType=1;
    private Timer timer;
    
    public AnimateCar(TransformGroup wholeCar, Transform3D trans,JFrame frame){
        go = new Button("Go");
        this.wholeCar=wholeCar;
        this.translateTransform=trans;
        this.mainFrame=frame;
        
        rotateTransformX= new Transform3D();
        rotateTransformY= new Transform3D();
        rotateTransformZ= new Transform3D();

        FirstMainClass.canvas.addKeyListener(this);
        timer = new Timer(100, this);
        
        Panel p =new Panel();
        p.add(go);
        mainFrame.add("North",p);
        go.addActionListener(this);
        go.addKeyListener(this);
    }

    private void initialCarState(){
        xloc=0.0f;
        yloc=0.0f;
        zloc=0.0f;
        zoom=2.0f;
        
        moveType=1;
        
        rotateTransformY.rotY(-Math.PI/2.8);
        translateTransform.mul(rotateTransformY);
        
        if(timer.isRunning()) timer.stop();
        
        go.setLabel("Go");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // start timer when button is pressed
       if (e.getSource()==go){
          if (!timer.isRunning()) {
             timer.start();
             go.setLabel("Stop");
          }
          else {
              timer.stop();
              go.setLabel("Go");
          }
       }
       else {
           Move(moveType);
           translateTransform.setScale(new Vector3d(zoom,zoom,zoom));
           translateTransform.setTranslation(new Vector3f(xloc,yloc,zloc));
           wholeCar.setTransform(translateTransform);
       }
    }

    private void Move(int mType){
        if(mType==1)
        { 
        	//rotate
           rotateTransformY.rotY(Math.PI/240);
           translateTransform.mul(rotateTransformY);
        }
        if(mType==2){
        	//do nothing
         }
        if(mType==3)
        { 
        	//rotate
           rotateTransformY.rotY(-Math.PI/30);
           translateTransform.mul(rotateTransformY);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
          //Invoked when a key has been typed.
    }

    @Override
    public void keyPressed(KeyEvent e) {
         //Invoked when a key has been pressed.
        if (e.getKeyChar()=='d') {xloc = xloc + .05f;}
        if (e.getKeyChar()=='a') {xloc = xloc - .05f;}
        if (e.getKeyChar()=='w') {yloc = yloc + .05f;}
        if (e.getKeyChar()=='s') {yloc = yloc - .05f;}
        if (e.getKeyChar()=='z') {zoom = zoom + .05f;}
        if (e.getKeyChar()=='x') {zoom = zoom - .05f;}

        
        if (e.getKeyChar()=='r') {
            rotateTransformX.rotX(Math.PI/180);
            translateTransform.mul(rotateTransformX);
        }
        if (e.getKeyChar()=='y') {
            rotateTransformX.rotX(- Math.PI/180);
            translateTransform.mul(rotateTransformX);
        }
        if (e.getKeyChar()=='f') {
            rotateTransformY.rotY(Math.PI/180);
            translateTransform.mul(rotateTransformY);
        }
        if (e.getKeyChar()=='h') {
            rotateTransformY.rotY(-Math.PI/180);
            translateTransform.mul(rotateTransformY);
        }
        if (e.getKeyChar()=='v') {
            rotateTransformZ.rotZ(Math.PI/180);
            translateTransform.mul(rotateTransformZ);
        }
        if (e.getKeyChar()=='n') {
            rotateTransformZ.rotZ(-Math.PI/180);
            translateTransform.mul(rotateTransformZ);
        }
        
        
        if (e.getKeyChar()=='1') {
        	moveType = 1;
        }
        if (e.getKeyChar()=='2') {
        	moveType = 2;
        }
        if (e.getKeyChar()=='3') {
        	moveType = 3;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // Invoked when a key has been released.
    }
    
}
