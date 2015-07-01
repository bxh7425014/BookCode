package irdc.ex05_24;

/* import相關class */
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/* 自定義的Adapter，繼承android.widget.BaseAdapter */
public class MyAdapter extends BaseAdapter
{
  /* 變數宣告 */
  private LayoutInflater mInflater;
  private List<String> items;
  private List<String> values;
  /* MyAdapter的建構子，傳入三個參數  */  
  public MyAdapter(Context context,List<String> item,List<String> value)
  {
    /* 參數初始化 */
    mInflater = LayoutInflater.from(context);
    items = item;
    values = value;
  }
  
  /* 因繼承BaseAdapter，需覆寫以下method */
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
  public View getView(int position,View convertView,ViewGroup parent)
  {
    ViewHolder holder;
  
    if(convertView == null)
    {
      /* 使用自定義的file_row作為Layout */
      convertView = mInflater.inflate(R.layout.row_layout,null);
      /* 初始化holder的text與icon */
      holder = new ViewHolder();
      holder.text1=(TextView)convertView.findViewById(R.id.myText1);
      holder.text2=(TextView)convertView.findViewById(R.id.myText2);
    
      convertView.setTag(holder);
    }
    else
    {
      holder = (ViewHolder) convertView.getTag();
    }
    /* 設定要顯示的資訊 */
    holder.text1.setText(items.get(position).toString());
    holder.text2.setText(values.get(position).toString());
  
    return convertView;
  }
  
  /* class ViewHolder */
  private class ViewHolder
  {
    /* text1：資訊名稱
     * text2：資訊內容 */
    TextView text1;
    TextView text2;
  }
}