import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")

public class TableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    public TableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }
    public Double getFrom() {
        return from;
    }
    public Double getTo() {
        return to;
    }
    public Double getStep() {
        return step;
    }
    public int getColumnCount() {
        // В данной модели два столбца
        return 4;
    }
    public int getRowCount() {
        // Вычислить количество точек между началом и концом отрезка
        // исходя из шага табулирования
        double value = (to - from) / step;
        int res = (int)Math.ceil(value);
        return res;
    }
    public Object getValueAt(int row, int col) {
        // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step*row;
        Double result = 0.0;
        if (col==0) {
            // Если запрашивается значение 1-го столбца, то это X
            result = x;
        }
        else if (col == 1){
            // Если запрашивается значение 2-го столбца, то это значение
            // многочлена
            // Вычисление значения в точке по схеме Горнера.
            // Вспомнить 1-ый курс и реализовать
            // ...
            result = coefficients[0]; // Начинаем с коэффициента при старшей степени
            for (int i = 1; i < coefficients.length; i++) {
                result = result * x + coefficients[i];
            }
        }
        else if (col == 2) {
            for (int i = 0; i < coefficients.length; i++) {
                result += coefficients[i] * Math.pow(x, coefficients.length - 1 - i);
            }
        }
        else
        {
            Double res1 = coefficients[0];
            Double res2 = 0.0;
            for (int i = 1; i < coefficients.length; i++) {
                res1 = res1 * x + coefficients[i];
            }
            for (int i = 0; i < coefficients.length; i++) {
                res2 += coefficients[i] * Math.pow(x, coefficients.length - 1 - i);
            }
            result = res1-res2;
        }
        return result;
    }
    public String getColumnName(int col) {
        switch (col) {
            case 0:
                // Название 1-го столбца
                return "Значение X";
            case 1:
                return "Схема Горнера";
            case 2:
                return "Возведение в степень";
            default:
                return "Разница";
        }
    }
    public Class<?> getColumnClass(int col) {
        // И в 1-ом и во 2-ом столбце находятся значения типа Double
        return Double.class;
    }
}
