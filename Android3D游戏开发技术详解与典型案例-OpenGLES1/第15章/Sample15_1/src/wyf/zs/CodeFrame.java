package wyf.zs;
import javax.swing.*;
public class CodeFrame extends JFrame
{
	JTextArea jta=new JTextArea();
	JScrollPane jsp=new JScrollPane(jta);
	
	public CodeFrame(String codeStr,String title)
	{
		this.setTitle(title);
		
		this.add(jsp);
		
		jta.setText(codeStr);
		
		this.setBounds(100,100,400,300);
		this.setVisible(true);
	}
}