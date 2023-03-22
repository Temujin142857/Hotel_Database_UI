public class Room {
    int ID_chambre;
    int ID_hotel;
    int prix;
    String commodities;
    String capacity;
    String vue;
    boolean peut_etendre;
    String dommages;

    public Room(String[] roomData){
        //ID_chambre = roomData[0];
        //ID_hotel = roomData[1];
        //prix = roomData[2];
        commodities = roomData[3];
        capacity = roomData[4];
        vue = roomData[5];
       // peut_etendre = roomData[6];
        dommages = roomData[7];
    }
    public Room(int ID_chambre, int ID_hotel, int prix, String commodities, String capacity, String vue, boolean peut_etendre, String dommages){
        this.ID_chambre=ID_chambre;
        this.ID_hotel=ID_hotel;
        this.prix=prix;
        this.commodities=commodities;
        this.capacity=capacity;
        this.vue=vue;
        this.peut_etendre=peut_etendre;
        this.dommages=dommages;
    }
}
