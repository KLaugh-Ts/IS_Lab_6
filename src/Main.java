import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // Функция генерации матрицы
    static String [][] generateMatrix (int n, int m, String [] S) {
        String [][] Matrix = new String[n][m];
        for (int i=1; i<n; i++) {
            for (int j=0; j<m; j++) {
                Matrix [0][j] = "Полные права";
                int k = 0 + (int) (Math.random() * 4); // генерация количества прав
                switch (k) {
                    case 0: Matrix[i][j] = "Нет доступа"; break;
                    case 1: {
                        int rand = 2; // исключение генерации передачи прав, когда передавать нечего
                        while (rand == 2) {
                            rand = 0 + (int) (Math.random() * 3); // генерация права
                            Matrix[i][j] = S[rand];
                        }
                        break;
                    }
                    case 2: {
                        int rand_1 = 0 + (int) (Math.random() * 3); // генерация 1-ого права
                        Matrix[i][j] = S[rand_1];
                        Matrix[i][j] += ", ";

                        int rand_2 = rand_1; // исключение генерации одинаковых прав
                        while (rand_2 == rand_1) {
                            rand_2 = 0 + (int) (Math.random() * 3); // генерация 2-ого права
                            if (rand_2 != rand_1) Matrix[i][j] += S[rand_2];
                        }
                        break;
                    }
                    case 3: Matrix[i][j] = "Полные права"; break;
                }
            }
        }
        return Matrix;
    }

    // Функция полной печати таблицы
    static void printAll (String [][] arr, int n, int m, String [] O, String [] U) {
        System.out.printf("%-10s", " ");
        for (int j=0; j<m; j++)
            System.out.printf("| %-35s ", O[j]);
        System.out.println("");

        for (int i=0; i<n; i++) {
            System.out.printf("%-10s", U[i]);
            for (int j=0; j<m; j++) {
                System.out.printf("| %-35s ", arr[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    // Функция действия
    static void action (String [][] Matrix, String [] S, String [] U, String login, int index) {
        int action = 0;
        while (action != 4) {
            Scanner in = new Scanner(System.in);
            System.out.print("Выберете действие: Чтение - 1, Запись - 2, Передача прав - 3, Выход - 4 ");
            action = Integer.parseInt(in.nextLine());
            if (action == 4) System.out.println("Пока, " + login + "!");
            else {
                System.out.print("Над каким объектом производится действие? ");
                int file = Integer.parseInt(in.nextLine());
                action--;
                file--;
                String action0 = Matrix[index][file];
                if ((action0.indexOf(S[action]) != -1 || action0 == "Полные права") && action == 2) {
                    System.out.print("Какое право хотите передать: Чтение - 1, Запись - 2 ");
                    action = Integer.parseInt(in.nextLine());
                    action--;
                    if (action0.indexOf(S[action]) == -1 && action0 != "Полные права") System.out.println("У вас нет на это прав");
                    else {
                        System.out.print("Какому пользователю передаётся право? ");
                        String user = in.nextLine();
                        int indexOfUser = Arrays.asList(U).indexOf(user);
                        if (indexOfUser != -1) {
                            System.out.println("Успешно");
                            String actionUser = Matrix[indexOfUser][file];
                            if (Matrix[indexOfUser][file] != "Полные права") {
                                if (Matrix[indexOfUser][file] == "Нет доступа") {
                                    Matrix[indexOfUser][file] = S[action];
                                }
                                else {
                                    if (actionUser.indexOf(S[action]) != -1)/*Matrix[indexOfUser][file] != S[action]*/ {
                                        Matrix[indexOfUser][file] += ", ";
                                        Matrix[indexOfUser][file] += S[action];
                                    }
                                }
                            }
                        }
                        else System.out.println("Пользователь не найден");
                    }
                }
                else if (action0.indexOf(S[action]) != -1) System.out.println("Успешно");
                else {
                    switch (action0) {
                        case "Полные права":
                            System.out.println("Успешно");
                            break;
                        default:
                            System.out.println("Отказано в доступе");
                            break;
                    }
                }
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {

        int n = 3, m = 5;
        String [] S = {"Доступ на чтение", "Доступ на запись", "Передача прав"};
        String [] U = {"Admin", "User", "Guest"};
        String [] O = {"file_1", "file_2", "file_3", "file_4", "file_5"};

        String [][] Matrix = generateMatrix(n, m, S); // генерация матрицы
        printAll(Matrix, n, m, O, U); // полная печать таблицы

        Scanner in = new Scanner(System.in);
        String login = " ";
        while (login != "") {
            System.out.print("Введите имя пользователя: ");
            login = in.nextLine();

            int index = Arrays.asList(U).indexOf(login);
            if (index != -1) {
                System.out.println("Привет, " + login + "!");
                System.out.println("");
                System.out.println("Перечень ваших прав: ");
                for (int j = 0; j < m; j++) {
                    System.out.print(O[j] + '\t');
                    System.out.println(Matrix[index][j]);
                }
                System.out.println("");

                action(Matrix, S, U, login, index);

            } else if (login == "") {
                System.out.println("Завершение работы");
            }
            else {
                System.out.println("Пользователь с таким именем не найден");
            }
        }

    }
}