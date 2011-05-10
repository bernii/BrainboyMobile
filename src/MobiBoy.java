
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class MobiBoy extends MIDlet {

	public MobiBoy() {
		instance = this ;
	}
	
	private static MobiBoy instance ;
    // Display
    private Display display;
    // For the exit command
    private Command exitCommand;
 
    public void commandAction(Command command, Displayable displayable) {
    	System.out.println("Exitting application ..");
            if (command == exitCommand) {
                exitMIDlet();
            }
    }
        
    public static MobiBoy getInstance(){
    	return instance ;
    }
    public void startApp() {
        if( display == null ){
            initMIDlet();
        } 
    }
    
    private void initMIDlet() {
    	display = Display.getDisplay( this );
        new VisualStimuli(display);
	}

	// Your MIDlet should not call pauseApp(), only system will call this life-cycle method
    public void pauseApp() {
    }
 
    // Your MIDlet should not call destroyApp(), only system will call this life-cycle method    
    public void destroyApp(boolean unconditional) {
    }
 
    public void exitMIDlet() {
        display.setCurrent(null);
        notifyDestroyed();
    }

}
