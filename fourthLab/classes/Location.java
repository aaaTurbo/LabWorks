package classes;

public class Location {
    private String location;
    private String city;

    public Location(String location, String city) throws NullPointerException {
        if (location == null || city == null) throw new NullPointerException();
        this.location = location;
        this.city = city;
    }

    public void changeLocation(String newLocation) throws NullPointerException {
        location = newLocation;
    }

    public void changeCity(String newCity, String newLocation) throws NullPointerException {
        city = newCity;
        location = newLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location l = (Location) o;
        return city == l.city;
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public String toString() {
        return "Локация " + location + " в городе " + city;
    }

}
