package goodee.gdj58.fileupload.mapper;

import org.apache.ibatis.annotations.Mapper;

import goodee.gdj58.fileupload.vo.Noticefile;

@Mapper
public interface NoticefileMapper {
	int insertNoticefile(Noticefile noticefile);
}