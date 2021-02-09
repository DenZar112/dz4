package com.company;
import java.util.Random;
import java.util.Scanner;

public class Main {
    /*Полностью разобраться с кодом, попробовать переписать с нуля, стараясь не подглядывать в методичку.
      Переделать проверку победы, чтобы она не была реализована просто набором условий,
      например, с использованием циклов.
    * Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и количества фишек 4.
      Очень желательно не делать это просто набором условий для каждой из возможных ситуаций;
    *** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока.*/

    final int SIZE = 5;
    char[][] map = new char[SIZE][SIZE];
    final char DOT_EMPTY = '•';
    final char DOT_X = 'x';
    final char DOT_O = 'o';
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    public static void main(String[] args) {

        Main dz4 = new Main();
        dz4.newMap();
        dz4.printMap();

        while (true) {
            //Ходит игрок
            dz4.humanTurn();
            dz4.printMap();
            if (dz4.checkWin(dz4.DOT_X)) {
                System.out.println("Победил Игрок!");
                break;
            }
            if (dz4.mapIsFull()) {
                System.out.println("Ничья!");
                break;
            }

            //Ходит компьютер
            dz4.aiTurn(dz4.DOT_O);
            dz4.printMap();
            if (dz4.checkWin(dz4.DOT_O)) {
                System.out.println("Победил компьютер!");
                break;
            }
            if (dz4.mapIsFull()) {
                System.out.println("Ничья!");
                break;
            }

        }
    }

    void newMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    void printMap() {

        System.out.println();
        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();

    }

    void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите коордаты");
            System.out.println("Введите X");
            x = scanner.nextInt() - 1;
            System.out.println("Введите Y");
            y = scanner.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[x][y] = DOT_X;
    }

    boolean isCellValid(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE && map[x][y] == DOT_EMPTY;
    }

    boolean checkWin(char symbol) {
        int vertical = 0;
        int horizon = 0;
        int straightDiagonal = 0;
        int reverseDiagonal = 0;
        for (int i = 0; i <= SIZE - 1; i++) {

            for (int j = 0; j <= SIZE - 1; j++) {
                //горизонтальная проверка
                if (map[i][j] == symbol) {
                    horizon++;
                    if (horizon == SIZE){
                        return true;
                    }
                }

                //вертикальная проверка
                if (map[j][i] == symbol) {
                    vertical++;
                    if (vertical == SIZE){
                        return true;
                    }
                }
            }

            // проверка прямой диагонали
            if (map[i][i] == symbol) {
                straightDiagonal++;
                if (straightDiagonal == SIZE){
                    return true;
                }
            } else{
                straightDiagonal = 0;
            }

            // проверка обратной диагонали
            if (map[i][SIZE - 1 - i] == symbol) {
                reverseDiagonal++;
                if (reverseDiagonal == SIZE){
                    return true;
                }
            } else{
                reverseDiagonal = 0;
            }
        }
        return false;
    }

    boolean mapIsFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY){
                    return false;
                }
            }
        }
        return true;
    }

    void aiTurn(char c) {
        int     x = 0, y = 0,
                horizon = 0, horizonFree = 0,
                vertical = 0, verticalFree = 0,
                straightDiagonal = 0, reverseDiagonal = 0,
                straightDiagonalFree = 0, reverseDiagonalFree = 0;

        System.out.println("Ход Компьютера");

        // 1. выигрышная стратегия игры компьютером:
        for (int i = 0; i < SIZE; i++) {
            horizon = 0;
            horizonFree = 0;
            vertical = 0;
            verticalFree = 0;
            for (int j = 0; j < SIZE; j++) {
                //блок по вертикали:
                if (map[j][i] == c){
                    vertical++;
                }
                else if (map[j][i] == DOT_EMPTY){
                    verticalFree++;
                }
                if ((vertical == SIZE - 1) && (verticalFree == 1)) {

                    for (int k = 0; k < SIZE; k++) {
                        if (map[k][i] == DOT_EMPTY) {
                            map[k][i] = c;
                            return;
                        }
                    }
                }
                //блок по горизонтали:
                if (map[i][j] == c){
                    horizon++;
                } else if (map[i][j] == DOT_EMPTY){
                    horizonFree++;
                }
                if ((horizon == SIZE - 1) && (horizonFree == 1)) {

                    for (int k = 0; k < SIZE; k++) {
                        if (map[i][k] == DOT_EMPTY) {
                            map[i][k] = c;
                            return;
                        }
                    }
                }

            }

            // блок по прямой диагонали:
            if (map[i][i] == c){
                straightDiagonal++;
            } else if (map[i][i] == DOT_EMPTY){
                straightDiagonalFree++;
            }
            if ((straightDiagonal == SIZE - 1) && (straightDiagonalFree == 1)) {

                for (int j = 0; j < SIZE; j++) {
                    if (map[j][j] == DOT_EMPTY) {
                        map[j][j] = c;
                        return;
                    }
                }
            }

            // блокировка по обратной диагонали:
            if (map[i][SIZE - 1 - i] == c){
                reverseDiagonal++;
            } else if (map[i][SIZE - 1 - i] == DOT_EMPTY){
                reverseDiagonalFree++;
            }
            if ((reverseDiagonal == SIZE - 1) && (reverseDiagonalFree == 1)) {

                for (int j = 0; j < SIZE; j++) {
                    if (map[j][SIZE - 1 - j] == DOT_EMPTY) {
                        map[j][SIZE - 1 - j] = c;
                        return;
                    }
                }
            }
        }


        straightDiagonal = 0;
        straightDiagonalFree = 0;
        reverseDiagonal = 0;
        reverseDiagonalFree = 0;


        // 2. Блокировки ходов игрока компьютером:
        for (int i = 0; i < SIZE; i++) {
            horizon = 0;
            horizonFree = 0;
            vertical = 0;
            verticalFree = 0;

            for (int j = 0; j < SIZE; j++) {
                //по вертикали:
                if (map[j][i] == DOT_X){
                    vertical++;
                } else if (map[j][i] == DOT_EMPTY){
                    verticalFree++;
                }
                if ((vertical == SIZE - 1) && (verticalFree == 1)) {

                    for (int k = 0; k < SIZE; k++) {
                        if (map[k][i] == DOT_EMPTY) {
                            map[k][i] = c;
                            return;
                        }
                    }
                }
                //по горизонтали:
                if (map[i][j] == DOT_X){
                    horizon++;
                } else if (map[i][j] == DOT_EMPTY){
                    horizonFree++;
                }
                if ((horizon == SIZE - 1) && (horizonFree == 1)) {

                    for (int k = 0; k < SIZE; k++) {
                        if (map[i][k] == DOT_EMPTY) {
                            map[i][k] = c;
                            return;
                        }
                    }
                }

            }


            // по прямой диагонали:

            if (map[i][i] == DOT_X){
                straightDiagonal++;
            } else if (map[i][i] == DOT_EMPTY){
                straightDiagonalFree++;
            }
            if ((straightDiagonal == SIZE - 1) && (straightDiagonalFree == 1)) {

                for (int j = 0; j < SIZE; j++) {
                    if (map[j][j] == DOT_EMPTY) {
                        map[j][j] = c;
                        return;
                    }
                }
            }

            // по обратной диагонали:

            if (map[i][SIZE - 1 - i] == DOT_X){
                reverseDiagonal++;
            } else if (map[i][SIZE - 1 - i] == DOT_EMPTY){
                reverseDiagonalFree++;
            }
            if ((reverseDiagonal == SIZE - 1) && (reverseDiagonalFree == 1)) {

                for (int j = 0; j < SIZE; j++) {
                    if (map[j][SIZE - 1 - j] == DOT_EMPTY) {
                        map[j][SIZE - 1 - j] = c;
                        return;
                    }
                }
            }
        }

        // 3. стратегия захвата центра поля компьютером:
        if (!(SIZE % 2 == 0)) {
            int center = (((SIZE + 1) / 2) - 1);
            if (map[center][center] == DOT_EMPTY) {
                map[center][center] = c;
                return;
            }
        }

        // 4. стратегия захвата диагоналей поля компьютером:
        if (map[0][0] == DOT_EMPTY) {
            map[0][0] = c;
            return;
        }
        if (map[0][map.length - 1] == DOT_EMPTY) {
            map[0][map.length - 1] = c;
            return;
        }
        if (map[map.length - 1][0] == DOT_EMPTY) {
            map[map.length - 1][0] = c;
            return;
        }
        if (map[map.length - 1][map.length - 1] == DOT_EMPTY) {
            map[map.length - 1][map.length - 1] = c;
            return;
        }

        // 5. ход компьютера:

        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(x, y));
        map[y][x] = c;

    }
}













