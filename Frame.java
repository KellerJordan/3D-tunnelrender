//t=stupidmovemove
//y=smartmove
//1-9=your move
//wasdqe
//mouse horizontal

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;
public class Frame extends JPanel implements KeyListener, MouseMotionListener{
    ArrayList<ArrayList<Point3D>> wireframes = new ArrayList<ArrayList<Point3D>>(); 
    ArrayList<ArrayList<Point3D>> connectFourPieces = new ArrayList<ArrayList<Point3D>>();
    int[][]connectFourBoard;
    //0 is null, 1 is red, 2 is blue
    double myX=0;
    double myY=0;
    double myZ=-500;
    int myWindowWidth=1680;
    int myWindowHeight=1050;
    double myWindowDist=1000;
    double mouseX=0;
    double mouseY=0;
    int moveSpeed=10;
    public Frame(){
        JFrame easel=new JFrame();
        easel.setSize(myWindowWidth, myWindowHeight);
        easel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        easel.add(this);
        easel.setVisible(true);
        addKeyListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        //populateConnectFourBoard(c4Rows,c4Cols);
        //addConnectFourBoard(0,0,0,50,c4Rows,c4Cols,50);
        addShapes(5);
        setValues();
    }    

    public void setValues(){
        //checkForWin();
        for(int x=0;x<wireframes.size();x++){
            for(int y=0;y<wireframes.get(x).size();y++){
                wireframes.get(x).get(y).transferValues(myX,myY,myZ,mouseX,mouseY,myWindowWidth,myWindowHeight,myWindowDist);
                wireframes.get(x).get(y).rotMod1();
                wireframes.get(x).get(y).rotMod2();
            }
        }
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,myWindowWidth,myWindowHeight);
        g.setColor(Color.black);
        //rectangular prism: 0-1,1-2,2-3,3-0,4-5,5-6,6-7,7-4,0-4,1-5,2-6,3-7
        //better to represent prism in two arrays. go through each and treat like normal polygon, then link corresponding points
        for(int x=0;x<wireframes.size();x++){
                draw3Line(wireframes.get(x).get(0).getX(), wireframes.get(x).get(0).getY(), wireframes.get(x).get(0).getZ(), wireframes.get(x).get(1).getX(), wireframes.get(x).get(1).getY(), wireframes.get(x).get(1).getZ(), g);
                draw3Line(wireframes.get(x).get(1).getX(), wireframes.get(x).get(1).getY(), wireframes.get(x).get(1).getZ(), wireframes.get(x).get(2).getX(), wireframes.get(x).get(2).getY(), wireframes.get(x).get(2).getZ(), g);
                draw3Line(wireframes.get(x).get(2).getX(), wireframes.get(x).get(2).getY(), wireframes.get(x).get(2).getZ(), wireframes.get(x).get(3).getX(), wireframes.get(x).get(3).getY(), wireframes.get(x).get(3).getZ(), g);
                draw3Line(wireframes.get(x).get(3).getX(), wireframes.get(x).get(3).getY(), wireframes.get(x).get(3).getZ(), wireframes.get(x).get(0).getX(), wireframes.get(x).get(0).getY(), wireframes.get(x).get(0).getZ(), g);
                draw3Line(wireframes.get(x).get(4).getX(), wireframes.get(x).get(4).getY(), wireframes.get(x).get(4).getZ(), wireframes.get(x).get(5).getX(), wireframes.get(x).get(5).getY(), wireframes.get(x).get(5).getZ(), g);
                draw3Line(wireframes.get(x).get(5).getX(), wireframes.get(x).get(5).getY(), wireframes.get(x).get(5).getZ(), wireframes.get(x).get(6).getX(), wireframes.get(x).get(6).getY(), wireframes.get(x).get(6).getZ(), g);
                draw3Line(wireframes.get(x).get(6).getX(), wireframes.get(x).get(6).getY(), wireframes.get(x).get(6).getZ(), wireframes.get(x).get(7).getX(), wireframes.get(x).get(7).getY(), wireframes.get(x).get(7).getZ(), g);
                draw3Line(wireframes.get(x).get(7).getX(), wireframes.get(x).get(7).getY(), wireframes.get(x).get(7).getZ(), wireframes.get(x).get(4).getX(), wireframes.get(x).get(4).getY(), wireframes.get(x).get(4).getZ(), g);
                draw3Line(wireframes.get(x).get(0).getX(), wireframes.get(x).get(0).getY(), wireframes.get(x).get(0).getZ(), wireframes.get(x).get(4).getX(), wireframes.get(x).get(4).getY(), wireframes.get(x).get(4).getZ(), g);
                draw3Line(wireframes.get(x).get(1).getX(), wireframes.get(x).get(1).getY(), wireframes.get(x).get(1).getZ(), wireframes.get(x).get(5).getX(), wireframes.get(x).get(5).getY(), wireframes.get(x).get(5).getZ(), g);
                draw3Line(wireframes.get(x).get(2).getX(), wireframes.get(x).get(2).getY(), wireframes.get(x).get(2).getZ(), wireframes.get(x).get(6).getX(), wireframes.get(x).get(6).getY(), wireframes.get(x).get(6).getZ(), g);
                draw3Line(wireframes.get(x).get(3).getX(), wireframes.get(x).get(3).getY(), wireframes.get(x).get(3).getZ(), wireframes.get(x).get(7).getX(), wireframes.get(x).get(7).getY(), wireframes.get(x).get(7).getZ(), g);
        }
    }
    
    public void draw3Line(double x1, double y1, double z1, double x2, double y2, double z2, Graphics g){
        double x3=0;
        double y3=0;
        double x4=0;
        double y4=0;
        g.drawLine(quadX(mod(x1,z1)),quadY(mod(y1,z1)),quadX(mod(x2,z2)),quadY(mod(y2,z2)));
    }
    
    public double mod(double var, double z){
        return(myWindowDist*(var/Math.abs(z)));
    }
    public int quadX(double x){
        return(int)(x+(.5*myWindowWidth));
    }
    public int quadY(double y){
        return(int)(y+(.5*myWindowHeight));
    }

    public void addRectPrism(double width, double height, double length, double x, double y, double z){
        wireframes.add(new ArrayList<Point3D>());
        wireframes.get((wireframes.size()-1)).add(new Point3D((x+(width/2)),(y+(height/2)),(z-(length/2))));
        wireframes.get((wireframes.size()-1)).add(new Point3D((x+(width/2)),(y-(height/2)),(z-(length/2))));
        wireframes.get((wireframes.size()-1)).add(new Point3D((x-(width/2)),(y-(height/2)),(z-(length/2))));
        wireframes.get((wireframes.size()-1)).add(new Point3D((x-(width/2)),(y+(height/2)),(z-(length/2))));
        wireframes.get((wireframes.size()-1)).add(new Point3D((x+(width/2)),(y+(height/2)),(z+(length/2))));
        wireframes.get((wireframes.size()-1)).add(new Point3D((x+(width/2)),(y-(height/2)),(z+(length/2))));
        wireframes.get((wireframes.size()-1)).add(new Point3D((x-(width/2)),(y-(height/2)),(z+(length/2))));
        wireframes.get((wireframes.size()-1)).add(new Point3D((x-(width/2)),(y+(height/2)),(z+(length/2))));
    }
    
    public void addShapes(int numShapes){
        for(int i=0;i<10;i++){
            addRectPrism(200*Math.random(), 200*Math.random(), 200*Math.random(), 1000*Math.random()-500, 1000*Math.random()-500, 100*Math.random());
        }
    }

    public void mouseMoved(MouseEvent e){
        mouseX=(e.getX()-(myWindowWidth/2));
        mouseY=(e.getY()-(myWindowHeight/2));
        setValues();
        repaint();
    }

    public void mouseDragged(MouseEvent e){
    }

    public void keyTyped(KeyEvent event){
    }

    public void keyPressed(KeyEvent event){
        if(event.getKeyChar()=='w'){
            myZ=myZ+moveSpeed;
            setValues();
            repaint();
        }
        if(event.getKeyChar()=='s'){
            myZ=myZ-moveSpeed;
            setValues();
            repaint();
        }
        if(event.getKeyChar()=='d'){
            myX=myX+moveSpeed;
            setValues();
            repaint();
        }
        if(event.getKeyChar()=='a'){
            myX=myX-moveSpeed;
            setValues();
            repaint();
        }
        if(event.getKeyChar()=='q'){
            myY=myY-moveSpeed;
            setValues();
            repaint();
        }
        if(event.getKeyChar()=='e'){
            myY=myY+moveSpeed;
            setValues();
            repaint();
        }
        if(event.getKeyChar()=='r'){
            myWindowDist-=50;
            setValues();
            repaint();
        }
        if(event.getKeyChar()=='f'){
            myWindowDist+=50;
            setValues();
            repaint();
        }
    }

    public void keyReleased(KeyEvent event){
    }

    public void keyEntered(KeyEvent event){
    }

    public void keyExited(KeyEvent event){
    }
}