package bingo;
import java.util.*;

public class TableManager {
    private static final int TABLE_COUNT = 180; // Количество таблиц
    private static final int MOVE_17 = 17; // Этап победы для 1-го типа
    private static final int MOVE_27 = 27; // Этап победы для 2-го типа
    private static final int MOVE_38 = 38; // Этап победы для 3-го типа

    private final List<int[][]> tables; // Список таблиц
    private final ActivationRules rules; // Правила активации чисел

    public TableManager(List<int[][]> tables) {
        this.tables = tables;
        this.rules = new ActivationRules();
    }

    // Проверка и модификация таблиц для этапа 17
    public List<int[][]> findWinnersAndModifyFor17() {
        List<int[][]> winners = new ArrayList<>();
        for (int[][] table : tables) {
            if (rules.isWinnerOn17(table, MOVE_17)) {
                winners.add(table);
                modifyTableToAvoidOtherWins(table, MOVE_17);
            }
        }
        return winners;
    }

    // Проверка и модификация таблиц для этапа 27
    public List<int[][]> findWinnersAndModifyFor27() {
        List<int[][]> winners = new ArrayList<>();
        for (int[][] table : tables) {
            if (rules.isWinnerOn27(table, MOVE_27)) {
                winners.add(table);
                modifyTableToAvoidOtherWins(table, MOVE_27);
            }
        }
        return winners;
    }

    // Проверка и модификация таблиц для этапа 38
    public List<int[][]> findWinnersAndModifyFor38() {
        List<int[][]> winners = new ArrayList<>();
        for (int[][] table : tables) {
            if (rules.isWinnerOn38(table, MOVE_38)) {
                winners.add(table);
                modifyTableToAvoidOtherWins(table, MOVE_38);
            }
        }
        return winners;
    }

    // Модификация таблицы, чтобы она не побеждала на других этапах
    private void modifyTableToAvoidOtherWins(int[][] table, int move) {
        Random random = new Random();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                // Если число входит в текущий диапазон хода, заменяем его на большее
                if (table[i][j] <= move) {
                    table[i][j] = move + random.nextInt(50 - move) + 1; // Новое число вне текущего диапазона
                }
            }
        }
    }

    // Финальная проверка всех таблиц после модификации
    public void finalValidation() {
        for (int[][] table : tables) {
            boolean winnerOn17 = rules.isWinnerOn17(table, MOVE_17);
            boolean winnerOn27 = rules.isWinnerOn27(table, MOVE_27);
            boolean winnerOn38 = rules.isWinnerOn38(table, MOVE_38);

            if ((winnerOn17 && (winnerOn27 || winnerOn38)) ||
                    (winnerOn27 && winnerOn38)) {
                modifyTableToAvoidOtherWins(table, Math.max(MOVE_17, Math.max(MOVE_27, MOVE_38)));
            }
        }
    }
}
