package ca.jrvs.apps.practice;
import java.util.regex.*;

public class RegexExcImp implements RegexExc {
    @Override
    public boolean matchJpeg(String fliename);

    {

        return filename.matches(".*.(jpeg|jeg)$");
    }

    @Override
    public boolean matchIp(String ip);

    {
        return ip.matches("\\d{1,2}|(0|1)\\" + "d{2}|2[0-9]\\d|99[0-9]");
    }

    @Override
    public boolean isEmptyLine(String line);

    {
        return line.matches("^[ ]*$");
    }

}