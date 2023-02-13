package goodee.gdj58.fileupload.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import goodee.gdj58.fileupload.service.NoticeService;
import goodee.gdj58.fileupload.vo.NoticeForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j // log객체변수 주입
@Controller
public class NoticeController {
	@Autowired private NoticeService noticeService;
	
	@GetMapping("/addNotice")
	public String addNotice() {
		return "addNotice";
	}
	
	@PostMapping("/addNotice")
	public String addNotice(HttpServletRequest request, NoticeForm noticeForm) {
		String path = request.getServletContext().getRealPath("/upload/");
		log.debug("▶▶▶▶▶▶ NoticeController.addNotice path : "+ path);
		
		log.debug("▶▶▶▶▶▶ NoticeController.addNotice noticeForm : "+ noticeForm);
		List<MultipartFile> noticefileList = noticeForm.getNoticefileList();
		if(noticefileList.get(0).getSize() > 0) { // 하나 이상의 파일이 업로드 되면
			for(MultipartFile mf : noticefileList) {
				log.debug("▶▶▶▶▶▶ NoticeController.addNotice filename : "+mf.getOriginalFilename());
			}
		}
		
		noticeService.addNotice(noticeForm, path);
		return "redirect:/";
	}
}