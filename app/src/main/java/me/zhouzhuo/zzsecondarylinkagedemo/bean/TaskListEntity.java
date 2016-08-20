package me.zhouzhuo.zzsecondarylinkagedemo.bean;

import java.util.List;

import me.zhouzhuo.zzsecondarylinkage.bean.BaseMenuBean;

/**
 * Created by zz on 2016/8/18.
 */
public class TaskListEntity {

    private String code;
    private String msg;
    private List<LeftMenuEntity> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<LeftMenuEntity> getData() {
        return data;
    }

    public void setData(List<LeftMenuEntity> data) {
        this.data = data;
    }

    //for left listView, java bean must extends BaseMenuBean
    public static class LeftMenuEntity extends BaseMenuBean {
        private String macId;
        private String macName;
        private String totalPage;

        private List<TaskListDataEntity> taskList;

        public String getMacId() {
            return macId;
        }

        public void setMacId(String macId) {
            this.macId = macId;
        }

        public String getMacName() {
            return macName;
        }

        public void setMacName(String macName) {
            this.macName = macName;
        }

        public String getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(String totalPage) {
            this.totalPage = totalPage;
        }

        public List<TaskListDataEntity> getTaskList() {
            return taskList;
        }

        public void setTaskList(List<TaskListDataEntity> taskList) {
            this.taskList = taskList;
        }

        public static class TaskListDataEntity {
            private String picUrl;
            private String MachineId;
            private String MachineName;
            private String taskId;
            private String taskNo;
            private String productName;
            private String startTime;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getMachineId() {
                return MachineId;
            }

            public void setMachineId(String MachineId) {
                this.MachineId = MachineId;
            }

            public String getMachineName() {
                return MachineName;
            }

            public void setMachineName(String MachineName) {
                this.MachineName = MachineName;
            }

            public String getTaskId() {
                return taskId;
            }

            public void setTaskId(String taskId) {
                this.taskId = taskId;
            }

            public String getTaskNo() {
                return taskNo;
            }

            public void setTaskNo(String taskNo) {
                this.taskNo = taskNo;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }
        }
    }
}
