package com.xmobileapp.NewsWidget; 

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes; 
import org.xml.sax.SAXException; 
import org.xml.sax.helpers.DefaultHandler; 

public class myHandler extends DefaultHandler
{ 
  private boolean mBoolItem = false;
  private boolean mBoolTitle = false;
  private boolean mBoolLink = false;
  private boolean mBoolDesc = false;
  private boolean mBoolDate = false;
  private boolean mBoolMainTitle = false;
  private List<newsClass> mListNews;
  private newsClass mNews;
  private String mStrNewsTitle="";
  private StringBuffer mStrBuffer=new StringBuffer();

  public List<newsClass> getParsedData()
  { 
    return mListNews; 
  }
  public String getRssTitle()
  { 
    return mStrNewsTitle; 
  }
  @Override 
  public void startDocument() throws SAXException
  { 
	  mListNews = new ArrayList<newsClass>(); 
  } 
  @Override 
  public void endDocument() throws SAXException
  {
  } 
  @Override 
  public void startElement(String namespaceURI, String localName, 
               String qName, Attributes atts) throws SAXException
  { 
    if (localName.equals("item"))
    { 
      mBoolItem = true;
      mNews = new newsClass();
    }
    else if (localName.equals("title"))
    { 
      if(mBoolItem)
      {
    	  mBoolTitle = true;
      }
      else
      {
    	  mBoolMainTitle = true;
      }
    }
    else if (localName.equals("link"))
    { 
      if(mBoolItem)
      {
    	  mBoolLink = true;
      }
    }
    else if (localName.equals("description"))
    { 
      if(mBoolItem)
      {
    	  mBoolDesc = true;
      }
    }
    else if (localName.equals("pubDate"))
    { 
      if(mBoolItem)
      {
    	  mBoolDate = true;
      }
    } 
  }
  @Override 
  public void endElement(String namespaceURI, String localName,
                         String qName) throws SAXException
  { 
    if (localName.equals("item"))
    { 
      mBoolItem = false;
      mListNews.add(mNews);
    }
    else if (localName.equals("title"))
    { 
      if(mBoolItem)
      {
    	  mNews.setTitle(mStrBuffer.toString().trim());
    	  mStrBuffer.setLength(0);
    	  mBoolTitle = false;
      }
      else
      {
    	  mStrNewsTitle = mStrBuffer.toString().trim();
    	  mStrBuffer.setLength(0);
    	  mBoolMainTitle = false;
      }
    }
    else if (localName.equals("link"))
    { 
      if(mBoolItem)
      {
	        mNews.setLink(mStrBuffer.toString().trim());
	        mStrBuffer.setLength(0);
	        mBoolLink = false;
      }
    }
    else if (localName.equals("description"))
    { 
      if(mBoolItem)
      {
	        mNews.setDesc(mStrBuffer.toString().trim());
	        mStrBuffer.setLength(0);
	        mBoolDesc = false;
      }
    }
    else if (localName.equals("pubDate"))
    { 
      if(mBoolItem)
      {
	        mNews.setDate(mStrBuffer.toString().trim());
	        mStrBuffer.setLength(0);
	        mBoolDate = false;
      }
    } 
  } 
  @Override 
  public void characters(char ch[], int start, int length)
  { 
    if(mBoolItem||mBoolMainTitle)
    {
    	mStrBuffer.append(ch,start,length);
    }
  } 
}