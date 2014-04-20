package com.jskj.asset.client.bean.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author tt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaiGouShenQingFindEntity {
    
    private int count;
    
    private  List<Zichanshenpiliuchengtb> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Zichanshenpiliuchengtb> getResult() {
        return result;
    }

    public void setResult(List<Zichanshenpiliuchengtb> result) {
        this.result = result;
    }
    
}
