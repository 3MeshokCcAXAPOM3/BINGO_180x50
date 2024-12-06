package bingo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriter {
    private static final int START_ROW = 0; // Начальная строка для записи таблиц

    // Метод записи таблиц в файл
    public void writeTablesToFile(List<int[][]> tables, String fileName) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Таблицы");

            int rowIndex = START_ROW;

            for (int i = 0; i < tables.size(); i++) {
                // Подпись над таблицей
                Row titleRow = sheet.createRow(rowIndex++);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("Таблица " + (i + 1));

                // Запись таблицы
                int[][] table = tables.get(i);
                for (int[] row : table) {
                    Row excelRow = sheet.createRow(rowIndex++);
                    for (int j = 0; j < row.length; j++) {
                        Cell cell = excelRow.createCell(j);
                        cell.setCellValue(row[j]);
                    }
                }

                // Пустая строка после таблицы
                rowIndex++;
            }

            // Сохранение файла
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
            }

            System.out.println("Файл успешно сохранён: " + fileName);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
