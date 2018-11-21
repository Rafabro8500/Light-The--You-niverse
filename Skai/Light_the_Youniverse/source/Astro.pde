
class Astro{
  float xi;
  float yi;
  float diameter;
  int type;
  boolean isFilled=false;
  PImage texture;
  float x;
  float y;
  float z =0,w=0;
  
  Astro(float tempX, float tempY, int tempD, int tempType, PImage tempTexture){
    xi=tempX;
    yi=tempY;
   diameter = tempD;
   type = tempType; 
   texture = tempTexture;
   x=xi;
   y=yi; 
  }
  
  void display(){
    if(type==1){
      noStroke();
      noFill();
      ellipse(x,y,diameter,diameter);
      imageMode(CENTER);
      image(astro_textures[0],x,y,diameter*4,diameter*4);  
    }
    if(type==-1){
      stroke(0);
      noFill();
      ellipse(x,y,diameter,diameter);
      imageMode(CENTER);
      image(texture,x,y,diameter*1.4,diameter*1.4);      
    }  
  } 

  void shake(float tempX, float tempY){
    x+=random(-0.5,0.5); 
    y+=random(-0.5,0.5);
    if(x>tempX+1 || x<tempX-1)
      x=tempX;
    if(y>tempY+1 || y<tempY-1)
      y=tempY;
  }
  
 void moveY(float dv,float finaly){
   if(y<=finaly && z==0)
     y+=dv;
   if(y>=finaly && z==0)
     z=1;
   if(z==1)
     y-=dv;
   if(y<=yi && z==1)
     z=0;  
  }
   void moveX(float dh,float finalx){
   if(x<=finalx && w==0)
     x+=dh;
   if(x>=finalx && w==0)
     w=1;
   if(w==1)
     x-=dh;
   if(x<=xi && w==1)
     w=0;  
  }
    
  boolean clicked(){ 
    if(dist(mouseX,mouseY,x,y)<diameter/2 && mousePressed)
      return true;     
    return false;  
  }

}
