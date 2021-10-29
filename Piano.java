import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.sound.midi.*;

/**
 * Implements a simulated piano with 36 keys.
 */
public class Piano extends JPanel {
	// DO NOT MODIFY THESE CONSTANTS
	public static int START_PITCH = 48;
	public static int WHITE_KEY_WIDTH = 40;
	public static int BLACK_KEY_WIDTH = WHITE_KEY_WIDTH/2;
	public static int WHITE_KEY_HEIGHT = 200;
	public static int BLACK_KEY_HEIGHT = WHITE_KEY_HEIGHT/2;
	public static int NUM_WHITE_KEYS_PER_OCTAVE = 7;
	public static int NUM_OCTAVES = 3;
	public static int NUM_WHITE_KEYS = NUM_WHITE_KEYS_PER_OCTAVE * NUM_OCTAVES;
	public static int WIDTH = NUM_WHITE_KEYS * WHITE_KEY_WIDTH;
	public static int HEIGHT = WHITE_KEY_HEIGHT;
		
	private ArrayList<Key> _keys = new ArrayList<>();
	private Receiver _receiver;
	private PianoMouseListener _mouseListener;

	/**
	 * Returns the list of keys in the piano.
	 * @return the list of keys.
	 */
	public java.util.List<Key> getKeys () {
		return _keys;
	}

	/**
	 * Sets the MIDI receiver of the piano to the specified value.
	 * @param receiver the MIDI receiver 
	 */
	public void setReceiver (Receiver receiver) {
		_receiver = receiver;
	}

	/**
	 * Returns the current MIDI receiver of the piano.
	 * @return the current MIDI receiver 
	 */
	public Receiver getReceiver () {
		return _receiver;
	}

	// DO NOT MODIFY THIS METHOD.
	/**
	 * @param receiver the MIDI receiver to use in the piano.
	 */
	public Piano (Receiver receiver) {
		// Some Swing setup stuff; don't worry too much about it.
		setFocusable(true);
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		setReceiver(receiver);
		_mouseListener = new PianoMouseListener(_keys);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		makeKeys();
	}

	/**
	 * Returns the PianoMouseListener associated with the piano.
	 * @return the PianoMouseListener associated with the piano.
	 */
	public PianoMouseListener getMouseListener () {
		return _mouseListener;
	}
	// TODO: implement this method. You should create and use several helper methods to do so.
	/**
	 * Instantiate all the Key objects with their correct polygons and pitches, and
	 * add them to the _keys array.
	 */
	private void makeKeys (){
		int X = 0;
		int startPitch = START_PITCH;
		for(int counter = 0; counter < 3; counter++){
			makeOcative(X,startPitch);
			X += 7  * WHITE_KEY_WIDTH;
			startPitch += 12;
		}
	}

	/**
	 * instantiate one Ocative on piano start from given coordinate
	 * @param startX the int value to be used in makeOcative
	 * @param startPitch the int value to be used in makeOcative
	 */
	private void makeOcative(int startX,int startPitch){
		System.out.println("ocative placed, start point x: " + startX);
		int pitch = startPitch;
		int start = startX;
		for(int counter = 0; counter < 7; counter++){
			System.out.println("expected black key palce: " + start);
			makeKey(start, pitch,true);
			pitch++;
			start += WHITE_KEY_WIDTH;
		}
		start = startX;
		for(int counter = 0; counter < 7; counter++){
			if(counter != 2 && counter != 6){
				makeKey(start, pitch, false);
				pitch++;
			}
			start += WHITE_KEY_WIDTH;
		}

	}

	/**
	 * instinate one single key with given coordinate, pitch and if it's white or not
	 * @param startX the int value to be used in makeKey
	 * @param pitch the int value to be used in makeKey
	 * @param isWhite the boolean valu to be used in makeKey
	 */
	private void makeKey(int startX, int pitch, boolean isWhite){
		int[] x;
		int[] y;
		System.out.println("is white?: " + isWhite);
		if(isWhite){
			x = new int[]{startX, startX + WHITE_KEY_WIDTH, startX + WHITE_KEY_WIDTH, startX};
			y = new int[]{0, 0, WHITE_KEY_HEIGHT, WHITE_KEY_HEIGHT};
			System.out.println("white key: " + x[0] + ' ' + x[1]);
		}else{
			x = new int[]{startX + WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2, startX + WHITE_KEY_WIDTH + BLACK_KEY_WIDTH/2,
					      startX + WHITE_KEY_WIDTH + BLACK_KEY_WIDTH/2, startX + WHITE_KEY_WIDTH - BLACK_KEY_WIDTH/2};
			y = new int[]{0,0,BLACK_KEY_HEIGHT, BLACK_KEY_HEIGHT};
			System.out.println("black key: " + x[0] + ' ' + x[1]);
		}
		Polygon block = new Polygon(x, y,x.length);
		Key key = new Key(block,isWhite,pitch,this);
		_keys.add(key);
	}


	// DO NOT MODIFY THIS METHOD.
	@Override
	/**
	 * Paints the piano and all its constituent keys.
	 * @param g the Graphics object to use for painting.
	 */
	public void paint (Graphics g) {
		// Delegates to all the individual keys to draw themselves.
		for (Key key: _keys) {
			key.paint(g);
		}
	}
}
