package solo;
import jm.music.data.*;
import jm.JMC;
import jm.util.*;
import jm.music.tools.*;
import java.util.ArrayList;

/**
 * a class to create a random solo phrase
 * fit it to a specified key
 * @author Aaron Hill
 */
public class Solo
{
  //FIELDS
  private SoloNode head;
  //CONSTRUCTORS
  public Solo (SoloNode head)
  {
    this.head = head;
  }
  
  public Solo(double rhythmTotal, int pitchMin, int pitchMax)
  {
    /**
     * creates a random solo with a total rhythm of rhythmTotal
     * with notes with pitches from pitchMin to pitchMax
     */
    int randNum = 0;
    double rValue = (.25 * (1 + (int) (Math.random() * 4)));
    double currentTotalRhythm = 0.0 + rValue; //stores the total rhythm of the solo
    SoloNode head = new SoloNode (new Note ((pitchMin + (int)(Math.random()* (pitchMax - pitchMin))),rValue));
    SoloNode current = head;
    
    while (currentTotalRhythm < rhythmTotal)
    {
      //creates random integer from 1 to 4. 
      randNum = 1 + (int)(Math.random() * 4);
      if (randNum == 1) //if randInt is 1, create a random note of rhythm .25
      {
        current.next = new SoloNode (new Note ((pitchMin + (int)(Math.random()* (pitchMax - pitchMin))), .25));
        currentTotalRhythm = currentTotalRhythm + .25;
        current = current.next;
      }
      
      if (randNum == 2 && ((currentTotalRhythm + .50) <= rhythmTotal)) //if randInt is 2, create a random note of rhythm .50
      {
        current.next = new SoloNode (new Note ((pitchMin + (int)(Math.random()* (pitchMax - pitchMin))), .50));
        currentTotalRhythm = currentTotalRhythm + .50;
        current = current.next;
      }
      if (randNum == 3 && ((currentTotalRhythm + .75) <= rhythmTotal)) //if randInt is 3, create a random note of rhythm .75
      {
        current.next = new SoloNode (new Note ((pitchMin + (int)(Math.random()* (pitchMax - pitchMin))), .75));
        currentTotalRhythm = currentTotalRhythm + .75;
        current = current.next;
      }
      if (randNum == 4 && ((currentTotalRhythm + 1) <= rhythmTotal)) //if randInt is 4, create a random note of rhythm 1
      {
        current.next = new SoloNode (new Note ((pitchMin + (int)(Math.random()*(pitchMax - pitchMin))), 1));
        currentTotalRhythm = currentTotalRhythm + 1;
        current = current.next;
      }
      //System.out.println(currentTotalRhythm);
    }
    this.head = head;
  }
//METHODS
  /*private int[] offsetList(int root, int[] scale)
  {
    int[] offSetList = new int[7];
    int modRoot = root % 12;
    //loops through the scale array and adds the modded value of
    //root to the scale array
    for(int i = 0; i < offSetList.length; i++)
      offSetList[i] = modRoot + scale[i];
    return offSetList; 
  }*/
  
  private boolean correctKey(int root, int pitch, int[] scale)
    {
    /**
     * checks to see if a given pitch is in key,
     * given the root and scale for it.
     */
      for(int i = 0; i < scale.length; i++)
      {
        if(((Math.abs(pitch + 12 - root))%12) == scale[i])
          return true;
      }
      return false;
    }
  
  public void fitKey(int root, int[] scale)
  {
    /**
     * goes through a list of notes and fits all of them
     * to the specified key using the correctKey method
     * the root, and the scale
     */
    SoloNode current = head;
      while(current != null)
      {
        if(correctKey(root, current.note.getPitch(), scale))
          current = current.next;
        else
        {
          current.note.setPitch(current.note.getPitch() + 1);
        }
      }
      
    /*SoloNode current = head;
    int noteModValue = 0;
    //stores the list of offsets taken from the offsetList method
    int[] offSetList = offsetList(root, scale);
    while (current.next != null)
    {
      //applies mod 12 to the current note's pitch
      noteModValue = current.note.getPitch() % 12;
      //will loop through the ints in offSetList, checking the offsets against
      //noteModValue
      for(int i = 0; (i < 1 + offSetList.length) || (noteModValue == offSetList[i]); i++)
      {
        //if the moteModValue is in offSetList, the pitch is in key, and moves on to the next note 
        if (noteModValue == offSetList[i])
        {
          current = current.next;
        }
        //else, the pitch will be incremented by 1 and the program will loop
        else if ((noteModValue != offSetList[i]) && (i == 1 + offSetList.length))
        {
          current.note.setPitch(current.note.getPitch() + 1);
        }
      }
      current = current.next;
    }*/
  }
  
  private SoloNode getLast()
  {
    /**
     * returns the last SoloNode in the given
     * linked list
     */
    SoloNode current = head;
    while (current.next != null)
    {
      current = current.next;
    }
    return current;
  }
  
  public void reverse()
    {
      SoloNode prev;
      SoloNode curr;
      SoloNode fut;
      prev = head;
      curr = prev.next;
      fut = curr.next;
      prev.next = null;
      
      while(fut != null)
      {
        curr.next = prev;
        prev = curr;
        curr = fut;
        fut = curr.next;
      }
      curr.next = prev;
      head = curr;   
    }
  public String getRMA()
  {
    /**
     * returns the RMA for the given solo
     * in a string
     */
    StringBuffer finalStringBuffer = new StringBuffer();
    SoloNode current = head;
    while(current.next.next != null)
    {
      if (current.note.getPitch() > current.next.note.getPitch())
      {
        finalStringBuffer.append("D");
        current = current.next;
      }
      if (current.note.getPitch() < current.next.note.getPitch())
      {
        finalStringBuffer.append("U");
        current = current.next;
      }
      else if (current.note.getPitch() == current.next.note.getPitch())
      {
        finalStringBuffer.append("E");
        current = current.next;
      }
    }
    String finalString = new String(finalStringBuffer); 
    return finalString;
  }
  
  public Phrase getPhrase()
  {
    /**
     * returns the phrase from a set of SoloNodes
     */
      
    Phrase phr = new Phrase();
    SoloNode current = head;
    while(current != null)
    {
      phr.add(current.note);
      current = current.next;
    }
    return phr;
  }
  
  public static void main(String [] args)
  {
   SoloNode head = new SoloNode(new Note(62, 0.5),
new SoloNode(new Note(65, 0.5),
new SoloNode(new Note(69, 0.5),
new SoloNode(new Note(67, 0.5),
new SoloNode(new Note(69, 0.5),
new SoloNode(new Note(69, 0.5),
new SoloNode(new Note(72, 0.5),
new SoloNode(new Note(74, 0.5)
))))))));
   
   Solo solo1 = new Solo(head);
   View.notate(new Phrase(solo1.getPhrase().copy().getNoteArray(),
                     "Manual Solo Original"), 20, 50);
   System.out.println(solo1.getRMA());
   solo1.fitKey(74, JMC.MAJOR_SCALE);
   View.notate(new Phrase(solo1.getPhrase().copy().getNoteArray(),
                          "Manual Solo In D Major"), 20, 250);
   
   Solo solo2 = new Solo(16.0, JMC.E2, JMC.E6);
   View.notate(new Phrase(solo2.getPhrase().copy().getNoteArray(),
                          "Random Solo Orignal"), 600, 50);
   solo2.fitKey(74, JMC.MAJOR_SCALE);
   View.notate(new Phrase(solo1.getPhrase().copy().getNoteArray(),
                          "Random Solo fitted"), 20, 250);
  }
}