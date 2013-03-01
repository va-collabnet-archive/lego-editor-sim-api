package gov.va.legoEdit.model.sim.lego;

import gov.va.sim.lego.LegoStampBI;
import java.util.Date;
import java.util.UUID;

/**
 * LegoStamp - LegoEditor Implementation of the SIM-API
 * @author Dan Armbrust 
 */
public class LegoStamp implements LegoStampBI
{
	private UUID instance_;
	private long time_;
	private String author_, module_, path_, status_;
	
	public LegoStamp(UUID instance, String status, long time, String author, String module, String path)
	{
		instance_ = instance;
		status_ = status;
		time_ = time;
		author_ = author;
		module_ = module;
		path_ = path;
	}
	
	@Override
	public UUID getInstanceUuid()
	{
		return instance_;
	}

	@Override
	public String getStatus()
	{
		return status_;
	}

	@Override
	public long getTime()
	{
		return time_;
	}

	@Override
	public String getAuthor()
	{
		return author_;
	}

	@Override
	public String getModule()
	{
		return module_;
	}

	@Override
	public String getPath()
	{
		return path_;
	}

	@Override
	public String toString()
	{
		return "Stamp: " + status_ + ":" + new Date(time_).toString() + " : " + author_+ " : " + module_ + " : " + path_ + " : " + instance_.toString();
	}
	
}
