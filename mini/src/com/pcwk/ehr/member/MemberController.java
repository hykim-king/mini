package com.pcwk.ehr.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;

public class MemberController implements PLog {

	private MemberDao dao;
	
	public MemberController() {
		dao = new MemberDao();
	}
	
	public List<MemberVO> doRetrieve() {
		DTO param=new DTO();
		//1.Scanner
		//2.등록사용자 정보
		//3.dao.doSave(param);
		
		Scanner scanner=new Scanner(System.in);
		
		System.out.printf("회원ID로 검색: 10,pcwk\n");
		System.out.printf("회원이름 검색: 20,이상무\n");
		System.out.printf("검색할 회원 정보를 입력 하세요.>");
		
		
		String inputData = scanner.nextLine().trim();
		String[] strArr = inputData.split(",");
		param.setSearchDiv(strArr[0]);
		param.setSearchWord(strArr[1]);
		
		return dao.doRetrieve(param);
	}
	
	public int writeFile() {
		return dao.writeFile(MemberDao.fileName);
	}
	
	
	public int doUpdate() {
		int flag = 0;
		MemberVO param=new MemberVO();
		
		//1.Scanner
		//2.등록사용자 정보
		//3.dao.doSave(param);
		
		Scanner scanner=new Scanner(System.in);
		System.out.printf("수정할 회원 정보를 입력 하세요.>"
				+ "\n(pcwk01,이상무01,4321,jamesol@paran.com,1,0,2024/10/17 14:33:00,일반)");
		
		String inputData = scanner.nextLine().trim();
		log.debug("1. inputData:{}",inputData);
		//회원정보를 받아, MemberVO변환
		param = dao.stringToMember(inputData);
		log.debug("2. param:{}",param);
		
		//dao호출
		flag = dao.doUpdate(param);
		log.debug("3. flag:{}",flag);
		return flag;
	}
	/**
	 * 회원삭제
	 * @return 1(성공)/0(실패)
	 */
	public int doDelete() {
		int flag = 0;
		MemberVO param =new MemberVO();//조회 파라메터:memberId
		//1.Scanner
		//2.조회 사용자ID 입력
		//3.dao.doDelete(param);	
		
		Scanner scanner=new Scanner(System.in);
		System.out.print("조회할 회원Id를 입력 하세요.>(pcwk01)");
		String inputData = scanner.nextLine().trim();
		log.debug("1. inputData:{}",inputData);
		param.setMemberId(inputData);
		log.debug("2. param:{}",param);
		
		flag = dao.doDelete(param);
		log.debug("3. flag:{}",flag
				);
		return flag;
	}
	
	/**
	 * 회원 단건 조회
	 * @return MemberVO
	 */
	public MemberVO doSelectOne() {
		MemberVO outVO = null;//조회 결과
		MemberVO param =new MemberVO();//조회 파라메터:memberId
		
		//1.Scanner
		//2.조회 사용자ID 입력
		//3.dao.doSave(param);		
		
		Scanner scanner=new Scanner(System.in);
		System.out.print("조회할 회원Id를 입력 하세요.>(pcwk01)");
		String inputData = scanner.nextLine().trim();
		log.debug("1. inputData:{}",inputData);
		
		param.setMemberId(inputData);
		log.debug("2. param:{}",param);
		
		outVO = dao.doSelectOne(param);
		log.debug("3. outVO:{}",outVO);
		
		
		return outVO;
	}
	/**
	 * 회원 가입
	 * @return 1(성공)/0(실패)
	 */
	public int doSave() {
		int flag = 0;
		MemberVO param=new MemberVO();
		
		//1.Scanner
		//2.등록사용자 정보
		//3.dao.doSave(param);
		
		Scanner scanner=new Scanner(System.in);
		System.out.printf("가입할 회원 정보를 입력 하세요.>"
				+ "\n(pcwk01,이상무01,4321,jamesol@paran.com,1,0,2024/10/17 14:33:00,일반)");
		
		String inputData = scanner.nextLine().trim();
		log.debug("1. inputData:{}",inputData);
		//회원정보를 받아, MemberVO변환
		param = dao.stringToMember(inputData);
		log.debug("2. param:{}",param);
		
		//dao호출
		flag = dao.doSave(param);
		log.debug("3. flag:{}",flag);
		return flag;
	}
	
	public int readFile(String path) {
		return dao.readFile(path);
	}
}
