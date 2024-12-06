//package bingo;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.*;
//
//public class TableGeneratorTest {
//
//    public static void main(String[] args) {
//        // Параметры задачи
//        final int tableCount = 180;
//        final int tableSize = 4;
//        final int maxValue = 50;
//        final String outputFilePath = "tables.xlsx";
//
//        // Создание и валидация таблиц
//        List<int[][]> tables = generateUniqueTables(tableCount, tableSize, maxValue);
//        determineWinners(tables, tableSize);
//
//        // Сохранение таблиц в файл
//        try {
//            saveTablesToExcel(tables, outputFilePath, tableSize);
//            System.out.println("Таблицы успешно сохранены в " + outputFilePath);
//        } catch (IOException e) {
//            System.err.println("Ошибка при сохранении файла: " + e.getMessage());
//        }
//    }
//
//    // Генерация уникальных таблиц
//    private static List<int[][]> generateUniqueTables(int count, int size, int maxValue) {
//        List<int[][]> tables = new ArrayList<>();
//        Random random = new Random();
//
//        while (tables.size() < count) {
//            int[][] table = new int[size][size];
//            Map<Integer, Integer> frequencyMap = new HashMap<>();
//
//            for (int i = 0; i < size; i++) {
//                for (int j = 0; j < size; j++) {
//                    int value;
//                    do {
//                        value = random.nextInt(maxValue) + 1;
//                    } while (frequencyMap.getOrDefault(value, 0) >= 2); // Ограничение на повторения
//                    table[i][j] = value;
//                    frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
//                }
//            }
//
//            // Проверка уникальности
//            if (!containsTable(tables, table)) {
//                tables.add(table);
//            }
//        }
//        return tables;
//    }
//
//    // Проверка на наличие таблицы в списке
//    private static boolean containsTable(List<int[][]> tables, int[][] table) {
//        for (int[][] existingTable : tables) {
//            if (Arrays.deepEquals(existingTable, table)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // Логика определения победителей
//    private static void determineWinners(List<int[][]> tables, int size) {
//        Set<Integer> winners = new HashSet<>();
//        int firstWinnerIndex = -1;
//        int secondWinnerIndex = -1;
//        int thirdWinnerIndex = -1;
//
//        for (int move = 1; move <= 50; move++) {
//            Set<Integer> activeNumbers = getActiveNumbers(move);
//
//            for (int i = 0; i < tables.size(); i++) {
//                if (winners.contains(i)) continue; // Пропускаем уже победившие таблицы
//
//                int[][] table = tables.get(i);
//
//                if (firstWinnerIndex == -1 && isFirstWinner(table, activeNumbers, size)) {
//                    if (move >= 17) {
//                        firstWinnerIndex = i;
//                        winners.add(i);
//                    }
//                } else if (secondWinnerIndex == -1 && isSecondWinner(table, activeNumbers, size)) {
//                    if (move >= 27) {
//                        secondWinnerIndex = i;
//                        winners.add(i);
//                    }
//                } else if (thirdWinnerIndex == -1 && isThirdWinner(table, activeNumbers, size, move)) {
//                    if (move >= 38) {
//                        thirdWinnerIndex = i;
//                        winners.add(i);
//                    }
//                }
//            }
//
//            // Выходим из цикла, если все победители найдены
//            if (firstWinnerIndex != -1 && secondWinnerIndex != -1 && thirdWinnerIndex != -1) {
//                break;
//            }
//        }
//
//        System.out.println("Индексы победителей:");
//        System.out.println("1-я таблица победитель: " + firstWinnerIndex);
//        System.out.println("2-я таблица победитель: " + secondWinnerIndex);
//        System.out.println("3-я таблица победитель: " + thirdWinnerIndex);
//    }
//
//    // Получение активных чисел для текущего хода
//    private static Set<Integer> getActiveNumbers(int move) {
//        Set<Integer> activeNumbers = new HashSet<>();
//        for (int i = 1; i <= move; i++) {
//            activeNumbers.add(i);
//        }
//        return activeNumbers;
//    }
//
//    // Проверка условия для 1-й таблицы победителя
//    private static boolean isFirstWinner(int[][] table, Set<Integer> activeNumbers, int size) {
//        // Проверка строк
//        for (int i = 0; i < size; i++) {
//            boolean rowActive = true;
//            for (int j = 0; j < size; j++) {
//                if (!activeNumbers.contains(table[i][j])) {
//                    rowActive = false;
//                    break;
//                }
//            }
//            if (rowActive) return true;
//        }
//
//        // Проверка столбцов
//        for (int j = 0; j < size; j++) {
//            boolean colActive = true;
//            for (int i = 0; i < size; i++) {
//                if (!activeNumbers.contains(table[i][j])) {
//                    colActive = false;
//                    break;
//                }
//            }
//            if (colActive) return true;
//        }
//
//        // Проверка диагоналей
//        boolean diag1Active = true, diag2Active = true;
//        for (int i = 0; i < size; i++) {
//            if (!activeNumbers.contains(table[i][i])) diag1Active = false;
//            if (!activeNumbers.contains(table[i][size - i - 1])) diag2Active = false;
//        }
//        return diag1Active || diag2Active;
//    }
//
//    // Проверка условия для 2-й таблицы победителя
//    private static boolean isSecondWinner(int[][] table, Set<Integer> activeNumbers, int size) {
//        int activeRows = 0, activeCols = 0;
//        boolean diag1Active = true, diag2Active = true;
//
//        // Проверка строк и столбцов
//        for (int i = 0; i < size; i++) {
//            boolean rowActive = true, colActive = true;
//            for (int j = 0; j < size; j++) {
//                if (!activeNumbers.contains(table[i][j])) rowActive = false;
//                if (!activeNumbers.contains(table[j][i])) colActive = false;
//            }
//            if (rowActive) activeRows++;
//            if (colActive) activeCols++;
//        }
//
//        // Проверка диагоналей
//        for (int i = 0; i < size; i++) {
//            if (!activeNumbers.contains(table[i][i])) diag1Active = false;
//            if (!activeNumbers.contains(table[i][size - i - 1])) diag2Active = false;
//        }
//        int activeDiagonals = (diag1Active ? 1 : 0) + (diag2Active ? 1 : 0);
//
//        return (activeRows + activeCols + activeDiagonals) >= 2;
//    }
//
//    // Проверка условия для 3-й таблицы победителя
//    private static boolean isThirdWinner(int[][] table, Set<Integer> activeNumbers, int size, int move) {
//        if (move < 38) return false;
//
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                if (!activeNumbers.contains(table[i][j]) || table[i][j] > 38) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//
//    // Сохранение таблиц в Excel
//    private static void saveTablesToExcel(List<int[][]> tables, String filePath, int size) throws IOException {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Tables");
//
//        int rowIndex = 0;
//        for (int[][] table : tables) {
//            for (int[] row : table) {
//                Row excelRow = sheet.createRow(rowIndex++);
//                for (int col = 0; col < size; col++) {
//                    excelRow.createCell(col).setCellValue(row[col]);
//                }
//            }
//            // Добавляем разделяющую строку
//            rowIndex++;
//        }
//
//        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
//            workbook.write(fileOut);
//        } finally {
//            workbook.close();
//        }
//    }
//}
