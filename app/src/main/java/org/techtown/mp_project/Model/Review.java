package org.techtown.mp_project.Model;

public class Review {
    private String nickname;
    private String review;
    private int rating;

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

    public void setRating(int rating){
        this.rating = rating;
    }
    public int getRating(){
        return rating;
    }
}
