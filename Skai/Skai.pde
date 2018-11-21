//////////////////////////////////////////////////
// Made by Teresa Goela & Rafael Duarte 
// LEI-Labratório de Programação 2017/18
///////////////////////////////////////////////////
import processing.sound.*;
SoundFile song;
import gifAnimation.*;
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


void setup(){
  size(800,600);
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

void draw(){  //draw calls levels and menus depending on the value of the global variable "start"
   
  if(start==-1){
    
    startMenu(); 
    soundButton();
    image(myAnimation,width*0.24,height*0.2,250,150);
    image(myAnimation_2,width*0.8,height*0.9,250,150);
    
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

void startMenu(){  //start menu interface
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
  image(myAnimation_3,width*0.5,height*0.9,250,300);
  text("Rafael Duarte & Teresa Goela", width*0.5,height*0.99);
  
  textSize(17); 
  strokeWeight(2);
  fill(120,80,80);
  //if(start<1){
  if(mouseX>width*0.1 && mouseX<width*0.3 && mouseY>height*0.6 && mouseY<height*0.7){
    fill(110,98,255,150); 
    if(mousePressed)
       start=1;
       newAstros();
  }
    rect(width*0.1,height*0.6,width*0.2,height*0.1);
    fill(255);
    text("Start",width*0.2,height*0.66);
    fill(120,80,80);
    if((mouseX>width*0.4 && mouseX<width*0.6 && mouseY>height*0.6 && mouseY<height*0.7)){
      fill(110,98,255,150);
      if(mousePressed)
      start=-2;
    //levels menu here      
  }
    rect(width*0.4,height*0.6,width*0.2,height*0.1);
    fill(255);
    text("Levels",width*0.5,height*0.66);
    fill(120,80,80);
    if((mouseX>width*0.7 && mouseX<width*0.9 && mouseY>height*0.6 && mouseY<height*0.7)){
      fill(110,98,255,150); 
      if(mousePressed)
       start=0;
  }
    rect(width*0.7,height*0.6,width*0.2,height*0.1);
    fill(255);
    text("instructions",width*0.8,height*0.66);
  
}

void endLvlMenu(){   //end level menu - only pops up when variable endLevel = 1
   textFont(normal);
   noFill();
   rectMode(CENTER);
   noStroke();
   fill(203,203,203,80);
   rect(width/2,height/2, width*0.35,height*0.5);
   strokeWeight(3);
   fill(80,120,80);
   rect(width/2,height*0.5,width*0.24,height*0.1);
   fill(120,80,80);
   rect(width/2,height*0.63,width*0.24,height*0.1);
   fill(255);
   strokeWeight(2);
   textAlign(CENTER,CENTER);
   text("Next Level",width/2,height*0.5);
   text("Replay",width/2,height*0.63);
   text("Time: " + endTime + " s", width/2, height*0.35);
   if(mouseX>width/2-width*0.12 && mouseX<width/2+width*0.12 && mouseY>height*0.63-height*0.05 && mouseY<height*0.63+height*0.05 && mousePressed){
     endLevel=0;
     restart();
   }
   if(mouseX>width/2-width*0.12 && mouseX<width/2+width*0.12 && mouseY>height*0.5-height*0.05 && mouseY<height*0.5+height*0.05 && mousePressed && start<levelnames.length){
    start++;  
    restart();
    endLevel=0;
    
   }  
}

void levelsMenu(){
  //if(start<-1){
    textFont(normal);
    stroke(0);
    strokeWeight(2);
  background(menu_background);
  rectMode(CORNER);
  
    fill(120,80,80);
    if(mouseX>width*0.140 && mouseX<width*0.14+width*0.15 && mouseY>height*0.45 && mouseY<height*0.45+height*0.1){
      fill(110,98,255,150);
     if(mousePressed){
       start=1;
       newAstros();
     }
    }
    rect(width*0.140,height*0.45,width*0.15,height*0.1);  
    fill(255);
    text("1",width*0.215,height*0.52);
    fill(120,80,80);
    if(mouseX>width*0.330 && mouseX<width*0.33+width*0.15 && mouseY>height*0.45 && mouseY<height*0.45+height*0.1){
      fill(110,98,255,150);
     if(mousePressed){
      start=2;
      newAstros();
     }
    }
    rect(width*0.330,height*0.45,width*0.15,height*0.1);
    fill(255);
    text("2",width*0.405,height*0.52);
    fill(120,80,80);
    if(mouseX>width*0.52 && mouseX<width*0.52+width*0.15 && mouseY>height*0.45 && mouseY<height*0.45+height*0.1){
      fill(110,98,255,150);
     if(mousePressed){
       start=3;
       newAstros();
     }
    }
    rect(width*0.520,height*0.45,width*0.15,height*0.1);
    fill(255);
    text("3",width*0.595,height*0.52);
    fill(120,80,80);
    if(mouseX>width*0.71 && mouseX<width*0.71+width*0.15 && mouseY>height*0.45 && mouseY<height*0.45+height*0.1){
      fill(110,98,255,150);
     if(mousePressed){
      start=4;
      newAstros();
     }
    }
     rect(width*0.710,height*0.45,width*0.15,height*0.1);
     fill(255);
     text("4",width*0.785,height*0.52);
 // }
}


void displayInstructions(){
  textFont(normal);
  background(menu_background);
  rectMode(CENTER);
  noStroke();
  fill(157,157,157,80);
  rect(width/2,height*0.45,600,300);
  fill(255,255,255); stroke(0);
  text("INSTRUCTIONS",width/2,height*0.31);
  text("Connect the planets with the stars without\n intersecting the lines making a closed cirtcuit.\nIf your astro is moving that means\nit's still disconnected or the first clicked on.\n See how fast you are.",width/2,height*0.4);
}

void soundButton(){
  if(playSound==1)
  image(sound,width*0.9,height*0.052,30,30);
  noFill();
  noStroke();
  rect(width*0.9,height*0.052,30,30);
  if(playSound==-1){
    image(nosound,width*0.9,height*0.052,30,30);
    }
}

void displayHomeButton(){
  imageMode(CENTER);
  image(homebutton,width*0.85,height*0.05,30,30);
  rectMode(CENTER);
  noStroke();
  noFill();
  rect(width*0.85,height*0.05,30,30);
  if(mousePressed && mouseX>width-width*0.15-12.5 && mouseX<width-width*0.15+12.5 && mouseY>height*0.025 && mouseY<height*0.07){
    start=-1;
    endLevel=0;
    restart();
  }
}

void displayRestartButton(){
  image(button,width*0.8,height*0.05,25,25);
  rectMode(CENTER);
  noStroke();
  noFill();
  rect(width*0.8,height*0.05,25,25);
  if(mousePressed && mouseX>width-width*0.2-12.5 && mouseX<width-width*0.2+12.5 && mouseY>height*0.025 && mouseY<height*0.07){
    endLevel=0;
    restart();
  }   
}

void displayLevel(){
   fill(255);
   textAlign(CENTER);
   textSize(25);
   textFont(normal);
   text("Level",width*0.1,height*0.06);
   text(start,width*0.15,height*0.06);
}

void displayParts(){
  fill(255);
  textAlign(CENTER);
   textSize(25);
   textFont(normal);
   text("Parts "+ part + "/" + lines.length,width*0.25,height*0.06);
}

void restart(){ //function to restart the the game - needs to be called after finishing a game in order to restart all global variables needed
  startTime=millis();
  lastClicked=-1;
  firstClicked=-1;
  iLines=0;
  newAstros();
  part=0;
}

void level_1(){ 
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

void level_2(){
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

void level_3(){ 
   
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
    astros[0].moveY(2,astros[0].yi+height*0.6);
  astros[0].display();
  if(lastClicked!=9 && firstClicked!=9 && !astros[9].isFilled)
    astros[9].moveY(2,astros[9].yi+height*0.6);
  astros[9].display();
  
  
  for(int i= 1; i<astros.length-1; i++){  //this time we only display astros from 1 to 8
   astros[i].display();
   if(!astros[i].isFilled && !astros[i].clicked() && i!= lastClicked)
     astros[i].shake(astros[i].xi,astros[i].yi);     
  }
   
}

void level_4(){ 
   
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
    astros[4].moveY(2,astros[4].yi+height*0.25);
    astros[4].moveX(2,astros[4].xi+height*0.25);
  }
  astros[4].display();
   if(lastClicked!=5 && firstClicked!=5 && !astros[5].isFilled){
    astros[5].moveY(2,astros[5].yi+height*0.25);
    astros[5].moveX(2,astros[5].xi+height*0.25);
  }
  astros[5].display();
   if(lastClicked!=6 && firstClicked!=6 && !astros[6].isFilled){
    astros[6].moveY(2,astros[6].yi+height*0.25);
    astros[6].moveX(2,astros[6].xi+height*0.25);
  }
  astros[6].display();
   if(lastClicked!=7 && firstClicked!=7 && !astros[7].isFilled){
    astros[7].moveY(2,astros[7].yi+height*0.25);
    astros[7].moveX(2,astros[7].xi+height*0.25);
  }
  astros[7].display();
  
  
  for(int i= 0; i<astros.length-4; i++){  //0 to 4 are the static shaking astros
   astros[i].display();
   if(!astros[i].isFilled && !astros[i].clicked() && i!= lastClicked)
     astros[i].shake(astros[i].xi,astros[i].yi);     
  }
   
}

void keyPressed(){ 
}

void newAstros(){ //this is the function that constructs the astros array. For each level it makes new "astros" spawn in different, pre-written positions
  if(start==1){    // needs to be called after altering the value of the "start" variable so it can then construct the astros array for the desired level
  astros= new Astro[10];
  lines = new Line[astros.length];
  astros[0]= new Astro(width*0.1,height*0.2,40,1,astro_textures[0]);
  astros[1]= new Astro(width*0.25,height*0.3,40,1,astro_textures[0]);
  astros[2]= new Astro(width*0.25,height*0.45,40,1,astro_textures[0]);
  astros[3] = new Astro(width*0.25,height*0.6,40,1,astro_textures[0]);
  astros[4] = new Astro(width*0.25,height*0.75,40,1,astro_textures[0]);
  astros[5] = new Astro(width*0.1,height*0.85,50,-1,astro_textures[1]);
  astros[6] = new Astro(width*0.85,height*0.3,50,-1,astro_textures[2]);
  astros[7] = new Astro(width*0.85,height*0.45,50,-1,astro_textures[3]);
  astros[8] = new Astro(width*0.85,height*0.6,50,-1,astro_textures[4]);
  astros[9] = new Astro(width*0.85,height*0.75,50,-1,astro_textures[5]);
}
  if(start==2){
  astros= new Astro[10];
  lines = new Line[astros.length];
  astros[0]= new Astro(width*0.5,height*0.2,50,-1,astro_textures[5]);
  astros[1]= new Astro(width*0.25,height*0.3,40,1,astro_textures[0]);
  astros[2]= new Astro(width*0.75,height*0.3,50,-1,astro_textures[1]);
  astros[3] = new Astro(width*0.15,height*0.5,50,-1,astro_textures[2]);
  astros[4] = new Astro(width*0.35,height*0.5,40,1,astro_textures[0]);
  astros[5] = new Astro(width*0.65,height*0.5,50,-1,astro_textures[3]);
  astros[6] = new Astro(width*0.85,height*0.5,40,1,astro_textures[2]);
  astros[7] = new Astro(width*0.25,height*0.7,40,1,astro_textures[3]);
  astros[8] = new Astro(width*0.75,height*0.75,50,-1,astro_textures[4]);
  astros[9] = new Astro(width*0.5,height*0.8,40,1,astro_textures[0]);
}
  if(start==3){
  astros = new Astro[10];
  lines = new Line[astros.length];
  astros[0]= new Astro(width*0.1,height*0.25,40,1,astro_textures[0]);
  astros[1]= new Astro(width*0.3,height*0.4,40,1,astro_textures[0]);
  astros[2]= new Astro(width*0.3, height*0.55,40,1,astro_textures[0]);
  astros[3]= new Astro(width*0.5,height*0.3,50,-1,astro_textures[1]);
  astros[4]= new Astro(width*0.5,height*0.42,50,-1,astro_textures[2]);
  astros[5]= new Astro(width*0.5,height*0.54,50,-1,astro_textures[3]);
  astros[6]= new Astro(width*0.5,height*0.66,50,-1,astro_textures[4]);
  astros[7]= new Astro(width*0.7,height*0.4,40,1,astro_textures[0]);
  astros[8]= new Astro(width*0.7,height*0.55,40,1,astro_textures[0]);
  astros[9]= new Astro(width*0.9,height*0.25,50,-1,astro_textures[5]);
}

if(start==4){
  astros = new Astro[8];
  lines = new Line[astros.length];
  astros[0]= new Astro(width*0.2,height*0.20,40,1,astro_textures[0]);
  astros[5]= new Astro(width*0.18,height*0.56,40,1,astro_textures[0]);
  astros[2]= new Astro(width*0.5,height*0.45,50,-1,astro_textures[1]);
  astros[3]= new Astro(width*0.85,height*0.75,50,-1,astro_textures[4]);
  astros[4]= new Astro(width*0.10, height*0.64,40,1,astro_textures[0]);
  astros[1]= new Astro(width*0.4,height*0.55,40,1,astro_textures[0]);
  astros[6]= new Astro(width*0.58,height*0.12,50,-1,astro_textures[2]);
  astros[7]= new Astro(width*0.50,height*0.20,50,-1,astro_textures[3]);
 }
} 

void mousePressed(){ 
  if(mouseX>width-width*0.1-15 && mouseX<width-width*0.10+15 && mouseY>height*0.025 && mouseY<height*0.07){ //here is where we can turn sound on and off
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

boolean intersects(float a,float b,float c,float d,float p, float q,float r,float s) { // returns true if the line from (a,b)->(c,d) intersects with (p,q)->(r,s)
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

Boolean checkIntersections(float x, float y, float fx, float fy){ //check intersections for all lines
  for(int i = 0; i<iLines; i++){
    if(intersects(x,y,fx,fy,lines[i].beginX,lines[i].beginY,lines[i].finalX,lines[i].finalY))//return true if this line intersects with any other line
      return true;
  }
  return false;
}

void loadLevels(){ //this loads the levels background images
  for(int i=0; i<levelnames.length;i++)
    levels[i] = loadImage(levelnames[i]);
}

void loadTextures(){ //this loads our textures, like astro's skins
 for(int i = 0; i<texture_names.length; i++)
   astro_textures[i]=loadImage(texture_names[i]);
}
