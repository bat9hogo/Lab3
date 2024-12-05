import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import static java.lang.Math.ceil;


public class CellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    // Ищем ячейки, строковое представление которых совпадает с needle
    // (иголкой). Применяется аналогия поиска иголки в стоге сена, в роли
    // стога сена - таблица
    private String needle = null;
    private  boolean isPrime = false;
    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();
    public CellRenderer() {
        // Показывать только 5 знаков после запятой
        formatter.setMaximumFractionDigits(10);
        // Не использовать группировку (т.е. не отделять тысячи
        // ни запятыми, ни пробелами), т.е. показывать число как "1000",
        // а не "1 000" или "1,000"
        formatter.setGroupingUsed(false);
        // Установить в качестве разделителя дробной части точку, а не
        // запятую. По умолчанию, в региональных настройках
        // Россия/Беларусь дробная часть отделяется запятой
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        // Разместить надпись внутри панели
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        // Установить выравнивание надписи по левому краю панели
    }
    public static boolean isWithinRangeOfPrime(Object value) {
        double num = (Double) value;
        int closestPrime = findClosestPrime((int) Math.round(num));
        return Math.abs(num - closestPrime) <= 0.1;
    }
    private static int findClosestPrime(int n) {
        if (n < 2)
            return 2;
        while (true) {
            if (isPrime(n))
                return n;
            n++;
        }
    }
    private static boolean isPrime(int n) {
        if (n <= 1)
            return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        // Преобразовать double в строку с помощью форматировщика
        String formattedDouble = formatter.format(value);
        // Установить текст надписи равным строковому представлению числа
        label.setText(formattedDouble);
        if((Double)value < 0) {
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        } else if((Double)value > 0) {
            panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        } else {
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        }

        if (needle!=null && needle.equals(formattedDouble)) {
            // Номер столбца = 1 (т.е. второй столбец) + иголка не null
            // (значит что-то ищем) +
            // значение иголки совпадает со значением ячейки таблицы -
            // окрасить задний фон панели в красный цвет
            panel.setBackground(Color.RED);
        } else {
            // Иначе - в обычный белый
            panel.setBackground(Color.WHITE);
        }

        if(isWithinRangeOfPrime(value) && isPrime) {
            panel.setBackground(Color.green);
        }

        return panel;
    }
    public void setNeedle(String needle) {
        this.needle = needle;
    }
    public void setPrime(){
        this.isPrime = true;
    }
}
