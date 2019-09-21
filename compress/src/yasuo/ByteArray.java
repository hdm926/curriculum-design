package yasuo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ByteArray {
	  
	 final byte[] arr ;
	  
	 ByteArray(byte[] b){
	   arr = (byte[]) b.clone() ; }
	  
	 ByteArray(){
	   arr = new byte[0] ; }
	  
	 ByteArray(byte b){
	   arr = new byte[] { b } ; }
	 
	 public boolean equals(Object o){
	    ByteArray ba = (ByteArray) o ;
	    return java.util.Arrays.equals(arr,ba.arr) ; }
	  
	 public int hashCode(){
	   int code = 0 ;
	   for (int i=0;i<arr.length;++i)
	   code = code*2+arr[i] ;
	   return code ; }
	  
	 public int size(){
	   return arr.length ; }
	  
	 byte getAt(int i){ 
	  return arr[i] ; }
	  
	  
	 public ByteArray conc(ByteArray b2){//两个数组加成一个
	    int sz = size()+b2.size() ;
	    byte[] b = new byte[sz] ;
	    for (int i=0;i<size();++i) b[i]=getAt(i) ;
	    for (int i=0;i<b2.size();++i) b[i+size()]=b2.getAt(i) ;
	    return new ByteArray(b) ; }
	 
	 public ByteArray conc(byte b2){
	   return conc(new ByteArray(b2)) ; }
	  
	 public byte[] getBytes(){
	   return (byte[]) arr.clone() ; }
	 
	 public boolean isEmpty(){
	   return size()==0 ; }
	  
	 public byte getLast(){
	   return arr[size()-1] ; }
	 
	 public ByteArray dropLast(){
	    byte[] newarr = new byte[size()-1] ;
	    for (int i=0;i<newarr.length;++i) {
	    	newarr[i] = arr[i] ;}	    	     
	    return new ByteArray(newarr) ; }
	 
	 public String toString(){
	   return new String(arr) ; }
	 
	}

	interface Compression
	{
	  
	 void compress(InputStream inp, OutputStream out) throws IOException ;
	  
	 void decompress(InputStream inp, OutputStream out) throws IOException ;

	}