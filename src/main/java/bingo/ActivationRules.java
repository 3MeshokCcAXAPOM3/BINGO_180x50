package bingo;
import java.util.Arrays;

public class ActivationRules {
    private static final int TABLE_SIZE = 4;

    // Метод для определения активных чисел на текущем ходу
    public boolean isNumberActive(int number, int currentMove) {
        return number <= currentMove;
    }

    // Проверка, активна ли строка
    public boolean isRowActive(int[][] table, int rowIndex, int currentMove) {
        for (int number : table[rowIndex]) {
            if (!isNumberActive(number, currentMove)) {
                return false;
            }
        }
        return true;
    }

    // Проверка, активен ли столбец
    public boolean isColumnActive(int[][] table, int columnIndex, int currentMove) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (!isNumberActive(table[i][columnIndex], currentMove)) {
                return false;
            }
        }
        return true;
    }

    // Проверка, активна ли диагональ
    public boolean isMainDiagonalActive(int[][] table, int currentMove) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (!isNumberActive(table[i][i], currentMove)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSecondaryDiagonalActive(int[][] table, int currentMove) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (!isNumberActive(table[i][TABLE_SIZE - i - 1], currentMove)) {
                return false;
            }
        }
        return true;
    }

    // Проверка, побеждает ли таблица на 17-м ходу
    public boolean isWinnerOn17(int[][] table, int currentMove) {
        if (currentMove < 17) {
            return false; // Победителей до 17-го хода быть не должно
        }

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (isRowActive(table, i, currentMove) || isColumnActive(table, i, currentMove)) {
                return true;
            }
        }

        return isMainDiagonalActive(table, currentMove) || isSecondaryDiagonalActive(table, currentMove);
    }

    // Проверка, побеждает ли таблица на 27-м ходу
    public boolean isWinnerOn27(int[][] table, int currentMove) {
        if (currentMove < 27) {
            return false; // Победителей до 27-го хода быть не должно
        }

        int activeLines = 0;

        // Проверяем строки и столбцы
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (isRowActive(table, i, currentMove)) activeLines++;
            if (isColumnActive(table, i, currentMove)) activeLines++;
        }

        // Проверяем диагонали
        if (isMainDiagonalActive(table, currentMove)) activeLines++;
        if (isSecondaryDiagonalActive(table, currentMove)) activeLines++;

        return activeLines >= 2;
    }

    // Проверка, побеждает ли таблица на 38-м ходу
    public boolean isWinnerOn38(int[][] table, int currentMove) {
        if (currentMove < 38) {
            return false; // Победителей до 38-го хода быть не должно
        }

        for (int i = 0; i < TABLE_SIZE; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
                if (!isNumberActive(table[i][j], currentMove)) {
                    return false;
                }
            }
        }
        return true;
    }
}
