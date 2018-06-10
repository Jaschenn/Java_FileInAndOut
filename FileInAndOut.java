package 文件操作;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FileInAndOut {
	boolean timesflag=true;//运行次数标志
	public FileInAndOut(String filename,int attribute){
		
		this.attribute=attribute;
		this.file=filename;
		//待解决的问题，当文件不存在时无法进行读取
		try {
		this.number=this.getSize(this.readFile());
		}catch(Exception e) {
			
			this.number=0;
			
		}
	
	}
	
	private int number=0;
	private int attribute=0;
	private String file=null;
	public int getNumber() {
		return number;
	}
	public void setNumber() {
		this.number = this.getSize(this.readFile());
	}
	public int getAttribute() {
		return attribute;
	}
	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}
	public String getFilename() {
		return file;
	}
	public void setFilename(String filename) {
		this.file = filename;
	}
	
	
//向文件中写入
public boolean toFile(String str) {
	// TODO 自动生成的方法存根
	//需不需要进行查重判断？
	boolean flag=false;
	File file=new File(this.file);
	FileWriter fw = null;
	try {
		 fw=new FileWriter(file,true);
		
	}catch (Exception e) {
		
		e.getMessage();
	}
	try {
		fw.write(str);
		flag=true;
	}catch(Exception e) {
		e.printStackTrace();
	}
	flag=false;
	try {
		fw.close();
		flag=true;
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	this.number++;
return flag;

}

//读取文件并返回一个List
public ArrayList<String> readFile() {
	// TODO 自动生成的方法存根
	ArrayList<String> Al=new ArrayList<String>();
	File file =new File(this.file);
	FileReader fr = null;
	String temp=null;
	try {
		fr = new FileReader(file);
	} catch (FileNotFoundException e1) {
		// TODO 自动生成的 catch 块
		e1.printStackTrace();
	}
	BufferedReader br=null;
	br=new BufferedReader(fr);
	try {
		while((temp=br.readLine())!=null) {
			Al.add(temp);
		}
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	return Al;
}
//把string 的arraylist转换成String的二维数组
@SuppressWarnings("null")
public String[][] altoStr(ArrayList<String> Al){
	
	String  [][]result=new String[999][999];//最大支持1000个属性，1000条数据。
	@SuppressWarnings("unused")
	int rflag=0,flag=0;
	String temp=null;
	int size=Al.size();
	for(int i=0;i<size;i++) {
		temp=Al.get(i);
		String	data[]	=temp.toString().trim().split("\\|");
		for(int j=0;j<data.length;j++) {
			result[rflag][j]=data[j];
		}
		rflag++;
		flag++;
	}
	return result;
}
//展示函数
private void show(String[][] s) {
	
	
	for(int i=0;i<number;i++) {//总行数
		
		for(int j=0;j<attribute;j++) {//属性的数量
			
			System.out.print(s[i][j]);
			System.out.print("\t");

		}
		System.out.println();
		
	}

}
public void show() {
	
	this.show(this.altoStr(this.readFile()));
	
	
}
public int getSize(ArrayList<String> al) {
	
	return al.size();
	
}
//增加功能，指定第几列当作索引
public String load(String index,int rownumber) {//row number 为要检索的第几列，1为第一列。
	
	String result="";
	String res[][]=this.altoStr(this.readFile());//二维结果集
	int flag=0;
	int size=this.getSize(this.readFile());
	//-------------------------------------------------//
	while(flag<size) {
		if(res[flag][rownumber-1].equals(String.valueOf(index))) {
			for(int i=0;i<this.attribute;i++) {
			result=result+res[flag][i]+"\t".toString();
			}
			break;
		}
		
		flag++;
	}
	
	return result;
	
}

public void update(String id,String upd) throws MyException {//需要修改的id，要改成的语句
	
	int obj=0;//需要修改的目标
	String res[][]=this.altoStr(this.readFile());
	int flag=0;
	int size=this.getSize(this.readFile());
	
	while(flag<size) {
		if(res[flag][0].equals(String.valueOf(id))) {
	
			break;
		}
		flag++;
		obj++;
	}
//	System.out.println("找到的目标值索引为："+obj);
	if(obj==size) {//没有找到
		
			throw new MyException("没有找到要修改的数据!");
	
	}
	ArrayList<String> result=new ArrayList<String>();
	result=this.readFile();
	result.set(obj, upd);
	FileWriter fw = null;
	 try {
		fw=new FileWriter(file,false);
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	 for(int i=0;i<this.getSize(result);i++) {
		 
		 try {
			fw.write(result.get(i)+"\n");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		 	 
	 }
	 try {
		fw.close();
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}

}
public void delete(String id) throws MyException {
	
	int obj=0;//需要删除的目标
	String res[][]=this.altoStr(this.readFile());
	int flag=0;
	int size=this.getSize(this.readFile());
	

	while(flag<size) {
		if(res[flag][0].equals(String.valueOf(id))) {
	
			break;
		}
		flag++;
		obj++;
	}
//	System.out.println("找到的目标值索引为："+obj);
	if(obj==size) {//没有找到
			throw new MyException("没有找到要删除的数据!");	
	}
	ArrayList<String> result=new ArrayList<String>();
	result=this.readFile();
	result.remove(obj);
	FileWriter fw = null;
	 try {
		fw=new FileWriter(file,false);
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	 for(int i=0;i<this.getSize(result);i++) {
		 
		 try {
			fw.write(result.get(i)+"\r");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		 	 
	 }
	 try {
		fw.close();
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	
	this.number--;
	
}

}












