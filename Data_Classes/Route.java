package Data_Classes;

public class Route{
    protected int id;
    private static int numberOfRoutes = 0;
    private String destination;
    private String origin;
    private int distance;
    private int tollCharges;

    public Route(int id, String destination, String origin, int distance, int tollCharges){
        this.id = id;
        this.destination = destination;
        this.origin = origin;
        this.distance = distance;
        this.tollCharges = tollCharges;
    }

    public int getId(){
        return this.id;
    }
    public String getDestination(){
        return this.destination;
    }

    public String getOrigin(){
        return this.origin;
    }
    public int getDistance(){
        return this.distance;
    }
    public int getTollCharges(){
        return this.tollCharges;
    }

    public String toString(){
        return "ID: "+ (this.id )+ " Destination: "+this.destination +" Origin: "+ this.origin + " Distance: " + this.distance+ " Toll Charges: "  + this.tollCharges;
    }

}
