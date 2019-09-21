package yasuo;
/*处理传进来的文件的字节流*/
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class BitInputStream extends FilterInputStream
{    
  public BitInputStream(InputStream is){
    super(is) ;
   }

 class BitManager {
  
  private int[] buf = new int[8] ;
  
  private int cnt = -1 ;
  
  boolean atTheEnd(){
    return ((buf[7]==1)&&(cnt<0)) ;
    }
  
  void setTheEnd(){
    buf[7]=1; cnt=-1; 
    }
  
  boolean noMoreBuffer(){
    return cnt<0 ; 
    }
 
  void setNext(int next){ 
    for (cnt=0; cnt<8; ++cnt) {
     buf[cnt] = next % 2 ;
     next /= 2 ;
    }
   
    if (buf[7]==1) {
     for (cnt=7;cnt>=0;cnt--)
      if (buf[cnt]==0)
       break ;
     cnt-- ;
    } else {
     cnt = 6 ;
    }
   }
  
  int getNext(){ 
    return buf[cnt--] ; 
    }
  
  int left(){ 
   return cnt+1 ; 
   }
 } ;
 
 BitManager bitManager = new BitManager() ;

 byte[] tempBuf = null ;
 int    tempBufPtr = 0 ;
 int   tempBufLen = 0 ;
 
 private int readNextByte() throws IOException{
	 
   int val = -1 ;
   if (tempBufPtr==tempBufLen)
    val = super.read() ;
   else {
    byte b = tempBuf[tempBufPtr++] ;
    if ((b&0x80)>0)
     val = ((int)(b&0x7F))|0x80 ;//位或运算
    else
     val = b ;
   }
   return val ;
  }
     
 public int read() throws IOException {
   
  if (bitManager.atTheEnd())
   return -1 ;
   
  if (bitManager.noMoreBuffer()) {
   int i = readNextByte() ;
   if (i<0) {
     bitManager.setTheEnd() ;}
   else {
    bitManager.setNext(i) ;}
   return read() ;
  }
   
  return bitManager.getNext() ;
 }
 
 public int read(byte[] arr) throws IOException{ 
  return read(arr,0,arr.length) ; 
  }

 public int read(byte[] arr, int off, int len) throws IOException{
  int bytelen = ( (len-bitManager.left()) / 7 ) ;
  tempBuf = new byte[bytelen] ;
  tempBufLen = in.read(tempBuf) ;
  tempBufPtr = 0 ;
  for (int i=0;i<len;++i) {
   int next = read() ;
   if (next<0)
    return i ;
   arr[off+i]=(byte) next ;
  }
  return len ;
 }

 public static void main(String[] args)
 {
  if (args.length<2)
  {
   System.out.println("Usage: java BitInputStream FromFile ToFile") ;
   System.out.println("where 'FromFile' is a file to be open as a Bit Stream") ;
   System.out.println("and they are written as characters of '0's and '1's") ;
   System.out.println("every line having one char") ;
   System.exit(1) ;
  }
  try {
   InputStream is = new BitInputStream(new BufferedInputStream(new FileInputStream(args[0]))) ;
   PrintWriter os = new PrintWriter(new OutputStreamWriter(new FileOutputStream(args[1]))) ;
   int next ;
   while ((next=is.read())>=0)
    os.println(next) ;
   is.close() ;
   os.close() ;
  } catch (FileNotFoundException fnfe) {
   System.out.println(args[0]+" file cannot be opened") ;
   System.exit(1) ;
  } catch (IOException ioe) {
   System.out.println("Error in reading file "+args[0]+" or writing file "+args[1]) ;
   System.exit(1) ;
  }
 }
}