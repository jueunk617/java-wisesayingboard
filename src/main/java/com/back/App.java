package com.back;

import com.back.domain.wiseSaying.controller.WiseSayingController;

import java.util.Scanner;

public class App {
    void run() {
        Scanner scanner = new Scanner(System.in);
        WiseSayingController wiseSayingController = new WiseSayingController(scanner);

        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("종료")) break;

            Rq rq = new Rq(cmd);
            wiseSayingController.handleCommand(rq);
        }
    }
}