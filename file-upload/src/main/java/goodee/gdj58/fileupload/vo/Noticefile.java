package goodee.gdj58.fileupload.vo;

import lombok.Data;

@Data
public class Noticefile {
	private int noticefileId;
	private int noticeId;
	private String noticefileName;
	private String noticefileType;
	private long noticefileSize;
}