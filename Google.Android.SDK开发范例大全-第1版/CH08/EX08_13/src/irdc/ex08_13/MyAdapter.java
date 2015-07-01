package irdc.ex08_13;

/* import闽class */
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/* ﹚竡Adapter膥┯android.widget.BaseAdapter */
public class MyAdapter extends BaseAdapter
{
  /* 跑计  */
  private LayoutInflater mInflater;
  private List<News> items;

  /* MyAdapter篶肚ㄢ把计  */  
  public MyAdapter(Context context,List<News> it)
  {
    /* 把计﹍て */
    mInflater = LayoutInflater.from(context);
    items = it;
  }
  
  /* 膥┯BaseAdapter惠滦糶method */
  @Override
  public int getCount()
  {
    return items.size();
  }

  @Override
  public Object getItem(int position)
  {
    return items.get(position);
  }
  
  @Override
  public long getItemId(int position)
  {
    return position;
  }
  
  @Override
  public View getView(int position,View convertView,ViewGroup par)
  {
    ViewHolder holder;
    
    if(convertView == null)
    {
      /* ㄏノ﹚竡news_rowLayout */
      convertView = mInflater.inflate(R.layout.news_row, null);
      /* ﹍てholdertext籔icon */
      holder = new ViewHolder();
      holder.text = (TextView) convertView.findViewById(R.id.text);      
      convertView.setTag(holder);
    }
    else
    {
      holder = (ViewHolder) convertView.getTag();
    }
    News tmpN=(News)items.get(position);
    holder.text.setText(tmpN.getTitle());
    
    return convertView;
  }
  
  /* class ViewHolder */
  private class ViewHolder
  {
    TextView text;
  }
}