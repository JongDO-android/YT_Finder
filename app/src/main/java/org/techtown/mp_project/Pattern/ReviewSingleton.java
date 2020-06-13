package org.techtown.mp_project.Pattern;

public class ReviewSingleton {
    private Long reviewNumber;
    private static ReviewSingleton ourInstance;

    public static ReviewSingleton getInstance() {
        if(ourInstance == null){
            ourInstance = new ReviewSingleton();
        }
        return ourInstance;
    }
    public void addReviewNumber(){
        reviewNumber ++;
    }
    public void setReviewNumber(Long reviewNumber){
        this.reviewNumber = reviewNumber;
    }
    public Long getReviewNumber(){
        return reviewNumber;
    }

}
