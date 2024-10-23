package com.pcwk.ehr.member;

import java.util.List;
import java.util.Scanner;

import com.pcwk.ehr.cmn.PLog;

public class Main implements PLog {

	MemberController controller;
	
	public Main() {
		controller = new MemberController();

		//메뉴선택
		doActionMenu();
	}
	
	//메뉴 선택 
	public void doActionMenu() {
		Scanner scanner=new Scanner(System.in);
		
		while(true) {
			//main 메뉴
			System.out.println(menu());			
			System.out.print("Menu를 선택 하세요.>");
			String menu = scanner.nextLine().trim();
			
			switch (menu) {
			case "1":{//회원 목록 조회
				List<MemberVO> list = controller.doRetrieve();
				for(MemberVO vo :list) {
					System.out.println(vo);
				}
			}
			break;
			
			case "2":{//회원 단건 조회
				MemberVO outVO = controller.doSelectOne();
				
				if(null == outVO) {
					System.out.println("회원 단건 조회 실패!");
				}else {
					System.out.printf("조회 회원: %s%n",outVO);
				}
			}
			break;
			case "3":{ //회원 가입
				int flag = controller.doSave();
				if(1 == flag) {
					System.out.println("회원 가입 되었습니다.");
				}else {
					System.out.println("회원 가입 실패!");
				}
			}
			break;
			
			case "4":{//회원 수정
				int flag = controller.doUpdate();
				if(2 == flag) {
					System.out.println("회원정보가 수정 되었습니다.");
				}else {
					System.out.println("회원 수정 실패!");
				}				
			}
			break;	
			
			
			case "5":{//회원 삭제
				int flag = controller.doDelete();
				if(1 == flag) {
					System.out.println("회원이 삭제 되었습니다.");
				}else {
					System.out.println("회원 삭제 실패!");
				}				
				
			}
			break;
			
			case "6": {
				//Todo : 저장기능 연결할것.
				System.out.println("┌─────────────────────────┐");
				System.out.println("│Programe exit.           │");
				System.out.println("└─────────────────────────┘");
				
				int count = controller.writeFile();
				System.out.println("│회원수: "+count);
				System.exit(0);
				
			}
			break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + menu);
			}
		}
	}
	
	//메뉴
	public String menu() {
		StringBuilder sb=new StringBuilder(2000);
		sb.append(" +-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+-+ \n");
		sb.append(" |M|i|n|i| |J|a|v|a| |P|r|o|j|e|c|t| \n");
		sb.append(" +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ \n");
		sb.append(" |2|0|2|4|.|1|0|.|2|3|               \n");
		sb.append(" +-+-+-+-+-+-+-+-+-+-+               \n");
		sb.append("  *** 회원 관리 프로그램 ***               \n");
		sb.append("  1. 회원 목록 조회:                     \n");
		sb.append("  2. 회원 단건 조회:                     \n");
		sb.append("  3. 회원 단건 저장:                     \n"); 
		sb.append("  4. 회원 수정:                        \n");
		sb.append("  5. 회원 삭제:                        \n");  
		sb.append("  6. 종료 :                           \n");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Main memberMain=new Main();
		

	}

}
