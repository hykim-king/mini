package com.pcwk.ehr.member;

public class MemberDaoMain {

	MemberVO member01=null;
	MemberVO member02=null;
	MemberVO member03=null;
	MemberDao dao= null;
	public MemberDaoMain() {
		dao = new MemberDao();
		member01 = new MemberVO("pcwk01","이상무01","4321","jamesol@paran.com",1,0,"2024/10/17 14:33:00","일반");
		member02 = new MemberVO("pcwk11","이상무11","4321","jamesol11@paran.com",1,0,"2024/10/17 14:33:00","일반");
		
	}
	
	public void doSave() {
		System.out.println("회원등록");
		int flag = dao.doSave(member02);
		
		if(2==flag) {
			System.out.println(member01.getMemberId()+"중복 되었습니다.");
		}else if(0 == flag) {
			System.out.println(member01.getMemberId()+"등록 실패");
		}else {
			System.out.println("**********************************************");
			System.out.println(member01.getMemberId()+"등록 성공");
			System.out.println("**********************************************");			
		}
		
		
		dao.displayList(MemberDao.members);
	}
	
	public void doDelete() {
		System.out.println("회원삭제");
		int flag = dao.doDelete(member01);
		
		if(1==flag) {
			System.out.println("**********************************************");
			System.out.println(member01.getMemberId()+"삭제 성공");
			System.out.println("**********************************************");						
		}else {
			
			System.out.println(member01.getMemberId()+"삭제 실패");
		}
		
		//회원목록 확인
		dao.displayList(MemberDao.members);
	}
	
	public void doSelectOne() {
		System.out.println("회원조회");
		MemberVO outVO = dao.doSelectOne(member02);
		
		if(null == outVO) {
			System.out.println(member01.getMemberId()+" 회원이 존재하지 않습니다.");
		}else {
			System.out.println("**********************************************");
			System.out.println(outVO+" 조회 성공");
			System.out.println("**********************************************");									
		}
	}
	
	public static void main(String[] args) {
		MemberDaoMain main=new MemberDaoMain();
		//단건조회
		main.doSelectOne();
		
		//등록
		//main.doSave();
		
		//삭제
		//main.doDelete();
		
		

	}

}
