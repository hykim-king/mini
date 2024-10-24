package com.pcwk.ehr.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.WorkDiv;

public class MemberDao implements WorkDiv<MemberVO>, PLog {

	// TODO: 파일 경로로 변경
	public static final String fileName = "member.csv";
	public static List<MemberVO> members = new ArrayList<MemberVO>();

	public MemberDao() {
		super();

		int count = readFile(fileName);
		// ------------------------------------

		// ------------------------------------
	}

	public void displayList(List<MemberVO> list) {

		if (list.size() > 0) {
			String message = "\r\n" + " /$$      /$$                         /$$            \r\n"
					+ "| $$$    /$$$                        | $$                            \r\n"
					+ "| $$$$  /$$$$  /$$$$$$  /$$$$$$/$$$$ | $$$$$$$   /$$$$$$   /$$$$$$   \r\n"
					+ "| $$ $$/$$ $$ /$$__  $$| $$_  $$_  $$| $$__  $$ /$$__  $$ /$$__  $$  \r\n"
					+ "| $$  $$$| $$| $$$$$$$$| $$ \\ $$ \\ $$| $$  \\ $$| $$$$$$$$| $$  \\__/\r\n"
					+ "| $$\\  $ | $$| $$_____/| $$ | $$ | $$| $$  | $$| $$_____/| $$       \r\n"
					+ "| $$ \\/  | $$|  $$$$$$$| $$ | $$ | $$| $$$$$$$/|  $$$$$$$| $$       \r\n"
					+ "|__/     |__/ \\_______/|__/ |__/ |__/|_______/  \\_______/|__/      \r\n"
					+ "                                                                     \r\n" + "";

			System.out.println(message);

			System.out.print(String.format("%-10s %-10s %-10s %-20s %-7s %-9s %-15s %-10s%n", "회원ID"
	                ,"이름"
	                ,"비밀번호"
	                ,"이메일"
	                ,"구룹ID"
	                ,"로그인횟수"
	                ,"가입일"
	                ,"그룹명"
	                ));
			System.out.println("---------------------------------------------------------------------------------------");
			for(MemberVO vo  :list) {
				System.out.print(vo);
			}
		} else {
			System.out.println("회원정보가 없습니다.");
		}
	}

	/**
	 * 1(성공)/0(실패)/2(memberId 중복)
	 */

	// boolean isExistsMember
	private boolean isExistsMember(MemberVO member) {
		boolean flag = false;

		for (MemberVO vo : members) {
			// param, vo의 memberId비교
			if (vo.getMemberId().equals(member.getMemberId())) {
				flag = true;
				return flag;
			}
		}

		return flag;
	}

	@Override
	public int doSave(MemberVO param) {
		// 1. 입력전에 memberId check필요.
		// 2. param입력된 데이터를 members 추가.

		int flag = 0;

		if (isExistsMember(param) == true) {
			flag = 2;
			return flag;
		}

		boolean check = this.members.add(param);
		flag = check == true ? 1 : 0;

		return flag;
	}

	/**
	 * 2(성공)/이외는 실패(실패)
	 */
	@Override
	public int doUpdate(MemberVO param) {
		// Delete, Insert
		int flag = doDelete(param);
		
		if(flag==1) {
			flag+=doSave(param);
		}
		
		return flag;
	}

	@Override
	public int doDelete(MemberVO param) {
		// 회원목록에서 동일한 회원을 찾고 삭제
		int flag = 0;
		flag = members.remove(param) == true ? 1 : 0;
		return flag;
	}

	@Override
	public MemberVO doSelectOne(MemberVO param) {
		// members에 회원ID에 해당되는 회원정보 전체를 return
		MemberVO outVO = null;

		for (MemberVO vo : members) {
			if (vo.getMemberId().equals(param.getMemberId())) {
				outVO = vo;
				break;
			}
		}

		return outVO;
	}

	@Override 
	public List<MemberVO> doRetrieve(DTO param,int pageNum, int pageSize) {
		
		DTO dto = param;
		List<MemberVO> list =new ArrayList<MemberVO>();
		
		if(dto.getSearchDiv().equals("전체")||dto.getSearchDiv().equals("ALL")) {
			return members;
		}else if(dto.getSearchDiv().equals("10")) {
			for(MemberVO vo  :members) {
				if(vo.getMemberId().contains(dto.getSearchWord())) {
					list.add(vo);
				}
			}
		}
		
		
		return list;
	}

	@Override
	public int writeFile(String path) {
		
		int count = 0; //저장 건수
		try(BufferedWriter bw=new BufferedWriter(new FileWriter(fileName))){
			//List<MemberVO> -> MemberVO
			String filedSeparator = ",";
			//pcwk01,이상무01,4321,jamesol@paran.com,1,0,2024/10/17 14:33:00,일반
			
			for(MemberVO vo :members) {
				++count;
				bw.write(vo.voToString());
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		return count;
	}

	public MemberVO stringToMember(String data) {
		MemberVO out = null;

		String memberStr = data;
		String[] memberArr = memberStr.split(",");

		String memberId = memberArr[0];// 회원ID<PK>
		String memberName = memberArr[1];// 이름
		String password = memberArr[2];// 비번
		String email = memberArr[3];// 이메일
		int teamId = Integer.parseInt(memberArr[4]);// 팀ID
		int loginCount = Integer.parseInt(memberArr[5]);// 로그인수
		String regDt = memberArr[6];// 가입일
		String roleName = memberArr[7];// 권한명

		out = new MemberVO(memberId, memberName, password, email, teamId, loginCount, regDt, roleName);

		return out;
	}

	@Override
	public int readFile(String path) {

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String data = "";
			while ((data = br.readLine()) != null) {
				MemberVO outVO = stringToMember(data);
				members.add(outVO);
			}

		} catch (IOException e) {
			System.out.println("IOException:" + e.getMessage());
		}

		// 회원정보 전체 조회:
		displayList(members);
		return members.size();
	}

}
