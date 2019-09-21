import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class courseForm implements TableModel {

    private String[] title = {
            "星期一", "星期二", "星期三", "星期四", "星期五"
    };
    private String[][] data = new String[4][5];

    public courseForm() {
    }
    public courseForm(String[] pass) {
        this.data = data;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                this.data[i][j] = pass[i + j * 4];
            }
        }
    }

    @Override
    public int getRowCount() {
        return 4;
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return title[columnIndex];
    }

    @Override
    public java.lang.Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = (String) aValue;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
