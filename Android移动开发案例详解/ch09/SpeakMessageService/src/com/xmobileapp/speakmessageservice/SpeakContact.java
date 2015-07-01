package com.xmobileapp.speakmessageservice;

public class SpeakContact
{
	private final Long id;
	private final String contact;
	private final Long contactId;
	private final boolean enabled;
	
	public SpeakContact(final Long id, final String contact, final Long contactId, final boolean enabled)
	{
		this.id = id;
		this.contact = contact;
		this.contactId = contactId;
		this.enabled = enabled;
	}

	public Long getId()
	{
		return id;
	}

	public String getContact()
	{
		return contact;
	}

	public Long getContactId()
	{
		return contactId;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
}
