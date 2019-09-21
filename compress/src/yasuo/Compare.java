package yasuo;

import java.util.Comparator;

//用于优先队列
public class Compare implements Comparator<HufTree>{

  @Override
  public int compare(HufTree o1, HufTree o2) {//两颗哈夫曼树权重的比较
      if(o1.weight < o2.weight)
          return -1;
      else if(o1.weight > o2.weight)
          return 1;
      return 0;
  }

}
