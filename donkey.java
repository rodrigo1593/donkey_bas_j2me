/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.microedition.media.MediaException;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.Display;
//import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.lcdui.game.*;
/**
 * @author rodrigo2
 */
public class donkey extends MIDlet {
    Display display=Display.getDisplay(this);
    Image spr_donkey;
    Image spr_car_menu;
    Image spr_car;
    int donkeyx=0;
    int donkeyy=320;
    int screen=0;
    int carx=228;
    int cary=590;
    int p1=92;
    int p2=228;
    int n;
    int score=0;
    int score_=1;
    int full=0;
   // int donkeysnd=0;
   // int donkeysndt=0;
    Random random=new Random();

    Player player;
    InputStream sound;

    public int choose(int c1, int c2){
        n=random.nextInt(2);
        //int n2;
//        n2=random.nextInt(4);
  //      for(int i=0;i < n2;i++){
     //   n=random.nextInt(2);
   //     }
        if (n==0){n=c2;}else{n=c1;}
        //if (n==1){n=c1;}
        //if (n==2){n=c2;}
       return n;
    }

    public void begingame(){
        score=0;
        score_=1;
                donkeyx=choose(92,228);
                donkeyy=0;
                try {
                    //player.stop();
                    player.deallocate();
                    player.close();
                    sound.close();
                    sound=getClass().getResourceAsStream("donkey.wav");
                    player= Manager.createPlayer(sound, "audio/X-wav");
                    //player.realize();
                    //player.prefetch();
                    player.start();
                } catch (MediaException ex) {} catch (Exception e){} {
                }
 screen=1;
    }



    
    //Canvas canvas=new Canvas(){

    GameCanvas gamecanvas=new GameCanvas(false) {
        Graphics d=getGraphics();
                protected void pointerPressed(int x, int y) {
                if (screen==0){
                begingame();
                }

                if (screen==1){
                if (carx==p1){carx=p2;}else{carx=p1;}
                }
                if (screen==2){
                begingame();
                }

                    super.pointerPressed(x, y);
                }

                public void paint(Graphics d) {
                
                if (full==0){
            super.setFullScreenMode(true);
            full=1;
            }

            try{
            if (screen==0){
            d.setColor(21, 71, 52);
            d.fillRect(0, 0, 360, 640);
            d.setColor(255,255,255);
            d.drawString("DONKEY!!!" +"    touch to begin",120,0,Graphics.TOP | Graphics.LEFT);
            donkeyx+=9;

            //d.fillRect(donkeyx,donkeyy,32,32);
            d.drawImage(spr_car_menu, donkeyx, donkeyy, Graphics.TOP | Graphics.LEFT);

            if (donkeyx >  360){
                donkeyx=-64;
            }

            }

            if (screen==1){

                 if (carx >= donkeyx && carx <= donkeyx+spr_donkey.getWidth() && cary >= donkeyy && cary <= donkeyy+spr_donkey.getHeight()){
                     player.deallocate();
                     player.close();
                     sound.close();
                    sound=getClass().getResourceAsStream("gameover.wav");
                        player = Manager.createPlayer(sound, "audio/X-wav");
                        //player.realize();
                        //player.prefetch();
                        player.start();
                        sound.close();
                        screen=2;

                }

                //positions for donkey and car
                int xp=40;
                d.setColor(8,228,52);
                d.fillRect(0, 0, 360, 640);

                //two rects for the path the donkey will move + the car will place.
                d.setColor(128,128,128);
                d.fillRect(64,0,96,640);
                d.fillRect(200,0,96,640);

                d.setColor(255,255,255);

                for(int i=0;i<20;i++){
                d.fillRect(64+xp, i*32, 8, 16);

                d.fillRect(200+xp, i*32, 8, 16);
                }

                donkeyy+=16+score_; //19 + score_
                if (donkeyy > 640){
                    //use score to threat the donkey to go faster (?)
                    score+=1;
                    donkeyx=choose(92,228);
                        //player.realize();
                        //player.prefetch();

                        if (player.getState()!= player.STARTED){
                        player.start();
                        }
                    if (score > 11 * score_){
                        score_+=1;
                    }
                    donkeyy=0;
                }

                //d.fillRect(carx,cary,32,32);
                //d.fillRect(donkeyx, donkeyy, 32, 32);
                d.drawImage(spr_car, carx, cary, Graphics.TOP | Graphics.LEFT);
                d.drawImage(spr_donkey, donkeyx, donkeyy, Graphics.TOP | Graphics.LEFT);

                //collision
                d.setColor(255,255,255);
                d.drawString(String.valueOf(score),15,15,Graphics.TOP | Graphics.LEFT);

            }

            if (screen==2){
                d.setColor(0,0,0);
                d.fillRect(0, 0, 360, 640);
                d.setColor(255,255,255);
                d.drawString("Gameover (touch to reset)",50,120,Graphics.TOP | Graphics.LEFT);
                d.drawString("Original By Bill Gates and Neil Konzen",0,560,Graphics.TOP|Graphics.LEFT);
                d.drawString("Pico-8 version by NITELITE",0,578,Graphics.TOP|Graphics.LEFT);
                d.drawString("J2ME version by rodrigo1593",0,596,Graphics.TOP|Graphics.LEFT);
                
            }
            Thread.sleep(30);
            }

            catch (IOException ex) {
                        //ex.printStackTrace();
                    } catch (MediaException ex) {
                        //ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    super.paint(d);
                    super.repaint();
                    //super.flushGraphics(0, 0, 360, 640);
                }

            };

    public void startApp() {
        sound=getClass().getResourceAsStream("menu.wav");
        //isnd_donkey=getClass().getResourceAsStream("donkey.wav");
        //isnd_gameover=getClass().getResourceAsStream("gameover.wav");
        try {
        //images
        spr_donkey=Image.createImage("donkey.png");
        spr_car_menu=Image.createImage("car_menu.png");
        spr_car=Image.createImage("car.png");

        //audio player
        player= Manager.createPlayer(sound,"audio/X-wav");
        //psnd_donkey= Manager.createPlayer(isnd_donkey,"audio/X-wav");

        //psnd_gameover= Manager.createPlayer(isnd_gameover,"audio/X-wav");
        //player.realize();
        //player.prefetch();
        player.start();
        }catch (MediaException ex){} catch (Exception e){
        }
        //display.setCurrent(canvas);

        display.setCurrent(gamecanvas);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
