package com.example.jaein.unitaxi;

/**
 * Created by jaein on 2017-05-29.
 */

public class member {
    private String member_id; // 사용자 id
    private String member_passwd;
    private String member_name;
    private String member_uni; // 대학교
    private int member_use; // 이용횟수
    private int member_warn; // 탈주횟수
    private int member_rel; // 신뢰도
    private String member_gender; // 성별
    public member(String member_id, String member_passwd,
                  String member_uni, String member_name, String member_gender,
                  int member_use, int member_warn, int member_rel){
        this.member_id = member_id;
        this.member_uni = member_uni;
        this.member_name = member_name;
        this.member_use = member_use;
        this.member_passwd = member_passwd;
        this.member_warn = member_warn;
        this.member_rel = member_rel;
        this.member_gender = member_gender;
    }

    public member(){
    }

    public String getMember_id(){
        return member_id;
    }
    public String getMember_uni(){
        return member_uni;
    }
    public String getMember_passwd() {return member_passwd;}
    public String getMember_name(){
        return member_name;
    }
    public int getMember_use(){
        return member_use;
    }
    public int getMember_warn(){
        return member_warn;
    }
    public int getMember_rel(){
        return member_rel;
    }
    public String getMember_gender(){
        return member_gender;
    }


    public void setMember_id(String member_id){
        this.member_id = member_id;
    }
    public void setMemeber_passwd(String member_passwd){this.member_passwd = member_passwd;}
    public void setMember_uni(String member_uni){
        this.member_uni = member_uni;
    }
    public void setMember_name(String member_name){
        this.member_name = member_name;
    }
    public void setMember_use(int use){
        this.member_use = use;
        setMember_rel(); // 신뢰도 갱신
    }
    public void setMember_warn(int warn){
        this.member_warn = warn;
        setMember_rel(); // 신뢰도 갱신
    }
    public void setMember_rel(){ // 신뢰도 계산
        if(member_use!=0)
            member_rel = ((member_use-member_warn)/member_use)*100;
    }
    public void setMember_gender(String member_gender){
        this.member_gender = member_gender;
    }
}
