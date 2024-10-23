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

			for (MemberVO vo : list) {
				System.out.println(vo);
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
				break;
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
	public List<MemberVO> doRetrieve(DTO param) {
		List<MemberVO> list=new ArrayList<MemberVO>();
		DTO dto = param;
		if(dto.getSearchDiv().equals("10")) {//회원Id검색
			//%pcwk01%
			for(MemberVO vo :members) {
				//id검색: 포함하고 있는
				if(vo.getMemberId().equals(dto.getSearchWord())) {
					list.add(vo);
					break;
				}
				//이름
				//이메일
				
			}			
		}else if(dto.getSearchDiv().equals("20")) {//회원이름
			//%pcwk01%
			for(MemberVO vo :members) {
				//id검색: 포함하고 있는
				if(vo.getMemberName().matches("*"+dto.getSearchWord()+"*")) {
					list.add(vo);
				}
				//이름
				//이메일
			}		
		}	

		return list;
	}

	@Override
	public int writeFile(String path) {
		int count = 0;
		try(BufferedWriter bw=new BufferedWriter(new FileWriter(path))){
			String separator = ",";
			
			for(MemberVO vo :members) {
				count++;
				StringBuilder sb=new StringBuilder(200);
				sb.append(vo.getMemberId());
				sb.append(separator);
				
				sb.append(vo.getMemberName());
				sb.append(separator);		
				
				sb.append(vo.getPassword());
				sb.append(separator);	

				sb.append(vo.getEmail());
				sb.append(separator);		
				
				sb.append(vo.getTeamId());
				sb.append(separator);	
				
				sb.append(vo.getLoginCount());
				sb.append(separator);
				
				sb.append(vo.getRegDt());
				sb.append(separator);		
				
				sb.append(vo.getRoleName());
				sb.append("\n");	
				
				bw.write(sb.toString());
				
			}
		} catch (IOException e) {
			System.out.println("IOException:" + e.getMessage());
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
