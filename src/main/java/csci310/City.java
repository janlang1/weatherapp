package csci310;

import java.util.Comparator;

public class City {
	public String city;
	public String country;
	public int min_temp;
	public int max_temp;
	public int curr_temp;
	public int distance;
	public int likes;
	public boolean inFavorites;
	
	public City(String ci, String co, int min, int max, int curr, int dist, int likes_) {
		city = ci;
		country = co;
		min_temp = min;
		max_temp = max;
		curr_temp = curr;
		distance = dist;
		likes = likes_;
		inFavorites = false;
	}
}

class SortbyDistance implements Comparator<City> 
{ 
    // Used for sorting in ascending order of distance number 
    public int compare(City a, City b) 
    { 
        return a.distance - b.distance; 
    } 
} 

class SortbyLikes implements Comparator<City> 
{ 
    // Used for sorting in ascending order of distance number 
    public int compare(City a, City b) 
    { 
        return a.likes - b.likes; 
    } 
} 
