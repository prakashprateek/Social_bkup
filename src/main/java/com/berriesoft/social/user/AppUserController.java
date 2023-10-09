package com.berriesoft.social.user;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.berriesoft.social.status.ErrorInfoList;
import com.berriesoft.social.status.SocialAppRequestStatus;
import com.berriesoft.social.status.UserNotCreatedExeption;
import com.berriesoft.social.status.UserNotFoundExeption;

import jakarta.validation.Valid;

@RestController
public class AppUserController
{
	AppUserDAOServiceJPA appUserDAOServiceJPA;

	Logger logger = LoggerFactory.getLogger(getClass());

	public AppUserController(AppUserDAOServiceJPA appUserDAOServiceJPA)
	{
		super();
		this.appUserDAOServiceJPA = appUserDAOServiceJPA;

	}
	
	
	@GetMapping(path = "users/v1")
	@ResponseBody
	public List<AppUser> getAllUsers()
	{
		logger.debug("in getAllUser");
		return appUserDAOServiceJPA.getAllUsers();
	}

	@GetMapping(path = "users/v1/{id}")
	@ResponseBody
	public ResponseEntity<Object> getUserById(@PathVariable int id)
	{
		logger.debug("in getUserById for id: " + id);
		SocialAppRequestStatus status = appUserDAOServiceJPA.getUserById(id);

		if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
		{
			throw new UserNotFoundExeption ((ErrorInfoList) status.getPayload());
		} else
		{
			return ResponseEntity.status(SocialAppRequestStatus.SUCCESSCODE).body((AppUser) status.getPayload());
		}
	}

	@PostMapping(path = "users/v1")
	public ResponseEntity<Object> addUser(@Valid @RequestBody AppUser user)
	{
		logger.debug("in addUser");
		SocialAppRequestStatus status = appUserDAOServiceJPA.addUser(user);
		
		if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
		{
			throw new UserNotCreatedExeption ((ErrorInfoList) status.getPayload());
		} else
		{
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
					.toUri();
			return ResponseEntity.created(location).build();
		}
	}

	@DeleteMapping(path = "users/v1/{id}")
	public ResponseEntity<Object> deleteUserById(@PathVariable int id)
	{
		logger.debug("in deleteUserById for id: " + id);
		
		SocialAppRequestStatus status = appUserDAOServiceJPA.deleteUserById(id);

		if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
		{
			throw new UserNotFoundExeption ((ErrorInfoList) status.getPayload());
		} else
		{
			return ResponseEntity.status(SocialAppRequestStatus.SUCCESSCODE).build();
		}
	}

	@PutMapping(path = "users/v1/{id}")
	public ResponseEntity<Object> updateUser(@RequestBody @Valid AppUser user)
	{
		logger.debug("in UpdateUser");
		SocialAppRequestStatus status = appUserDAOServiceJPA.updateUser(user);

		if (status.getErrorCode() != SocialAppRequestStatus.SUCCESSCODE)
		{
			throw new UserNotFoundExeption ((ErrorInfoList) status.getPayload());
		} else
		{
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
			return ResponseEntity.created(location).build();
		}
	}

}
