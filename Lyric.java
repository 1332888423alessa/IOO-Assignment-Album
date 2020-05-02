/*
 * Class Name:    Lyric
 *
 * Class Description:
 *
 * The Lyric class represents the lyrics. 
 *    the class contains  the following attributes:
 *    (1) lyric :one line of lyrics (String type)
 *    (2) interval : the time interval in seconds (double type). 
 */
public class Lyric {
	// one line of lyrics
	private String lyric;
	// the time interval in seconds
	private double interval;
		
	//Constructor method without parameters
	public Lyric(){}
	
	//Constructor method with two parameters	
	public Lyric(String lyric, double interval) {
		super();
		this.lyric = lyric;
		this.interval = interval;
	}

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}

	public double getInterval() {
		return interval;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	} 
	
	@Override
	public String toString() {
		return this.lyric + "(" + this.interval +")";
	}

}
