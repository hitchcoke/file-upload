package goodee.gdj58.fileupload.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import goodee.gdj58.fileupload.mapper.NoticeMapper;
import goodee.gdj58.fileupload.mapper.NoticefileMapper;
import goodee.gdj58.fileupload.vo.Notice;
import goodee.gdj58.fileupload.vo.NoticeForm;
import goodee.gdj58.fileupload.vo.Noticefile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class NoticeService {

	@Autowired private NoticeMapper noticeMapper;
	@Autowired private NoticefileMapper noticefileMapper;
	public void removeNotice(int noticeId, String path) {
		// 1) 저장 장치의 파일을 삭제 -> 파일이름
		List<String> noticefileList = noticefileMapper.selectNoticefileNameList(noticeId);
		for(String filename : noticefileList) {
			File f = new File(path+filename);
			if(f.exists()) {
				f.delete();
			}
		}
		// 2) db행 삭제
		noticefileMapper.deleteNoticefileList(noticeId); 
		noticeMapper.deleteNotice(noticeId);
	}
	
	
	public void addNotice(NoticeForm noticeForm, String path) {
		log.debug("▶▶▶ NoticeService.addNotice.param path : ",path);
		log.debug("▶▶▶ NoticeService.addNotice.param noticeForm", noticeForm);
		
		// NoticeMapper
		Notice notice = new Notice();
		notice.setNoticeTitle(noticeForm.getNoticeTitle());
		notice.setNoticeContent(noticeForm.getNoticeContent());
		// notice.getNoticeId() --> 0
		int row = noticeMapper.insertNotice(notice);
		// insert시 입력된 autoincrement값이 출력됨
		log.debug("▶▶▶ NoticeService.addNotice noticeId : ", notice.getNoticeId()); 
		
		// nooticefileList가 하나이상이고 row==1 일때
		// NoticefileMapper
		if(noticeForm.getNoticefileList() != null && noticeForm.getNoticefileList().get(0).getSize() > 0 && row == 1) {
			log.debug("▶▶▶ NoticeService.addNotice : ","첨부된 파일이 있습니다.");
			for(MultipartFile mf : noticeForm.getNoticefileList()) {
				// mf -> Noticefile
				Noticefile noticefile = new Noticefile();
				
				String originName = mf.getOriginalFilename();
				// originName에서 마지막 .문자열 위치
				String ext = originName.substring(originName.lastIndexOf("."));
				
				// 파일을 저장할대 사용할 중복되지않는 새로운 이름 필요(UUID API사용)
				String filename = UUID.randomUUID().toString();
				// filename = filename.replace("-","");
	
				filename = filename + ext;
				
				noticefile.setNoticeId(notice.getNoticeId());
				noticefile.setNoticefileName(filename);
				noticefile.setNoticefileType(mf.getContentType());
				noticefile.setNoticefileSize(mf.getSize());
				log.debug("▶▶▶ NoticeService.addNotice noticefile : "+noticefile);
				noticefileMapper.insertNoticefile(noticefile);
				
				try {
					mf.transferTo(new File(path+filename));
				} catch (Exception e) {
					e.printStackTrace();
					// 새로운 예외 발생시켜야지만 @Transactional 작동을 위해
					throw new RuntimeException(); // RuntimeException은 예외처리를 하지 않아도 컴파일된다
				}
			}
		}
	}
}	