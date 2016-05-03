public class Point3D{
    double x0;
    double y0;
    double z0;
    double x1;
    double y1;
    double z1;
    double x2;
    double y2;
    double z2;
    //transferred values with transferValues
    double myX;
    double myY;
    double myZ;
    int myWindowWidth;
    int myWindowHeight;
    double myWindowDist;
    double rotX;
    double rotY;
    public Point3D(double x,double y,double z){
        x0=x;
        y0=y;
        z0=z;
    }
    public void transferValues(double a, double b, double c, double d, double e, int f, int g, double h){
        myX=a;
        myY=b;
        myZ=c;
        myWindowWidth=f;
        myWindowHeight=g;
        rotX=(-1*Math.PI*(d*2)/myWindowWidth);
        rotY=(-1*Math.PI*(e*2)/myWindowHeight);
        myWindowDist=h;
        x1=x0-myX;
        y1=y0-myY;
        z1=z0-myZ;
    }
    public double getX(){
        return x2;
    }
    public double getY(){
        return y2;
    }
    public double getZ(){
        return z2;
    }
    //when x is negative, renders half of rect across y axis
    public void rotMod1(){
        x2=x1*Math.cos(rotX)+z1*Math.sin(rotX);
        z2=z1*Math.cos(rotX)-x1*Math.sin(rotX);
    }
    public void rotMod2(){
        y2=y1*Math.cos(rotY)+z2*Math.sin(rotY);
        z2=z2*Math.cos(rotY)-y1*Math.sin(rotY);
    }
}
