package bingo;
import java.util.*;

public class TableGenerator {
    private static final int TABLE_SIZE = 4; // Размер таблицы 4x4
    private static final int NUMBER_RANGE = 50; // Диапазон чисел от 1 до 50
    private static final int MAX_REPEATS = 2; // Максимальное количество повторений числа в одной таблице

    // Метод для генерации одной таблицы
    public int[][] generateTable() {
        int[][] table = new int[TABLE_SIZE][TABLE_SIZE];
        Map<Integer, Integer> numberCount = new HashMap<>(); // Хранит, сколько раз число встречается

        Random random = new Random();
        for (int i = 0; i < TABLE_SIZE; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
                int num;
                do {
                    num = random.nextInt(NUMBER_RANGE) + 1; // Генерируем число от 1 до 50
                } while (numberCount.getOrDefault(num, 0) >= MAX_REPEATS);

                table[i][j] = num;
                numberCount.put(num, numberCount.getOrDefault(num, 0) + 1);
            }
        }
        return table;
    }

    // Метод для генерации нескольких уникальных таблиц
    public List<int[][]> generateUniqueTables(int count) {
        List<int[][]> tables = new ArrayList<>();
        Set<String> tableHashes = new HashSet<>(); // Для проверки уникальности таблиц

        while (tables.size() < count) {
            int[][] table = generateTable();
            String hash = Arrays.deepToString(table); // Хэш строки для проверки уникальности
            if (!tableHashes.contains(hash)) {
                tables.add(table);
                tableHashes.add(hash);
            }
        }
        return tables;
    }

    // Вывод таблицы в консоль (для отладки)
    public void printTable(int[][] table) {
        for (int[] row : table) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }
}
