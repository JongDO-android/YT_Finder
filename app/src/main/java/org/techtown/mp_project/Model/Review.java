package org.techtown.mp_project.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class Review {
    private String nickname;
    private String review;
    private float rating;

    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return nickname;
    }

    public void setReview(String review){
        this.review = review;
    }
    public String getReview(){
        return review;
    }

    public void setRating(float rating){
        this.rating = rating;
    }
    public float getRating(){
        return rating;
    }

    @Exclude
    public Object toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("nickname", nickname);
        result.put("review", review);
        result.put("rating", rating);

        return result;
    }
}
