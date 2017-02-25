import java.io.BufferedReader;

import java.io.DataOutputStream;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;

import java.net.MalformedURLException;

import java.net.URL;

import java.io.File;

import java.io.IOException;

import java.awt.image.*;

import javax.imageio.*;





import javax.net.ssl.HttpsURLConnection;



public class UnifyBitMap {



private final static String USER_AGENT = "Mozilla/5.0";



public static void main(String[] args) throws Exception {



int width =10;

int height =10;

int count = 0;

BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);

File f = null;


for (int y = 0; y < height; y++) {


for (int x=0; x < width; x++) {



//Make Http requests to Random Web API

int alpha = 0;

int red = 0;

int blue = 0;

int green = 0;

for( int i = 0; i < 4; i++) {


if(i == 0) {


//Set Alpha

alpha = GetRandNumFromWeb();

}


if(i == 1) {


//Set Red

red = GetRandNumFromWeb();

}


if(i == 2) {


//Set Blue

blue = GetRandNumFromWeb();

}


if(i == 3) {


//Set Green

green = GetRandNumFromWeb();

}


}


System.out.println("Alpha: " + alpha+" Red: " + red+" Blue: " + blue + " Green: " + green);


int pixel = (alpha << 24) | (red << 16) | (green << 8) | blue;

img.setRGB(x, y, pixel);


count++;

System.out.println("count: "+ count);



}

}


try{

f = new File("/Users/DhivyaIyer/Desktop/UnifyOutput.png");

ImageIO.write(img, "png", f);


}catch(IOException e) {

System.out.println("Error: "+ e);

}


System.out.println("Done Writing image");



}



private static int GetRandNumFromWeb() throws Exception {


int value= 0;


String url = "https://www.random.org/integers/?num=1&min=0&max=127&col=1&base=10&format=plain&rnd=new";



URL obj = new URL(url);

HttpURLConnection con = (HttpURLConnection) obj.openConnection();


// optional default is GET

con.setRequestMethod("GET");



//add request header

con.setRequestProperty("User-Agent", USER_AGENT);


int responseCode = con.getResponseCode();

System.out.println("\nSending 'GET' request to URL : " + url);

System.out.println("Response Code : " + responseCode);


int try_threshold =200;

int get_count = 0;


while(responseCode !=200) {


con = (HttpURLConnection) obj.openConnection();

// optional default is GET

con.setRequestMethod("GET");



//add request header

con.setRequestProperty("User-Agent", USER_AGENT);


responseCode = con.getResponseCode();

get_count++;


if(get_count > 200) {

System.out.println("\n Tried 200 times yet unsuccessful");

break;

}




}


//Response code is 200--> Success


BufferedReader in = new BufferedReader(

        new InputStreamReader(con.getInputStream()));

String inputLine;

StringBuffer response = new StringBuffer();



while ((inputLine = in.readLine()) != null) {

response.append(inputLine);

}

in.close();



//print result

System.out.println("Response size: "+ response.length());

System.out.println(response.toString());


value = Integer.parseInt(response.toString());


System.out.println("Value: "+ value);



return value;


}







}