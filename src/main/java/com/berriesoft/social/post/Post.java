package com.berriesoft.social.post;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="post")
@SQLDelete(sql = "UPDATE post SET is_deleted = 1 WHERE id=?")
@Where(clause = "is_deleted=0")
public class Post
{
	
	@Id
	@GeneratedValue
	private int id;
	
	// Foreign key for user
	@NotNull
	private int appUserID;

	@Size(min = 10, max = 256)
	@NotNull
	private String Post;
	
	@NotNull
	private LocalDateTime addedDate;
	@NotNull
	private LocalDateTime lastModifiedDate;
	
	@NotNull
	private int isDeleted;
	@NotNull
	private int isHidden;
	
	public Post(int id, @NotNull int appUserID, @Size(min = 10, max = 256) @NotNull String post,
			@NotNull LocalDateTime addedDate, @NotNull LocalDateTime lastModifiedDate, @NotNull int isDeleted,
			@NotNull int isHidden)
	{
		super();
		this.id = id;
		this.appUserID = appUserID;
		Post = post;
		this.addedDate = addedDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isDeleted = isDeleted;
		this.isHidden = isHidden;
	}

	public Post()
	{
	}

	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getAppUserId()
	{
		return appUserID;
	}
	public void setAppUserId(int appUserID)
	{
		this.appUserID = appUserID;
	}

	public String getPost()
	{
		return Post;
	}
	public void setPost(String post)
	{
		Post = post;
	}
	public int getIsDeleted()
	{
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted)
	{
		this.isDeleted = isDeleted;
	}
	public int getIsHidden()
	{
		return isHidden;
	}
	public void setIsHidden(int isHidden)
	{
		this.isHidden = isHidden;
	}

	public LocalDateTime getAddedDate()
	{
		return addedDate;
	}

	public void setAddedDate(LocalDateTime addedDate)
	{
		this.addedDate = addedDate;
	}

	public LocalDateTime getLastModifiedDate()
	{
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate)
	{
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString()
	{
		return "Post [id=" + id + ", appUserId=" + appUserID + ", Post=" + Post + ", addedDate=" + addedDate
				+ ", lastModifiedDate=" + lastModifiedDate + ", isDeleted=" + isDeleted + ", isHidden=" + isHidden
				+ "]";
	}

	
	
	
	

}
