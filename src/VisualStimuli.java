

import java.util.*;
import javax.microedition.lcdui.*;

public class VisualStimuli extends Canvas implements Runnable 
{
    private int w,h ;
	private Image actualScreen;

    public VisualStimuli(Display display){
    	setFullScreenMode(true);
		w = getWidth() ;
		h = getHeight() ;
        display.setCurrent( this );
        if (!isDoubleBuffered () && false)
			actualScreen = Image.createImage (getWidth (), getHeight ());
        run() ;
    }

    protected void paint( Graphics g ){
    	Graphics gActual = actualScreen == null ? g  : actualScreen.getGraphics ();
    	if(firstDraw==-1){
    		gActual.setColor(255,255,255);
    		gActual.fillRect(0, 0, w, h);
    		firstDraw=-2 ;
    	}else if(firstDraw==-2){
        	Random rnd = new Random();
        	int rndNum = rnd.nextInt(100);
        	if(rndNum<50)
        		firstDraw = 0 ;
        	else
        		firstDraw = 1 ;
        	drawRect(firstDraw,gActual);
    	}else{
    		if(firstDraw>0)
        		drawRect(0,gActual);
        	else
        		drawRect(1,gActual);
	    	gActual.drawString("Actual threshold = "+thresholdTime+" ms", w/5, h/2,Graphics.TOP|Graphics.LEFT);
	    	gActual.drawString("First = "+firstDraw, w/5,(int)( ((float)3/4)*h),Graphics.TOP|Graphics.LEFT);
    	}
    	
    	if (actualScreen != null) 
			g.drawImage (actualScreen, 0, 0, Graphics.TOP | Graphics.LEFT); 
    }

    int thresholdTime = 1000 ;
    int firstDraw = -1 ;
       
    private void drawRect(int i,Graphics g) {
    	int stepX = w/5;
    	int stepY = h/4;
    	g.setColor(31, 115, 193);
		if(i<1)	
			g.fillRect(stepX, stepY, stepX, stepY) ;
		else
			g.fillRect(3*stepX, stepY, stepX, stepY) ;
	}


    int thresholdStepUp = 10 ; 
    float timeReduction = 1 ;
    public void keyPressed (int keyCode){
    	boolean correct = false ;
    	
    	if( ( getGameAction(keyCode) == LEFT || keyCode == KEY_NUM4 ) && firstDraw==0 )
    		correct = true ;
    	else if( ( getGameAction(keyCode) == RIGHT || keyCode == KEY_NUM6 ) && firstDraw==1 )
    		correct = true ;
    	else if( getGameAction(keyCode) == 8 || keyCode == KEY_NUM5 ) 
    		MobiBoy.getInstance().exitMIDlet() ;

		firstDraw = -1 ;
		if(correct)
			thresholdTime = (int)((float)thresholdTime/(1.0+timeReduction)) ;
		else{
			thresholdTime += thresholdStepUp ; // add thresholdStepUp ms in delay if answer was wrong
			timeReduction /= 2.0 ; // make step smaller to train processing speed limit
		}
		this.run() ;
    }

	public void run() {
		repaint();
		serviceRepaints();
		System.out.println("Starting sleep "+800);
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
		serviceRepaints();
		System.out.println("Starting sleep "+thresholdTime);
		try {
			Thread.sleep(thresholdTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
		serviceRepaints();
	}
}