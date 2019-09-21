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
            File file = new File(filepath);//读config.txt
            if (file.isFile() && file.exists()) {
                InputStreamReader cin = new InputStreamReader(new FileInputStream(file), "GBK");
                BufferedReader read = new BufferedReader(cin);
                String[] outline_result = read.readLine().split(" ");
                int max_grade = Integer.parseInt(outline_result[0]);//config.txt的第一行第一个字符表示了最高等级
                //构造房间等级的队列
                for (int i = 1; i <= max_grade; i++) {
                    current_node.next_grade = new Grade_Room();
                    current_node = current_node.next_grade;
                    current_node.setGrade(i);
                    //存入相应等级房间的房价
                    map.put(i, Integer.parseInt(outline_result[i]));
                    
                    String[] item_result = read.readLine().split(" ");
                    int rooms = Integer.parseInt(item_result[0]);//rooms表示该等级的房间总数
                    Rooms current_room = new Rooms();//不同等级房间依次构成的队列
                    
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
                System.out.println("找不到指定文件");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //查询住客信息，循环层次：等级-房间-床位
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
        System.out.println("房间号 床位数  已使用房间 日收费  +");
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
 
    //预定
    private static void passenger_in(Grade_Room root_node) {
        Grade_Room current_node = root_node;
        Passengers new_passenger;
        Scanner cin = new Scanner(System.in);
        System.out.println("本次需要订房的人数、入住日期、所需房间等级以及无房间是否愿意更换房间等级（0代表不同意，1代表同意）。eg：4 2018-09-12 1 0");
        String[] passenger_information = cin.nextLine().split(" ");
        if (Integer.parseInt(passenger_information[0]) == 0) {
            System.out.println("订房人数有误。");
            return;
        }
        for (int i = 0; i < Integer.parseInt(passenger_information[2]); i++) {
            current_node = current_node.next_grade;
            if (current_node == null) {
                System.out.println("该级别的房间已满房。");
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
                    System.out.println("该等级床位房间不足，旅客不更换房间等级。");
                    return;
                }
                System.out.println("该等级床位房间不足，进行房间等级更换。");
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
                    System.out.println("没有空余的床位。");
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
            System.out.println("请输入第" + (i + 1) + "名旅客的姓名和手机号。eg：小小 18888888888");
            String[] name_phone = cin.nextLine().split(" ");
            new_passenger.setName(String.format("%1$-15s", name_phone[0]));
            new_passenger.setPhone_number(String.format("%1$-11s", name_phone[1]));
            System.out.println("该客人是否会员,是输入1,否输入0");
            new_passenger.setVip(Integer.parseInt(cin.nextLine()));
           

            System.out.println("**************************************************************************");
            System.out.println("grade name            room_id bed_id check_in phone_number     ");
            System.out.println("**************************************************************************");
            System.out.println(new_passenger.getGrade() + "     " + new_passenger.getName() +" " 
            		+ new_passenger.getRoom_id() + "    " + new_passenger.getBed_id() + "    " 
            		+ new_passenger.getDate() + "    " + new_passenger.getPhone_number());
        }
        System.out.println("旅客全部入住成功。");
    }
    //退房
    private static void passenger_out(Grade_Room root_node) {
        Grade_Room current_node = root_node.next_grade;
        Rooms current_room;
        Passengers current_passenger;
        Scanner cin = new Scanner(System.in);
        System.out.println("请输入您的手机号，以便我们唯一的确认您的身份进行退房。");
        String phone_number = cin.nextLine();
        while (current_node != null) {
            current_room = current_node.next_room;
            while (current_room != null) {
                current_passenger = current_room.next_passenger;
                while (current_passenger != null) {
                	//该住客存在
                    if (current_passenger.getPhone_number().equals(String.format("%1$-11s", phone_number))) {
                        try {//写入住客日志
                            FileWriter fw = new FileWriter(new File("passenger_log.txt"), true);
                            BufferedWriter bw = new BufferedWriter(fw);                            
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            
                            Date begin = df.parse(current_passenger.getDate());
                            Date end = df.parse(df.format(new Date()));    
                            //日期相减得到相差的微秒数，除以1000是为了转化成秒
                            double pay=((end.getTime() - begin.getTime()) / (60 * 60 * 1000 * 24)) * (map.get(current_passenger.getGrade()));
                            if(current_passenger.getVip()) {
                            	pay=pay*0.8;//会员打八折
                            }
                            bw.write(current_passenger.getDate() + " " + df.format(new Date()) + " " + 
                                     current_passenger.getName() + " " + current_passenger.getPhone_number() + " " + 
                            		 current_passenger.getRoom_id() +" "+pay+"\r\n");              
                            
                            System.out.println("退房成功。退房日期：" + df.format(new Date()) + "。   本次收益：" + pay);
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
            //从住客日志中读取收益
            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(new File("passenger_log.txt")), "GBK"));
            Scanner cin = new Scanner(System.in);
            System.out.println("请输入所查收入的年份和月份。eg：2015-04");
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
        System.out.println("             模拟旅馆管理系统中的床位分配和回收系统      ");
        System.out.println("***********************************************************");
        System.out.println("                     请输入您的操作代号                      ");
        System.out.println("                     1：旅客入住                                  ");
        System.out.println("                     2：旅客退房                              ");
        System.out.println("                     3：查看所有旅客信息                    ");
        System.out.println("                     4：查看所有房间信息                    ");
        System.out.println("                     5：查看月收入                          ");
        System.out.println("                     0：退出系统                            ");
        System.out.println("***********************************************************");
        Grade_Room root = new Grade_Room();
        Init(root, "config.txt");
        Scanner cin = new Scanner(System.in);
        int index;
        while (true) {
            System.out.println("请输入操作代号：");
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
