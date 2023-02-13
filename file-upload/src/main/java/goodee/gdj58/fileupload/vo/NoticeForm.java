package goodee.gdj58.fileupload.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NoticeForm {
	private String noticeTitle;
	private String noticeContent;
	private List<MultipartFile> noticefileList;
}