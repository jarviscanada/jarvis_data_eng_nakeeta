package ca.jrvs.apps.practice;

public class RegexExcImp extends RegexExc {

    public boolean matchJpeg(String fliename){
        return fliename.matches(".*.(jpeg|jeg)$");
    }


    public boolean matchIp(String ip){
        return ip.matches("\\d{1,2}|(0|1)\\" + "d{2}|2[0-9]\\d|99[0-9]");
    }


    public boolean isEmptyLine(String line){
        return line.matches("^[ ]*$");
    }


}