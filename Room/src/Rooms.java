public class Rooms {
    private int room_id;
    private int used_bed=0;
    private int room_beds;
    private int room_status=0;
    //����״̬�����շ�(0)���෿(1)���˷�1������תΪ�շ�����Ԥ��(2)��ס��(3)������(4)���ӵ㷿(5)��
    public Rooms next_room;
    public Passengers next_passenger;

    public int getRoom_status() {
    	return room_status;
    }
    
    public void setRoom_status(int status) {
    	this.room_status=status;
    }
    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getUsed_bed() {
        return used_bed;
    }

    public void setUsed_bed(int used_bed) {
        this.used_bed = used_bed;
    }

    public int getRoom_beds() {
        return room_beds;
    }

    public void setRoom_beds(int room_beds) {
        this.room_beds = room_beds;
    }
}
