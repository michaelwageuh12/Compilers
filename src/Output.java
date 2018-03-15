
public class Output {
	int startIndx,endIndx;
	String value;
	Token token;
	Output(int s,int e,String v,Token t){
		token=t;
		value=v;
		startIndx=s;
		endIndx =e ;
	}
	Output(){
		token=new Token();
		value="";
		startIndx=-1;
		endIndx =-1 ;
	}
}
