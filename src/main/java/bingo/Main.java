package bingo;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Генерация таблиц
        TableGenerator tableGenerator = new TableGenerator();
        List<int[][]> tables = tableGenerator.generateUniqueTables(180);
        System.out.println("Сгенерировано 180 уникальных таблиц.");

        // 2. Управление проверкой и модификацией таблиц
        TableManager tableManager = new TableManager(tables);

        System.out.println("Проверка и модификация таблиц для этапа 17...");
        List<int[][]> winners17 = tableManager.findWinnersAndModifyFor17();
        System.out.println("Победителей на этапе 17: " + winners17.size());

        System.out.println("Проверка и модификация таблиц для этапа 27...");
        List<int[][]> winners27 = tableManager.findWinnersAndModifyFor27();
        System.out.println("Победителей на этапе 27: " + winners27.size());

        System.out.println("Проверка и модификация таблиц для этапа 38...");
        List<int[][]> winners38 = tableManager.findWinnersAndModifyFor38();
        System.out.println("Победителей на этапе 38: " + winners38.size());

        // 3. Финальная проверка всех таблиц
        System.out.println("Финальная проверка таблиц...");
        tableManager.finalValidation();
        System.out.println("Финальная проверка завершена.");

        // 4. Запись таблиц в файл .xlsx
        ExcelWriter excelWriter = new ExcelWriter();
        String outputFileName = "Tables.xlsx";

        System.out.println("Запись таблиц в файл...");
        excelWriter.writeTablesToFile(tables, outputFileName);
        System.out.println("Все таблицы успешно записаны в файл: " + outputFileName);
    }
}
