package bot;

public class Current
{


    private String temp_c;

    private String wind_kph;

    private String pressure_mb;

    private String cloud;


    public String getTemp_c ()
    {
        return temp_c;
    }

    public void setTemp_c (String temp_c)
    {
        this.temp_c = temp_c;
    }

    public String getWind_kph ()
    {
        return wind_kph;
    }

    public void setWind_kph (String wind_kph)
    {
        this.wind_kph = wind_kph;
    }

    public String getPressure_mb ()
    {
        return pressure_mb;
    }

    public void setPressure_mb (String pressure_mb)
    {
        this.pressure_mb = pressure_mb;
    }

    public String getCloud ()
    {
        return cloud;
    }

    public void setCloud (String cloud)
    {
        this.cloud = cloud;
    }

}