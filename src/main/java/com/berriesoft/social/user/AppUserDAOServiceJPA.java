package com.berriesoft.social.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.berriesoft.social.status.ErrorInfo;
import com.berriesoft.social.status.ErrorInfoList;
import com.berriesoft.social.status.SocialAppRequestStatus;

@Service
public class AppUserDAOServiceJPA
{
	private AppUserRepository appUserRepository;
	Logger logger = LoggerFactory.getLogger(getClass());

	public AppUserDAOServiceJPA(AppUserRepository appUserRepository)
	{
		super();
		this.appUserRepository = appUserRepository;
	}

	// Return all users
	public List<AppUser> getAllUsers()
	{
		return appUserRepository.findAll();
	}

	// Return one user by ID
	public SocialAppRequestStatus getUserById(int id)
	{

		Integer userID = id;
		Optional<AppUser> user = appUserRepository.findById(userID);

		if (user.isPresent())
		{
			logger.debug("found user" + user);
			AppUser appUser = user.stream().filter(u -> u.getId() == userID).findFirst().get();
			return new SocialAppRequestStatus(SocialAppRequestStatus.SUCCESSCODE, SocialAppRequestStatus.SUCCESS,
					appUser);
		} else
		{
			logger.debug("user not found");
			List<ErrorInfo> errorInfo = new ArrayList<ErrorInfo>();
			errorInfo.add(new ErrorInfo(ErrorInfo.USER_NOT_FOUND_ERRORCODE, "id:"+userID.toString(), ErrorInfo.USER_NOT_FOUND_MESSAGE));
			ErrorInfoList errorInfoList = new ErrorInfoList(errorInfo);
			return new SocialAppRequestStatus(SocialAppRequestStatus.ERRORCODE, SocialAppRequestStatus.ERROR,
					errorInfoList);
		}
	}

	// Add a user
	public SocialAppRequestStatus addUser(AppUser user)
	{
		AppUser savedUser = appUserRepository.save(user);
		if (savedUser.equals(user))
		{
			logger.debug("added user" + user);
			return new SocialAppRequestStatus(SocialAppRequestStatus.SUCCESSCODE, SocialAppRequestStatus.SUCCESS,
					savedUser);
		}
		else
		{
			logger.debug("Error: couldnt add user " + user);
			List<ErrorInfo> errorInfo = new ArrayList<ErrorInfo>();
			errorInfo.add(new ErrorInfo(ErrorInfo.USER_NOT_CREATED_ERRORCODE, ErrorInfo.USER_NOT_CREATED_MESSAGE + ": {" + user.toString() + "}"));
			ErrorInfoList errorInfoList = new ErrorInfoList(errorInfo);
			return new SocialAppRequestStatus(SocialAppRequestStatus.ERRORCODE, SocialAppRequestStatus.ERROR,
					errorInfoList);
		}
	}

	// Delete a user
	public SocialAppRequestStatus deleteUserById(int id)
	{
		Integer userID = id;
		SocialAppRequestStatus requestStatus = getUserById(userID);
		if (requestStatus.getErrorCode() == SocialAppRequestStatus.SUCCESSCODE)
		{
			appUserRepository.deleteById(id);
			logger.debug("deleted user id" + id);
			return new SocialAppRequestStatus(SocialAppRequestStatus.SUCCESSCODE, SocialAppRequestStatus.SUCCESS, null);
		} else
		{
			List<ErrorInfo> errorInfo = new ArrayList<ErrorInfo>();
			errorInfo.add(new ErrorInfo(ErrorInfo.USER_NOT_FOUND_ERRORCODE, "id:"+userID.toString(), ErrorInfo.USER_NOT_FOUND_MESSAGE));
			ErrorInfoList errorInfoList = new ErrorInfoList(errorInfo);
			return new SocialAppRequestStatus(SocialAppRequestStatus.ERRORCODE, SocialAppRequestStatus.ERROR,
					errorInfoList);
		}
	}

	// Update a user
	public SocialAppRequestStatus updateUser(AppUser user)
	{
		Integer userID = user.getId();
		SocialAppRequestStatus requestStatus = getUserById(userID);
		if (requestStatus.getErrorCode() == SocialAppRequestStatus.SUCCESSCODE)
		{
			AppUser savedUser = appUserRepository.save(user);
			logger.debug("updated user" + user);

			return new SocialAppRequestStatus(SocialAppRequestStatus.SUCCESSCODE, SocialAppRequestStatus.SUCCESS,
					savedUser);
		} else
		{
			List<ErrorInfo> errorInfo = new ArrayList<ErrorInfo>();
			errorInfo.add(new ErrorInfo(ErrorInfo.USER_NOT_FOUND_ERRORCODE, "id:"+userID.toString(), ErrorInfo.USER_NOT_FOUND_MESSAGE));
			ErrorInfoList errorInfoList = new ErrorInfoList(errorInfo);
			return new SocialAppRequestStatus(SocialAppRequestStatus.ERRORCODE, SocialAppRequestStatus.ERROR,
					errorInfoList);
		}

	}

}
