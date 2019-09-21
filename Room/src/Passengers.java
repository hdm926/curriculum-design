public class Passengers {
    public Passengers next_passenger;
    public Passengers pre_passenger;
    private boolean vip;
    private String name;
    private String phone_number;
    private int grade;
    private int bed_id;
    private int room_id;
    private String date;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getBed_id() {
        return bed_id;
    }

    public void setBed_id(int bed_id) {
        this.bed_id = bed_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public boolean getVip() {
    	return vip;
    }
    
    public void setVip(int i) {
    	if(i==1)
    	this.vip=true;
    }
}
