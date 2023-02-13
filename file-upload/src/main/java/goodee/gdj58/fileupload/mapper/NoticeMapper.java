package goodee.gdj58.fileupload.mapper;

import org.apache.ibatis.annotations.Mapper;

import goodee.gdj58.fileupload.vo.Notice;

@Mapper
public interface NoticeMapper {
	// 공지사항 입력
	int insertNotice(Notice notice);
}