package �ļ�����;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FileInAndOut {
	boolean timesflag=true;//���д�����־
	public FileInAndOut(String filename,int attribute){
		
		this.attribute=attribute;
		this.file=filename;
		//����������⣬���ļ�������ʱ�޷����ж�ȡ
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
	
	
//���ļ���д��
public boolean toFile(String str) {
	// TODO �Զ����ɵķ������
	//�費��Ҫ���в����жϣ�
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
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	this.number++;
return flag;

}

//��ȡ�ļ�������һ��List
public ArrayList<String> readFile() {
	// TODO �Զ����ɵķ������
	ArrayList<String> Al=new ArrayList<String>();
	File file =new File(this.file);
	FileReader fr = null;
	String temp=null;
	try {
		fr = new FileReader(file);
	} catch (FileNotFoundException e1) {
		// TODO �Զ����ɵ� catch ��
		e1.printStackTrace();
	}
	BufferedReader br=null;
	br=new BufferedReader(fr);
	try {
		while((temp=br.readLine())!=null) {
			Al.add(temp);
		}
	} catch (IOException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	return Al;
}
//��string ��arraylistת����String�Ķ�ά����
@SuppressWarnings("null")
public String[][] altoStr(ArrayList<String> Al){
	
	String  [][]result=new String[999][999];//���֧��1000�����ԣ�1000�����ݡ�
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
//չʾ����
private void show(String[][] s) {
	
	
	for(int i=0;i<number;i++) {//������
		
		for(int j=0;j<attribute;j++) {//���Ե�����
			
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
//���ӹ��ܣ�ָ���ڼ��е�������
public String load(String index,int rownumber) {//row number ΪҪ�����ĵڼ��У�1Ϊ��һ�С�
	
	String result="";
	String res[][]=this.altoStr(this.readFile());//��ά�����
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

public void update(String id,String upd) throws MyException {//��Ҫ�޸ĵ�id��Ҫ�ĳɵ����
	
	int obj=0;//��Ҫ�޸ĵ�Ŀ��
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
//	System.out.println("�ҵ���Ŀ��ֵ����Ϊ��"+obj);
	if(obj==size) {//û���ҵ�
		
			throw new MyException("û���ҵ�Ҫ�޸ĵ�����!");
	
	}
	ArrayList<String> result=new ArrayList<String>();
	result=this.readFile();
	result.set(obj, upd);
	FileWriter fw = null;
	 try {
		fw=new FileWriter(file,false);
	} catch (IOException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	 for(int i=0;i<this.getSize(result);i++) {
		 
		 try {
			fw.write(result.get(i)+"\n");
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		 	 
	 }
	 try {
		fw.close();
	} catch (IOException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}

}
public void delete(String id) throws MyException {
	
	int obj=0;//��Ҫɾ����Ŀ��
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
//	System.out.println("�ҵ���Ŀ��ֵ����Ϊ��"+obj);
	if(obj==size) {//û���ҵ�
			throw new MyException("û���ҵ�Ҫɾ��������!");	
	}
	ArrayList<String> result=new ArrayList<String>();
	result=this.readFile();
	result.remove(obj);
	FileWriter fw = null;
	 try {
		fw=new FileWriter(file,false);
	} catch (IOException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	 for(int i=0;i<this.getSize(result);i++) {
		 
		 try {
			fw.write(result.get(i)+"\r");
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		 	 
	 }
	 try {
		fw.close();
	} catch (IOException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	
	this.number--;
	
}

}












