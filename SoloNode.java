package solo;

import jm.music.data.*;
import jm.JMC;
import jm.util.*;
import jm.music.tools.*;

/**
 * Class to represent a single note in a 
 * linked list for a solo.
 * @author Benjamin Carle
 */
public class SoloNode implements JMC 
{
  
  /** the Note for this node */
  Note note;
  
  /** the next SoloNode in the list */
  SoloNode next;
  
  /**
   * Construct a new solo node.
   * @param note The note for this node
   */
  public SoloNode(Note note) 
  {
    this.note = note;
    this.next = null;
  }
  
  /**
   * Construct a new solo node.
   * @param note The note for this node
   * @param next the reference to the next node
   */
  public SoloNode(Note note, SoloNode next)
  {
    this.note = note;
    this.next = next;
  }
}
