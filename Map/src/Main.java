import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final int Infinite = 50000;
    private static final int N = 35;
    private static Map<Integer, String> map = new HashMap<>();//记录地点编号
    private static Map<String, String> roadMap = new HashMap<>();//记录路况信息
    private static Scanner cin = new Scanner(System.in);
    private static int[][] cost = new int[N][N];
    private static int[][] shortest = new int[N][N];
    private static int[][] path = new int[N][N];
    private static String[][] roads=new String[N][N];

    private static void Motor() {
        System.out.println("请输入机动车查询路线的起终点，以空格号隔开起终点编号：");
        int i = cin.nextInt();
        int j = cin.nextInt();
        while (i >= N || i < 1 || j >= N || j < 1) {
            System.out.println("输入信息有误。");
            i = cin.nextInt();
            j = cin.nextInt();
        }
        cost[9][13] = cost[13][9] = cost[9][15] = cost[15][9] = cost[3][15] = cost[15][3] = cost[3][16] = cost[16][3] = cost[3][17] = cost[17][3] = cost[14][22] = cost[22][14] = cost[16][22] = cost[22][16] = cost[15][24] = cost[24][15] = cost[16][24] = cost[24][16] = Infinite;
        Floyed();
        show(i, j, path[i][j]);
        System.out.println("机动车导航完成。");
    }

    private static void Bicycle() {

        System.out.println("请输入自行车查询路线的起终点，以空格号隔开起终点编号：");
        int i = cin.nextInt();
        int j = cin.nextInt();
        while (i >= N || i < 1 || j >= N || j < 1) {
            System.out.println("输入信息有误。");
            i = cin.nextInt();
            j = cin.nextInt();
        }
        cost[9][13] = cost[13][9] = 294;
        cost[9][15] = cost[15][9] = 358;
        cost[3][15] = cost[15][3] = 417;
        cost[3][16] = cost[16][3] = 442;
        cost[3][17] = cost[17][3] = 306;
        cost[14][22] = cost[22][14] = 179;
        cost[16][22] = cost[22][16] = 364;
        cost[15][24] = cost[24][15] = 345;
        cost[16][24] = cost[24][16] = 182;
        Floyed();

        show(i, j, path[i][j]);
        System.out.println("自行车导航完成。");
    }

    private static void Floyed() {//弗洛伊德算法
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                shortest[i][j] = cost[i][j];
                path[i][j] = 0;
            }
        }
        for (int k = 1; k < N; k++) {
            for (int i = 1; i < N; i++) {
                for (int j = 1; j < N; j++) {
                    if (shortest[i][j] > (shortest[i][k] + shortest[k][j])) {
                        shortest[j][i] = shortest[i][j] = shortest[i][k] + shortest[k][j];
                        path[i][j] = k;
                        path[j][i] = k;
                    }
                }
            }
        }
    }

    private static void show(int i, int j, int k) {
        if (k == 0) {
            System.out.print(map.get(i) + "-->" + map.get(j) + "-->");
            return;
        }
        if (path[i][k] == 0 && path[k][j] == 0) {
            System.out.print(map.get(i) + "-->" + map.get(k) + "-->" + map.get(j) + "-->");
            return;
        }
        if (path[i][k] == 0 && path[k][j] != 0) {
            System.out.print(map.get(i) + "-->");
            show(k, j, path[k][j]);
            return;
        }
        if (path[i][k] != 0 && path[k][j] == 0) {
            show(i, k, path[i][k]);
            System.out.print(map.get(j) + "-->");
            return;

        }
        if (path[i][k] != 0 && path[k][j] != 0) {
            show(i, k, path[i][k]);
            System.out.println();
            show(k, j, path[k][j]);
        }

    }

    private static void all() {
        System.out.println("+---------------------------------------------------------+");
        System.out.println("+          建筑物编号             建筑物名称               +");
        System.out.println("+---------------------------------------------------------+");
        for (int i = 1; i < N; i++)
            System.out.println("+            (" + String.format("%1$-2s", i) + ")            " + map.get(i));
        System.out.println("+---------------------------------------------------------+");
        System.out.println();
        System.out.println();
        System.out.println("+---------------------------------------------------------+");
        System.out.println("+           道路名称             道路状态                  +");
        System.out.println("+---------------------------------------------------------+");

        for (Map.Entry<String, String> entry : roadMap.entrySet())
            System.out.println("+           " + entry.getKey() + "               " + entry.getValue());
        System.out.println("+---------------------------------------------------------+");
        System.out.println();
        System.out.println();

    }

    public static void main(String[] args) {

        map.put(1, "北洋门诊部");
        map.put(2, "青园餐厅");
        map.put(3, "化工学院");
        map.put(4, "天津大学体育馆(博文北路)");
        map.put(5, "天津大学网球场");
        map.put(6, "天津大学留园餐厅");
        map.put(7, "天津大学54教学楼");
        map.put(8, "天津大学梅园餐厅");
        map.put(9, "天津大学国家示范型软件学院");
        map.put(10, "天津大学-材料科学与工程学院");
        map.put(11, "棠园餐厅(学3食堂店)");
        map.put(12, "桃园餐厅(新元中路)");
        map.put(13, "天津大学北洋园校区-郑东图书馆(有地下停车场)");
        map.put(14, "天津大学公共教学楼44教");
        map.put(15, "天津大学公共教学楼45教");
        map.put(16, "天津大学47号教学楼");
        map.put(17, "天津大学物理实验中心");
        map.put(18, "天津大学理学院");
        map.put(19, "天津大学公共教学楼");
        map.put(20, "天津大学外国语言语文学学院");
        map.put(21, "北洋广场");
        map.put(22, "天津大学环境科学与工程学院");
        map.put(23, "第三十九教学楼水土建教学组团");
        map.put(24, "天津大学机械工程学院");
        map.put(25, "天津大学篮球场");
        map.put(26, "天津大学热动力大楼");
        map.put(27, "格园");
        map.put(28, "知园");
        map.put(29, "平园");
        map.put(30, "诚园");
        map.put(31, "正园");
        map.put(32, "修园");
        map.put(33, "治园");
        map.put(34, "齐园");

        roadMap.put("自善北道", "通行");
        roadMap.put("自善南道", "通行");
        roadMap.put("侯德榜路", "通行");
        roadMap.put("博文北路", "通行");
        roadMap.put("西沽北路", "通行");
        roadMap.put("七星北路", "通行");
        roadMap.put("双台北路", "通行");
        roadMap.put("亲民西道", "通行");
        roadMap.put("明德西道", "通行");
        roadMap.put("明德北道", "通行");
        roadMap.put("新元北路", "通行");
        roadMap.put("亲民北道", "通行");
        roadMap.put("敬业路  ", "通行");
        roadMap.put("博文南路", "通行");
        roadMap.put("兴学路  ", "通行");
        roadMap.put("明德南道", "通行");
        roadMap.put("亲民南道", "通行");
        roadMap.put("自善东道", "不可通行");
        roadMap.put("王正廷道", "通行");
        roadMap.put("西沽南路", "通行");
        roadMap.put("七星南路", "通行");
        roadMap.put("双台南路", "通行");
        roadMap.put("新元南路", "通行");
        roadMap.put("新元中路", "通行");
        roadMap.put("英华道  ", "通行");
        roadMap.put("双台中路", "通行");
        roadMap.put("书田北道", "通行");
        roadMap.put("书田南道", "通行");
        roadMap.put("七星中路", "自行车通行");
        roadMap.put("西沽中路", "自行车通行");
        roadMap.put("花堤道  ", "通行");
        roadMap.put("求是北道", "通行");


        for (int i = 1; i < N; i++)
            for (int j = 1; j < N; j++)
                cost[i][j] = Infinite;//没有赋值的两点距离默认为无穷
        for (int i = 1; i < N; i++) {
            cost[i][i] = 0;

        }
        cost[1][2] = 135;
        cost[1][3] = 426;
        cost[1][4] = 221;
        cost[1][5] = 278;
        cost[2][6] = 344;
        cost[2][7] = 220;
        cost[2][8] = 376;
        cost[3][4] = 212;
        cost[3][5] = 289;
        cost[3][6] = 311;
        cost[3][7] = 198;
        cost[3][8] = 231;
        cost[3][9] = 301;
        cost[3][10] = 379;
        cost[3][15] = 417;
        cost[3][16] = 442;
        cost[3][17] = 306;
        cost[4][5] = 192;
        cost[4][9] = 483;
        cost[5][9] = 1100;
        cost[5][10] = 244;
        cost[6][8] = 103;
        cost[6][9] = 285;
        cost[7][8] = 163;
        cost[8][9] = 208;
        cost[9][11] = 280;
        cost[9][13] = 294;
        cost[9][15] = 358;
        cost[10][17] = 382;
        cost[10][18] = 198;
        cost[10][19] = 397;
        cost[10][21] = 233;
        cost[11][12] = 249;
        cost[11][13] = 202;
        cost[12][13] = 202;
        cost[12][14] = 143;
        cost[12][22] = 246;
        cost[13][14] = 136;
        cost[13][15] = 136;
        cost[14][15] = 213;
        cost[14][16] = 325;
        cost[14][22] = 179;
        cost[15][16] = 171;
        cost[15][17] = 233;
        cost[15][24] = 345;
        cost[16][17] = 161;
        cost[16][22] = 364;
        cost[16][23] = 418;
        cost[16][24] = 182;
        cost[17][18] = 271;
        cost[18][19] = 209;
        cost[18][20] = 235;
        cost[18][21] = 182;
        cost[19][20] = 134;
        cost[19][21] = 332;
        cost[19][24] = 210;
        cost[20][21] = 237;
        cost[20][24] = 334;
        cost[22][23] = 249;
        cost[22][24] = 387;
        cost[23][24] = 298;
        cost[23][25] = 373;
        cost[23][26] = 395;
        cost[24][25] = 225;
        cost[24][26] = 438;
        cost[25][26] = 269;
        cost[6][27] = 91;
        cost[7][27] = 161;
        cost[8][28] = 90;
        cost[6][28] = 92;
        cost[7][28] = 83;
        cost[9][29] = 221;
        cost[11][29] = 195;
        cost[9][30] = 141;
        cost[9][30] = 141;
        cost[3][30] = 181;
        cost[15][31] = 147;
        cost[13][31] = 211;
        cost[15][32] = 81;
        cost[13][32] = 182;
        cost[16][32] = 133;
        cost[12][33] = 170;
        cost[22][33] = 181;
        cost[22][34] = 172;
        cost[24][34] = 209;
        cost[23][34] = 201;


        for (int i = 1; i < N; i++)
            for (int j = 1; j < i; j++)
                cost[i][j] = cost[j][i];
        all();
        System.out.println("+---------------------------------------------------------+");
        System.out.println("+              欢迎来到天津大学北洋园校区导航系统           +");
        System.out.println("+---------------------------------------------------------+");
        System.out.println("+             1:机动车导航                                +");
        System.out.println("+             2:自行车导航                                +");
        System.out.println("+             3:查询所有建筑和道路信息                     +");
        System.out.println("+             4:退出                                      +");
        System.out.println("+---------------------------------------------------------+");
        int index;
        while (true) {

            System.out.println("请输入您要执行的操作代号：");
            index = cin.nextInt();
            switch (index) {
                case 1:
                    Motor();
                    break;
                case 2:
                    Bicycle();
                    break;
                case 3:
                    all();
                    break;
                default:
                    return;
            }
        }


    }
}