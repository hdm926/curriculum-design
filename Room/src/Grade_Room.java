public class Grade_Room {
    private int grade;
    private  int room_capacity;
    public Grade_Room next_grade;
    public Rooms next_room;

    public int getRoom_capacity() {
        return room_capacity;
    }

    public void setRoom_capacity(int room_capacity) {
        this.room_capacity = room_capacity;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
