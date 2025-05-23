package com.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    List<WiseSaying> wiseSayings = new ArrayList<>();
    int lastId = 0;

    void run() {

        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("종료")) {
                break;

            } else if (cmd.equals("등록")) {
                actionWrite();

            } else if (cmd.equals("목록")) {
                actionList();

            } else if (cmd.startsWith("삭제?id=")) {
                actionRemove(cmd);

            } else if (cmd.startsWith("수정?id=")) {
                actionModify(cmd);

            }
        }

        scanner.close();
    }

    void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();

        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        int id = ++lastId;

        WiseSaying ws = new WiseSaying(id, content, author);
        wiseSayings.add(ws);
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for (int i = wiseSayings.size() - 1; i >= 0; i--) {
            WiseSaying ws = wiseSayings.get(i);
            System.out.println(ws.id + " / " + ws.author + " / " + ws.content);
        }
    }

    void actionRemove(String cmd) {
        int idToDelete = Integer.parseInt(cmd.substring("삭제?id=".length())); // 명령어에서 삭제할 id 추출
        boolean delete = false;

        for (int i=0; i<wiseSayings.size(); i++) {
            if (wiseSayings.get(i).id == idToDelete) {
                wiseSayings.remove(i);
                System.out.println(idToDelete + "번 명언이 삭제되었습니다.");
                delete = true;
                break;
            }
        }

        if (!delete) { // 이미 삭제함
            System.out.println(idToDelete + "번 명언은 존재하지 않습니다.");
        }
    }

    void actionModify(String cmd) {
        int idToModify = Integer.parseInt(cmd.substring("수정?id=".length())); // 명령어에서 수정할 id 추출
        WiseSaying target = null; // 수정할 명언 객체를 담을 용도

        for (WiseSaying ws : wiseSayings) { // 번호가 같으면 해당 명언 객체 저장
            if (ws.id == idToModify) {
                target = ws;
                break;
            }
        }

        if (target == null) { // 객체가 없는 경우
            System.out.println(idToModify + "번 명언은 존재하지 않습니다.");
        } else {
            System.out.println("명언(기존) : " + target.content);
            System.out.print("명언 : ");
            String newContent = scanner.nextLine().trim();

            System.out.println("작가(기존) : " + target.author);
            System.out.print("작가 : ");
            String newAuthor = scanner.nextLine().trim();

            target.content = newContent;
            target.author = newAuthor;
        }
    }
}