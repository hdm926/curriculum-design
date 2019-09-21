import javax.swing.*;
import java.io.*;
import java.util.*;


public class Main {
    private static LinkedList<Class> classList = new LinkedList<>();
    private static LinkedList<ClassRoom> classroomList = new LinkedList<>();
    private static LinkedList<Course> courseList = new LinkedList<>();
    private static LinkedList<Teacher> teacherList = new LinkedList<>();
    private static Set<String> preCouse = new HashSet<>();
    private static Set<String> having_preCourse = new HashSet<>();
    private static LinkedList<String> temList1;
    private static LinkedList<String> temList2;

    private static LinkedList<String> randList(LinkedList<String> list, int timesWeek) {
        LinkedList<String> subList = new LinkedList<String>();
        Random rand = new Random();

        int j = 0;
        
        while (true) {
            String m = list.get(rand.nextInt(list.size()));

            if (!subList.contains(m)) {
                subList.add(m);
                j++;
            }
            if (j == timesWeek) {
                break;
            }
        }
        return subList;
    }


    private static void order(Teacher te, Class cl, Course co) {
        temList1 = (LinkedList<String>) (te.getSq().getList().clone());
        te.getSq().getList().retainAll(cl.getSq().getList());
        if (te.getSq().getList().size() < co.getTimesWeek()) {
            System.out.println("��ʦ��ͬѧ����������ʱ�䲻�㰲��" + co.getName() + "�γ̣��Ƿ����½��г��ԣ�(����1�������г��ԣ����������ַ���������)");
            Scanner cin = new Scanner(System.in);
            if (new Scanner(System.in).nextInt() == 1)
                Arranging();
            else
                return;

        }
        temList2 = randList(te.getSq().getList(), co.getTimesWeek());
        cl.getSq().getList().removeAll(temList2);
        te.getSq().setList(temList1);
        te.getSq().getList().removeAll(temList2);
    
        for (String x : temList2) {
            int max=1000;
            ClassRoom selectRoom=null;
            for (ClassRoom y: classroomList) {
                if(y.getSq().getList().contains(x)&&y.getCapacity()-cl.getSum()<max&&y.getCapacity()-cl.getSum()>=0) {
                    max = y.getCapacity() - cl.getSum();
                    selectRoom=y;
                }
            }
            if(selectRoom!=null){
                selectRoom.getSq().getList().remove(x);
            }
            else{
                System.out.println("������Դ������");
                return;
            }
            te.observed[x.charAt(0) - 'a'] = co.getName()+" "+cl.getName()+" "+selectRoom.getName();
            cl.observed[x.charAt(0) - 'a'] = co.getName()+" "+te.getName()+" "+selectRoom.getName();
        }
    }

    private static void Arranging() {
        for (Course cou : courseList) {
            Teacher tea = null;
            Class cla = null;
            if (preCouse.contains(cou.getName())) {
                for (Class x : classList) {
                    if (x.getGrade() == 1) {
                        cla = x;
                        break;
                    }
                }
                for (Teacher x : teacherList) {
                    if (x.teachCourse.contains(cou.getName())) {
                        tea = x;
                        break;
                    }
                }
               
                order(tea, cla, cou);
                
            } else if (having_preCourse.contains(cou.getName())) {
                int max = 0;
                for (Class x : classList) {
                    if (x.getSq().getList().size() > max && x.getGrade() != 1) {
                        cla = x;
                        max = x.getSq().getList().size();
                    }
                }
                for (Teacher x : teacherList) {
                    if (x.teachCourse.contains(cou.getName())) {
                        tea = x;
                        break;
                    }
                }
               
                order(tea, cla, cou);
                
            } else {
                int max = 0;
                for (Class x : classList) {
                    
                    if (x.getSq().getList().size() > max) {
                        cla = x;
                        max = x.getSq().getList().size();
                    }
                }
                for (Teacher x : teacherList) {
                    if (x.teachCourse.contains(cou.getName())) {
                        tea = x;
                        break;
                    }
                }
               
                order(tea, cla, cou);
            }
        }
    }

    private static void showClass(int grade) {
        for (Class x : classList) {
            if (x.getGrade() == grade) {
                Show(x.observed, x.getName() + "�İ༶�α�");
                return;
            }
        }

    }

    private static void showTeacher(int teacherId) {
        for (Teacher x : teacherList) {
            if (x.getId() == teacherId) {
                Show(x.observed, x.getName() + "�Ŀα�");
                return;
            }
        }
    }

    private static void Show(String[] list, String string) {
        JFrame frame = new JFrame(string);
        JTable table = new JTable(new courseForm(list));
        table.setRowHeight(120);

        JScrollPane pane = new JScrollPane(table);

        frame.add(pane);
       
        frame.setSize(1130,550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("Class_config.txt")), "GBK"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] result = line.split(" ");
                classList.add(new Class(Integer.parseInt(result[0]), result[1], Integer.parseInt(result[2])));
            }
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("ClassRoom_config.txt")), "GBK"));
            while ((line = br.readLine()) != null) {
                String[] result = line.split(" ");
                classroomList.add(new ClassRoom(Integer.parseInt(result[0]), result[1], Integer.parseInt(result[2])));
            }
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("Course_config.txt")), "GBK"));
            while ((line = br.readLine()) != null) {
                String[] result = line.split(" ");
                courseList.add(new Course(Integer.parseInt(result[0]), result[1], Integer.parseInt(result[2])));
                for (int i = 3; i < result.length; i++) {
                    courseList.getLast().precourse.add(result[i]);
                    preCouse.add(result[i]);
                    having_preCourse.add(result[1]);
                }
            }
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("Teacher_config.txt")), "GBK"));
            while ((line = br.readLine()) != null) {
                String[] result = line.split(" ");
                teacherList.add(new Teacher(Integer.parseInt(result[0]), result[1]));
                for (int i = 2; i < result.length; i++) {
                    if (result[i].length() == 1 && result[i].charAt(0) <= 'z' && result[i].charAt(0) >= 'a') {
                        teacherList.getLast().getSq().getList().remove(result[i].charAt(0) - 'a');
                    } else
                        teacherList.getLast().teachCourse.add(result[i]);
                }
            }

 
        } catch (IOException e) {
            e.printStackTrace();
        }





        int index;
        Scanner cin = new Scanner(System.in);
        System.out.println("����������������������������������������������������������������������������������������������������������������������������");
        System.out.println("                                               �ſ�ϵͳ                                                       ");
        System.out.println("����������������������������������������������������������������������������������������������������������������������������");
        System.out.println("                      1���ſ�                                                                   ");
        System.out.println("                      2����ѯ�γ̱�                                         ");
        System.out.println("                      3���˳�ϵͳ                                                            ");
        System.out.println("����������������������������������������������������������������������������������������������������������������������������");
        while (true) {
            System.out.println("���������Ĳ�������");
            index = cin.nextInt();
            switch (index) {
                case 3:
                    return;
                case 1:
                    System.out.println("�����ſΣ�");
                    Arranging();
                    System.out.println("�ſγɹ���");
                    break;
             
                case 2:
                    for (Class x : classList) {
                    
                        Show(x.observed, x.getName() + "�İ༶�α�");
                    }

            }
        }

    }
}
