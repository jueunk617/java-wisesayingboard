package com.back.domain.wiseSaying.controller;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(Scanner scanner) {
        this.scanner = scanner;
        this.wiseSayingService = new WiseSayingService();
    }

    public void handleCommand(String cmd) {
        if (cmd.equals("등록")) actionWrite();
        else if (cmd.equals("목록")) actionList();
        else if (cmd.startsWith("삭제?id=")) actionDelete(cmd);
        else if (cmd.startsWith("수정?id=")) actionModify(cmd);
        else if (cmd.equals("빌드")) actionBuild();
        else if (cmd.equals("초기화")) actionReset(); // 테스트용
        else System.out.println("알 수 없는 명령어입니다.");
    }

    private void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        int id = wiseSayingService.write(content, author);
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    private void actionList() {
        List<WiseSaying> list = wiseSayingService.findAllReverse();

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for (WiseSaying ws : list) {
            System.out.println(ws.getId() + " / " + ws.getAuthor() + " / " + ws.getContent());
        }
    }

    private void actionDelete(String cmd) {
        try {
            int id = Integer.parseInt(cmd.substring("삭제?id=".length()));
            boolean result = wiseSayingService.delete(id);

            if (result) {
                System.out.println(id + "번 명언이 삭제되었습니다.");
            } else {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID 형식이 잘못되었습니다.");
        }
    }

    private void actionModify(String cmd) {
        try {
            int id = Integer.parseInt(cmd.substring("수정?id=".length()));
            WiseSaying ws = wiseSayingService.findById(id);

            if (ws == null) {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
                return;
            }

            System.out.println("명언(기존) : " + ws.getContent());
            System.out.print("명언 : ");
            String newContent = scanner.nextLine().trim();

            System.out.println("작가(기존) : " + ws.getAuthor());
            System.out.print("작가 : ");
            String newAuthor = scanner.nextLine().trim();

            wiseSayingService.modify(ws, newContent, newAuthor);
        } catch (NumberFormatException e) {
            System.out.println("ID 형식이 잘못되었습니다.");
        }
    }

    private void actionBuild() {
        wiseSayingService.buildJson();
    }

    // 테스트용 데이터 초기화 기능
    private void actionReset() {
        wiseSayingService.reset();
        System.out.println("데이터가 초기화되었습니다.");
    }
}
