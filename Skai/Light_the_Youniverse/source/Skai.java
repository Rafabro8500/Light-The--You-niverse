import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 
import gifAnimation.*; 

import gifAnimation.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Skai extends PApplet {

//////////////////////////////////////////////////
// Made by Teresa Goela & Rafael Duarte 
// LEI-Labratório de Programação 2017/18
///////////////////////////////////////////////////

SoundFile song;

PImage menu_background, button, homebutton,sound,nosound;
String[] texture_names = {"star.png","planet1.png","planet2.png","planet3.png","planet4.png","planet5.png","planet6.png"};
PImage[] astro_textures = new PImage[texture_names.length];
String[] levelnames = {"level_5.jpg","level_2.jpg","level_3.jpg","level_4.jpg"};
PImage[] levels = new PImage[levelnames.length];
PFont star,normal;
Gif myAnimation,myAnimation_2,myAnimation_3;
int start=-1;
int endLevel=0;
int startTime, endTime,part=0;
int lastClicked=-1;
int firstClicked=-1;
int playSound=1;

Astro astros[];
Line lines[]; 
int iLines = 0;


public void setup(){
  
  star=createFont("star.ttf",32);
  normal=createFont("normal.ttf",32);
  menu_background = loadImage("menu_background.png");
  homebutton = loadImage("homebutton.png");
  button=loadImage("button.png");
  sound=loadImage("sound.png");nosound=loadImage("nosound.png");
  loadTextures();
  loadLevels();
  newAstros();
  song = new SoundFile(this,"song.mp3");
  song.loop(1,2,1);
  myAnimation =new Gif(this, "rick.gif");
  myAnimation.play();
  myAnimation_2=new Gif(this,"rick_2.gif");
  myAnimation_2.play();
  myAnimation_3 = new Gif(this,"daddy.gif");
  myAnimation_3.play();
}

public void draw(){  //draw calls levels and menus depending on the value of the global variable "start"
   
  if(start==-1){
    
    startMenu(); 
    soundButton();
    image(myAnimation,width*0.24f,height*0.2f,250,150);
    image(myAnimation_2,width*0.8f,height*0.9f,250,150);
    
  }
    
    println(firstClicked,lastClicked);
  
  if(start==-2)
    levelsMenu();
    soundButton();
    displayHomeButton();

 if(start==1){
   level_1(); 
   soundButton();
   displayHomeButton();
   displayLevel();
   displayParts();
   displayRestartButton();
 }  
  if(start==2){
  level_2();
  soundButton();
  displayHomeButton();
  displayLevel();
  displayParts();
  displayRestartButton();
 }  
 if(start==3){
  level_3();
  soundButton();
  displayHomeButton();
  displayLevel();
  displayParts();
  displayRestartButton();
 }  
  if(start==4){
  level_4();
  soundButton();
  displayHomeButton();
  displayLevel();
  displayParts();
  displayRestartButton();
 }  
  
  if(start==0 || keyPressed){
    displayInstructions();
    displayHomeButton(); 
  }
    
  if(endLevel>0 ){
   endLvlMenu();
  }
}

public void startMenu(){  //start menu interface
  stroke(0);
  strokeWeight(3);
  rectMode(CORNER);
  textFont(star);
  background(menu_background);
  textAlign(CENTER);
  textSize(50);
  fill(255);
  text("Light the You(niverse)",width/2,height/2); 
  textSize(15);
  image(myAnimation_3,width*0.5f,height*0.9f,250,300);
  text("Rafael Duarte & Teresa Goela", width*0.5f,height*0.99f);
  
  textSize(17); 
  strokeWeight(2);
  fill(120,80,80);
  //if(start<1){
  if(mouseX>width*0.1f && mouseX<width*0.3f && mouseY>height*0.6f && mouseY<height*0.7f){
    fill(110,98,255,150); 
    if(mousePressed)
       start=1;
       newAstros();
  }
    rect(width*0.1f,height*0.6f,width*0.2f,height*0.1f);
    fill(255);
    text("Start",width*0.2f,height*0.66f);
    fill(120,80,80);
    if((mouseX>width*0.4f && mouseX<width*0.6f && mouseY>height*0.6f && mouseY<height*0.7f)){
      fill(110,98,255,150);
      if(mousePressed)
      start=-2;
    //levels menu here      
  }
    rect(width*0.4f,height*0.6f,width*0.2f,height*0.1f);
    fill(255);
    text("Levels",width*0.5f,height*0.66f);
    fill(120,80,80);
    if((mouseX>width*0.7f && mouseX<width*0.9f && mouseY>height*0.6f && mouseY<height*0.7f)){
      fill(110,98,255,150); 
      if(mousePressed)
       start=0;
  }
    rect(width*0.7f,height*0.6f,width*0.2f,height*0.1f);
    fill(255);
    text("instructions",width*0.8f,height*0.66f);
  
}

public void endLvlMenu(){   //end level menu - only pops up when variable endLevel = 1
   textFont(normal);
   noFill();
   rectMode(CENTER);
   noStroke();
   fill(203,203,203,80);
   rect(width/2,height/2, width*0.35f,height*0.5f);
   strokeWeight(3);
   fill(80,120,80);
   rect(width/2,height*0.5f,width*0.24f,height*0.1f);
   fill(120,80,80);
   rect(width/2,height*0.63f,width*0.24f,height*0.1f);
   fill(255);
   strokeWeight(2);
   textAlign(CENTER,CENTER);
   text("Next Level",width/2,height*0.5f);
   text("Replay",width/2,height*0.63f);
   text("Time: " + endTime + " s", width/2, height*0.35f);
   if(mouseX>width/2-width*0.12f && mouseX<width/2+width*0.12f && mouseY>height*0.63f-height*0.05f && mouseY<height*0.63f+height*0.05f && mousePressed){
     endLevel=0;
     restart();
   }
   if(mouseX>width/2-width*0.12f && mouseX<width/2+width*0.12f && mouseY>height*0.5f-height*0.05f && mouseY<height*0.5f+height*0.05f && mousePressed && start<levelnames.length){
    start++;  
    restart();
    endLevel=0;
    
   }  
}

public void levelsMenu(){
  //if(start<-1){
    textFont(normal);
    stroke(0);
    strokeWeight(2);
  background(menu_background);
  rectMode(CORNER);
  
    fill(120,80,80);
    if(mouseX>width*0.140f && mouseX<width*0.14f+width*0.15f && mouseY>height*0.45f && mouseY<height*0.45f+height*0.1f){
      fill(110,98,255,150);
     if(mousePressed){
       start=1;
       newAstros();
     }
    }
    rect(width*0.140f,height*0.45f,width*0.15f,height*0.1f);  
    fill(255);
    text("1",width*0.215f,height*0.52f);
    fill(120,80,80);
    if(mouseX>width*0.330f && mouseX<width*0.33f+width*0.15f && mouseY>height*0.45f && mouseY<height*0.45f+height*0.1f){
      fill(110,98,255,150);
     if(mousePressed){
      start=2;
      newAstros();
     }
    }
    rect(width*0.330f,height*0.45f,width*0.15f,height*0.1f);
    fill(255);
    text("2",width*0.405f,height*0.52f);
    fill(120,80,80);
    if(mouseX>width*0.52f && mouseX<width*0.52f+width*0.15f && mouseY>height*0.45f && mouseY<height*0.45f+height*0.1f){
      fill(110,98,255,150);
     if(mousePressed){
       start=3;
       newAstros();
     }
    }
    rect(width*0.520f,height*0.45f,width*0.15f,height*0.1f);
    fill(255);
    text("3",width*0.595f,height*0.52f);
    fill(120,80,80);
    if(mouseX>width*0.71f && mouseX<width*0.71f+width*0.15f && mouseY>height*0.45f && mouseY<height*0.45f+height*0.1f){
      fill(110,98,255,150);
     if(mousePressed){
      start=4;
      newAstros();
     }
    }
     rect(width*0.710f,height*0.45f,width*0.15f,height*0.1f);
     fill(255);
     text("4",width*0.785f,height*0.52f);
 // }
}


public void displayInstructions(){
  textFont(normal);
  background(menu_background);
  rectMode(CENTER);
  noStroke();
  fill(157,157,157,80);
  rect(width/2,height*0.45f,600,300);
  fill(255,255,255); stroke(0);
  text("INSTRUCTIONS",width/2,height*0.31f);
  text("Connect the planets with the stars without\n intersecting the lines making a closed cirtcuit.\nIf your astro is moving that means\nit's still disconnected or the first clicked on.\n See how fast you are.",width/2,height*0.4f);
}

public void soundButton(){
  if(playSound==1)
  image(sound,width*0.9f,height*0.052f,30,30);
  noFill();
  noStroke();
  rect(width*0.9f,height*0.052f,30,30);
  if(playSound==-1){
    image(nosound,width*0.9f,height*0.052f,30,30);
    }
}

public void displayHomeButton(){
  imageMode(CENTER);
  image(homebutton,width*0.85f,height*0.05f,30,30);
  rectMode(CENTER);
  noStroke();
  noFill();
  rect(width*0.85f,height*0.05f,30,30);
  if(mousePressed && mouseX>width-width*0.15f-12.5f && mouseX<width-width*0.15f+12.5f && mouseY>height*0.025f && mouseY<height*0.07f){
    start=-1;
    endLevel=0;
    restart();
  }
}

public void displayRestartButton(){
  image(button,width*0.8f,height*0.05f,25,25);
  rectMode(CENTER);
  noStroke();
  noFill();
  rect(width*0.8f,height*0.05f,25,25);
  if(mousePressed && mouseX>width-width*0.2f-12.5f && mouseX<width-width*0.2f+12.5f && mouseY>height*0.025f && mouseY<height*0.07f){
    endLevel=0;
    restart();
  }   
}

public void displayLevel(){
   fill(255);
   textAlign(CENTER);
   textSize(25);
   textFont(normal);
   text("Level",width*0.1f,height*0.06f);
   text(start,width*0.15f,height*0.06f);
}

public void displayParts(){
  fill(255);
  textAlign(CENTER);
   textSize(25);
   textFont(normal);
   text("Parts "+ part + "/" + lines.length,width*0.25f,height*0.06f);
}

public void restart(){ //function to restart the the game - needs to be called after finishing a game in order to restart all global variables needed
  startTime=millis();
  lastClicked=-1;
  firstClicked=-1;
  iLines=0;
  newAstros();
  part=0;
}

public void level_1(){ 
  background(levels[0]);
  
  if(lastClicked!=-1 && endLevel==0 ){ //this creates a line to follow the mouse when clicking on an astor
    stroke(255);
    strokeWeight(3);
   line(astros[lastClicked].x,astros[lastClicked].y,mouseX,mouseY);
  } 
  for(int i = 0; i<iLines; i++) //display lines
    lines[i].display();
  for(int i= 0; i<astros.length; i++){ //display astros
   astros[i].display();
   if(!astros[i].isFilled && !astros[i].clicked() && i!= lastClicked)
     astros[i].shake(astros[i].xi,astros[i].yi);
  } 
}

public void level_2(){
  background(levels[1]);   
   if(lastClicked!=-1 && endLevel==0 ){
    stroke(255);
    strokeWeight(3);
   line(astros[lastClicked].x,astros[lastClicked].y,mouseX,mouseY);
  } 
  for(int i = 0; i<iLines; i++) //display static lines from lines array
    lines[i].display();
  for(int i= 0; i<astros.length; i++){
   astros[i].display(); //display all astros
   if(!astros[i].isFilled && !astros[i].clicked() && i!= lastClicked)
     astros[i].shake(astros[i].xi,astros[i].yi);
  }
}

public void level_3(){ 
   
  background(levels[2]);
    
    if(lastClicked!=-1 && endLevel==0){
    stroke(255);
    strokeWeight(3);
    line(astros[lastClicked].x,astros[lastClicked].y,mouseX,mouseY);
  } 
   
  
  for(int i = 0; i<iLines; i++){
    
    lines[i].display();
  }
  
  if(lastClicked!=0 && firstClicked!=0 && !astros[0].isFilled) //we have to display this individually because we want them to move instead of shaking
    astros[0].moveY(2,astros[0].yi+height*0.6f);
  astros[0].display();
  if(lastClicked!=9 && firstClicked!=9 && !astros[9].isFilled)
    astros[9].moveY(2,astros[9].yi+height*0.6f);
  astros[9].display();
  
  
  for(int i= 1; i<astros.length-1; i++){  //this time we only display astros from 1 to 8
   astros[i].display();
   if(!astros[i].isFilled && !astros[i].clicked() && i!= lastClicked)
     astros[i].shake(astros[i].xi,astros[i].yi);     
  }
   
}

public void level_4(){ 
   
  background(levels[3]);
    
    if(lastClicked!=-1 && endLevel==0){
    stroke(255);
    strokeWeight(3);
    line(astros[lastClicked].x,astros[lastClicked].y,mouseX,mouseY);
  } 
  for(int i = 0; i<iLines; i++){  
    lines[i].display();
  } 
 
  
  if(lastClicked!=4 && firstClicked!=4 && !astros[4].isFilled){ //these are the moving astros from 4 to 7
    astros[4].moveY(2,astros[4].yi+height*0.25f);
    astros[4].moveX(2,astros[4].xi+height*0.25f);
  }
  astros[4].display();
   if(lastClicked!=5 && firstClicked!=5 && !astros[5].isFilled){
    astros[5].moveY(2,astros[5].yi+height*0.25f);
    astros[5].moveX(2,astros[5].xi+height*0.25f);
  }
  astros[5].display();
   if(lastClicked!=6 && firstClicked!=6 && !astros[6].isFilled){
    astros[6].moveY(2,astros[6].yi+height*0.25f);
    astros[6].moveX(2,astros[6].xi+height*0.25f);
  }
  astros[6].display();
   if(lastClicked!=7 && firstClicked!=7 && !astros[7].isFilled){
    astros[7].moveY(2,astros[7].yi+height*0.25f);
    astros[7].moveX(2,astros[7].xi+height*0.25f);
  }
  astros[7].display();
  
  
  for(int i= 0; i<astros.length-4; i++){  //0 to 4 are the static shaking astros
   astros[i].display();
   if(!astros[i].isFilled && !astros[i].clicked() && i!= lastClicked)
     astros[i].shake(astros[i].xi,astros[i].yi);     
  }
   
}

public void keyPressed(){ 
}

public void newAstros(){ //this is the function that constructs the astros array. For each level it makes new "astros" spawn in different, pre-written positions
  if(start==1){    // needs to be called after altering the value of the "start" variable so it can then construct the astros array for the desired level
  astros= new Astro[10];
  lines = new Line[astros.length];
  astros[0]= new Astro(width*0.1f,height*0.2f,40,1,astro_textures[0]);
  astros[1]= new Astro(width*0.25f,height*0.3f,40,1,astro_textures[0]);
  astros[2]= new Astro(width*0.25f,height*0.45f,40,1,astro_textures[0]);
  astros[3] = new Astro(width*0.25f,height*0.6f,40,1,astro_textures[0]);
  astros[4] = new Astro(width*0.25f,height*0.75f,40,1,astro_textures[0]);
  astros[5] = new Astro(width*0.1f,height*0.85f,50,-1,astro_textures[1]);
  astros[6] = new Astro(width*0.85f,height*0.3f,50,-1,astro_textures[2]);
  astros[7] = new Astro(width*0.85f,height*0.45f,50,-1,astro_textures[3]);
  astros[8] = new Astro(width*0.85f,height*0.6f,50,-1,astro_textures[4]);
  astros[9] = new Astro(width*0.85f,height*0.75f,50,-1,astro_textures[5]);
}
  if(start==2){
  astros= new Astro[10];
  lines = new Line[astros.length];
  astros[0]= new Astro(width*0.5f,height*0.2f,50,-1,astro_textures[5]);
  astros[1]= new Astro(width*0.25f,height*0.3f,40,1,astro_textures[0]);
  astros[2]= new Astro(width*0.75f,height*0.3f,50,-1,astro_textures[1]);
  astros[3] = new Astro(width*0.15f,height*0.5f,50,-1,astro_textures[2]);
  astros[4] = new Astro(width*0.35f,height*0.5f,40,1,astro_textures[0]);
  astros[5] = new Astro(width*0.65f,height*0.5f,50,-1,astro_textures[3]);
  astros[6] = new Astro(width*0.85f,height*0.5f,40,1,astro_textures[2]);
  astros[7] = new Astro(width*0.25f,height*0.7f,40,1,astro_textures[3]);
  astros[8] = new Astro(width*0.75f,height*0.75f,50,-1,astro_textures[4]);
  astros[9] = new Astro(width*0.5f,height*0.8f,40,1,astro_textures[0]);
}
  if(start==3){
  astros = new Astro[10];
  lines = new Line[astros.length];
  astros[0]= new Astro(width*0.1f,height*0.25f,40,1,astro_textures[0]);
  astros[1]= new Astro(width*0.3f,height*0.4f,40,1,astro_textures[0]);
  astros[2]= new Astro(width*0.3f, height*0.55f,40,1,astro_textures[0]);
  astros[3]= new Astro(width*0.5f,height*0.3f,50,-1,astro_textures[1]);
  astros[4]= new Astro(width*0.5f,height*0.42f,50,-1,astro_textures[2]);
  astros[5]= new Astro(width*0.5f,height*0.54f,50,-1,astro_textures[3]);
  astros[6]= new Astro(width*0.5f,height*0.66f,50,-1,astro_textures[4]);
  astros[7]= new Astro(width*0.7f,height*0.4f,40,1,astro_textures[0]);
  astros[8]= new Astro(width*0.7f,height*0.55f,40,1,astro_textures[0]);
  astros[9]= new Astro(width*0.9f,height*0.25f,50,-1,astro_textures[5]);
}

if(start==4){
  astros = new Astro[8];
  lines = new Line[astros.length];
  astros[0]= new Astro(width*0.2f,height*0.20f,40,1,astro_textures[0]);
  astros[5]= new Astro(width*0.18f,height*0.56f,40,1,astro_textures[0]);
  astros[2]= new Astro(width*0.5f,height*0.45f,50,-1,astro_textures[1]);
  astros[3]= new Astro(width*0.85f,height*0.75f,50,-1,astro_textures[4]);
  astros[4]= new Astro(width*0.10f, height*0.64f,40,1,astro_textures[0]);
  astros[1]= new Astro(width*0.4f,height*0.55f,40,1,astro_textures[0]);
  astros[6]= new Astro(width*0.58f,height*0.12f,50,-1,astro_textures[2]);
  astros[7]= new Astro(width*0.50f,height*0.20f,50,-1,astro_textures[3]);
 }
} 

public void mousePressed(){ 
  if(mouseX>width-width*0.1f-15 && mouseX<width-width*0.10f+15 && mouseY>height*0.025f && mouseY<height*0.07f){ //here is where we can turn sound on and off
   playSound*=-1; //sound is controllod by this variable - if -1 sound off, if 1 sound on
   if(playSound==-1)
     song.stop();
   if(playSound==1)
     song.play();
     
  }
  
  if(start>0){ //this is the core levels engine
  for(int i = 0; i<astros.length; i++){ //go through all the astros in the array and check the following conditions everytime the mouse is pressed
    
   if(astros[i].clicked()){//clicked on a new astro     
     if(lastClicked!=-1){ //if there was one clicked on before
      if(astros[i].type!=astros[lastClicked].type && (!astros[i].isFilled)){ //if the new clicked on is a different type 
        if(!checkIntersections(astros[lastClicked].x,astros[lastClicked].y,astros[i].x,astros[i].y) && iLines<astros.length){//if there is no intersection         
          lines[iLines++]= new Line(astros[lastClicked].x,astros[lastClicked].y,astros[i].x,astros[i].y); // create a new static line
          part++;
          if(iLines>1)//we don't want to fill the first one since it has to be conected at the end to make the circuit
            astros[lastClicked].isFilled = true; //last astro now has a line conected
            if(iLines == astros.length){ //if every astro is connected           
            endTime=(millis()-startTime)/1000;                                
            endLevel = 1;
            // Next Level
          }
          else
            lastClicked = i; //update last clicked astro            
        }
       }
     } 
     else {
       lastClicked = i;
       firstClicked=i;
     }
   }  
  }
  }
}

public boolean intersects(float a,float b,float c,float d,float p, float q,float r,float s) { // returns true if the line from (a,b)->(c,d) intersects with (p,q)->(r,s)
  float det, gamma, lambda;
  det = (c - a) * (s - q) - (r - p) * (d - b); //reading these lines as vectors we can put them on a matrix -> this is the formula for the determinant
  if (det == 0) { //if the determinant is 0 the lines don't intersect 
    return false;
  } 
  else { //if det !=0 we have to check lambda and gamma
    lambda = ((s - q) * (r - a) + (p - r) * (s - b)) / det;
    gamma = ((b - d) * (r - a) + (c - a) * (s - b)) / det;
    return (0 < lambda && lambda < 1) && (0 < gamma && gamma < 1); //if gamma and lamba are between 0 and 1 then the lines intersect, else they don't intersect
  }
}

public Boolean checkIntersections(float x, float y, float fx, float fy){ //check intersections for all lines
  for(int i = 0; i<iLines; i++){
    if(intersects(x,y,fx,fy,lines[i].beginX,lines[i].beginY,lines[i].finalX,lines[i].finalY))//return true if this line intersects with any other line
      return true;
  }
  return false;
}

public void loadLevels(){ //this loads the levels background images
  for(int i=0; i<levelnames.length;i++)
    levels[i] = loadImage(levelnames[i]);
}

public void loadTextures(){ //this loads our textures, like astro's skins
 for(int i = 0; i<texture_names.length; i++)
   astro_textures[i]=loadImage(texture_names[i]);
}

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
  
  public void display(){
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
      image(texture,x,y,diameter*1.4f,diameter*1.4f);      
    }  
  } 

  public void shake(float tempX, float tempY){
    x+=random(-0.5f,0.5f); 
    y+=random(-0.5f,0.5f);
    if(x>tempX+1 || x<tempX-1)
      x=tempX;
    if(y>tempY+1 || y<tempY-1)
      y=tempY;
  }
  
 public void moveY(float dv,float finaly){
   if(y<=finaly && z==0)
     y+=dv;
   if(y>=finaly && z==0)
     z=1;
   if(z==1)
     y-=dv;
   if(y<=yi && z==1)
     z=0;  
  }
   public void moveX(float dh,float finalx){
   if(x<=finalx && w==0)
     x+=dh;
   if(x>=finalx && w==0)
     w=1;
   if(w==1)
     x-=dh;
   if(x<=xi && w==1)
     w=0;  
  }
    
  public boolean clicked(){ 
    if(dist(mouseX,mouseY,x,y)<diameter/2 && mousePressed)
      return true;     
    return false;  
  }

}
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
  
  public void display(){
   stroke(255);
   strokeWeight(3);
   line(beginX,beginY,finalX,finalY); 
  }
  
}
  public void settings() {  size(800,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Skai" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
