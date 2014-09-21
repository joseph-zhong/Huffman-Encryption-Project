/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package HuffmanPackage;

/**
 *
 *  Joseph Zhong
 *  Assignment 12 (v1.45)
 *  Node for the Huffman Tree
 *  Huffman Encoding
 *  24 March 2014
 *
 **/

public class HuffmanNode implements Comparable 
{
    /**
     * @charElement is a Character Object which resembles a portion of the data 
     *  of the HuffmanNode
     */
   protected Character charElement;
   
   /**
    * @frequency is an integer value which resembles a portion of the data of 
    *   the HuffmanNode - it is a count of how many times a character has 
    *   appeared in a given text file
    */
   protected int frequency;
   
   /**
    * @left is the left pointer to the "left" HuffmanNode
    */
   protected HuffmanNode left;
   
   /**
    * @right is the right pointer to the "right" HuffmanNode
    */
   protected HuffmanNode right;
   
   /**
    * @ARBITRARY_NUMBER is an arbitrary number to set to default nodes.
    */
   private final int ARBITRARY_NUMBER = -1;
   
   /**
    * Constructor. Sets fields to the parameters
    * @param _c is the input character to set to the HuffmanNode
    * @param _f is the input integer value to set to the HuffmanNode
    * @param _l is the input left HuffmanNode to set to the HuffmanNode's pointer
    * @param _r is the input right HuffmanNode to set to the HuffmanNode's pointer
    */
   public HuffmanNode(Character _c, int _f, HuffmanNode _l, HuffmanNode _r)
   {
      this.charElement = _c;
      this.frequency = _f;
      this.left = _l;
      this.right = _r;        
   }
   
   
   /**
    * Default Constructor. After completing the Tree, I realized that this would
    *   come in handy, as I creating many new nodes which I didn't have any data 
    *   to insert.
    */
   
   public HuffmanNode()
   {
       this.charElement = null;
       this.frequency = ARBITRARY_NUMBER;
       this.left = null;
       this.right = null;
   }
   
   @Override
   /**
    * CompareTo method. The Tree needs to utilize a PriorityQueue to build the 
    *   tree from frequencies. Thus, HuffmanNode must be Comparable, able to compare
    *   frequencies.
    */
   public int compareTo(Object o) 
   {
      HuffmanNode h = null;
      if(o instanceof HuffmanNode)
      {
         h = (HuffmanNode) o;
      }        
      return frequency - h.frequency;
   }
}
