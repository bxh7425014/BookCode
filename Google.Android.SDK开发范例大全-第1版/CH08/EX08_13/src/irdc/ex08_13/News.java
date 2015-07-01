package irdc.ex08_13; 

public class News
{ 
  /* News的四個變數 */
  private String _title="";
  private String _link="";
  private String _desc="";
  private String _date="";
  
  public String getTitle()
  { 
    return _title; 
  } 
  public String getLink()
  { 
    return _link; 
  }
  public String getDesc()
  { 
    return _desc; 
  } 
  public String getDate()
  { 
    return _date; 
  }
  public void setTitle(String title)
  { 
    _title=title; 
  } 
  public void setLink(String link)
  { 
    _link=link; 
  }
  public void setDesc(String desc)
  { 
    _desc=desc; 
  } 
  public void setDate(String date)
  { 
    _date=date; 
  }
}