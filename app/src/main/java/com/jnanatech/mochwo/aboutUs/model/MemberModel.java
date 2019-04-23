package com.jnanatech.mochwo.aboutUs.model;

public class MemberModel {

    String memberName;
    String memeberPosition;
    String memeberImage;


    public MemberModel(String memberName, String memeberPosition, String memeberImage) {
        this.memberName = memberName;
        this.memeberPosition = memeberPosition;
        this.memeberImage = memeberImage;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemeberPosition() {
        return memeberPosition;
    }

    public void setMemeberPosition(String memeberPosition) {
        this.memeberPosition = memeberPosition;
    }

    public String getMemeberImage() {
        return memeberImage;
    }

    public void setMemeberImage(String memeberImage) {
        this.memeberImage = memeberImage;
    }
}
