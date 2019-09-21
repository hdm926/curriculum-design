package yasuo;

public class HufTree {
	public byte uch; //以8位为单元的字节
    public int weight;//该字节在文件中出现的次数
    public String code; //对应的哈夫曼编码
    //因为用数组存储的哈夫曼树
    //所以设置一个index记录当前节点在数组中的下标
    //用于在构建哈夫曼树的时候指明节点在数组中的位置
    public int index; 
    public int parent,lchild,rchild;

 
    public String toString(){
        return "uch:" + uch + ",weight:" + weight + ",code:" + code + ",parent:" + parent + ",lchild:" + lchild + ",rchild:" + rchild;
    }

}
//统计字符频度的临时节点
class TmpNode implements Comparable<TmpNode>{
  public byte uch;
  public int weight;

  @Override
  public int compareTo(TmpNode arg0) {
      if(this.weight < arg0.weight)
          return 1;
      else if(this.weight > arg0.weight)
          return -1;
      return 0;
  }
}