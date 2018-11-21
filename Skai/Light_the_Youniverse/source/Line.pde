class Line{
 float beginX;
 float beginY;
 float finalX;
 float finalY;
 
   Line(float x, float y, float fx, float fy){
     beginX = x;
     beginY = y;
     finalX = fx;
     finalY = fy;
  }
  
  void display(){
   stroke(255);
   strokeWeight(3);
   line(beginX,beginY,finalX,finalY); 
  }
  
}
