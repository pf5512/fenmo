package com.cn.fenmo.dao;

import java.util.HashMap;

import com.cn.fenmo.pojo.RoomBjImg;

public interface RoomBjImgMapper {

    int insert(RoomBjImg record);
    
    int update(RoomBjImg record);
    
    RoomBjImg selectBy(HashMap<String, Object> params);

}


