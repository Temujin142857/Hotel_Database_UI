package global;

public class Room {
    private int ID_chambre;
    private int ID_hotel;
    private int prix;
    private String commodities;
    private String capacity;
    private String vue;
    private boolean peut_etendre;
    private String dommages;


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

    public int getID_chambre() {
        return ID_chambre;
    }

    public int getID_hotel() {
        return ID_hotel;
    }

    public int getPrix() {
        return prix;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getCommodities() {
        return commodities;
    }

    public String getDommages() {
        return dommages;
    }

    public String getVue() {
        return vue;
    }

}

