public class MyTools{
   	public String change(String source){
   		source=source.replace("<","&lt;");
   		source=source.replace(">","&gt;");
   		return source;
   	}
}
