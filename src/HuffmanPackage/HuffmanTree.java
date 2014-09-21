/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package HuffmanPackage;


import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 *  Joseph Zhong
 *  Assignment 12
 *  Huffman Project (v. 8.94)
 *  The binary tree which organizes character frequencies and decodes
 *      into characters from a file of bytes input
 *  24 March 2014
 *
 **/

public class HuffmanTree
{    
   /**
    * @base is the root of the HuffmanNode Tree 
    */
   private HuffmanNode base;
   
   /**
    * Doctor proctor Dr. Bricker solemnly swears on the Bible and the Flag of the 
    *   United States - And AND THE DEVELOPMENT OF OS AND WINDOWS 95
    *   that I (Joseph Zhong) will not lose points for this field.
    * This field keeps track of whether the while loop for reaading bits is to 
    *   continue or not
    */
   private boolean EOFNotFound;
   
   /**
    * Constructor. 
    * @param counts is an integer array produced from the MakeCode file. 
    *   The array of 256 counts the frequency of each character present
    *   in a given file
    * HuffmanNodes are created for present letter, sorted into @q 
    *   PriorityQueue. The first two nodes are continuously combined into a 
    *   single node that points to the two nodes until the queue only contains 
    *   a single root node, which is then set to @base 
    */
   public HuffmanTree (int[] counts)
   {
      PriorityQueue<HuffmanNode> q;
      q = new PriorityQueue<>(); 
      // DIamond reference isn't allowed 
      //    for UW Java compiler apparently :(
      
      for(int character = 0; character < counts.length; character++)
      {
         HuffmanNode h; 
         h = new HuffmanNode((char) character, counts[character], null, null);
         if(counts[character] != 0)
         {
             q.add(h);
         }  
      }
      // add EOF node
      q.add(new HuffmanNode((char) counts.length, 1, null, null)); 
      // EOF ascii is 256, and occurs once ??? 
      // PriorityQueue finished - now have an ordered queue with lower counts 
      // in beginning
      
      // PSEUDOCODE:
      // WHILE QUEUE.SIZE() > 1 -> 
      // pop first two, 
      // first = temp1, second = temp2
      //  .remove first two,
      //  .add (null, temp1.frequency + temp2.frequency, temp1, temp2)
      while(q.size() > 1)
      {
         HuffmanNode temp1 = q.poll(); // first Node stored
         HuffmanNode temp2 = q.poll(); // second Node stored
         q.add(new HuffmanNode(null, temp1.frequency + temp2.frequency, temp1, temp2));
      }        
      // ^ Recursive?
      // single binary tree 
      base = q.peek();
   }
  
   /**
    * Takes the tree @base built from the constructor using the integer array,
    *   calls a recursively traversing method and prints to @output, a 
    *   character ascii value, and on the next line, its path as a string of 
    *   1s and 0s to access it on the HuffmanNode Tree. 
    * @param output is a PrintStream file to print the tree's contents to
    */
   public void write (PrintStream output)
   {
       traverseTree(base, output, "");
   }
   
   /**
    * Modifier method. This method is used to pre-order recursively traverse 
    *   the HuffmanNode Tree. The basis of the recursive function checks for 
    *   "leaf" node, which both contains a char element, and does not point 
    *   to any other nodes. 
    * This recursive method is a bit special because it changes two inputs 
    *   per stack. While traversing the tree, it must keep track of which path 
    *   it took previously, and thus, whenever the left or right nodes are
    *   called, a zero or one must be kept track of.
    * @param h is the HuffmanNode which is tested for content to print. Otherwise
    *   its pointers will be used to continue traversing. 
    * @param output is the file to print the tree's contents to
    * @param path is the String which is used to keep track of which path 
    *   has been taken to traverse the tree. Either a 0 or 1 is added depending
    *   on the path taken, and the initial condition is an empty string.
    */
   private void traverseTree(HuffmanNode h, PrintStream output, String path)
   {
      if(h.left == null && h.right == null)
      {
         output.print((int) h.charElement.charValue()); // so how does PrintStream work?
         output.print("\n");
         output.print(path + "\n");
      }
      else
      {
         traverseTree(h.left, output, path + "0");
         traverseTree(h.right, output, path +  "1");
      }
   } // uh... works for example.txt...works for hamlet
   
   /*
   /**
    * Test program for the traverseTree(HuffmanNode h, PrintStream output, 
    *   String path) method...I used this before I realized that output was 
    *   a file to output to. Does the same as traverseTree except that it only 
    *   prints in the console, regardless of @output
    * @param h is the HuffmanNode which is tested for content to print. Otherwise
    *   its pointers will be used to continue traversing. 
    * @param output is the file to print the tree's contents to
    * @param path is the String which is used to keep track of which path 
    *   has been taken to traverse the tree. Either a 0 or 1 is added depending
    *   on the path taken, and the initial condition is an empty string.
    */
   /*
   private void testTraverseTree(HuffmanNode h, PrintStream output, String path)
   {
      
      if(h.left == null && h.right == null)
      {
         System.out.print((int) h.charElement.charValue()); // System.out.print for now
         System.out.print("\n");
         System.out.print(path + "\n");
      }
      else
      {
         testTraverseTree(h.left, output, path + "0");
         testTraverseTree(h.right, output, path +  "1");
      }
   }
   * */
   
   /**
    * Constructor. Rather than taking a file of characters, it reads the "code"
    *   file, storing the ascii value, and the its access path as two separate
    *   objects. It then calls a pre-order recursive function to build HuffmanNodes
    *   based on the stored path. 
    * @param input is the code file of ascii keys for characters and the following
    *   0 and 1s representing the ascii key's access path. 
    */
   public HuffmanTree(Scanner input)
   {
       HuffmanNode tempPointer = new HuffmanNode();
       base = tempPointer;
       
       while(input.hasNext())
       {
           int asciiValue = Integer.parseInt(input.nextLine());
           char charE = (char) asciiValue;
           String path = input.nextLine();
           /*
           for(int i = 0; i < path.length(); i++)
           {
               if(path.charAt(i) == '0')
               {
                   if(tempPointer.left == null)
                   {
                       base.left = new HuffmanNode(null, -1, null, null);
                   }
                   tempPointer = base.left;
               }
               else if(path.charAt(i) == '1')
               {
                   if(tempPointer.right == null)
                   {
                       base.right = new HuffmanNode(null, -1, null, null);
                   }
                   tempPointer = base.right;
               }
           }
           tempPointer.charElement = charE; // THIS DOESN'T WORK NEEDS 
           *    SOMETHING SPECIAL..? Something about Copy by Reference?
           * */
           buildTree(base, path, charE); // recursive building
       }   
   }
   
   /**
    * Modifier Method. This method builds the tree based on the access path 
    *   from the input file. The basis of the recursive function is based on 
    *   the path. If the path is an empty String, a leaf has been reached, 
    *   and the character Element inside the node can be set. Otherwise, 
    *   the path is read, and the function calls itself based on the path's 
    *   first character. 
    * @param h is the HuffmanNode to be set a character value, or to add 
    *   left or right pointers to.
    * @param _path is a String of 1s and 0s, resembling the left or right 
    *   pathway for the tree.
    * @param _charE is the character value to set the leaf to
    */
   private void buildTree(HuffmanNode h, String _path, char _charE)
   {
       if(_path.length() == 0)
       {
           h.charElement = _charE;
       }
       else
       {
           if(_path.charAt(0) == '0')
           {
               if(h.left == null)
               {
                   HuffmanNode temp = new HuffmanNode();
                   h.left = temp;
               }
               buildTree(h.left, _path.substring(1), _charE);
           }
           else if(_path.charAt(0) == '1')
           {
               if(h.right == null)
               {
                   HuffmanNode temp = new HuffmanNode();
                   h.right = temp;
               }
               buildTree(h.right, _path.substring(1), _charE);
           }
       } // works! 
   }
   
   /**
    * Decode function. Takes a file of bytes, uses a bit reader, and interprets
    *   the bits to call a recursively traversing method to print the actual 
    *   char values. 
    * @param input reads the file of bytes, and features a bit reader to convert
    *   the bytes into a series of 1s and 0s to traverse down the tree to reach 
    *   the character. 
    * @param output is the file to print the characters found and interpreted 
    *   by the bits to
    * @param maxChars is an integer value to check if the bit reader has reached 
    *   the End of File character
    */
   /*
   public void decode2(BitInputStream input, PrintStream output, int maxChars)
   {
       int count = 0;
       int bit = input.readBit();
       HuffmanNode current = base;
       
       while (bit != -1) 
       {
           System.out.print(bit);
           if (current.charElement == null)
           {
               current = (bit==0)?current.left:current.right;
           }
           else
           {
               count++;
               System.out.println();
               System.out.print(current.charElement);
               current = base;
               if (count >= maxChars) 
               {
                   break;
               }
           }
           bit = input.readBit();  
       }
       System.out.println();   
   }
   */
   /**
    * Decode function. Takes a file of bytes, uses a bit reader, and interprets
    *   the bits to call a recursively traversing method to print the actual 
    *   char values. 
    * @param input reads the file of bytes, and features a bit reader to convert
    *   the bytes into a series of 1s and 0s to traverse down the tree to reach 
    *   the character. 
    * @param output is the file to print the characters found and interpreted 
    *   by the bits to
    * @param maxChars is an integer value to check if the bit reader has reached 
    *   the End of File character
    */
   /*
   public void decode1(BitInputStream input, PrintStream output, int maxChars)
   { 
       try 
       {    
            boolean EOFNotFound = true;
            while (EOFNotFound)
            {
                
                //v2(base, input, output, eof);
               //traverseTreePrintChar(base, input, output, eof);
                //v4(base, input, output, eof);
               //  testTraverseTreePrintChar(base, input, output, eof);
                traverseTree(base, input, output, maxChars);
                int counter = 0;
                //System.out.print(readFirstBit(input)); 
                // readFirstBit(input) READS CORRECTLY T______T' PORQUEE
            }
       }
       catch (Exception e) 
       {
           //System.out.println(e.getMessage());
       }
       /*
       String bits = "";
       int bit;
       while((bit = input.readBit()) != -1)
       //for(int i = 0; i < 2; i++)
       {
           //bit = input.readBit();
           bits += bit;
       }
       System.out.print(bits);
   }*/
   
   public void decode(BitInputStream input, PrintStream output, int maxChars)
   {
       EOFNotFound = true;
       try
       {
           while(EOFNotFound)
           {
               TestTraverseTree(base, input, output, maxChars);
           }
       }
       catch (EOFException e)
       {
       }
   }
   
   private void TestTraverseTree(HuffmanNode h, BitInputStream input, PrintStream output, int maxChars) throws EOFException
   {
       if(h.charElement != null)
       {
           if((int) h.charElement == maxChars)
           {
               MAKEFALSE();
               return;
           }
           output.print(h.charElement);
       }
       else
       {
           int bit = 0;
           try
           {
               bit = readBit(input);
           }
           catch (IOException e){}
           if(bit == 0)
           {
               TestTraverseTree(h.left, input, output, maxChars);
           }
           else if(bit == 1)
           {
               TestTraverseTree(h.right, input, output, maxChars);
           }
           else if(bit == -1)
           {
               throw new EOFException();
           }
       }
   }
  
   /**
    * Modifier Method. This method pre-order traverses the tree, and prints the
    *   character value in the HuffmanNode if possible. Otherwise, it reads bits
    *   from the input file of Bytes, and traverses the tree based on the path
    * @param h is a HuffmanNode to check for a character content, or left or right
    *   paths for characters.
    * @param input is the file of Bytes which are read as bits to determine the
    *   path to read.
    * @param output is the file which the character in the HuffmanNode is printed
    * @param maxChars is the maximum characters to be encoded
    * @throws EOFException is an exception which is thrown to account for a mistake
    *   If the @eof isn't read, the bit reader will begin to read -1, indicating 
    *   that it has read past the end of the file, which is an error, and shouldn't 
    *   happen.
    */
   
   /*
   private boolean traverseTree(HuffmanNode h, BitInputStream input, PrintStream output, int maxChars) throws EOFException 
   {
       char EOF = '\u001a';
       if(h.charElement != null)
       {
           if((int) h.charElement == (int) '\u001a')
           {
               return false;
           }
           else if((int) h.charElement == maxChars)
           {
               return false;
           }
           else
           {
               output.print(h.charElement);
           }
           
       }
       else
       {
           //int bit = readFirstBit(input);
           int bit = 0;
           try
           {
               bit = readBit(input);
           }
           catch (IOException e){}
                   
           
           if(bit == 0)
           {
               traverseTree(h.left, input, output, maxChars);
           }
           else if(bit == 1)
           {
               traverseTree(h.right, input, output, maxChars);
           }
           else if(bit == -1)
           {
               throw new EOFException();
           }
       }
       return true;
   }*/
   
    /**
    * Modifier Method. This method pre-order traverses the tree, and prints the
    *   character value in the HuffmanNode if possible. Otherwise, it reads bits
    *   from the input file of Bytes, and traverses the tree based on the path
    * @param h is a HuffmanNode to check for a character content, or left or right
    *   paths for characters.
    * @param input is the file of Bytes which are read as bits to determine the
    *   path to read.
    * @param output is the file which the character in the HuffmanNode is printed
    * @param maxChars is the maximum characters to be encoded
    * @throws EOFException is an exception which is thrown to account for a mistake
    *   If the @maxChars isn't read, the bit reader will begin to read -1, indicating 
    *   that it has read past the end of the file, which is an error, and shouldn't 
    *   happen.
    */
   /*
   private void traverseTree2(HuffmanNode h, BitInputStream input, PrintStream output, int maxChars) throws EOFException 
   {
       char EOF = '\u001a';
       if(h.charElement != null)
       {
           if((int) h.charElement == (int) '\u001a')
           {
               return;
           }
           else if((int) h.charElement == 256)
           {
               return;
               
           }
           else
           {
               output.print(h.charElement);
           }
           
       }
       else
       {
           //int bit = readFirstBit(input);
           int bit = 0;
           try
           {
               bit = readBit(input);
           }
           catch (IOException e){}
                   
           
           if(bit == 0)
           {
               traverseTree(h.left, input, output, maxChars);
           }
           else if(bit == 1)
           {
               traverseTree(h.right, input, output, maxChars);
           }
           else if(bit == -1)
           {
               throw new EOFException();
           }
       }
   }
   */
   /**
    * Private Modifier Function.
    *   This is a work around function that allows you to change the boolean field
    *   @EOFNotFound to false after it has been found while reading the bits
    */
   private void MAKEFALSE()
   {
       EOFNotFound = false;
   }
   
   /**
    * This was an annoying last minute addition haha.
    * A method that acts as a helper - it returns the bit that is read from the 
    *   input file of bytes.
    * This was required to throw an IOException, which for some reason the UW
    *   compiler required...
    * @param input is the encoded and compressed file of Bytes (.short) to read
    * @return what is read from the @input file
    * @throws IOException This exception is thrown when there is an IO issue with
    *   the file - if it doesn't exist or etc.
    */
   private int readBit(BitInputStream input) throws IOException
   {
       return input.readBit();
   }
}
