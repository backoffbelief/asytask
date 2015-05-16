package com.kael.mybatis.gen;

import java.io.Serializable;
import java.util.Date;

public class TaskRecord implements Serializable {
    private String id;

    private String type;

    private String subtype;

    private Integer timeoutmillis;

    private String state;

    private Date starttime;

    private Date finishtime;

    private Integer progress;

    private String msg;

    private String flag;

    private String inputdata;

    private String outputdata;

    private String props;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype == null ? null : subtype.trim();
    }

    public Integer getTimeoutmillis() {
        return timeoutmillis;
    }

    public void setTimeoutmillis(Integer timeoutmillis) {
        this.timeoutmillis = timeoutmillis;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getInputdata() {
        return inputdata;
    }

    public void setInputdata(String inputdata) {
        this.inputdata = inputdata == null ? null : inputdata.trim();
    }

    public String getOutputdata() {
        return outputdata;
    }

    public void setOutputdata(String outputdata) {
        this.outputdata = outputdata == null ? null : outputdata.trim();
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props == null ? null : props.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TaskRecord other = (TaskRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getSubtype() == null ? other.getSubtype() == null : this.getSubtype().equals(other.getSubtype()))
            && (this.getTimeoutmillis() == null ? other.getTimeoutmillis() == null : this.getTimeoutmillis().equals(other.getTimeoutmillis()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getStarttime() == null ? other.getStarttime() == null : this.getStarttime().equals(other.getStarttime()))
            && (this.getFinishtime() == null ? other.getFinishtime() == null : this.getFinishtime().equals(other.getFinishtime()))
            && (this.getProgress() == null ? other.getProgress() == null : this.getProgress().equals(other.getProgress()))
            && (this.getMsg() == null ? other.getMsg() == null : this.getMsg().equals(other.getMsg()))
            && (this.getFlag() == null ? other.getFlag() == null : this.getFlag().equals(other.getFlag()))
            && (this.getInputdata() == null ? other.getInputdata() == null : this.getInputdata().equals(other.getInputdata()))
            && (this.getOutputdata() == null ? other.getOutputdata() == null : this.getOutputdata().equals(other.getOutputdata()))
            && (this.getProps() == null ? other.getProps() == null : this.getProps().equals(other.getProps()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getSubtype() == null) ? 0 : getSubtype().hashCode());
        result = prime * result + ((getTimeoutmillis() == null) ? 0 : getTimeoutmillis().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getStarttime() == null) ? 0 : getStarttime().hashCode());
        result = prime * result + ((getFinishtime() == null) ? 0 : getFinishtime().hashCode());
        result = prime * result + ((getProgress() == null) ? 0 : getProgress().hashCode());
        result = prime * result + ((getMsg() == null) ? 0 : getMsg().hashCode());
        result = prime * result + ((getFlag() == null) ? 0 : getFlag().hashCode());
        result = prime * result + ((getInputdata() == null) ? 0 : getInputdata().hashCode());
        result = prime * result + ((getOutputdata() == null) ? 0 : getOutputdata().hashCode());
        result = prime * result + ((getProps() == null) ? 0 : getProps().hashCode());
        return result;
    }
}