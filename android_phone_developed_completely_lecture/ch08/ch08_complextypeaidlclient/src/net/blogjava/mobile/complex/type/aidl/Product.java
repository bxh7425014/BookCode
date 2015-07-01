package net.blogjava.mobile.complex.type.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable
{
	private int id;
	private String name;
	private float price;
	public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>()
	{
		public Product createFromParcel(Parcel in)
		{
			return new Product(in);
		}

		public Product[] newArray(int size)
		{
			return new Product[size]; 
		}
	};
    public Product()
    {
    	
    }
	private Product(Parcel in)
	{
		readFromParcel(in);
	}

	@Override
	public int describeContents()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public void readFromParcel(Parcel in)
	{
		id = in.readInt();
		name = in.readString();
		price = in.readFloat();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeFloat(price);

	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public float getPrice()
	{
		return price;
	}

	public void setPrice(float price)
	{
		this.price = price;
	}

}
