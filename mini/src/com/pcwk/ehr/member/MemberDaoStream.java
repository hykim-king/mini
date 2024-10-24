package com.pcwk.ehr.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.WorkDiv;

public class MemberDaoStream implements WorkDiv<MemberVO>, PLog {

	// TODO: 파일 경로로 변경
	public static final String fileName = "member.csv";
	public static List<MemberVO> members = new ArrayList<MemberVO>();

	public MemberDaoStream() {
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

			System.out.print(String.format("%-10s %-10s %-10s %-20s %-7s %-9s %-15s %-10s%n", "회원ID", "이름", "비밀번호",
					"이메일", "구룹ID", "로그인횟수", "가입일", "그룹명"));
			System.out
					.println("---------------------------------------------------------------------------------------");

			for (MemberVO vo : list) {
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

		if (flag == 1) {
			flag += doSave(param);
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


		outVO = members.stream()
				.filter(
						(vo) -> vo.getMemberId().equals(param.getMemberId())
						)
				.findFirst()
				.orElse(null);
		return outVO;
	}


	public List<MemberVO> doRetrieveStream(DTO param,int pageNum,int pageSize) {

		DTO dto = param;

		String searchWord = dto.getSearchWord();

		if(param.getSearchDiv().equals("전체")||param.getSearchDiv().equals("ALL")) {
			return members.stream()
					.sorted(Comparator.comparing(MemberVO::getRegDt).reversed())//날짜 역순으로 비교
					.skip((pageNum - 1) * pageSize) //// 페이징: (pageNum - 1) * pageSize 만큼 건너뜀
					.limit(pageSize) //페이지 크기만큼 제한
					.collect(Collectors.toList());   // 리스트로 변환하여 반환
		}
		
		return members.stream()
				.sorted(Comparator.comparing(MemberVO::getRegDt).reversed())//날짜 역순으로 비교
				.filter(vo -> vo.getMemberId().contains(searchWord)
						      || vo.getMemberName().contains(searchWord)
						      || vo.getEmail().contains(searchWord)
						)
				.skip((pageNum - 1) * pageSize) //// 페이징: (pageNum - 1) * pageSize 만큼 건너뜀
				.limit(pageSize) //페이지 크기만큼 제한
				.collect(Collectors.toList());   // 리스트로 변환하여 반환
		

	}

	@Override
	public int writeFile(String path) {

		int count = 0; // 저장 건수
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
			// List<MemberVO> -> MemberVO
			String filedSeparator = ",";
			// pcwk01,이상무01,4321,jamesol@paran.com,1,0,2024/10/17 14:33:00,일반

			for (MemberVO vo : members) {
				++count;
				bw.write(vo.voToString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return count;
	}

	public static MemberVO stringToMember(String data) {
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

	public List<MemberVO> readFileNio() {

		try (BufferedReader br = Files.newBufferedReader(Paths.get(this.fileName))) {

			// 라인을 스트림으로 처리하여 MemberVO 객체로 변환
			members = br.lines().map(MemberDaoStream::stringToMember).collect(Collectors.toList());

		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
			return null; // 예외 발생 시 null 반환
		}

		// 회원정보 전체 조회:
		displayList(members);
		return members;
	}

	@Override
	public int readFile(String path) {
		readFileNio();
		return members.size();
	}

	@Override
	public List<MemberVO> doRetrieve(DTO param, int pageNum, int pageSize) {
		return doRetrieveStream(param, pageNum,pageSize);
	}



}
