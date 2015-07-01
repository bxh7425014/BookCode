package wyf.sj;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import static wyf.sj.Constant.*;

public class CactusGroup {

	CactusForDraw cfd;//用于绘制单个仙人掌的对象引用
    ArrayList<SingleCactus> al=new ArrayList<SingleCactus>();//仙人掌列表
    
    public CactusGroup(int texId)
    {
    	cfd=new CactusForDraw(texId);//初始化用于绘制单个仙人掌的对象
    	
    	//扫描地图生成各个位置的仙人掌对象
    	for(int i=0;i<DESERT_ROWS;i++)
    	{
    		for(int j=0;j<DESERT_COLS;j++)
    		{
    			if(MAP_CACTUS[i][j]==0)
    			{
    				continue;
    			}
    			//创建此位置的仙人掌对象
    			SingleCactus tempSc=new SingleCactus
    			(
    					(j+0.5f)*UNIT_SIZE,
    					(i+0.5f)*UNIT_SIZE,
    					0,
    					this
    			);
    			//将仙人掌对象加入仙人掌列表
    			al.add(tempSc);
    		}
    	}
    }
    
    public void calculateBillboardDirection()
    {
    	//计算列表中每个仙人掌的朝向
    	for(int i=0;i<al.size();i++)
    	{
    		al.get(i).calculateBillboardDirection();
    	}
    }
    
    public void drawSelf(GL10 gl)
    {//绘制列表中的每个仙人掌
    	for(int i=0;i<al.size();i++)
    	{
    		al.get(i).drawSelf(gl);
    	}
    }
}
