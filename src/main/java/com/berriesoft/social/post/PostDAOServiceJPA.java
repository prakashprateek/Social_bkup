package com.berriesoft.social.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.berriesoft.social.status.ErrorInfo;
import com.berriesoft.social.status.ErrorInfoList;
import com.berriesoft.social.status.SocialAppRequestStatus;
import com.berriesoft.social.user.AppUser;

import jakarta.validation.Valid;

@Service
public class PostDAOServiceJPA
{
	private PostRepository postRepository;
	Logger logger = LoggerFactory.getLogger(getClass());

	public PostDAOServiceJPA(PostRepository postRepository)
	{
		super();
		this.postRepository = postRepository;
	}

	// Return all posts
	public List<Post> getAllPosts()
	{
		List<Post> listPost = postRepository.findAll();

		if (!listPost.isEmpty())
		{
			logger.debug("found " + listPost.size() + " posts");
		} else
		{
			logger.debug("No Posts found");
		}
		return listPost;
	}

	// Return Post by ID
	public SocialAppRequestStatus getPostByID(int id)
	{

		Optional<Post> resultPost = postRepository.findById(id);

		if (resultPost.isPresent())
		{
			logger.debug("found posts for id " + id);
			Post post = resultPost.stream().filter(u -> u.getId() == id).findFirst().get();
			return new SocialAppRequestStatus(SocialAppRequestStatus.SUCCESSCODE, SocialAppRequestStatus.SUCCESS, post);
		} else
		{
			logger.debug("No Posts found for id " + id);
			List<ErrorInfo> errorInfo = new ArrayList<ErrorInfo>();
			errorInfo.add(new ErrorInfo(ErrorInfo.POST_NOT_FOUND_ERRORCODE, "id:" + String.valueOf(id),
					ErrorInfo.POST_NOT_FOUND_MESSAGE));
			ErrorInfoList errorInfoList = new ErrorInfoList(errorInfo);
			return new SocialAppRequestStatus(SocialAppRequestStatus.ERRORCODE, SocialAppRequestStatus.ERROR,
					errorInfoList);
		}
	}

	// Return all posts for a user
	public SocialAppRequestStatus getPostByUserID(int appUserID)
	{

		List<Post> listPost = postRepository.findAllPostByAppUserID(appUserID);

		if (!listPost.isEmpty())
		{
			logger.debug("found " + listPost.size() + " posts for " + appUserID);
			return new SocialAppRequestStatus(SocialAppRequestStatus.SUCCESSCODE, SocialAppRequestStatus.SUCCESS,
					listPost);
		} else
		{
			logger.debug("No Posts found for user " + appUserID);
			List<ErrorInfo> errorInfo = new ArrayList<ErrorInfo>();
			errorInfo.add(new ErrorInfo(ErrorInfo.POST_NOT_FOUND_ERRORCODE, "id:" + String.valueOf(appUserID),
					ErrorInfo.POST_NOT_FOUND_MESSAGE));
			ErrorInfoList errorInfoList = new ErrorInfoList(errorInfo);
			return new SocialAppRequestStatus(SocialAppRequestStatus.ERRORCODE, SocialAppRequestStatus.ERROR,
					errorInfoList);
		}
	}

	// Add a post
	public SocialAppRequestStatus addPost(Post post)
	{
		Post savedPost = postRepository.save(post);
		logger.debug("saved post = " + savedPost);
		if (savedPost.equals(post))
		{
			logger.debug("added post" + post);
			return new SocialAppRequestStatus(SocialAppRequestStatus.SUCCESSCODE, SocialAppRequestStatus.SUCCESS,
					savedPost);
		} else
		{
			logger.debug("Error: couldnt add post " + post);
			List<ErrorInfo> errorInfo = new ArrayList<ErrorInfo>();
			errorInfo.add(new ErrorInfo(ErrorInfo.POST_NOT_CREATED_ERRORCODE,
					ErrorInfo.POST_NOT_CREATED_MESSAGE + ": {" + post.toString() + "}"));
			ErrorInfoList errorInfoList = new ErrorInfoList(errorInfo);
			return new SocialAppRequestStatus(SocialAppRequestStatus.ERRORCODE, SocialAppRequestStatus.ERROR,
					errorInfoList);
		}
	}

	// Delete a post
	public SocialAppRequestStatus deletePostById(int postid)
	{
		postRepository.deleteById(postid);
		logger.debug("deleted post id " + postid);
		return new SocialAppRequestStatus(SocialAppRequestStatus.SUCCESSCODE, SocialAppRequestStatus.SUCCESS, null);
	}

	// Update a post
	public SocialAppRequestStatus updatePost(@Valid Post post)
	{
		Post savedPost = postRepository.save(post);
		logger.debug("updated post" + post);

		return new SocialAppRequestStatus(SocialAppRequestStatus.SUCCESSCODE, SocialAppRequestStatus.SUCCESS,
				savedPost);
	}

}
