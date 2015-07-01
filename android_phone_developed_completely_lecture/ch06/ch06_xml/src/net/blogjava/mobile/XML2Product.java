package net.blogjava.mobile;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
public class XML2Product extends DefaultHandler
{
	private List<Product> products;
	private Product product;
	

	private StringBuffer buffer = new StringBuffer();

	public List<Product> getProducts()
	{
		return products;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException
	{

		buffer.append(ch, start, length);
		super.characters(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		if (localName.equals("product"))
		{
			products.add(product);
		}
		else if (localName.equals("id"))
		{
			product.setId(Integer.parseInt(buffer.toString().trim()));
			buffer.setLength(0);
		}
		else if (localName.equals("name"))
		{
			product.setName(buffer.toString().trim());
			buffer.setLength(0);
		}
		else if (localName.equals("price"))
		{
			product.setPrice(Float.parseFloat(buffer.toString().trim()));
			buffer.setLength(0);
		}
		super.endElement(uri, localName, qName);
	}
	@Override
	public void startDocument() throws SAXException
	{
		products = new ArrayList<Product>();

	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{

		if (localName.equals("product"))
		{
			product = new Product();
		}
		super.startElement(uri, localName, qName, attributes);
	}
}
