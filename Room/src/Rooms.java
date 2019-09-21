public class Rooms {
    private int room_id;
    private int used_bed=0;
    private int room_beds;
    private int room_status=0;
    //房间状态包括空房(0)、脏房(1)（退房1天后可以转为空房）、预定(2)、住人(3)、长包(4)和钟点房(5)。
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
