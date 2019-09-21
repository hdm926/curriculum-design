package yasuo;

public class HufTree {
	public byte uch; //��8λΪ��Ԫ���ֽ�
    public int weight;//���ֽ����ļ��г��ֵĴ���
    public String code; //��Ӧ�Ĺ���������
    //��Ϊ������洢�Ĺ�������
    //��������һ��index��¼��ǰ�ڵ��������е��±�
    //�����ڹ�������������ʱ��ָ���ڵ��������е�λ��
    public int index; 
    public int parent,lchild,rchild;

 
    public String toString(){
        return "uch:" + uch + ",weight:" + weight + ",code:" + code + ",parent:" + parent + ",lchild:" + lchild + ",rchild:" + rchild;
    }

}
//ͳ���ַ�Ƶ�ȵ���ʱ�ڵ�
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