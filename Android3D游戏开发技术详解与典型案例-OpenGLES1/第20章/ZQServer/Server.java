package wyf.jsc;

import java.util.*;
import java.io.*;
import java.net.*;

public class Server
{
	public static void main(String args[])
	{
		new ServerThread().start();
	}
}