package com.cn.fenmo.pojo;

import java.io.Serializable;
import java.util.Date;

import com.cn.fenmo.util.DateUtil;
import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

public class UserBean implements Serializable {
    private Long mainid;
    
    private String fmNo;
    
    private String headImgPath;

    private String username;

    private String password;

    private String imgUrls;

    private String specificsign;

    private Integer age;

    private Integer sex;

    private long phone;

    private String constellation;

    private String hxid;

    private Integer circle;

    private String idol;
    
    private String nickname;
    
    private String token;
    
    private String nationality;
    
    private String occupation;
    
    private String birthday;
    
    private String registerTime;
    
    private int starLevel;
    
    private double lat;//γ��
    
    private double lng;//γ��
    
    private double distance;
    
    private static final long serialVersionUID = 1L;
    
    public int getStarLevel() {
      return starLevel;
    }

    public void setStarLevel(int starLevel) {
      this.starLevel = starLevel;
    }

    public String getRegisterTime() {
      return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
      this.registerTime =  DateUtil.getDateSecondDashFormat(registerTime);
    }

    public String getHeadImgPath() {
      return headImgPath==null?"":headImgPath;
    }

    public void setHeadImgPath(String headImgPath) {
      this.headImgPath = headImgPath!=null?headImgPath:"";
    }

    public String getFmNo() {
      return fmNo;
    }

    public void setFmNo(String fmNo) {
      this.fmNo = fmNo;
    }

    public String getNickname() {
      return nickname==null?username:nickname;
    }

    public void setNickname(String nickname) {
      this.nickname = nickname;
    }

    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
    }

    public Long getMainid() {
        return mainid;
    }

    public void setMainid(Long mainid) {
        this.mainid = mainid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getImgUrls() {
      return imgUrls==null?"":imgUrls;
    }

    public void setImgUrls(String imgUrls) {
      this.imgUrls = imgUrls;
    }

    public String getSpecificsign() {
        return specificsign;
    }

    public void setSpecificsign(String specificsign) {
        this.specificsign = specificsign == null ? null : specificsign.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
    public String getConstellation() {
      return constellation;
    }

    public void setConstellation(String constellation) {
      this.constellation = constellation;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid == null ? null : hxid.trim();
    }

    public Integer getCircle() {
        return circle;
    }

    public void setCircle(Integer circle) {
        this.circle = circle;
    }

    public String getIdol() {
        return idol;
    }

    public void setIdol(String idol) {
        this.idol = idol == null ? null : idol.trim();
    }

    public String getNationality() {
      return nationality;
    }

    public void setNationality(String nationality) {
      this.nationality = nationality;
    }

    public String getOccupation() {
      return occupation;
    }

    public void setOccupation(String occupation) {
      this.occupation = occupation;
    }

    public String  getBirthday() {
      return birthday;
    }

    public void setBirthday(Date birthday) {
      this.birthday =  DateUtil.getDateDayDashFormat(birthday);
    }

    public double getLat() {
      return lat;
    }

    public void setLat(double lat) {
      this.lat = lat;
    }

    public double getLng() {
      return lng;
    }

    public void setLng(double lng) {
      this.lng = lng;
    }

    public double getDistance() {
      return distance;
    }

    public void setDistance(double distance) {
      this.distance = distance;
    }
    @Override
    public String toString(){
      return "userName:"+this.getUsername()+";age:"+this.getAge()+";sex:"+this.getSex()+";headImgPath:"+this.getHeadImgPath();
    }
    
    
}