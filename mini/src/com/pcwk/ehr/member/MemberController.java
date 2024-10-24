package com.pcwk.ehr.member;

import java.util.List;
import java.util.Scanner;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;

public class MemberController implements PLog {

	private MemberDaoStream dao;
	
	public MemberController() {
		dao = new MemberDaoStream();
	}
	
	public List<MemberVO> doRetrieve() {
		DTO param = new DTO();
		Scanner scanner=new Scanner(System.in);
		//검색 구분( 회원ID(10),이름(20),이메일(30))
		System.out.println("전체 : 전체,ALL");
		System.out.println("회원ID : 10,pcwk");
		System.out.println("이름 : 20,이상무");
		System.out.println("이메일 : 30,jamesol@paran.com");
		
		System.out.print("검색할 정보를 입력 하세요>");
		String inputData  = scanner.nextLine().trim();
		log.debug("1. inputData:{}",inputData);
		String searchDiv  = inputData.split(",")[0];
		String searchWord = inputData.split(",")[1];
		
		param.setSearchDiv(searchDiv);
		param.setSearchWord(searchWord);
		log.debug("2. param:{}",param);
		
		System.out.print("페이지 번호를 입력 하세요.>");
		int pageNum = scanner.nextInt();
		int pageSize = 10;
		return dao.doRetrieve(param,pageNum,pageSize);
		
	}
	
	
	/**
	 * List<MemberVO>를 member.csv기록
	 * @return
	 */
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
