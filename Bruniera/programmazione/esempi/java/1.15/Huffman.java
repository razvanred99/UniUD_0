import huffman_toolkit.*;
import java.util.*;
 
public class Huffman{
  
  public static int[] tabOccorrenze(String doc){
    int[] tab=new int[256];
    InputTextFile itf=new InputTextFile(doc);
    while(itf.textAvailable()){
      char c=itf.readChar();
      tab[c]++;
    }
    itf.close();
    return tab;
  }
  
  public static void creaCoda( int[] tab ){
    PriorityQueue<HNode> q =new PriorityQueue<HNode>();
    for(int i=0;i<256;i++){
      if( tab[i]>0 ){
        HNode n =new HNode( (char) i, tab[i] );
        q.add(n);
      }
    }
    
    while(q.size()>0){
      HNode n=q.poll();
      System.out.println(""+n);
    }
  }
  
  public static HNode hAlbero( int[] tab ){
    PriorityQueue<HNode> q =new PriorityQueue<HNode>();
    for(int i=0;i<256;i++){
      if( tab[i]>0 ){
        HNode n =new HNode( (char) i, tab[i] );
        q.add(n);
      }
    }
    
    while(q.size()>1){
      HNode l=q.poll();
      HNode r=q.poll();
      q.add(new HNode(l,r));
    }
    return q.poll();
  }
  
  public static void comprimi(String doc, String com){
    int[] tab=tabOccorrenze(doc);
    HNode t=hAlbero(tab);
    int count=t.weight();
    String[] codes=t.codesTab();
    InputTextFile itf=new InputTextFile(doc);
    OutputTextFile otf=new OutputTextFile(com);
    
    otf.writeTextLine(""+t.weight());
    otf.writeTextLine(t.codAlbero());
    
    for(int i=0;i<count;i++){
      char c=itf.readChar();
      
      otf.writeCode(codes[c]);
    }
    itf.close();
    otf.close();
  }
}