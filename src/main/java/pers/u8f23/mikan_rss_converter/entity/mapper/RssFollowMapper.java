package pers.u8f23.mikan_rss_converter.entity.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import pers.u8f23.mikan_rss_converter.entity.RssFollow;

import java.util.List;

/**
 * @author 8f23
 * @create 2023/4/14-18:06
 */
@Mapper
@Repository
public interface RssFollowMapper
{
	List<RssFollow> getFollowList();

	List<RssFollow> getFollowById(long id);

	void addFollow(String rssSite, String regexFilter, String comment);

	void deleteFollowById(long id);

	void modifyFollowById(long id, String rssSite, String regexFilter,
		String comment, Integer enabled);

	void notifyUpdate(long id, boolean success);
}
