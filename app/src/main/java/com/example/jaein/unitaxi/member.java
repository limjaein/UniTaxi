package com.example.jaein.unitaxi;

/**
 * Created by jaein on 2017-05-29.
 */

public class member {
    private String m_id; // 사용자 id
    private String m_uni; // 대학교
    private int m_use; // 이용횟수
    private int m_warn; // 탈주횟수
    private int m_rel; // 신뢰도
    private String m_gender; // 성별

    public member(String member_id, String member_uni, String member_gender){
        this.m_id = member_id;
        this.m_uni = member_uni;
        this.m_use = 0;
        this.m_warn = 0;
        this.m_rel = 0;
        this.m_gender = member_gender;
    }

    public member(String member_id, String member_uni, int member_use,
                  int member_warn, int member_rel, String member_gender){
        this.m_id = member_id;
        this.m_uni = member_uni;
        this.m_use = member_use;
        this.m_warn = member_warn;
        this.m_rel = member_rel;
        this.m_gender = member_gender;
    }
    public member(){
        this.m_id = "";
        this.m_uni = "";
        this.m_use = 0;
        this.m_warn = 0;
        this.m_rel = 0;
        this.m_gender = "";
    }

    public String getMember_id(){
        return m_id;
    }
    public String getMember_uni(){
        return m_uni;
    }
    public int getMember_use(){
        return m_use;
    }
    public int getMember_warn(){
        return m_warn;
    }
    public int getMember_rel(){
        return m_rel;
    }
    public String getMember_gender(){
        return m_gender;
    }
    public void setMember_id(String member_id){
        this.m_id = member_id;
    }
    public void setMember_uni(String member_uni){
        this.m_uni = member_uni;
    }
    public void setMember_use(int use){
        this.m_use = use;
        setMember_rel(); // 신뢰도 갱신
    }
    public void setMember_warn(int warn){
        this.m_warn = warn;
        setMember_rel(); // 신뢰도 갱신
    }
    public void setMember_rel(){ // 신뢰도 계산
        if(m_use!=0)
            m_rel = ((m_use-m_warn)/m_use)*100;
    }
    public void setMember_gender(String member_gender){
        this.m_gender = member_gender;
    }
}
