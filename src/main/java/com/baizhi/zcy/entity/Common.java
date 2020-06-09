package com.baizhi.zcy.entity;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Common {

    private String sex;
    private List<City> citys;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<City> getCitys() {
        return citys;
    }

    public void setCitys(List<City> citys) {
        this.citys = citys;
    }

    public Common(String sex, List<City> citys) {
        this.sex = sex;
        this.citys = citys;
    }
}
