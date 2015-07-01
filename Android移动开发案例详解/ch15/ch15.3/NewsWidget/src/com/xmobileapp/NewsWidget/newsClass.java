package com.xmobileapp.NewsWidget; 

public class newsClass
{ 
  private String mStrTitle="";
  private String mStrLink="";
  private String mStrDesc="";
  private String mStrDate="";
  
  public String getTitle()
  { 
    return mStrTitle; 
  } 
  public String getLink()
  { 
    return mStrLink; 
  }
  public String getDesc()
  { 
    return mStrDesc; 
  } 
  public String getDate()
  { 
    return mStrDate; 
  }
  public void setTitle(String title)
  { 
	  mStrTitle=title; 
  } 
  public void setLink(String link)
  { 
	  mStrLink=link; 
  }
  public void setDesc(String desc)
  { 
	  mStrDesc=desc; 
  } 
  public void setDate(String date)
  { 
	  mStrDate=date; 
  }
}