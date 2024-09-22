package org.example.computerservice.entity;

public class LaptopFilter {
    private String manufacture;
    private int ssd_value;
    private String graph_type;
    private String cpu;
    private String os;
    private String matrix_type;
    private String resolution;
    private boolean touch_screen;
    private boolean key_light;

    public LaptopFilter() {
        super();
    }

    public LaptopFilter(String manufacture, String ssd_value, String graph_type, String cpu, String os, String matrix_type, String resolution, String touch_screen, String key_light) {
        this.manufacture = manufacture;
        this.ssd_value = (ssd_value.isEmpty()) ? 0 : Integer.parseInt(ssd_value);
        this.graph_type = graph_type;
        this.cpu = cpu;
        this.os = os;
        this.matrix_type = matrix_type;
        this.resolution = resolution;
        if (touch_screen != null){
            this.touch_screen = touch_screen.equals("on");
        } else {
            this.touch_screen = false;
        }
        if (key_light != null){
            this.key_light = key_light.equals("on");
        } else {
            this.key_light = false;
        }
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public void setSsd_value(String ssd_value) {
        this.ssd_value = (ssd_value.isEmpty()) ? 0 : Integer.parseInt(ssd_value);
    }

    public void setGraph_type(String graph_type) {
        this.graph_type = graph_type;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public void setMatrix_type(String matrix_type) {
        this.matrix_type = matrix_type;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setTouch_screen(String touch_screen) {
        this.touch_screen = touch_screen.equals("1");
    }

    public void setKey_light(String key_light) {
        this.key_light = key_light.equals("1");
    }

    public String getSQLFilter() {
        String result = "";
        if (!manufacture.isEmpty()) {
            result += "lower(manufacture) LIKE '%" + manufacture.toLowerCase() + "%'";
        }

        if (ssd_value > 0) {
            if (!result.isEmpty()) {
                result += " and ";
            }
            result += "ssd_value >= " + String.valueOf(ssd_value);
        }

        if (!graph_type.isEmpty()) {
            if (!result.isEmpty()) {
                result += " and ";
            }
            String graphType = (graph_type.equals("discret")) ? "Дискретная" : "Интегрированная";
            result += " graph_type = '" + graphType + "'";
        }

        if (!cpu.isEmpty()) {
            if (!result.isEmpty()) {
                result += " and ";
            }
            result += " lower(cpu) LIKE '%" + cpu.toLowerCase() + "%'";
        }

        if (!os.isEmpty()) {
            if (!result.isEmpty()) {
                result += " and ";
            }
            result += " lower(os) LIKE '%" + os.toLowerCase() + "%'";
        }

        if (!matrix_type.isEmpty()) {
            if (!result.isEmpty()) {
                result += " and ";
            }
            result += " lower(os) LIKE '%" + matrix_type.toLowerCase() + "%'";
        }

        if (!resolution.isEmpty()) {
            if (!result.isEmpty()) {
                result += " and ";
            }
            result += " resolution = '" + resolution + "'";
        }

        if (touch_screen) {
            if (!result.isEmpty()) {
                result += " and ";
            }
            result += " touch_screen = " + true;
        }

        if (key_light) {
            if (!result.isEmpty()) {
                result += " and ";
            }
            result += " key_light = " + true;
        }

        if (!result.isEmpty()){
            result = " WHERE " + result;
        }

        System.out.println(result);

        return result;
    }
}
