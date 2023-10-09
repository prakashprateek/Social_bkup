package com.berriesoft.social.post;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.berriesoft.social.status.ErrorInfo;
import com.berriesoft.social.status.ErrorInfoList;
import com.berriesoft.social.status.IllegalAccessExeption;
import com.berriesoft.social.status.InternalErrorExeption;
import com.berriesoft.social.status.PostNotCreatedExeption;
import com.berriesoft.social.status.PostNotFoundExeption;
import com.berriesoft.social.status.SocialAppRequestStatus;

import jakarta.validation.Valid;

@Controller
public class PostController
{
	PostDAOServiceJPA postDAOServiceJPA;

	Logger logger = LoggerFactory.getLogger(getClass());

	public PostController(PostDAOServiceJPA postDAOServiceJPA)
	{
		super();
		this.postDAOServiceJPA = postDAOServiceJPA;

	}

	@GetMapping(path = "posts/v1")
	@ResponseBody
	public List<Post> getAllPosts()
	{
		logger.debug("in getAllPosts");
		return postDAOServiceJPA.getAllPosts();
	}

	@GetMapping(path = "posts/v1/{id}")
	@ResponseBody
	public ResponseEntity<Object> getPostById(@PathVariable int id)
	{
		logger.debug("in gePostById for id: " + id);
		SocialAppRequestStatus status = postDAOServiceJPA.getPostByID(id);

		if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
		{
			throw new PostNotFoundExeption((ErrorInfoList) status.getPayload());
		} else
		{
			return ResponseEntity.status(SocialAppRequestStatus.SUCCESSCODE).body((Post) status.getPayload());
		}
	}

	@GetMapping(path = "users/v1/{userid}/posts/{postid}")
	@ResponseBody
	public ResponseEntity<Object> getPostForUserByPostId(@PathVariable int userid, @PathVariable int postid)
	{
		logger.debug("in getPostForUserByPostId for id: " + userid);
		SocialAppRequestStatus status = postDAOServiceJPA.getPostByID(postid);

		if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
		{
			throw new PostNotFoundExeption((ErrorInfoList) status.getPayload());
		} else
		{

			Post post = (Post) status.getPayload();
			if (post.getAppUserId() == userid)
			{
				return ResponseEntity.status(SocialAppRequestStatus.SUCCESSCODE).body(post);
			} else
			{
				ErrorInfoList errorInfoList = new ErrorInfoList(List.of(new ErrorInfo(ErrorInfo.ILLEGAL_ACCESS_CODE,
						"USER ID not VALID for this post", ErrorInfo.ILLEGAL_ACCESS_MESSAGE)));
				throw new IllegalAccessExeption(errorInfoList);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "users/v1/{userid}/posts")
	@ResponseBody
	public ResponseEntity<Object> getPostByUserId(@PathVariable int userid)
	{
		logger.debug("in gePostByUserId for id: " + userid);
		SocialAppRequestStatus status = postDAOServiceJPA.getPostByUserID(userid);

		if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
		{
			throw new PostNotFoundExeption((ErrorInfoList) status.getPayload());
		} else
		{
			return ResponseEntity.status(SocialAppRequestStatus.SUCCESSCODE).body((List<Post>) status.getPayload());
		}
	}

	@PostMapping(path = "users/v1/{id}/posts")
	public ResponseEntity<Object> addUser(@PathVariable int id, @Valid @RequestBody Post post)
			throws PostNotCreatedExeption
	{
		logger.debug("in addPost");
		post.setAppUserId(id);

		SocialAppRequestStatus status = postDAOServiceJPA.addPost(post);

		if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
		{
			throw new PostNotCreatedExeption((ErrorInfoList) status.getPayload());
		} else
		{
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
					.toUri();
			return ResponseEntity.created(location).build();
		}
	}

	@DeleteMapping(path = "users/v1/{userid}/posts/{postid}")
	public ResponseEntity<Object> deletePostForUserByPostId(@PathVariable int userid, @PathVariable int postid)
	{
		logger.debug("in deletePostForUserByPostId for id: " + postid);
		SocialAppRequestStatus status = postDAOServiceJPA.getPostByID(postid);
		if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
		{
			throw new PostNotFoundExeption((ErrorInfoList) status.getPayload());
		} else
		{

			Post post = (Post) status.getPayload();
			if (post.getAppUserId() == userid)
			{
				
				status = postDAOServiceJPA.deletePostById(postid);

				if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
				{
					throw new InternalErrorExeption ((ErrorInfoList) status.getPayload());
				} else
				{
					return ResponseEntity.status(SocialAppRequestStatus.SUCCESSCODE).build();
				}
				//return ResponseEntity.status(SocialAppRequestStatus.SUCCESSCODE).body(post);
			} else
			{
				ErrorInfoList errorInfoList = new ErrorInfoList(List.of(new ErrorInfo(ErrorInfo.ILLEGAL_ACCESS_CODE,
						"USER ID not VALID for this post", ErrorInfo.ILLEGAL_ACCESS_MESSAGE)));
				throw new IllegalAccessExeption(errorInfoList);
			}
		}
	}
	
	@PutMapping(path = "users/v1/{userid}/posts/{postid}")
	public ResponseEntity<Object> updatePostForUserByPostId(@PathVariable int userid, @PathVariable int postid, @RequestBody @Valid Post post)
	{
		logger.debug("in updatePostForUserByPostId for id: " + postid);
		SocialAppRequestStatus status = postDAOServiceJPA.getPostByID(postid);
		if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
		{
			throw new PostNotFoundExeption((ErrorInfoList) status.getPayload());
		} else
		{
			if (post.getAppUserId() == userid)
			{
				
				status = postDAOServiceJPA.updatePost(post);

				if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
				{
					throw new InternalErrorExeption ((ErrorInfoList) status.getPayload());
				} else
				{
					URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
					return ResponseEntity.created(location).build();
				}
			} else
			{
				ErrorInfoList errorInfoList = new ErrorInfoList(List.of(new ErrorInfo(ErrorInfo.ILLEGAL_ACCESS_CODE,
						"USER ID not VALID for this post", ErrorInfo.ILLEGAL_ACCESS_MESSAGE)));
				throw new IllegalAccessExeption(errorInfoList);
			}
		}
	}

}
