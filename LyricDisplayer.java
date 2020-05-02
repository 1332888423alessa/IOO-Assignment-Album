import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException; 

public class LyricDisplayer {
	private Scanner kb;
	private Album album;

	private final int EXIT = 0;
    private final int SONGFILEEMPTY = 500; 
    
    public LyricDisplayer(){
    	kb = new Scanner( System.in );
    	album = new Album();
    }
    
    public static void main( String [ ] args ) throws IOException{
    	LyricDisplayer displayer = new LyricDisplayer();
    	//If the args parameter is empty
    	if(args==null||args.length==0){
    		displayer.processMain(displayer.SONGFILEEMPTY);
    		return;
    	}
    	String filename = args[0];    	
    	displayer.load(filename);
    	displayer.run();
    	System.out.println("-end-");
    }
    
    public void run( ) throws IOException{
        int choice = -1;		
        while( choice != EXIT ){
        	displayKaraokeMainMenu( );
            System.out.print( "\n Enter a number to select a song or type 0 to exit the system:" );
            choice = kb.nextInt( );
            kb.nextLine( );
            processMain( choice );
        }
    }
    
    public void displayKaraokeMainMenu( ){
    	if(this.album==null||this.album.getNextSong()==null){
    		System.out.println("info:Song list is empty");
    	}else{
    		System.out.println(this.album.getAlbumName());
    		Song p = this.album.getNextSong();
    		while(p!=null){
    			System.out.println(p.getSerialNumber() + "." + p.getTitle());
    			p = p.getNextSong();
    		}
    	}
    }
    
    public void processMain( int choice ) throws IOException{
        switch( choice ){
	        case EXIT: 
                System.out.println( "\nGood bye from Karaoke subsystem" );
                System.exit(0);
                break;
	        case SONGFILEEMPTY:
	        	System.out.println( "\nError info:The songlist file is empty" );
	        	choice = EXIT;
	        	break;
            default:
            	selectSong(choice);
                break;
        }
    } 
    
    public void displaySongMenu(Song song){ 
		System.out.println( "\n" + song.getSerialNumber() + "." + song.getTitle() );
		System.out.println( "\tA.Play it" );
		System.out.println( "\tB.Delete it from the menu and the album" );
		System.out.println( "\tC.Add a new song behind it" );  
    }
    
    
    
    public void selectSong(int serialNumber) throws IOException{
        //search the serial number to find the song
    	Song song = this.searchSongBySerialNumber(serialNumber);
    	if(song==null){
    		System.out.println("[==info==]Songs with serial number " + serialNumber + " do not exist" );
    		return;
    	}else{
    		String command = "A";
            while(command.startsWith("A")||command.startsWith("B")||command.startsWith("C")){
            	displaySongMenu(song);
            	System.out.print( "\n Select a function(Except for A,B,C,will return to main menu):" ); 
                command = kb.nextLine();
                processSong(command,song);
            }
    	}
         
    }
    
    public void processSong(String command,Song song){
    	switch(command){
    	   case "A":
    		   song.play();
    		   break;
    	   case "B":
    		   deleteSong(song);
    		   break;
    	   case "C":
    		   addSong(song);
    		   break; 
    	}
    }
    //Delete the Song
    public void deleteSong(Song song){
        // find previous node of song
    	Song p = album.getNextSong();
    	while(p.getNextSong()!=song){
    		p = p.getNextSong();
    	}
    	p.setNextSong(song.getNextSong());
        //change serial number of after songs
    	while(p.getNextSong()!=null){
    		p = p.getNextSong();
    		p.setSerialNumber(p.getSerialNumber()-1);
    	}
        //save
    	try {
			save("Album-new-delete.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void addSong(Song song){
        //For solo
    	System.out.print( " please input title>>>" );
    	String title = kb.nextLine();
    	System.out.print( " please input lines of Lyrics>>>" );
    	int lineNum = kb.nextInt();
        //construct a solo object
    	Solo solo = new Solo(lineNum);
    	solo.setTitle(title);
    	solo.setSerialNumber(song.getSerialNumber()+1);
    	solo.setType("S");
        //Firstly change the serial NO. of songs behind song
    	Song p = song.getNextSong();
    	while(p!=null){
    		p.setSerialNumber(p.getSerialNumber()+1);
    		p = p.getNextSong();
    	}
    	solo.setNextSong(song.getNextSong());
    	song.setNextSong(solo);    	
    	
    	System.out.println( " please input Lyric of eachline>>>" );
    	for(int i=1;i<=lineNum;i++){
    		System.out.println( i + " line Lyric(include seconds and lyric)>>>" );
    		double interval = kb.nextDouble();
    		String lyric = kb.nextLine();
    		Lyric lyricObj = new Lyric();
    		lyricObj.setInterval(interval);
    		lyricObj.setLyric(lyric);
    		solo.setLyric(i-1, lyricObj);    		
    	}
    	//save to file
		try {
			save("Album-new-add.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public void save(String filename) throws Exception{
    //create file output flow object
    	BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
    	if(album!=null){
    		writer.write(album.getAlbumName());
    		writer.newLine();
        //Output song info
    		Song s = album.getNextSong();
    		while(s!=null){
    			String content = s.getType() + " " + s.getSerialNumber() + " " + s.getTitle();
    			writer.write(content);
    			writer.newLine();
    			String lines = "";
    			if("S".equals(s.getType())){
    				Solo solo = (Solo)s;
    				lines = "" + solo.lyricLink().length;
    				writer.write(lines);
    				writer.newLine();
    				Lyric lyrics[] = solo.lyricLink();
    				for(Lyric lyric:lyrics){
    					String lyricContent = lyric.getInterval() + " " + lyric.getLyric();
    					writer.write(lyricContent);
    					writer.newLine();
    				}    				
    			}else{
    				Duet duet = (Duet)s;
    				lines = "" + duet.getLyrics1().length;
    				writer.write(lines + " " + lines);
    				writer.newLine();
    				Lyric singer1[] = duet.getLyrics1();
    				for(Lyric lyric:singer1){
    					String lyricContent = lyric.getInterval() + " " + lyric.getLyric();
    					writer.write(lyricContent);
    					writer.newLine();
    				}  
    				Lyric singer2[] = duet.getLyrics2();
    				for(Lyric lyric:singer2){
    					String lyricContent = lyric.getInterval() + " " + lyric.getLyric();
    					writer.write(lyricContent);
    					writer.newLine();
    				}
    			}
    			s = s.getNextSong();
    		}
    	}
    	if(writer!=null) writer.close();
    }
    
    private Song searchSongBySerialNumber(int serialNumber){
    	if(this.album==null || this.album.getNextSong()==null) return null;
    	Song p = this.album.getNextSong();
    	while(p!=null && p.getSerialNumber()!=serialNumber){
    		p = p.getNextSong();
    	}
    	return p;
    }
    
    
    public void load(String filename) throws IOException{
    	if(filename==null||"".equals(filename.trim())){
    		processMain(SONGFILEEMPTY);
    		return;
    	}
    	BufferedReader reader = new BufferedReader(new FileReader(filename));
        String eachLine = null;
        int lineNum = 0;
        String data[] = new String[200];
        while((eachLine=reader.readLine())!=null && !"".equals(eachLine.trim())){
            if(lineNum+1>200){ break;}
            data[lineNum++] = eachLine;
        }
        album.setAlbumName(data[0]);
        for(int i=1;i<lineNum;){
        	//Analyse song info
            String songinfo = data[i];
        	String[] songinfos = songinfo.split(" ");//split array
        	String type = songinfos[0];
        	int serialNumber = Integer.parseInt(songinfos[1]);
        	String songTtile = songinfo.substring((type+" " + serialNumber + " ").length());
        //Lines of Lyrics
        	String lyricLines = data[i+1];        	
        	int lyricLine = Integer.parseInt(lyricLines.split(" ")[0]);
        	Song song = null;
            //if it's solo
        	if("S".equalsIgnoreCase(type)){
                //create song 
        		Solo solo = new Solo(lyricLine);
        		solo.setType(type);
        		solo.setSerialNumber(serialNumber);
        		solo.setTitle(songTtile);
                //create lyric
        		for(int k=0;k<lyricLine;k++){
        			String lyrics = data[i+2+k];
        			String[] lyricinfo = lyrics.split(" ");
        			double interval = Double.parseDouble(lyricinfo[0]);
        			String lyric = lyrics.substring((lyricinfo[0] + " ").length());
                    //Add lyric
        			Lyric lyricObj = new Lyric(lyric,interval); 
                    //Put the lyric into Lyric array
        			solo.setLyric(k, lyricObj);   
        		} 
        		song = solo; 
        		i = i + lyricLine + 2; //+title and type+line
        	}else if("D".equalsIgnoreCase(type)){
            //if it's duet
                int lyricLine1 = Integer.parseInt(lyricLines.split(" ")[0]);
                int lyricLine2 = Integer.parseInt(lyricLines.split(" ")[1]);
        		Duet duet = new Duet(lyricLine1, lyricLine2);
                //Analyze info
        		duet.setType(type);
        		duet.setSerialNumber(serialNumber);
        		duet.setTitle(songTtile);
               //create lyric
        		for(int k=0;k<lyricLine1;k++){
                   //singer1 lyrics
        			String lyrics1 = data[i+2+k];
        			String[] lyricinfo1 = lyrics1.split(" ");
        			double interval1 = Double.parseDouble(lyricinfo1[0]);
        			String lyric1 = lyrics1.substring((lyricinfo1[0] + " ").length());
        			Lyric singer1 = new Lyric(lyric1,interval1);
	                   //Add lyrics to lyric array
        			duet.setLyrics1(k, singer1);
                }
                   //singer2 lyrics
        		for (int k=0; k<lyricLine2; k++) {
                	String lyrics2 = data[i+2+k+lyricLine1];
        			String[] lyricinfo2 = lyrics2.split(" ");
        			double interval2 = Double.parseDouble(lyricinfo2[0]);
        			String lyric2 = lyrics2.substring((lyricinfo2[0] + " ").length());
        			Lyric singer2 = new Lyric(lyric2,interval2);
                    //Add lyrics to lyric array
        			duet.setLyrics2(k, singer2); 
        		}        		
        		song = duet; //Give value to father class
        		i = i + lyricLine1+lyricLine2 + 2;
        	}
             //Put the analyzed song to the end list of lAlbum		
        	album.setNextSong(song);        	
        }

        if(reader!=null) reader.close();
    } 
}
