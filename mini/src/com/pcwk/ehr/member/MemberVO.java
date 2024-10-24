package com.pcwk.ehr.member;

import java.util.Comparator;
import java.util.Objects;

import com.pcwk.ehr.cmn.DTO;

public class MemberVO extends DTO implements Comparator<String> {
	
	private String    memberId    ;//회원ID<PK>
	private String    memberName  ;//이름
	private String    password    ;//비번
	private String    email       ;//이메일
	private int       teamId      ;//팀ID
	private int       loginCount  ;//로그인수
	private String    regDt       ;//가입일
	private String    roleName    ;//권한명
	
	
	public MemberVO() {
		super();
	}


	public MemberVO(String memberId, String memberName, String password, String email, int teamId, int loginCount,
			String regDt, String roleName) {
		super();
		this.memberId = memberId;
		this.memberName = memberName;
		this.password = password;
		this.email = email;
		this.teamId = teamId;
		this.loginCount = loginCount;
		this.regDt = regDt;
		this.roleName = roleName;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getTeamId() {
		return teamId;
	}


	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}


	public int getLoginCount() {
		return loginCount;
	}


	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}


	public String getRegDt() {
		return regDt;
	}


	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	
	
	@Override
	public int hashCode() {
		return Objects.hash(memberId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberVO other = (MemberVO) obj;
		return Objects.equals(memberId, other.memberId);
	}

	public String voToString() {
		return String.format("%s,%s,%s,%s,%d,%d,%s,%s%n", memberId
				                       ,memberName
				                       ,password
				                       ,email
				                       ,teamId
				                       ,loginCount
				                       ,regDt
				                       ,roleName
				                       );
	}
	
	
	@Override
	public String toString() {
		return String.format("%-10s %-10s %-10s %-20s %7d %9d %-20s %-10s%n", memberId
                ,memberName
                ,password
                ,email
                ,teamId
                ,loginCount
                ,regDt
                ,roleName
                );
	}


	@Override
	public int compare(String o1, String o2) {
		//문자열 오름차순 비교
		return o1.compareTo(o2);
	}
	
	
	
}
