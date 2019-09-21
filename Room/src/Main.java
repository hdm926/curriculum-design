import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

    private static void Init(Grade_Room current_node, String filepath) {
        try {
            File file = new File(filepath);//��config.txt
            if (file.isFile() && file.exists()) {
                InputStreamReader cin = new InputStreamReader(new FileInputStream(file), "GBK");
                BufferedReader read = new BufferedReader(cin);
                String[] outline_result = read.readLine().split(" ");
                int max_grade = Integer.parseInt(outline_result[0]);//config.txt�ĵ�һ�е�һ���ַ���ʾ����ߵȼ�
                //���췿��ȼ��Ķ���
                for (int i = 1; i <= max_grade; i++) {
                    current_node.next_grade = new Grade_Room();
                    current_node = current_node.next_grade;
                    current_node.setGrade(i);
                    //������Ӧ�ȼ�����ķ���
                    map.put(i, Integer.parseInt(outline_result[i]));
                    
                    String[] item_result = read.readLine().split(" ");
                    int rooms = Integer.parseInt(item_result[0]);//rooms��ʾ�õȼ��ķ�������
                    Rooms current_room = new Rooms();//��ͬ�ȼ��������ι��ɵĶ���
                    
                    for (int j = 1; j <= rooms; j++) {
                        if (j == 1) {
                            current_node.next_room = new Rooms();
                            current_room = current_node.next_room;
                        } else {
                            current_room.next_room = new Rooms();
                            current_room = current_room.next_room;
                        }
                        current_room.setRoom_beds(Integer.parseInt(item_result[j]));
                        current_room.setRoom_id(i * 100 + j);               
                    }
                    current_room.next_room = null;
                }
                current_node.next_grade = null;
                
                read.close();
                cin.close();
            } else {
                System.out.println("�Ҳ���ָ���ļ�");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //��ѯס����Ϣ��ѭ����Σ��ȼ�-����-��λ
    private static void allpassenger(Grade_Room root_node) {
        Grade_Room current_node = root_node.next_grade;
        Rooms current_room;
        Passengers current_passenger;
        System.out.println("**************************************************************************");
        System.out.println("grade name     room_id bed_id check_in phone_number VIP ");
        System.out.println("**************************************************************************");
        while (current_node != null) {
            current_room = current_node.next_room;
            while (current_room != null) {
                current_passenger = current_room.next_passenger;
                while (current_passenger != null) {
                    System.out.println(current_passenger.getGrade() + "     " + current_passenger.getName() + "" + 
                                        current_passenger.getRoom_id() + "   " + current_passenger.getBed_id() + 
                    		          "  " + current_passenger.getDate() + "  " + current_passenger.getPhone_number() +
                    		          "  "+current_passenger.getVip());
                    current_passenger = current_passenger.next_passenger;
                }
               
                current_room = current_room.next_room;
            }
            current_node = current_node.next_grade;
        }

    }

    private static void allroom(Grade_Room root_node) {
        Grade_Room current_node = root_node.next_grade;
        Rooms current_room;
        System.out.println("*********************************************");
        System.out.println("����� ��λ��  ��ʹ�÷��� ���շ�  +");
        System.out.println("*********************************************");
        while (current_node != null) {
            current_room = current_node.next_room;
            while (current_room != null) {
                System.out.println(current_room.getRoom_id() + "   " + current_room.getRoom_beds() + "   " 
                                   + current_room.getUsed_bed() + "   " + map.get(current_room.getRoom_id() / 100));
                current_room = current_room.next_room;
            }
            System.out.println("*********************************************");
            current_node = current_node.next_grade;
        }

    }
 
    //Ԥ��
    private static void passenger_in(Grade_Room root_node) {
        Grade_Room current_node = root_node;
        Passengers new_passenger;
        Scanner cin = new Scanner(System.in);
        System.out.println("������Ҫ��������������ס���ڡ����跿��ȼ��Լ��޷����Ƿ�Ը���������ȼ���0����ͬ�⣬1����ͬ�⣩��eg��4 2018-09-12 1 0");
        String[] passenger_information = cin.nextLine().split(" ");
        if (Integer.parseInt(passenger_information[0]) == 0) {
            System.out.println("������������");
            return;
        }
        for (int i = 0; i < Integer.parseInt(passenger_information[2]); i++) {
            current_node = current_node.next_grade;
            if (current_node == null) {
                System.out.println("�ü���ķ�����������");
                return;
            }
        }
        
        Rooms current_room = current_node.next_room;
        
        for (int i = 0; i < Integer.parseInt(passenger_information[0]); i++) {
            while (current_room != null && (current_room.getRoom_beds() - current_room.getUsed_bed()) < 1) {
                current_room = current_room.next_room;
            }
            if (current_room == null) {
                if (Integer.parseInt(passenger_information[3]) == 0) {
                    System.out.println("�õȼ���λ���䲻�㣬�ÿͲ���������ȼ���");
                    return;
                }
                System.out.println("�õȼ���λ���䲻�㣬���з���ȼ�������");
                current_node = root_node.next_grade;
                while (current_node != null) {
                    current_room = current_node.next_room;
                    while (current_room != null && (current_room.getRoom_beds() - current_room.getUsed_bed()) < 1) {
                        current_room = current_room.next_room;
                    }
                    if (current_room == null)
                        current_node = current_node.next_grade;
                    else
                        break;
                }
                if (current_node == null) {
                    System.out.println("û�п���Ĵ�λ��");
                    return;
                }
            }
            new_passenger = current_room.next_passenger;
            if (new_passenger == null) {
                current_room.next_passenger = new Passengers();
                new_passenger = current_room.next_passenger;
            } else {
                while (new_passenger.next_passenger != null)
                new_passenger = new_passenger.next_passenger;
                new_passenger.next_passenger = new Passengers();
                new_passenger.next_passenger.pre_passenger = new_passenger;
                new_passenger = new_passenger.next_passenger;
            }
            current_room.setUsed_bed(current_room.getUsed_bed() + 1);
            new_passenger.setBed_id(current_room.getUsed_bed());
            new_passenger.setDate(passenger_information[1]);
            new_passenger.setGrade(current_node.getGrade());
            new_passenger.setRoom_id(current_room.getRoom_id());
            System.out.println("�������" + (i + 1) + "���ÿ͵��������ֻ��š�eg��СС 18888888888");
            String[] name_phone = cin.nextLine().split(" ");
            new_passenger.setName(String.format("%1$-15s", name_phone[0]));
            new_passenger.setPhone_number(String.format("%1$-11s", name_phone[1]));
            System.out.println("�ÿ����Ƿ��Ա,������1,������0");
            new_passenger.setVip(Integer.parseInt(cin.nextLine()));
           

            System.out.println("**************************************************************************");
            System.out.println("grade name            room_id bed_id check_in phone_number     ");
            System.out.println("**************************************************************************");
            System.out.println(new_passenger.getGrade() + "     " + new_passenger.getName() +" " 
            		+ new_passenger.getRoom_id() + "    " + new_passenger.getBed_id() + "    " 
            		+ new_passenger.getDate() + "    " + new_passenger.getPhone_number());
        }
        System.out.println("�ÿ�ȫ����ס�ɹ���");
    }
    //�˷�
    private static void passenger_out(Grade_Room root_node) {
        Grade_Room current_node = root_node.next_grade;
        Rooms current_room;
        Passengers current_passenger;
        Scanner cin = new Scanner(System.in);
        System.out.println("�����������ֻ��ţ��Ա�����Ψһ��ȷ��������ݽ����˷���");
        String phone_number = cin.nextLine();
        while (current_node != null) {
            current_room = current_node.next_room;
            while (current_room != null) {
                current_passenger = current_room.next_passenger;
                while (current_passenger != null) {
                	//��ס�ʹ���
                    if (current_passenger.getPhone_number().equals(String.format("%1$-11s", phone_number))) {
                        try {//д��ס����־
                            FileWriter fw = new FileWriter(new File("passenger_log.txt"), true);
                            BufferedWriter bw = new BufferedWriter(fw);                            
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            
                            Date begin = df.parse(current_passenger.getDate());
                            Date end = df.parse(df.format(new Date()));    
                            //��������õ�����΢����������1000��Ϊ��ת������
                            double pay=((end.getTime() - begin.getTime()) / (60 * 60 * 1000 * 24)) * (map.get(current_passenger.getGrade()));
                            if(current_passenger.getVip()) {
                            	pay=pay*0.8;//��Ա�����
                            }
                            bw.write(current_passenger.getDate() + " " + df.format(new Date()) + " " + 
                                     current_passenger.getName() + " " + current_passenger.getPhone_number() + " " + 
                            		 current_passenger.getRoom_id() +" "+pay+"\r\n");              
                            
                            System.out.println("�˷��ɹ����˷����ڣ�" + df.format(new Date()) + "��   �������棺" + pay);
                            bw.close();
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        current_room.setUsed_bed(current_room.getUsed_bed() - 1);
                        if (current_passenger.pre_passenger == null)
                            current_room.next_passenger = current_passenger.next_passenger;
                        else
                            current_passenger.pre_passenger.next_passenger = current_passenger.next_passenger;

                    }

                    current_passenger = current_passenger.next_passenger;
                }
                current_room = current_room.next_room;
            }
            current_node = current_node.next_grade;
        }
    }

    private static void reward() {
        Double result = 0.0;
        try {
            //��ס����־�ж�ȡ����
            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(new File("passenger_log.txt")), "GBK"));
            Scanner cin = new Scanner(System.in);
            System.out.println("�����������������ݺ��·ݡ�eg��2015-04");
            String oriend_date = cin.nextLine();
            
            String item = read.readLine();
            while ((item = read.readLine()) != null) {
                String[] log_information = item.split(" ");
                
                if (log_information[0].substring(0, 7).equals(oriend_date)) {                  
                  result += Double.parseDouble(log_information[log_information.length - 1]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        System.out.println("***********************************************************");
        System.out.println("             ģ���ùݹ���ϵͳ�еĴ�λ����ͻ���ϵͳ      ");
        System.out.println("***********************************************************");
        System.out.println("                     ���������Ĳ�������                      ");
        System.out.println("                     1���ÿ���ס                                  ");
        System.out.println("                     2���ÿ��˷�                              ");
        System.out.println("                     3���鿴�����ÿ���Ϣ                    ");
        System.out.println("                     4���鿴���з�����Ϣ                    ");
        System.out.println("                     5���鿴������                          ");
        System.out.println("                     0���˳�ϵͳ                            ");
        System.out.println("***********************************************************");
        Grade_Room root = new Grade_Room();
        Init(root, "config.txt");
        Scanner cin = new Scanner(System.in);
        int index;
        while (true) {
            System.out.println("������������ţ�");
            index = cin.nextInt();
            switch (index) {
                case 0:
                    return;
                case 1:
                    passenger_in(root);
                    break;
                case 2:
                    passenger_out(root);
                    break;
                case 3:
                    allpassenger(root);
                    break;
                case 4:
                    allroom(root);
                    break;
                case 5:
                    reward();
            }
        }

    }
}
