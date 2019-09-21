package yasuo;

//哈夫曼压缩
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanCompress{
	 private PriorityQueue<HufTree> queue = null;
	 
	 //压缩函数
	 public void compress(File inputFile,File outputFile){
		 Compare cmp=new Compare();
		 queue = new PriorityQueue<HufTree>(12,cmp);
		 
		 //映射字节及其对应的哈夫曼编码
		 HashMap<Byte,String> map=new HashMap<Byte,String>();
		 //文件中含有的字符种类数
		    int i,char_kinds = 0;
	        int char_temp,file_len = 0;
	        FileInputStream fis = null;
	        FileOutputStream fos = null;
	        ObjectOutputStream oos = null;
	        
	        //哈夫曼节点数
	        int node_num;
	        HufTree[] huf_tree=null;
	        String code_buf=null;
	        
	        //临时存储字符频度的数组
	        TmpNode[] tmp_nodes = new TmpNode[256];
	        
	        for( i=0;i<256;++i){
	        	tmp_nodes[i] = new TmpNode();
	            tmp_nodes[i].weight = 0;
	            tmp_nodes[i].uch = (byte)i;
	        }
	        
	        try{
	        	 fis = new FileInputStream(inputFile);
	             fos = new FileOutputStream(outputFile);
	             oos = new ObjectOutputStream(fos);
	             
	             //统计字符频度，计算文件长度
	             while((char_temp=fis.read())!=-1){
	            	 ++tmp_nodes[char_temp].weight;
	                 ++file_len;
	             }
	             fis.close();
	             Arrays.sort(tmp_nodes);
	             
	           //排序后就会将频度为0的字节放在数组最后，从而去除频度为0的字节
	           //同时计算出字节的种类
	             for(i=0;i<256;++i){
	            	 if(tmp_nodes[i].weight == 0)
	                     break;
	             }
	             char_kinds = i;
	           //只有一种字节的情况
	             if(char_kinds==1){
	            	 oos.writeInt(char_kinds);
	                 oos.writeByte(tmp_nodes[0].uch);
	                 oos.writeInt(tmp_nodes[0].weight);
	             }
	             //多余一种
	             else{
	            	 node_num = 2*char_kinds-1;//计算哈夫曼树所有节点个数
	                 huf_tree = new HufTree[node_num];
	                 for(i = 0; i < char_kinds; ++i){
	                     huf_tree[i] = new HufTree();
	                     huf_tree[i].uch = tmp_nodes[i].uch;
	                     huf_tree[i].weight = tmp_nodes[i].weight;
	                     huf_tree[i].parent = 0;

	                     huf_tree[i].index = i;
	                     queue.add(huf_tree[i]);
	             }
	                 tmp_nodes = null;
	                 for(; i < node_num; ++i){
	                     huf_tree[i] = new HufTree();
	                     huf_tree[i].parent = 0;
	                 }
	                 //创建哈夫曼树
	                 createTree(huf_tree, char_kinds, node_num,queue);
	                 //生成哈夫曼编码
	                 hufCode(huf_tree, char_kinds);
	                 //写入字节种类
	                 oos.writeInt(char_kinds);
	                 for(i = 0; i < char_kinds; ++i){
	                     oos.writeByte(huf_tree[i].uch);
	                     oos.writeInt(huf_tree[i].weight);

	                     map.put(huf_tree[i].uch, huf_tree[i].code);
	                 }
	                 oos.writeInt(file_len);
	                 fis = new FileInputStream(inputFile);
	                 code_buf = "";
	                 //将读出的字节对应的哈夫曼编码转化为二进制存入文件
	                 while((char_temp = fis.read()) != -1){
	                	 code_buf += map.get((byte)char_temp);
	                	 while(code_buf.length() >= 8){
	                		 char_temp = 0;
	                		 for(i = 0; i < 8; ++i){
	                			 char_temp <<= 1;
	                             if(code_buf.charAt(i) == '1')
	                                 char_temp |= 1;
	                		 }
	                		 oos.writeByte((byte)char_temp);
	                         code_buf = code_buf.substring(8);
	                	 }
	                 }
	                 //最后编码长度不够8位的时候，用0补齐
	                 if(code_buf.length() > 0){
	                	 char_temp = 0;
	                	 for(i = 0; i < code_buf.length(); ++i){
	                		 char_temp <<= 1;
	                         if(code_buf.charAt(i) == '1')
	                             char_temp |= 1;
	                	 }
	                	 char_temp <<= (8-code_buf.length());
	                     oos.writeByte((byte)char_temp);
	                 }
	                 
	        }
	             oos.close();
	             fis.close();
	 }
	        catch (Exception e) { 
	            e.printStackTrace();
	        }
	
}
	 //解压函数
	 
	 public void extract(File inputFile, File outputFile){
		 Compare cmp = new Compare();
	        queue = new PriorityQueue<HufTree>(12,cmp);

	        int i;
	        int file_len = 0;
	        int writen_len = 0;
	        FileInputStream fis = null;
	        FileOutputStream fos = null;
	        ObjectInputStream ois = null;

	        int char_kinds = 0;
	        int node_num;
	        HufTree[] huf_tree = null;
	        byte code_temp;
	        int root;
	        try{
	            fis = new FileInputStream(inputFile);
	            ois = new ObjectInputStream(fis);
	            fos = new FileOutputStream(outputFile);

	            char_kinds = ois.readInt();

	            //字节只有一种的情况
	            if(char_kinds == 1){
	                code_temp = ois.readByte();
	                file_len = ois.readInt();
	                while((file_len--) != 0){
	                    fos.write(code_temp);
	                }
	            //字节多于一种的情况
	            }else{
	                node_num = 2 * char_kinds - 1; //计算哈夫曼树所有节点个数
	                huf_tree = new HufTree[node_num];
	                for(i = 0; i < char_kinds; ++i){
	                    huf_tree[i] = new HufTree();
	                    huf_tree[i].uch = ois.readByte();
	                    huf_tree[i].weight = ois.readInt();
	                    huf_tree[i].parent = 0;

	                    huf_tree[i].index = i;
	                    queue.add(huf_tree[i]);
	                }
	                for(;i < node_num; ++i){
	                    huf_tree[i] = new HufTree();
	                    huf_tree[i].parent = 0;
	                }
	                createTree(huf_tree, char_kinds, node_num,queue);

	                file_len = ois.readInt();
	                root = node_num-1;
	                while(true){
	                    code_temp = ois.readByte();
	                    for(i = 0; i < 8; ++i){
	                        if((code_temp & 128) == 128){
	                            root = huf_tree[root].rchild;
	                        }else{
	                            root = huf_tree[root].lchild;
	                        }

	                        if(root < char_kinds){
	                            fos.write(huf_tree[root].uch);
	                            ++writen_len;
	                            if(writen_len == file_len) break;
	                            root = node_num - 1; //恢复为根节点的下标，匹配下一个字节
	                        }
	                        code_temp <<= 1;
	                    }
	                    //在压缩的时候如果最后一个哈夫曼编码位数不足八位则补0
	                    //在解压的时候，补上的0之前的那些编码肯定是可以正常匹配到和他对应的字节
	                    //所以一旦匹配完补的0之前的那些编码，写入解压文件的文件长度就和压缩之前的文件长度是相等的
	                    //所以不需要计算补的0的个数
	                    if(writen_len == file_len) break;
	                }
	            }
	            fis.close();
	            fos.close();
	        }catch(Exception e){
	            e.printStackTrace();
	        }

	 }
	 //构建哈夫曼树
	 public void createTree(HufTree[] huf_tree, int char_kinds, int node_num,PriorityQueue<HufTree> queue){
	        int i;
	        int[] arr = new int[2];
	        for(i = char_kinds; i < node_num; ++i){
	            arr[0] = queue.poll().index;
	            arr[1] = queue.poll().index;
	            huf_tree[arr[0]].parent = huf_tree[arr[1]].parent = i;
	            huf_tree[i].lchild = arr[0];
	            huf_tree[i].rchild = arr[1];
	            huf_tree[i].weight = huf_tree[arr[0]].weight + huf_tree[arr[1]].weight;

	            huf_tree[i].index = i;
	            queue.add(huf_tree[i]);
	        }
	    }
	//获取哈夫曼编码
	    public void hufCode(HufTree[] huf_tree, int char_kinds){
	        int i;
	        int cur,next;

	        for(i = 0; i < char_kinds; ++i){
	            String code_tmp = "";
	            for(cur = i,next = huf_tree[i].parent; next != 0; cur = next,next = huf_tree[next].parent){
	                if(huf_tree[next].lchild == cur)
	                    code_tmp += "0";
	                else
	                    code_tmp += "1";
	            }
	            huf_tree[i].code = (new StringBuilder(code_tmp)).reverse().toString();
	        }
	    }
}
